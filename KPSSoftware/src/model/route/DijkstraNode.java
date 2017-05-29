package model.route;

/**
 * This class represents a node used in path finding with dijkstra algorithm
 *
 * @author Hector
 * @version 2017/5/29
 */
public class DijkstraNode implements Comparable<DijkstraNode> {

    private GraphNode graphNode;
    private DijkstraNode cameFrom;
    private GraphEdge edgeToParent;
    private double profitFromStart = 0;

    public DijkstraNode(GraphNode selfNode, DijkstraNode fromNode, GraphEdge edgeToParent) {
        this.graphNode = selfNode;
        this.cameFrom = fromNode;
        this.edgeToParent = edgeToParent;
    }

    public void updateProfitFromStart(float weight, float volume) {
        if (cameFrom == null) {
            this.profitFromStart = 0;
        } else {
            this.profitFromStart = this.edgeToParent.getRouteProfit(weight, volume) + this.cameFrom.getProfitFromStart();
        }
    }

    public GraphNode getGraphNode() {
        return this.graphNode;
    }

    public DijkstraNode getCameFromNode() {
        return this.cameFrom;
    }

    public GraphEdge getEdgeToParent() {
        return this.edgeToParent;
    }

    public void setEdgeToParent(GraphEdge edgeToParent) {
        this.edgeToParent = edgeToParent;
    }

    public double getProfitFromStart() {
        return this.profitFromStart;
    }

    public void setProfitFromStart(double newCostFromStart) {
        this.profitFromStart = newCostFromStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DijkstraNode))
            return false;

        DijkstraNode that = (DijkstraNode) o;

        return Double.compare(that.profitFromStart, profitFromStart) == 0
                && graphNode.equals(that.graphNode)
                && (cameFrom != null ? cameFrom.equals(that.cameFrom) : that.cameFrom == null)
                && (edgeToParent != null ? edgeToParent.equals(that.edgeToParent) : that.edgeToParent == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = graphNode.hashCode();
        result = 31 * result + (cameFrom != null ? cameFrom.hashCode() : 0);
        result = 31 * result + (edgeToParent != null ? edgeToParent.hashCode() : 0);
        temp = Double.doubleToLongBits(profitFromStart);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(DijkstraNode other) {
        if (this.profitFromStart < other.profitFromStart) {
            return 1;
        } else if (this.profitFromStart > other.profitFromStart) {
            return -1;
        } else {
            return 0;
        }
    }
}
