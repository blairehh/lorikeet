package lorikeet.ecosphere.transcript;

public class TextReader {
    private final String text;
    private int index;

    public TextReader(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public String nextToken() {
        final StringBuilder token = new StringBuilder();
        boolean hasFoundNonWhitespace = false;
        for (; this.index < text.length(); this.index++) {
            final char character = text.charAt(this.index);
            if (Character.isWhitespace(character) && !hasFoundNonWhitespace) {
                continue;
            }
            if (Character.isWhitespace(character) && hasFoundNonWhitespace) {
                return token.toString();
            }
            if (!Character.isWhitespace(character) && !hasFoundNonWhitespace) {
                hasFoundNonWhitespace = true;
                token.append(character);
                continue;
            }
            token.append(character);
        }
        return token.toString();
    }
}
