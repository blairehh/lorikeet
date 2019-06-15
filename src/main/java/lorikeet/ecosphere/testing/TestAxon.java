package lorikeet.ecosphere.testing;


import lorikeet.Dict;
import lorikeet.Seq;
import lorikeet.ecosphere.Axon;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import lorikeet.ecosphere.Action3;
import lorikeet.ecosphere.Action4;
import lorikeet.ecosphere.Action5;
import lorikeet.ecosphere.meta.ParameterMeta;
import lorikeet.ecosphere.testing.Microscope;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.ecosphere.testing.data.interpreter.Interpreter;
import lorikeet.ecosphere.testing.graph.CellGraph;
import lorikeet.ecosphere.testing.graph.CellGraphNode;
import lorikeet.error.NumberOfParameterValuesDifferToExpectedInCellForm;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestAxon implements Axon {

    private final Interpreter interpreter = new Interpreter();
    private final Microscope microscope = new Microscope();

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
        final CellGraphNode child = this.prepareCellNode(CellFormType.ACTION_1, action, parameter);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e.getClass().getName());
            throw e;
        }
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Action2<ReturnType, ParameterType1, ParameterType2> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        final CellGraphNode child = this.prepareCellNode(CellFormType.ACTION_2, action, parameter1, parameter2);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e.getClass().getName());
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
        final CellGraphNode child = this.prepareCellNode(CellFormType.ACTION_3, action, parameter1, parameter2, parameter3);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e.getClass().getName());
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
        final CellGraphNode child = this.prepareCellNode(CellFormType.ACTION_4, action, parameter1, parameter2, parameter3, parameter4);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e.getClass().getName());
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
        final CellGraphNode child = this.prepareCellNode(CellFormType.ACTION_5, action, parameter1, parameter2, parameter3, parameter4, parameter5);
        try {
            action.inject(new TestAxon(child));
            final ReturnType returnValue = action.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
            child.setReturnValue(this.interpreter.interpret(returnValue));
            return returnValue;
        } catch (RuntimeException e) {
            child.setExceptionThrown(e.getClass().getName());
            throw e;
        }
    }

    private CellGraphNode prepareCellNode(CellFormType formType, Cell cell, Object... params) {

        // if we can not find the form that the whole point of the TextTract goes out the window.
        // so panic and throw the exception!
        final CellForm cellForm = this.microscope.inspect(cell.getClass())
            .formFor(formType)
            .orPanic();

        if (cellForm.getParameters().size() != params.length) {
            throw new NumberOfParameterValuesDifferToExpectedInCellForm();
        }
        
        
        CellValue cellValue = new CellValue(
            cell.getClass().getName(),
            buildArguments(cellForm.getParameters(), Arrays.asList(params)),
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

    private Dict<String, Value> buildArguments(Seq<ParameterMeta> params, List<Object> parameterValues) {
        Dict<String, Value> arguments = Dict.empty();
        for (int i = 0; i < parameterValues.size(); i++) {
            final ParameterMeta parameter = params.fetch(i).orPanic();
            if (parameter.isIgnore()) {
                continue;
            }

            final Value value = parameter.isUseHash()
                ? this.interpreter.interpretAsHash(parameterValues.get(i))
                : this.interpreter.interpret(parameterValues.get(i));

            arguments = arguments.push(parameter.getIdentifier(), value);
        }
        return arguments;
    }


     public CellGraph getCellGraph() {
        return new CellGraph(this.rootCellNode);
     }

}
