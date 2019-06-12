package lorikeet.ecosphere.testing.article.type;

import lorikeet.Err;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.meta.ParameterMeta;
import lorikeet.ecosphere.testing.CellForm;
import lorikeet.ecosphere.testing.CellStructure;
import lorikeet.ecosphere.testing.Microscope;
import lorikeet.ecosphere.testing.article.RunResult;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.NullValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.ecosphere.testing.data.generator.Generator;
import lorikeet.ecosphere.testing.data.interpreter.Interpreter;
import lorikeet.error.CouldNotConstructCellFromArticle;
import lorikeet.error.CouldNotFindCellFormToInvoke;
import lorikeet.error.CouldNotInvokeCell;

import java.lang.reflect.InvocationTargetException;

public class CellArticleRunner {

    private final Generator generator = new Generator();
    private final Interpreter interpreter = new Interpreter();
    private final Microscope microscope = new Microscope();

    public Err<RunResult> run(CellArticle article) {
        try {
            Cell cell = this.load(article.getCell().getClassName());
            if (cell instanceof Action1) {
                return this.runAction1(article, (Action1)cell);
            }
            return Err.failure(new CouldNotFindCellFormToInvoke());
        } catch (ReflectiveOperationException err) {
            return Err.failure(new CouldNotConstructCellFromArticle());
        }
    }

    private Err<RunResult> runAction1(CellArticle article, Action1<?, ?> action) {
        final CellStructure structure = this.microscope.inspect(action.getClass());
        return structure.formFor(1)
            .map(form -> this.runAction1(article, action, form))
            .orElse(Err.failure(new CouldNotFindCellFormToInvoke()));
    }

    private Err<RunResult> runAction1(CellArticle article, Action1<?, ?> action, CellForm form) {
        final Object[] invokeParameters = new Object[form.getParameters().size()];
        for (ParameterMeta param : form.getParameters()) {
            System.out.println(param.getIdentifier());
            final Value parameterValue = article.getCell()
                .getArguments()
                .find(param.getIdentifier())
                .orElse(new NullValue());

            invokeParameters[param.getPosition()] = this.generator.generate(param.getType(), parameterValue)
                .orElse(null);
        }
        try {
            final Object result = form.getInvokeMethod().invoke(action, invokeParameters);
            return Err.of(determineResult(article.getCell(), this.interpreter.interpret(result)));
        } catch (InvocationTargetException e) {
            return Err.of(exceptionThrownResult(article.getCell(), e.getCause()));
        } catch (ReflectiveOperationException e) {
            return Err.failure(new CouldNotInvokeCell(e));
        }
    }

    private Cell load(String className) throws ReflectiveOperationException {
        return (Cell)Cell.class.getClassLoader()
            .loadClass(className)
            .getDeclaredConstructor()
            .newInstance();
    }

    private static RunResult determineResult(CellValue cell, Value returnValue) {
        if (!cell.getReturnValue().isPresent()) {
            return new RunResult(returnValue, false, null, true);
        }

        final boolean matched = cell.getReturnValue().orPanic().equals(returnValue);
        return new RunResult(returnValue, matched, null, true);
    }

    private static RunResult exceptionThrownResult(CellValue cell, Throwable exception) {
        final String exceptionName = exception.getClass().getName();
        final boolean exceptionMatched = cell.getExceptionThrown().map(exc -> exc.toString().equalsIgnoreCase(exceptionName))
                .orElse(false);

        final boolean returnValueMatched = !cell.getReturnValue().isPresent();

        return new RunResult(null, returnValueMatched, exceptionName, exceptionMatched);
    }
}
