package lorikeet.container.testing;

import lorikeet.Dict;
import lorikeet.Seq;
import lorikeet.container.ActionContainer;
import lorikeet.container.Container;
import lorikeet.container.Edict1;
import lorikeet.container.Edict2;
import lorikeet.container.Edict3;
import lorikeet.container.Edict4;
import lorikeet.container.Edict5;
import lorikeet.container.Meta;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestActionContainer implements ActionContainer {

    private final String cid;
    private ContainerGraphNode currentGraphNode;
    private ContainerGraphNode rootGraphNode;

    public TestActionContainer() {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public String getCid() {
        return this.cid;
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Edict1<ReturnType, ParameterType> edict, ParameterType parameter) {
        this.prepareGraphNode(edict, parameter);
        return edict.invoke(parameter);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Edict2<ReturnType, ParameterType1, ParameterType2> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        this.prepareGraphNode(edict, parameter1, parameter2);
        edict.inject(this);
        return edict.invoke(parameter1, parameter2);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Edict3<ReturnType, ParameterType1, ParameterType2, ParameterType3> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    ) {
        this.prepareGraphNode(edict, parameter1, parameter2, parameter3);
        edict.inject(this);
        return edict.invoke(parameter1, parameter2, parameter3);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> ReturnType yield(
        Edict4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4
    ) {
        this.prepareGraphNode(edict, parameter1, parameter2, parameter3, parameter4);
        edict.inject(this);
        return edict.invoke(parameter1, parameter2, parameter3, parameter4);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> ReturnType yield(
        Edict5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    ) {
        this.prepareGraphNode(edict, parameter1, parameter2, parameter3, parameter4, parameter5);
        edict.inject(this);
        return edict.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
    }

    private void prepareGraphNode(Container container, Object... params) {
        List<ContainerParameter> parameters = new ArrayList<>();

        this.populateParameters(container.getMeta(), Arrays.asList(params), parameters);

        ContainerGraphNode thisNode = new ContainerGraphNode(
            container.getClass().getSimpleName(),
            parameters,
            Instant.now(),
            new ArrayList<>()
        );
        if (this.currentGraphNode == null) {
            this.currentGraphNode = thisNode;
            this.rootGraphNode = this.currentGraphNode;
        } else {
            this.currentGraphNode.getChildren().add(thisNode);
            this.currentGraphNode = thisNode;
        }
    }

    private void populateParameters(Meta meta, List<Object> params, List<ContainerParameter> parameters) {
        for (int i = 0; i < params.size(); i++) {
            final String parameterName = meta.getParameters().fetch(i)
                .filter(name -> name != null && !name.trim().isEmpty())
                .orElse(null);
            if (parameterName == null) {
                continue;
            }

            final Object value = params.get(i);
            parameters.add(new ContainerParameter(determineRenderType(value), parameterName, value == null ? "" : value.toString()));
        }
    }

    private RenderType determineRenderType(Object object) {
        if (object == null) {
            return RenderType.NULL;
        }

        if (object instanceof String) {
            return RenderType.STRING;
        }

        if (object instanceof Number) {
            return RenderType.NUMBER;
        }

        if (object instanceof Boolean) {
            return RenderType.BOOLEAN;
        }

        return RenderType.OBJECT;
    }

    public ContainerGraph getGraph() {
        return new ContainerGraph(this.rootGraphNode);
    }
}
