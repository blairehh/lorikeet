package lorikeet.token;

public class QuotationToken implements Token {
    private final String quote;
    private final String quotation;
    private final int line;

    public QuotationToken(String quote, String quotation, int line) {
        this.quote = quote;
        this.quotation = quotation;
        this.line = line;
    }

    @Override
    public int getLine() {
        return this.line;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.QUOTATION;
    }

    @Override
    public boolean isNewLine() {
        return false;
    }

    @Override
    public String str() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.quote);
        builder.append(this.quotation);
        builder.append(this.quote);
        return builder.toString();
    }

    public String getQuote() {
        return this.quote;
    }

    public String getQuotation() {
        return this.quotation;
    }

}
