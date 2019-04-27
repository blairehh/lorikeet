package lorikeet.ecosphere.testing;

import java.util.Comparator;
import java.util.List;

public class CrateGraphNodeTranscriber {

    public String transcribe(CrateGraphNode root) {
        StringBuilder transcript = new StringBuilder();
        transcribe(transcript, 0, root);
        return transcript.toString();
    }

    private void transcribe(StringBuilder transcript, int indentation, CrateGraphNode node) {
        final int childIndentation = indentation + 1;
        this.transcribeNode(transcript, indentation, node);
        transcript.append('\n');
        node.getChildren()
            .stream()
            .sorted(Comparator.comparing(CrateGraphNode::getTimestamp))
            .forEach(child -> this.transcribe(transcript, childIndentation, child));
    }

    private void transcribeNode(StringBuilder transcript, int indentation, CrateGraphNode node) {
        transcript.append("\t".repeat(indentation));
        transcript.append("<");
        transcript.append(node.getName());
        transcript.append(buildParameters(node.getParameters()));
        transcript.append(">");
    }

    private String buildParameters(List<CrateParameter> parameters) {
        StringBuilder builder = new StringBuilder();
        parameters.stream()
            .sorted(Comparator.comparing(CrateParameter::getName))
            .forEach(parameter -> {
                builder.append(stringy(parameter));
            });

        return builder.toString();
    }

    private String stringy(CrateParameter parameter) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        builder.append(parameter.getName());
        builder.append("=");

        switch (parameter.getRenderType()) {
            case NULL: builder.append("null"); break;
            case STRING: builder.append(String.format("\"%s\"", parameter.getValue())); break;
            case BOOLEAN:
            case OBJECT:
            case NUMBER: builder.append(parameter.getValue()); break;
        }

        return builder.toString();
    }
}
