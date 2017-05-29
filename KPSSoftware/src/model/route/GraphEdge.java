package model.route;

/**
 * This class represents an edge between two nodes in the graph data structure.
 *
 * @author Hector
 * @version 2017/5/29
 */
public class GraphEdge {

    private final Route route;
    private GraphNode start, end;

    /**
     * Constructor
     */
    public GraphEdge(Route route, GraphNode start, GraphNode end) {
        this.route = route;
        this.start = start;
        this.end = end;
    }

    public GraphNode getStart() {
        return start;
    }

    public GraphNode getEnd() {
        return end;
    }

    public Route getRoute() {
        return this.route;
    }

    public double getRouteProfit(float weight, float volume) {
        return this.route.getNetProfit(weight, volume);
    }
}
