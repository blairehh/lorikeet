package lorikeet.ecosphere.testing;

public class CrateGraph {

    private final CrateGraphNode rootNode;

    public CrateGraph(CrateGraphNode root) {
        this.rootNode = root;
    }

    public CrateGraphNode getRootNode() {
        return this.rootNode;
    }
}
