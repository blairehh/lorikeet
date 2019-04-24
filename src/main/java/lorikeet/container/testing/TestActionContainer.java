package lorikeet.container.testing;

import lorikeet.Dict;
import lorikeet.Seq;
import lorikeet.container.ActionContainer;
import lorikeet.container.Edict1;
import lorikeet.container.Edict2;
import lorikeet.container.Edict3;
import lorikeet.container.Edict4;
import lorikeet.container.Edict5;

import java.time.Instant;
import java.util.ArrayList;
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
        this.prepareGraphNode(edict.getClass());
        return edict.invoke(parameter);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Edict2<ReturnType, ParameterType1, ParameterType2> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        this.prepareGraphNode(edict.getClass());
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
        this.prepareGraphNode(edict.getClass());
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
        this.prepareGraphNode(edict.getClass());
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
        this.prepareGraphNode(edict.getClass());
        edict.inject(this);
        return edict.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
    }

    private void prepareGraphNode(Class<?> containerClass) {
        ContainerGraphNode thisNode = new ContainerGraphNode(
            containerClass.getSimpleName(),
            Dict.empty(),
            Dict.empty(),
            Dict.empty(),
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

    public ContainerGraph getGraph() {
        return new ContainerGraph(this.rootGraphNode);
    }
}
