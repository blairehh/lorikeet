package lorikeet.ecosphere.testing;

import java.util.Comparator;
import java.util.List;

public class CrateGraphNodeTranscriber {

    private final ParameterSerializer serializer;

    public CrateGraphNodeTranscriber(ParameterSerializer serializer) {
        this.serializer = serializer;
    }


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
                builder.append(" ");
                builder.append(parameter.getName());
                builder.append("=");
                stringify(builder, parameter);
            });

        return builder.toString();
    }

    private void stringify(StringBuilder builder, CrateParameter parameter) {
        // @TODO get the correct context
        builder.append(this.serializer.serialize(parameter.getValue(), String.class));
    }
}
