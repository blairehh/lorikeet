package lorikeet.lobe.articletesting.reader;

import lorikeet.Fun;

public class LineReader {
    private final String[] lines;
    private int currentLine;

    public LineReader(String articleText) {
        this.lines = articleText.split("\n");
        this.currentLine = 0;
    }

    public boolean seek(Fun<String, Boolean> reader) {
        for (int i = this.currentLine; i < this.lines.length; i++ ) {
            if (reader.apply(this.lines[i])) {
                this.currentLine = i;
                return true;
            }
        }
        return false;
    }

    public String collect(Fun<String, Boolean> reader) {
        StringBuilder text = new StringBuilder();
        for (int i = this.currentLine; i < this.lines.length; i++ ) {
            if (this.lines[i].length() == 0) {
                continue;
            }
            if (!reader.apply(this.lines[i])) {
                this.currentLine = i;
                return text.toString();
            } else {
                text.append(this.lines[i]);
                text.append("\n");
            }
        }
        return text.toString();
    }

    public String getCurrentLine() {
        return this.lines[this.currentLine];
    }

    public int getCurrentLineNumber() {
        return this.currentLine;
    }

    public boolean isAtEndOfArticle() {
        return this.currentLine == this.lines.length;
    }

    public void skipToNextLine() {
        this.currentLine++;
    }

    public boolean isLineEmpty() {
        return this.getCurrentLine().isEmpty();
    }
}
