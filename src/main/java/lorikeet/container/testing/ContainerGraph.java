package lorikeet.container.testing;

public class ContainerGraph {

    private final ContainerGraphNode rootNode;

    public ContainerGraph(ContainerGraphNode root) {
        this.rootNode = root;
    }

    public ContainerGraphNode getRootNode() {
        return this.rootNode;
    }
}
