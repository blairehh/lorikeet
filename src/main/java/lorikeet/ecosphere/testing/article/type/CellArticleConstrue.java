package lorikeet.ecosphere.testing.article.type;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.ecosphere.testing.CellFormType;
import lorikeet.ecosphere.testing.article.Article;
import lorikeet.ecosphere.testing.article.Stanza;
import lorikeet.ecosphere.testing.data.deserialize.CellValueDeserializer;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.CellArticleMustHaveCaseStanza;
import lorikeet.error.CouldNotDeserializeCellValue;
import lorikeet.error.NotApplicable;

public class CellArticleConstrue {
    private static final String TYPE = "cell";

    private final CellValueDeserializer cellValueDeserializer = new CellValueDeserializer();

    public Err<CellArticle> construe(Article article) {
        if (!article.getType().equalsIgnoreCase(TYPE)) {
            return Err.failure(new NotApplicable());
        }
        final Opt<Stanza> caseStanza = article.getStanzas()
            .filter(stanza -> stanza.getName().equalsIgnoreCase("case"))
            .first();

        final CellFormType formType = article.getStanzas()
            .filter(stanza -> stanza.getName().equalsIgnoreCase("form"))
            .first()
            .pipe(stanza -> CellFormType.fromName(stanza.getContent().trim()))
            .orNull();

        if (!caseStanza.isPresent()) {
            return Err.failure(new CellArticleMustHaveCaseStanza());
        }

        return this.cellValueDeserializer.deserialize(new TextReader(caseStanza.orPanic().getContent()))
            .map(cell -> Err.of(new CellArticle(cell, formType)))
            .orElse(Err.failure(new CouldNotDeserializeCellValue()));
    }

}
