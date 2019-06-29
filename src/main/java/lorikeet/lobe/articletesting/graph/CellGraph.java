package lorikeet.lobe.articletesting.graph;

public class CellGraph {

    private final CellGraphNode rootNode;

    public CellGraph(CellGraphNode rootNode) {
        this.rootNode = rootNode;
    }

    public CellGraphNode getRootNode() {
        return this.rootNode;
    }
}
