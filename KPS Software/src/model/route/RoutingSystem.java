package model.route;

import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZLocation;
import model.mail.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class is essentially a graph containing all locations and routes. It also offers some utility methods,
 * e.g. finding paths.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class RoutingSystem {

    private Set<GraphNode> graphNodes;
    private Set<GraphEdge> graphEdges;


    public RoutingSystem(Set<Route> routes) {

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

    private class GraphNode {

    }

    private class GraphEdge {

    }


    private class AStarNode {


    }
}
