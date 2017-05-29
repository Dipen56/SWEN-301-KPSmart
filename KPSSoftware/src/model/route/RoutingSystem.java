package model.route;

import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;

import java.util.*;

/**
 * This class manages all routes in this programme. In addition, this class maintains a graph system internally to
 * perform path-finding jobs.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class RoutingSystem {

    private Map<Integer, Route> routes;
    private Map<Integer, Location> locations;

    private Map<Integer, GraphNode> graphNodes;
    private Map<Integer, GraphEdge> graphEdges;

    public RoutingSystem(Set<Route> routes) {
        this.routes = new HashMap<>();
        this.locations = new HashMap<>();
        this.graphNodes = new HashMap<>();
        this.graphEdges = new HashMap<>();

        routes.forEach(this::addRoute);
    }

    public RoutingSystem() {
        this.routes = new HashMap<>();
        this.locations = new HashMap<>();
        this.graphNodes = new HashMap<>();
        this.graphEdges = new HashMap<>();
    }

    public List<Route> findRoutes(Mail mail) {
        NZLocation origin = mail.getOrigin();
        Location destination = mail.getDestination();
        Priority priority = mail.getPriority();
        float weight = mail.getWeight();
        float volume = mail.getVolume();

        if ((destination instanceof InternationalLocation && !priority.isInternational())
                || (destination instanceof NZLocation && priority.isInternational())) {
            // domestic mail should not have international priority, and vice versa.
            return null;
        }

        GraphNode start = graphNodes.get(origin.id);
        GraphNode end = graphNodes.get(destination.id);

        return findPath(start, end, priority, weight, volume);
    }

    public List<NZLocation> getAvailableOriginLocations() {
        // TODO

        return new ArrayList<>();
    }

    public boolean addRoute(Route route) {
        // add route into this.routes
        if (this.routes.containsKey(route.id)) {
            return false;
        }

        this.routes.put(route.id, route);

        // add locations in this.locations
        Location start = route.getStartLocation();
        Location end = route.getEndLocation();

        this.locations.put(start.id, start);
        this.locations.put(end.id, end);

        // for each location, find the GraphNode; if there is none, create a new one.
        GraphNode startNode = graphNodes.get(start.id);
        if (startNode == null) {
            startNode = new GraphNode(start);
            graphNodes.put(startNode.getLocationId(), startNode);
        }

        GraphNode endNode = graphNodes.get(end.id);
        if (endNode == null) {
            endNode = new GraphNode(end);
            graphNodes.put(endNode.getLocationId(), endNode);
        }

        // for the route, create a GraphEdge
        GraphEdge edge = new GraphEdge(route, startNode, endNode);
        graphEdges.put(route.id, edge);

        // update Dijkstra graph, make sure the edge and two nodes are connected to correct neighbours
        return startNode.addOutgoingEdge(edge);
    }

    public boolean deleteRouteById(int id) {
        if (!this.routes.containsKey(id)) {
            return false;
        }

        routes.remove(id);
        GraphEdge deletedEdge = graphEdges.remove(id);
        GraphNode startNode = deletedEdge.getStart();

        return startNode.removeOutgoingEdge(deletedEdge);
    }

    public void updateRoutePriceById(int id, float newPricePerGram, float newPricePerVolume) {
        Route route = getRouteById(id);

        route.setPricePerGram(newPricePerGram);
        route.setPricePerVolume(newPricePerVolume);
    }

    public void updateRouteCostById(int id, float newCostPerGram, float newCostPerVolume) {
        Route route = getRouteById(id);

        route.setCostPerGram(newCostPerGram);
        route.setCostPerVolume(newCostPerVolume);
    }

    public Route getRouteById(int id) {
        return routes.get(id);
    }

    private List<Route> findPath(GraphNode start, GraphNode end, Priority priority, float weight, float volume) {
        // a collection storing all GraphNode that are visited
        Set<Integer> visitedNodes = new HashSet<>();

        // a priority queue which is necessary for the path-finding algorithm
        PriorityQueue<DijkstraNode> fringe = new PriorityQueue<>();

        // enqueue the start GraphNode
        fringe.offer(new DijkstraNode(start, null, null));

        while (!fringe.isEmpty()) {
            DijkstraNode polledNode = fringe.poll();
            // mark it as visited by adding it into visited set.
            visitedNodes.add(polledNode.getGraphNode().getLocationId());

            // if the end GraphNode is dequeued, the path is found
            if (polledNode.getGraphNode().getLocationId() == end.getLocationId()) {
                List<Route> path = new ArrayList<>();
                DijkstraNode backTracer = polledNode;
                // back trace it from end to start.
                while (backTracer.getCameFromNode() != null) {
                    path.add(backTracer.getEdgeToParent().getRoute());
                    backTracer = backTracer.getCameFromNode();
                }
                return path;
            }

            for (int edgeId : polledNode.getGraphNode().getOutgoingEdgeIds()) {
                GraphEdge edge = graphEdges.get(edgeId);

                if (!isOKForPriority(edge, priority)) {
                    continue;  // filter out those who don't suit the priority.
                }

                GraphNode endNode = edge.getEnd();

                if (visitedNodes.contains(endNode.getLocationId())) {
                    continue;  // filter out those visited
                }

                DijkstraNode neighbour = new DijkstraNode(endNode, polledNode, edge);
                // a flag to mark if this neighbor is updated or added directly
                boolean isInFringe = false;
                for (DijkstraNode dijkstraNode : fringe) {
                    if (dijkstraNode.getGraphNode().getLocationId() == endNode.getLocationId()) {
                        // if already in fringe, see if its cost needs to be updated
                        compareAndUpdate(neighbour, dijkstraNode, edge, weight, volume);
                        isInFringe = true;
                        break;
                    }
                }

                // if not in fringe, add it in.
                if (!isInFringe) {
                    fringe.offer(neighbour);
                }
            }
        }

        // if cannot find a path, return null
        return null;
    }

    /**
     * Can this edge be used given this priority?
     *
     * @param edge
     * @param priority
     * @return
     */
    private boolean isOKForPriority(GraphEdge edge, Priority priority) {
        Route route = edge.getRoute();

        if (Priority.International_Air.equals(priority)) {
            return !route.isInternationalRoute() || RouteType.Air.equals(route.routeType);
        } else if (Priority.Domestic_Air.equals(priority)) {
            return !route.isInternationalRoute() && RouteType.Air.equals(route.routeType);
        }

        return true;
    }

    private void compareAndUpdate(DijkstraNode neighbour, DijkstraNode nodeInFringe, GraphEdge newEdgeToParent, float weight, float volume) {
        if (neighbour.getProfitFromStart() < nodeInFringe.getProfitFromStart()) {
            nodeInFringe.setProfitFromStart(neighbour.getProfitFromStart());
            nodeInFringe.setEdgeToParent(newEdgeToParent);
            nodeInFringe.updateProfitFromStart(weight, volume);
        }
    }
}
