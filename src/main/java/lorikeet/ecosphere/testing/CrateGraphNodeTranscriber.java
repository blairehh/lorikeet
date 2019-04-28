package lorikeet.ecosphere.testing;

import lorikeet.data.DataSerializer;

import java.util.Comparator;
import java.util.List;

public class CrateGraphNodeTranscriber {

    private final DataSerializer serializer;

    public CrateGraphNodeTranscriber(DataSerializer serializer) {
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
        transcript.append(" ");
        this.transcribeResponse(transcript, node);
        transcript.append(">");
    }

    private void transcribeResponse(StringBuilder transcript, CrateGraphNode node) {
        if (node.getExceptionThrown() != null) {
            transcript.append("-exception=");
            transcript.append(node.getExceptionThrown().getClass().getName());
            return;
        }
        transcript.append("-return=");
        transcript.append(this.serializer.serialize(node.getReturnValue(), String.class));
    }

    private String buildParameters(List<CrateParameter> parameters) {
        StringBuilder builder = new StringBuilder();
        parameters.stream()
            .forEach(parameter -> transcribeParameter(builder, parameter));

        return builder.toString();
    }

    private void transcribeParameter(StringBuilder transcript, CrateParameter parameter) {

        if (parameter.getMeta().isIgnore()) {
            return;
        }
        transcript.append(" ");
        transcript.append(parameter.getMeta().getName());
        transcript.append("=");
        if (parameter.getValue() == null) {
            transcript.append(DataSerializer.NULL_VALUE);
            return;
        }
        if (parameter.getMeta().isUseHash()) {
            transcript.append(parameter.getValue().getClass().getName());
            transcript.append("#");
            transcript.append(parameter.getValue().hashCode());
        } else {
            transcript.append(this.serializer.serialize(parameter.getValue(), String.class));
        }

    }
}
