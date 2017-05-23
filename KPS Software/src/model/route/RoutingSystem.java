package model.route;

import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZLocation;
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

    private int currentRouteId = -1;
    private Map<Integer, Route> routes;

    private Set<GraphNode> graphNodes;
    private Set<GraphEdge> graphEdges;

    public RoutingSystem(Set<Route> routes) {
        this.routes = new HashMap<>();
        currentRouteId = findCurrentRouteId();

        routes.forEach(this::addRoute);
    }

    public RoutingSystem() {
        routes = new HashMap<>();
        currentRouteId = findCurrentRouteId();
    }


    public List<Route> findRoutes(NZLocation origin, Location destination, Priority priority) {
        if ((destination instanceof InternationalLocation && !priority.isInternational())
                || (destination instanceof NZLocation && priority.isInternational())) {
            // domestic mail should not have international priority, and vice versa.
            return null;
        }

        // TODO: use AStar to find the routes

        return new ArrayList<>();
    }

    public List<NZLocation> getAvailableOriginLocations() {
        // TODO

        return new ArrayList<>();
    }

    public void addRoute(Route route) {
        routes.put(currentRouteId, route);

        // TODO: update the AStar graph

        currentRouteId++;
    }

    public void deleteRouteById(int id) {
        routes.remove(id);

        // TODO: update the AStar graph
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

    private int findCurrentRouteId() {
        // TODO: should read the XML file and find the last ID
        return currentRouteId < 0 ? 0 : currentRouteId;
    }


    // ===============================================
    // inner classes for AStar search
    // ===============================================

    private class GraphNode {

    }

    private class GraphEdge {

    }


    private class AStarNode {

    }
}
