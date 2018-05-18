package lorikeet.token;

import java.util.List;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.io.File;

public class Tokenizer {

    private static final String SYMBOLS = "!@$%^&*'|?\\()[]{}<>:.,=/+;-";

    public TokenSeq tokenize(String data) {
        return this.tokenize(null, data);
    }

    public TokenSeq tokenize(File file, String data) {
        Tokenization tokenization = new Tokenization(file);
        boolean inComment = false;

        for (int i = 0; i < data.length(); i++) {
            Character c = data.charAt(i);
            tokenization.bumpColumn();

            if (c == '>' && nextIs(data, i, '#')) {
                inComment = false;
                i += 1;
                continue;
            }

            if (inComment) {
                continue;
            }

            if (c == '#' && nextIs(data, i, '<')) {
                inComment = true;
                continue;
            }

            if (c == '\n') {
                tokenization.putNewLine();
                continue;
            }

            if (c == '\'' || c == '"' || c =='`') {
                tokenization.putQuotation(c.toString());
                continue;
            }

            if (Character.isWhitespace(c)) {
                tokenization.putWhitespace();
                continue;
            }

            if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
                tokenization.putAlphaNumeric(c.toString());
                continue;
            }

            if (c == '.') {
                tokenization.putDot();
                continue;
            }

            if (c == '-' && !nextIs(data, i, '>')) {
                tokenization.putHyphen();
                continue;
            }

            if (SYMBOLS.indexOf(c) != -1) {
                if (c == '=' && nextIs(data, i, '=')) {
                    tokenization.putSymbol("==");
                    i += 1;
                } else if (c == '!' && nextIs(data, i, '=')) {
                    tokenization.putSymbol("!=");
                    i += 1;
                } else if (c == '&' && nextIs(data, i, '&')) {
                    tokenization.putSymbol("&&");
                    i += 1;
                } else if (c == '|' && nextIs(data, i, '|')) {
                    tokenization.putSymbol("||");
                    i += 1;
                } else if (c == '<' && nextIs(data, i, '=')) {
                    tokenization.putSymbol("<=");
                    i += 1;
                } else if (c == '>' && nextIs(data, i, '=')) {
                    tokenization.putSymbol(">=");
                    i += 1;
                } else if (c == '-' && nextIs(data, i, '>')) {
                    tokenization.putSymbol("->");
                    i += 1;
                } else {
                    tokenization.putSymbol(c.toString());
                }
                continue;
            }
        }
        tokenization.putEof();
        return new TokenSeq(file, tokenization.getTokens());
    }

    private boolean nextIs(String data, int current, Character value) {
        if (data.length() == current + 1) {
            return false;
        }
        return data.charAt(current + 1) == value;
    }

}

class Tokenization {

    private int tokenStartLine;
    private int tokenStartColumn;
    private int lineCount;
    private int columnCount;

    private boolean inQuotes;
    private String quoteChar;

    private boolean wasLastNewLine;
    private boolean whitespaceToken;

    private List<Token> tokens;
    private StringBuilder token;

    private final File file;


    public Tokenization(File file) {
        this.file = file;

        this.lineCount = 1;
        this.tokenStartLine = 1;
        this.tokenStartColumn = 1;
        this.columnCount = 1;

        this.token = new StringBuilder();
        this.whitespaceToken = false;
        this.tokens = new LinkedList<Token>();
        this.inQuotes = false;
        this.wasLastNewLine = false;
    }

    public void bumpColumn() {
        this.columnCount += 1;
    }

    public void putDot() {
        if (Pattern.matches("-?[0-9]+([0-9\\.]+)?", this.token.toString())) {
            this.token.append(".");
        } else {
            this.putSymbol(".");
        }
    }

    public void putHyphen() {
        if (this.inQuotes) {
            this.token.append("-");
            return;
        }
        if (this.whitespaceToken || !this.isEmptyToken()) {
            this.pushToken();
        }
        this.token.append("-");
        this.wasLastNewLine = false;
    }

    public void putNewLine() {
        if (this.inQuotes) {
            this.token.append("\n");
            this.lineCount += 1;
            return;
        }
        this.pushToken();
        if (!this.wasLastNewLine) {
            this.tokens.add(new NewLineToken(this.lineCount));
        }
        this.wasLastNewLine = true;
        this.lineCount += 1;
    }

    public List<Token> getTokens() {
        return this.tokens;
    }

    public boolean inQuotation() {
        return this.inQuotes;
    }

    public void putQuotation(String quote) {
        if (this.inQuotes && quote.equals(this.quoteChar)) {
            this.quoteChar = null;
            this.inQuotes = false;
            String quotation = this.token.toString();
            this.tokens.add(new QuotationToken(quote, quotation, this.tokenStartLine));
            this.wasLastNewLine = false;
            this.whitespaceToken = false;
            this.tokenStartLine = this.lineCount;
            this.tokenStartColumn = this.columnCount;
            this.token = new StringBuilder();
            return;
        }

        if (this.inQuotes && !quote.equals(this.quoteChar)) {
            this.token.append(quote);
            return;
        }

        if (this.whitespaceToken || !this.isEmptyToken()) {
            this.pushToken();
        }
        this.inQuotes = true;
        this.quoteChar = quote;
	}

	public void putWhitespace() {
        if (this.inQuotes) {
            this.token.append(" ");
            return;
        }

        if (!this.isEmptyToken() && !this.whitespaceToken) {
            this.pushToken();
        }
        this.token.append(" ");
        this.whitespaceToken = true;
    }

    public void putAlphaNumeric(String c) {
        if (this.inQuotes) {
            this.token.append(c);
            return;
        }
        if (this.whitespaceToken) {
            this.pushToken();
        }
        this.token.append(c);
    }

    public void putSymbol(String c) {
        if (this.inQuotes) {
            this.token.append(c);
            return;
        }
        if (this.whitespaceToken || !this.isEmptyToken()) {
            this.pushToken();
        }
        Token token = Symbol.fromString(c)
            .map(symbol -> ((Token)new SymbolToken(symbol, this.lineCount)))
            .orElse(this.textOrNumberToken(c, this.lineCount));
        this.tokens.add(token);
        this.wasLastNewLine = false;
	}

    public void putEof() {
        if (!this.isEmptyToken()) {
            this.pushToken();
        }
    }

    private boolean isEmptyToken() {
        return this.token.length() == 0;
    }

    private void pushToken() {
        if (this.isEmptyToken()) {
            return;
        }
        this.addToken();
        this.whitespaceToken = false;
        this.token = new StringBuilder();
        this.tokenStartLine = this.lineCount;
        this.tokenStartColumn = this.columnCount;
    }

    private void addToken() {
        String tokenText = this.token.toString();
        if (this.whitespaceToken) {
            if (!this.wasLastNewLine) {
                this.tokens.add(new WhitespaceToken(this.lineCount));
                this.wasLastNewLine = false;
            }
        } else {
            Token token = Keyword.fromString(tokenText)
                .map(keyword -> (Token)new KeywordToken(keyword, this.lineCount))
                .orElse(this.textOrNumberToken(tokenText, this.lineCount));
            this.tokens.add(token);
            this.wasLastNewLine = false;
        }
    }

    private Token textOrNumberToken(String value, int line) {
        if (Pattern.matches("-?[0-9]+(\\.[0-9]+)?", value)) {
            return new NumberToken(value, line);
        }
        return new TextToken(value, line);
    }

}
