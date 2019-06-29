package lorikeet.ecosphere.articletesting.article.type;

import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.articletesting.meta.ParameterMeta;
import lorikeet.ecosphere.articletesting.CellForm;
import lorikeet.ecosphere.articletesting.CellFormType;
import lorikeet.ecosphere.articletesting.CellKind;
import lorikeet.ecosphere.articletesting.CellStructure;
import lorikeet.ecosphere.articletesting.Microscope;
import lorikeet.ecosphere.articletesting.TestTract;
import lorikeet.ecosphere.articletesting.article.RunResult;
import lorikeet.ecosphere.articletesting.data.NullValue;
import lorikeet.ecosphere.articletesting.data.Value;
import lorikeet.ecosphere.articletesting.data.generator.Generator;
import lorikeet.ecosphere.articletesting.data.interpreter.Interpreter;
import lorikeet.error.CouldNotConstructCellFromArticle;
import lorikeet.error.CouldNotFindCellFormToInvoke;
import lorikeet.error.CouldNotInvokeCell;
import lorikeet.error.CouldNotDetermineCellFormTypeToTest;
import lorikeet.error.CellTooAmbiguousToNotSpecifyFormTypeToTest;

import java.lang.reflect.InvocationTargetException;

public class ActionArticleRunner {

    private final Generator generator = new Generator();
    private final Interpreter interpreter = new Interpreter();
    private final Microscope microscope = new Microscope();

    public Err<RunResult> run(ActionArticle article) {
        try {
            final Cell cell = this.load(article.getCell().getClassName());
            final CellStructure structure = this.microscope.inspect(cell.getClass());

            return article.getFormType()
                .asErr(this.determineFormTypeToRun(article, structure))
                .pipe(formType -> this.run(article, cell, formType));

        } catch (ReflectiveOperationException err) {
            return Err.failure(new CouldNotConstructCellFromArticle());
        }
    }

    private Err<CellFormType> determineFormTypeToRun(ActionArticle article, CellStructure structure) {
        final int argCount = article.getCell().getArguments().size();
        final Seq<CellForm> applicableForms = structure.getForms()
            .filter(form -> form.getType().getKind() == CellKind.ACTION)
            .filter(form -> form.getParameters().size() == argCount);

        if (applicableForms.size() == 0) {
            return Err.failure(new CouldNotDetermineCellFormTypeToTest());
        }
        if (applicableForms.size() != 1) {
            return Err.failure(new CellTooAmbiguousToNotSpecifyFormTypeToTest());
        }

        return applicableForms
            .first()
            .map(CellForm::getType)
            .asErr();
    }

    private Err<RunResult> run(ActionArticle article, Cell cell, CellFormType formType) {
        final CellStructure structure = this.microscope.inspect(cell.getClass());
        return structure.formFor(formType)
            .map(cellForm -> this.run(article, cell, cellForm))
            .orElse(Err.failure(new CouldNotFindCellFormToInvoke()));
    }

    private Err<RunResult> run(ActionArticle article, Cell cell, CellForm form) {
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
            final TestTract tract = new TestTract();
            form.getConnectMethod().invoke(cell, tract);
            final Object result = form.getInvokeMethod().invoke(cell, invokeParameters);
            return Err.of(RunResult.resultForReturn(article.getCell(), this.interpreter.interpret(result)));
        } catch (InvocationTargetException e) {
            return Err.of(RunResult.resultForException(article.getCell(), e.getCause().getClass().getName()));
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
}
