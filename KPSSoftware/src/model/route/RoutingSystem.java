package model.route;

import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;

import java.util.*;

/**
 * This class manages all routes in this programme. In addition, this class maintains a graph system internally to
 * perform path-finding tasks.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class RoutingSystem {

    /**
     * All routes in the system. They are maintained as a HashMap, where the key is the id of route, and the value is
     * the route itself.
     */
    private Map<Integer, Route> routes;

    /**
     * All locations in the system. They are maintained as a HashMap, where the key is the id of location, and the value
     * is the location itself.
     */
    private Map<Integer, Location> locations;

    /**
     * All nodes in the graph, where each node represents a location. They are maintained as a HashMap, where the key is
     * the id of the location, and the value is the GraphNode.
     */
    private Map<Integer, GraphNode> graphNodes;

    /**
     * All edges in the graph, where each edge represents a route. They are maintained as a HashMap, where the key is
     * the id of the route, and the value is the GraphEdge.
     */
    private Map<Integer, GraphEdge> graphEdges;

    /**
     * All recorded origins in the system.
     */
    private Set<NZLocation> origins;

    /**
     * All recorded destinations in the system.
     */
    private Set<Location> destinations;

    /**
     * Constructor. This constructor accepts a set of routes, and construct the graph system from those routes.
     *
     * @param routes the routes that compose the graph
     */
    public RoutingSystem(Set<Route> routes) {
        this.routes = new HashMap<>();
        this.locations = new HashMap<>();
        this.graphNodes = new HashMap<>();
        this.graphEdges = new HashMap<>();

        routes.forEach(this::addRoute);
    }

    /**
     * Constructor. There is no graph built after constructing. Use it when you want to add routes later.
     */
    public RoutingSystem() {
        this.routes = new HashMap<>();
        this.locations = new HashMap<>();
        this.graphNodes = new HashMap<>();
        this.graphEdges = new HashMap<>();
    }

    /**
     * Finds a series of continuous routes that can be used for the given mail. If no continuous routes can be found for
     * the given mail, this method will return null.
     *
     * @param mail the mail that we want to find the routes for
     * @return an ordered list of routes,
     */
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

    public boolean addRoute(Route route) {
        // 1. add route into this.routes
        if (this.routes.containsKey(route.id)) {
            return false;
        }

        this.routes.put(route.id, route);

        // 2. add locations in this.locations
        Location start = route.getStartLocation();
        Location end = route.getEndLocation();

        this.locations.put(start.id, start);
        this.locations.put(end.id, end);

        // 3. for each location, find the GraphNode; if there is none, create a new one.
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

        // 4. for the route, create a GraphEdge
        GraphEdge edge = new GraphEdge(route, startNode, endNode);
        graphEdges.put(route.id, edge);

        // 5. if this route starts from a NZ location, we can use it as one of the origins in the system
        if (route.getStartLocation() instanceof NZLocation) {
            NZLocation origin = (NZLocation) route.getStartLocation();
            this.origins.add(origin);
        }

        // 6. add the end of this route to this.destinations.
        this.destinations.add(route.getEndLocation());

        // 7. update Dijkstra graph, make sure the edge and two nodes are connected to correct neighbours
        return startNode.addOutgoingEdge(edge);
    }

    /**
     * Delete the route with the given id.
     *
     * @param id the id of the route that needs to be deleted
     * @return true if the deletion is successful, or false if failed.
     */
    public boolean deleteRouteById(int id) {
        if (!this.routes.containsKey(id)) {
            return false;
        }

        routes.remove(id);
        GraphEdge deletedEdge = graphEdges.remove(id);
        GraphNode startNode = deletedEdge.getStart();

        return startNode.removeOutgoingEdge(deletedEdge);
    }

    /**
     * Update the prices (price per gram & price per volume) of the route with the given id.
     *
     * @param id                the id of the route that needs to be updated
     * @param newPricePerGram   the new price per gram
     * @param newPricePerVolume the new price per volume
     */
    public void updateRoutePriceById(int id, float newPricePerGram, float newPricePerVolume) {
        Route route = findRouteById(id);

        route.setPricePerGram(newPricePerGram);
        route.setPricePerVolume(newPricePerVolume);
    }

    /**
     * Update the costs (cost per gram & cost per volume) of the route with the given id.
     *
     * @param id               the id of the route that needs to be updated
     * @param newCostPerGram   the new cost per gram
     * @param newCostPerVolume the new cost per volume
     */
    public void updateRouteCostById(int id, float newCostPerGram, float newCostPerVolume) {
        Route route = findRouteById(id);

        route.setCostPerGram(newCostPerGram);
        route.setCostPerVolume(newCostPerVolume);
    }

    /**
     * @param id the id of the route that needs to be found
     * @return the route with the given id
     */
    public Route findRouteById(int id) {
        return routes.get(id);
    }

    /**
     * @return the set of all available origins in the system
     */
    public Set<NZLocation> getOrigins() {
        return this.origins;
    }

    /**
     * @return the set of all available destinations in the system
     */
    public Set<Location> getDestinations() {
        return this.destinations;
    }

    /**
     * Use Dijkstra algorithm to find a path from the given start node to the given end node. If no continuous routes
     * can be found, this method will return null.
     *
     * @param start
     * @param end
     * @param priority
     * @param weight
     * @param volume
     * @return
     */
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

                // filter out those who don't suit the priority.
                if (!isOKForPriority(edge, priority)) {
                    continue;
                }

                GraphNode endNode = edge.getEnd();

                // filter out those visited
                if (visitedNodes.contains(endNode.getLocationId())) {
                    continue;
                }

                // a flag to mark if this neighbor is updated or added directly
                boolean isInFringe = false;
                DijkstraNode neighbour = new DijkstraNode(endNode, polledNode, edge);

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
     * Can we use this edge for the given priority?
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

    /**
     * Update the profit so far on Dijkstra node, and update the path from root.
     *
     * @param neighbour
     * @param nodeInFringe
     * @param newEdgeToParent
     * @param weight
     * @param volume
     */
    private void compareAndUpdate(DijkstraNode neighbour, DijkstraNode nodeInFringe, GraphEdge newEdgeToParent, float weight, float volume) {
        if (neighbour.getProfitFromStart() < nodeInFringe.getProfitFromStart()) {
            nodeInFringe.setProfitFromStart(neighbour.getProfitFromStart());
            nodeInFringe.setEdgeToParent(newEdgeToParent);
            nodeInFringe.updateProfitFromStart(weight, volume);
        }
    }
}
