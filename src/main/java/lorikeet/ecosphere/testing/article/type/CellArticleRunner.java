package lorikeet.ecosphere.testing.article.type;

import lorikeet.Seq;
import lorikeet.ecosphere.Action1;
import lorikeet.ecosphere.Action2;
import lorikeet.ecosphere.Cell;
import lorikeet.ecosphere.meta.Meta;
import lorikeet.ecosphere.meta.MetaFromDbgAnnotations;
import lorikeet.ecosphere.meta.ParameterMeta;
import lorikeet.ecosphere.testing.CellForm;
import lorikeet.ecosphere.testing.CellStructure;
import lorikeet.ecosphere.testing.Microscope;
import lorikeet.ecosphere.testing.TestAxon;
import lorikeet.ecosphere.testing.data.NullValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.ecosphere.testing.data.generator.Generator;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class CellArticleRunner {

    private final Generator generator = new Generator();
    private final Microscope microscope = new Microscope();

    public void run(CellArticle article) {
        try {
            Cell cell = this.load(article.getCell().getClassName());
            if (cell instanceof Action1) {
                this.runAction1(article, (Action1)cell);
            }


        } catch (ReflectiveOperationException err) {
            err.printStackTrace();
        }

    }

    private void runAction1(CellArticle article, Action1<?, ?> action) {
        final CellStructure structure = this.microscope.inspect(action.getClass());
        structure.formFor(1)
            .then(form -> this.runAction1(article, action, form));
    }

    private void runAction1(CellArticle article, Action1<?, ?> action, CellForm form) {
        final Object[] invokeParameters = new Object[form.getParameters().size()];
        for (ParameterMeta param : form.getParameters()) {
            final Value parameterValue = article.getCell()
                .getArguments()
                .find(param.getName())
                .orElse(new NullValue());

            invokeParameters[param.getPosition()] = this.generator.generate(param.getType(), parameterValue)
                .orElse(null);
        }
        try {
            form.getInvokeMethod().invoke(action, invokeParameters);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private Cell load(String className) throws ReflectiveOperationException {
        return (Cell)Cell.class.getClassLoader()
            .loadClass(className)
            .getDeclaredConstructor()
            .newInstance();
    }

}
