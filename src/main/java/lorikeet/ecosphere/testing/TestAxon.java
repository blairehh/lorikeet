package lorikeet.ecosphere.testing;


import lorikeet.ecosphere.Axon;
import lorikeet.ecosphere.Crate;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import lorikeet.ecosphere.Action3;
import lorikeet.ecosphere.Action4;
import lorikeet.ecosphere.Action5;
import lorikeet.ecosphere.meta.Meta;
import lorikeet.ecosphere.meta.MetaFromDbgAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestAxon implements Axon {

    private final String cid;
    private CrateGraphNode rootGraphNode;

    public TestAxon() {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
    }

    private TestAxon(CrateGraphNode root) {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
        this.rootGraphNode = root;
    }

    @Override
    public String getCid() {
        return this.cid;
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Action1<ReturnType, ParameterType> action, ParameterType parameter) {
        final CrateGraphNode child = this.prepareGraphNode(action, parameter);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter);
            child.setReturnValue(returnValue);
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Action2<ReturnType, ParameterType1, ParameterType2> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        final CrateGraphNode child = this.prepareGraphNode(action, parameter1, parameter2);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2);
            child.setReturnValue(returnValue);
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Action3<ReturnType, ParameterType1, ParameterType2, ParameterType3> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    ) {
        final CrateGraphNode child = this.prepareGraphNode(action, parameter1, parameter2, parameter3);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3);
            child.setReturnValue(returnValue);
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> ReturnType yield(
        Action4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4
    ) {
        final CrateGraphNode child = this.prepareGraphNode(action, parameter1, parameter2, parameter3, parameter4);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4);
            child.setReturnValue(returnValue);
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> ReturnType yield(
        Action5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    ) {
        final CrateGraphNode child = this.prepareGraphNode(action, parameter1, parameter2, parameter3, parameter4, parameter5);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
            child.setReturnValue(returnValue);
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            throw e;
        }
    }

    private CrateGraphNode prepareGraphNode(Crate crate, Object... params) {
        List<CrateParameter> parameters = new ArrayList<>();
        final Meta meta = MetaFromDbgAnnotations.meta(crate, params.length);
        this.populateParameters(meta, Arrays.asList(params), parameters);

        CrateGraphNode createdNode = new CrateGraphNode(
            crate.getClass().getName(),
            parameters,
            Instant.now(),
            new ArrayList<>()
        );

        if (this.rootGraphNode == null) {
            this.rootGraphNode = createdNode;
        } else {
            this.rootGraphNode.getChildren().add(createdNode);
        }

        return createdNode;
    }

    private void populateParameters(Meta meta, List<Object> params, List<CrateParameter> parameters) {
        for (int i = 0; i < params.size(); i++) {
            parameters.add(new CrateParameter(meta.findParameterOrCreate(i), params.get(i)));
        }
    }

    public CrateGraph getGraph() {
        return new CrateGraph(this.rootGraphNode);
    }
}
