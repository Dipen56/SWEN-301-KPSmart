package model.route;

import model.location.Location;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a node in the graph data structure.
 *
 * @author Hector
 * @version 2017/5/29
 */
public class GraphNode {

    private final Location location;
    private final Set<Integer> outgoingEdgeIds;

    /**
     * Constructor.
     */
    public GraphNode(Location location) {
        this.location = location;
        outgoingEdgeIds = new HashSet<>();
    }

    public int getLocationId() {
        return this.location.id;
    }

    public Set<Integer> getOutgoingEdgeIds() {
        return this.outgoingEdgeIds;
    }

    public boolean addOutgoingEdge(GraphEdge edge) {
        return this.getLocationId() == edge.getStart().getLocationId() && this.outgoingEdgeIds.add(edge.getRoute().id);
    }

    public boolean removeOutgoingEdge(GraphEdge edge) {
        int id = edge.getRoute().id;
        return this.outgoingEdgeIds.contains(id) && this.outgoingEdgeIds.remove(id);
    }
}
