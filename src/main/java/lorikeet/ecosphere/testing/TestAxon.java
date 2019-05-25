package lorikeet.ecosphere.testing;


import lorikeet.Dict;
import lorikeet.ecosphere.Axon;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import lorikeet.ecosphere.Action3;
import lorikeet.ecosphere.Action4;
import lorikeet.ecosphere.Action5;
import lorikeet.ecosphere.meta.Meta;
import lorikeet.ecosphere.meta.MetaFromDbgAnnotations;
import lorikeet.ecosphere.meta.ParameterMeta;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.ecosphere.testing.data.interpreter.Interpreter;
import lorikeet.ecosphere.testing.graph.CellGraph;
import lorikeet.ecosphere.testing.graph.CellGraphNode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestAxon implements Axon {

    private final Interpreter interpreter = new Interpreter();

    private final String cid;
    private CellGraphNode rootCellNode;

    public TestAxon() {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
    }

    private TestAxon(CellGraphNode root) {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
        this.rootCellNode = root;
    }

    @Override
    public String getCid() {
        return this.cid;
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Action1<ReturnType, ParameterType> action, ParameterType parameter) {
        final CellGraphNode child = this.prepareCellNode(action, parameter);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Action2<ReturnType, ParameterType1, ParameterType2> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        final CellGraphNode child = this.prepareCellNode(action, parameter1, parameter2);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
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
        final CellGraphNode child = this.prepareCellNode(action, parameter1, parameter2, parameter3);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
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
        final CellGraphNode child = this.prepareCellNode(action, parameter1, parameter2, parameter3, parameter4);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
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
        final CellGraphNode child = this.prepareCellNode(action, parameter1, parameter2, parameter3, parameter4, parameter5);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(new IdentifierValue(e.getClass().getName()));
            throw e;
        }
    }

    private CellGraphNode prepareCellNode(Cell cell, Object... params) {
        final Meta meta = MetaFromDbgAnnotations.meta(cell, params.length);

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

     public CellGraph getCellGraph() {
        return new CellGraph(this.rootCellNode);
     }

}
