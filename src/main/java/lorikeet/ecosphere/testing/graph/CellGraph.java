package lorikeet.ecosphere.testing.graph;

public class CellGraph {

    private final CellGraphNode rootNode;

    public CellGraph(CellGraphNode rootNode) {
        this.rootNode = rootNode;
    }

    public CellGraphNode getRootNode() {
        return this.rootNode;
    }
}
