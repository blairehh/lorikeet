package lorikeet.ecosphere.testing.article;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.ecosphere.testing.reader.LineReader;

public class ArticleReader {

    public Opt<Article> read(LineReader reader) {
        Seq<Stanza> stanzas = Seq.empty();
        String doc = "";
        while (true) {
            if (reader.isAtEndOfArticle()) {
                return build(stanzas);
            }
            if (reader.isLineEmpty()) {
                reader.skipToNextLine();
                continue;
            }
            if (reader.getCurrentLine().isBlank()) {
                continue;
            }
            final Opt<StanzaTitle> titleOpt = this.nextTitle(reader);
            if (!titleOpt.isPresent()) {
                if (reader.isAtEndOfArticle() || stanzas.size() > 0) {
                    return build(stanzas);
                }
                return Opt.empty();
            }
            final StanzaTitle title = titleOpt.orPanic();
            final String contents = reader.collect(line -> Character.isWhitespace(line.charAt(0)));
            if (title.getName().equalsIgnoreCase("doc")) {
                doc = contents.trim();
            } else {
                stanzas = stanzas.push(new Stanza(title, doc, contents));
                doc = "";
            }
        }
    }

    private Opt<StanzaTitle> nextTitle(LineReader reader) {
        final boolean found = reader.seek(line -> Character.isLetter(line.charAt(0)));
        if (!found) {
            reader.skipToNextLine();
            return Opt.empty();
        }
        final TextReader textReader = new TextReader(reader.getCurrentLine());
        reader.skipToNextLine();

        final Opt<String> name = textReader.nextKebabWord();
        Seq<String> attributes = Seq.empty();
        if (!name.isPresent()) {
            return Opt.empty();
        }

        while (true) {
            if (textReader.isAtEnd()) {
                return Opt.empty();
            }

            if (textReader.getCurrentChar() == ':') {
                return Opt.of(new StanzaTitle(name.orPanic(), attributes));
            }

            final Opt<String> attribute = textReader.nextKebabWord();
            if (!attribute.isPresent()) {
                return Opt.empty();
            }

            attributes = attributes.push(attribute);
        }
    }

    private static Opt<Article> build(Seq<Stanza> stanzas) {
        final Opt<String> type = stanzas
            .stream()
            .filter(stanza -> stanza.getName().equalsIgnoreCase("type"))
            .map(stanza -> stanza.getContent().trim())
            .collect(Seq.collector())
            .first();

        if (!type.isPresent()) {
            return Opt.empty();
        }

        final Seq<Stanza> otherStanzas = stanzas
            .stream()
            .filter(stanza -> !stanza.getName().equalsIgnoreCase("type"))
            .collect(Seq.collector());

        return Opt.of(new Article(type.orPanic(), otherStanzas));
    }

}
