package lorikeet.ecosphere.testing.article.type;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.testing.CellFormType;
import lorikeet.ecosphere.testing.CellKind;
import lorikeet.ecosphere.testing.article.Article;
import lorikeet.ecosphere.testing.article.Stanza;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.ecosphere.testing.data.deserialize.ArticleCellDeserializer;
import lorikeet.ecosphere.testing.data.deserialize.Deserializer;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.CanNotSpecifyExceptionThrownInExpectAndToThrowStanza;
import lorikeet.error.CanNotSpecifyReturnValueInExpectAndToReturnStanza;
import lorikeet.error.CellArticleMustHaveCaseStanza;
import lorikeet.error.CouldNotDeserializeCellValue;
import lorikeet.error.CouldNotDeserializeExceptionClassInToThrowStanza;
import lorikeet.error.CouldNotDeserializeValueInToReturnStanza;
import lorikeet.error.NotApplicable;

public class ActionArticleConstrue {

    private final ArticleCellDeserializer cellDeserializer = new ArticleCellDeserializer();
    private final Deserializer deserializer = new Deserializer();

    public Err<ActionArticle> construe(Article article) {
        if (!isApplicableType(article.getType())) {
            // @TODO replace this with a more meaningful error
            return Err.failure(new NotApplicable());
        }

        final Opt<CellFormType> cellFormType = CellFormType.ofKind(CellKind.ACTION)
            .filter(formType -> formType.getName().equalsIgnoreCase(article.getType()))
            .first();

        final Opt<Stanza> expectStanza = article.findStanza("expect");

        if (!expectStanza.isPresent()) {
            // @TODO rename the error below
            return Err.failure(new CellArticleMustHaveCaseStanza());
        }

        final Err<CellValue> cellValueErr = this.cellDeserializer.deserialize(new TextReader(expectStanza.orPanic().getContent()));
        if (!cellValueErr.isPresent()) {
            return Err.failure(new CouldNotDeserializeCellValue());
        }

        CellValue cell = cellValueErr.orPanic();

        final Opt<Stanza> toReturnStanza = article.findStanza("to-return");

        if (toReturnStanza.isPresent()) {
            if (cell.getReturnValue().isPresent()) {
                return Err.failure(new CanNotSpecifyReturnValueInExpectAndToReturnStanza());
            }
            final String toReturnContent = toReturnStanza.orPanic().getContent();
            final Opt<Value> returnValueOpt = this.deserializer.deserialize(new TextReader(toReturnContent));
            if (!returnValueOpt.isPresent()) {
                return Err.failure(new CouldNotDeserializeValueInToReturnStanza());
            }
            cell = cell.withReturnValue(returnValueOpt.orPanic());
        }


        final Opt<Stanza> toThrowStanza = article.findStanza("to-throw");

        if (toThrowStanza.isPresent()) {
            if (cell.getExceptionThrown().isPresent()) {
                return Err.failure(new CanNotSpecifyExceptionThrownInExpectAndToThrowStanza());
            }
            final String toThrowContent = toThrowStanza.orPanic().getContent();
            final Err<String> exception = new TextReader(toThrowContent).nextIdentifier();
            if (!exception.isPresent()) {
                return Err.failure(new CouldNotDeserializeExceptionClassInToThrowStanza());
            }
            cell = cell.withExceptionThrown(exception.orPanic());
        }

        final Opt<Stanza> nameStanza = article.findStanza("name");
        final String name = nameStanza.map(stanza -> stanza.getContent().trim()).orNull();
        final String doc = nameStanza.map(Stanza::getDocumentation).orNull();

        return Err.of(Err.of(new ActionArticle(article.getFilePath(), name, doc, cell, cellFormType.orNull())));
    }

    private boolean isApplicableType(String type) {
        if (type.equalsIgnoreCase("action")) {
            return true;
        }

        return CellFormType.ofKind(CellKind.ACTION)
            .anyMatch(formType -> formType.getName().equalsIgnoreCase(type));
    }
}
