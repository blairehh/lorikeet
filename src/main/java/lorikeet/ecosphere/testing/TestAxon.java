package lorikeet.ecosphere.testing;


import lorikeet.Dict;
import lorikeet.ecosphere.Axon;
import lorikeet.ecosphere.Crate;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import lorikeet.ecosphere.Action3;
import lorikeet.ecosphere.Action4;
import lorikeet.ecosphere.Action5;
import lorikeet.ecosphere.meta.Meta;
import lorikeet.ecosphere.meta.MetaFromDbgAnnotations;
import lorikeet.ecosphere.meta.ParameterMeta;
import lorikeet.ecosphere.transcript.CellValue;
import lorikeet.ecosphere.transcript.IdentifierValue;
import lorikeet.ecosphere.transcript.Value;
import lorikeet.ecosphere.transcript.interpreter.Interpreter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestAxon implements Axon {

    private final Interpreter interpreter = new Interpreter();

    private final String cid;
    private CrateGraphNode rootGraphNode;
    private CellGraphNode rootCellNode;

    public TestAxon() {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
    }

    private TestAxon(CrateGraphNode root, CellGraphNode cellRoot) {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
        this.rootGraphNode = root;
        this.rootCellNode = cellRoot;
    }

    @Override
    public String getCid() {
        return this.cid;
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Action1<ReturnType, ParameterType> action, ParameterType parameter) {
        final CrateGraphNode child = this.prepareGraphNode(action, parameter);
        final CellGraphNode childCell = this.prepareCellNode(action, parameter);
        try {
            action.inject(new TestAxon(child, childCell));
            final ReturnType returnValue = action.invoke(parameter);
            child.setReturnValue(returnValue);
            childCell.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            childCell.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
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
        final CellGraphNode childCell = this.prepareCellNode(action, parameter1, parameter2);
        try {
            action.inject(new TestAxon(child, childCell));
            final ReturnType returnValue = action.invoke(parameter1, parameter2);
            child.setReturnValue(returnValue);
            childCell.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            childCell.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
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
        final CellGraphNode childCell = this.prepareCellNode(action, parameter1, parameter2, parameter3);
        try {
            action.inject(new TestAxon(child, childCell));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3);
            child.setReturnValue(returnValue);
            childCell.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            childCell.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
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
        final CellGraphNode childCell = this.prepareCellNode(action, parameter1, parameter2, parameter3, parameter4);
        try {
            action.inject(new TestAxon(child, childCell));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4);
            child.setReturnValue(returnValue);
            childCell.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            childCell.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
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
        final CellGraphNode childCell = this.prepareCellNode(action, parameter1, parameter2, parameter3, parameter4, parameter5);
        try {
            action.inject(new TestAxon(child, childCell));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
            child.setReturnValue(returnValue);
            childCell.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e);
            childCell.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
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

    private CellGraphNode prepareCellNode(Crate cell, Object... params) {
        List<CrateParameter> parameters = new ArrayList<>();
        final Meta meta = MetaFromDbgAnnotations.meta(cell, params.length);

        this.populateParameters(meta, Arrays.asList(params), parameters);


        CellValue cellValue = new CellValue(
            cell.getClass().getName(),
            buildArguments(meta, Arrays.asList(params)),
            null,
            null
        );
        CellGraphNode createdNode = new CellGraphNode(
            cellValue,
            new ArrayList<>(),
            Instant.now()
        );

        if (this.rootCellNode == null) {
            this.rootCellNode = createdNode;
        } else {
            this.rootCellNode.getChildren().add(createdNode);
        }

        return createdNode;
    }

    private void populateParameters(Meta meta, List<Object> params, List<CrateParameter> parameters) {
        for (int i = 0; i < params.size(); i++) {
            parameters.add(new CrateParameter(meta.findParameterOrCreate(i), params.get(i)));
        }
    }

    private Dict<String, Value> buildArguments(Meta meta, List<Object> params) {
        Dict<String, Value> arguments = Dict.empty();
        for (int i = 0; i < params.size(); i++) {
            final ParameterMeta parameter = meta.findParameterOrCreate(i);
            if (parameter.isIgnore()) {
                continue;
            }

            final Value value = parameter.isUseHash()
                ? this.interpreter.interpretAsHash(params.get(i))
                : this.interpreter.interpret(params.get(i));

            arguments = arguments.push(parameter.getName(), value);
        }
        return arguments;
    }

    public CrateGraph getGraph() {
        return new CrateGraph(this.rootGraphNode);
    }

     public CellGraph getCellGraph() {
        return new CellGraph(this.rootCellNode);
     }

}
