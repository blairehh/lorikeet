package lorikeet.ecosphere.testing.article.type;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.testing.CellFormType;
import lorikeet.ecosphere.testing.CellKind;
import lorikeet.ecosphere.testing.article.Article;
import lorikeet.ecosphere.testing.article.Stanza;
import lorikeet.ecosphere.testing.data.deserialize.CellValueDeserializer;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.CellArticleMustHaveCaseStanza;
import lorikeet.error.CouldNotDeserializeCellValue;
import lorikeet.error.NotApplicable;

public class ActionArticleConstrue {

    private final CellValueDeserializer cellValueDeserializer = new CellValueDeserializer();

    public Err<ActionArticle> construe(Article article) {
        if (!isApplicableType(article.getType())) {
            // @TODO replace this with a more meaningful error
            return Err.failure(new NotApplicable());
        }

        final Opt<CellFormType> cellFormType = CellFormType.ofKind(CellKind.ACTION)
            .filter(formType -> formType.getName().equalsIgnoreCase(article.getType()))
            .first();

        final Opt<Stanza> expectStanza = article.getStanzas()
            .filter(stanza -> stanza.getName().equalsIgnoreCase("expect"))
            .first();

        if (!expectStanza.isPresent()) {
            // @TODO rename the error below
            return Err.failure(new CellArticleMustHaveCaseStanza());
        }

        return this.cellValueDeserializer.deserialize(new TextReader(expectStanza.orPanic().getContent()))
            .map(cell -> Err.of(new ActionArticle(cell, cellFormType.orNull())))
            .orElse(Err.failure(new CouldNotDeserializeCellValue()));
    }

    private boolean isApplicableType(String type) {
        if (type.equalsIgnoreCase("action")) {
            return true;
        }

        return CellFormType.ofKind(CellKind.ACTION)
            .anyMatch(formType -> formType.getName().equalsIgnoreCase(type));
    }
}
