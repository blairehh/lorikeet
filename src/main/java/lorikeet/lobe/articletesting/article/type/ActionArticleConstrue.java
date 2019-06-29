package lorikeet.lobe.articletesting.article.type;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.lobe.articletesting.CellFormType;
import lorikeet.lobe.articletesting.CellKind;
import lorikeet.lobe.articletesting.article.Article;
import lorikeet.lobe.articletesting.article.Stanza;
import lorikeet.lobe.articletesting.data.CellDefinition;
import lorikeet.lobe.articletesting.data.Value;
import lorikeet.lobe.articletesting.data.deserialize.ArticleCellDeserializer;
import lorikeet.lobe.articletesting.data.deserialize.Deserializer;
import lorikeet.lobe.articletesting.reader.TextReader;
import lorikeet.error.CanNotSpecifyExceptionThrownInExpectAndToThrowStanza;
import lorikeet.error.CanNotSpecifyReturnValueInExpectAndToReturnStanza;
import lorikeet.error.ActionArticleMustHaveCaseStanza;
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
            return Err.failure(new ActionArticleMustHaveCaseStanza());
        }

        final Err<CellDefinition> cellDefinitionErr = this.cellDeserializer.deserialize(new TextReader(expectStanza.orPanic().getContent()));
        if (!cellDefinitionErr.isPresent()) {
            return Err.from(cellDefinitionErr);
        }

        CellDefinition cell = cellDefinitionErr.orPanic();

        final Opt<Stanza> toReturnStanza = article.findStanza("to-return");

        if (toReturnStanza.isPresent()) {
            if (cell.getReturnValue().isPresent()) {
                return Err.failure(new CanNotSpecifyReturnValueInExpectAndToReturnStanza());
            }
            final String toReturnContent = toReturnStanza.orPanic().getContent();
            final Err<Value> returnValueOpt = this.deserializer.deserialize(new TextReader(toReturnContent));
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
