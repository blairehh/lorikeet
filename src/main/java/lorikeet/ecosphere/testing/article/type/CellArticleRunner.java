package lorikeet.ecosphere.testing.article.type;

import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.meta.ParameterMeta;
import lorikeet.ecosphere.testing.CellForm;
import lorikeet.ecosphere.testing.CellFormType;
import lorikeet.ecosphere.testing.CellStructure;
import lorikeet.ecosphere.testing.Microscope;
import lorikeet.ecosphere.testing.TestAxon;
import lorikeet.ecosphere.testing.article.RunResult;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.NullValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.ecosphere.testing.data.generator.Generator;
import lorikeet.ecosphere.testing.data.interpreter.Interpreter;
import lorikeet.error.CouldNotConstructCellFromArticle;
import lorikeet.error.CouldNotFindCellFormToInvoke;
import lorikeet.error.CouldNotInvokeCell;
import lorikeet.error.CouldNotDetermineCellFormTypeToTest;
import lorikeet.error.CellTooAmbiguousToNotSpecifyFormTypeToTest;

import java.lang.reflect.InvocationTargetException;

public class CellArticleRunner {

    private final Generator generator = new Generator();
    private final Interpreter interpreter = new Interpreter();
    private final Microscope microscope = new Microscope();

    public Err<RunResult> run(CellArticle article) {
        try {
            final Cell cell = this.load(article.getCell().getClassName());
            final CellStructure structure = this.microscope.inspect(cell.getClass());

            return article.getCellFormType()
                .asErr(this.determineFormTypeToRun(article, structure))
                .pipe(formType -> this.run(article, cell, formType));

        } catch (ReflectiveOperationException err) {
            return Err.failure(new CouldNotConstructCellFromArticle());
        }
    }

    private Err<CellFormType> determineFormTypeToRun(CellArticle article, CellStructure structure) {
        final int argCount = article.getCell().getArguments().size();
        final Seq<CellForm> applicbleForms = structure.getForms()
            .filter(form -> form.getParameters().size() == argCount);
        if (applicbleForms.size() == 0) {
            return Err.failure(new CouldNotDetermineCellFormTypeToTest());
        }
        if (applicbleForms.size() != 1) {
            return Err.failure(new CellTooAmbiguousToNotSpecifyFormTypeToTest());
        }

        return applicbleForms
            .first()
            .map(CellForm::getType)
            .asErr();
    }

    private Err<RunResult> run(CellArticle article, Cell cell, CellFormType formType) {
        final CellStructure structure = this.microscope.inspect(cell.getClass());
        return structure.formFor(formType)
            .map(cellForm -> this.run(article, cell, cellForm))
            .orElse(Err.failure(new CouldNotFindCellFormToInvoke()));
    }

    private Err<RunResult> run(CellArticle article, Cell cell, CellForm form) {
        final Object[] invokeParameters = new Object[form.getParameters().size()];
        for (ParameterMeta param : form.getParameters()) {
            final Value parameterValue = article.getCell()
                .getArguments()
                .find(param.getIdentifier())
                .orElse(new NullValue());

            invokeParameters[param.getPosition()] = this.generator.generate(param.getType(), parameterValue)
                .orElse(null);
        }
        try {
            final TestAxon axon = new TestAxon();
            form.getConnectMethod().invoke(cell, axon);
            final Object result = form.getInvokeMethod().invoke(cell, invokeParameters);
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
