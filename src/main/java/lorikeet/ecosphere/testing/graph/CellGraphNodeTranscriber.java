package lorikeet.ecosphere.testing.graph;


import lorikeet.ecosphere.testing.graph.CellGraphNode;

import java.util.Comparator;

public class CellGraphNodeTranscriber {

    public String transcribe(CellGraphNode root) {
        StringBuilder transcript = new StringBuilder();
        transcribe(transcript, 0, root);
        return transcript.toString();
    }

    private void transcribe(StringBuilder transcript, int indentation, CellGraphNode node) {
        final int childIndentation = indentation + 1;
        this.transcribeNode(transcript, indentation, node);
        transcript.append('\n');
        node.getChildren()
            .stream()
            .sorted(Comparator.comparing(CellGraphNode::getTimestamp))
            .forEach(child -> this.transcribe(transcript, childIndentation, child));
    }

    private void transcribeNode(StringBuilder transcript, int indentation, CellGraphNode node) {
        transcript.append("\t".repeat(indentation));
        // @TODO should never fail here but what to do if so
        transcript.append(node.getCell().toString());
    }
}
