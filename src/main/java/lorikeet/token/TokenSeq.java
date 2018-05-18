package lorikeet.token;

import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.regex.Pattern;
import java.io.File;

/**
  @TODO
  Use iterators instead of random access
*/
public class TokenSeq implements Iterable<Token> {

    private final File file;
    private final List<Token> tokens;
    private final int index;

    public TokenSeq(File file, List<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
        this.file = file;
    }

    public TokenSeq(File file, List<Token> tokens, int index) {
        this.file = file;
        this.tokens = tokens;
        this.index = index;
    }

    public File getFile() {
        return this.file;
    }

    @Override
    public Iterator<Token> iterator() {
        return this.tokens.iterator();
    }

    public ListIterator<Token> listIterator() {
        return this.tokens.listIterator();
    }


    public int count() {
        return this.tokens.size();
    }

    public Token at(int index) {
        return this.tokens.get(index);
    }

    public Token current() {
        return this.tokens.get(this.index);
    }

    public int currentIndex() {
        return this.index;
    }

    public String currentStr() {
        if (this.eof()) {
            return "<<EOF>>";
        } else {
            return this.current().str();
        }
    }

    /**
    * If the token sequence is on a white then move to the next none whitespace.
    * @TODO have another func that adjusts over new lines
    */
    public TokenSeq adjust() {
        if (this.eof()) {
            return this;
        }
        if (this.tokens.get(this.index).getTokenType() == TokenType.WHITESPACE) {
            return this.skip();
        }
        if (this.tokens.get(this.index).getTokenType() == TokenType.NEWLINE) {
            return this.jump();
        }
        return this;
    }

    /**
    * Move the token sequence on token towards the end
    */
    public TokenSeq shift() {
        return new TokenSeq(this.file, this.tokens, this.index + 1);
    }

    /**
    * Move the token sequence to the next non whitespace token (which could be a new line)
    */
    public TokenSeq skip() {
        return this.jump(1, 0);
    }

    /**
    * Move the token sequence to the next non whitespace or new line token
    */
    public TokenSeq jump() {
        return this.jump(1, 1);
    }

    /**
    * Move the token seq to the last token of the seq.
    */
    public TokenSeq toLast() {
        return new TokenSeq(this.file, this.tokens, this.tokens.size() - 1);
    }

    /**
    * Move the token seq to the next none white or new line x amount of times.
    * @param count   the number of times to move to the next token ignoring new line or whitespace
    * @param newLineCount how many times a new line can be ignored. -1 for infinite. this is param is
    *     kinda meaningless now since there are no consective new lines.
    */
    private TokenSeq jump(int count, int newLineCount) {
        if (this.index == this.tokens.size() - 1) {
            return new TokenSeq(this.file, this.tokens, this.tokens.size());
        }
        for (int i = this.index + 1; i < this.tokens.size(); i++) {
            if (this.tokens.get(i).getTokenType() == TokenType.WHITESPACE) {
                continue;
            }
            if (newLineCount != 0  && this.tokens.get(i).isNewLine()) {
                newLineCount--;
                continue;
            }
            count--;
            if (count == 0) {
                return new TokenSeq(this.file, this.tokens, i);
            } else {
                continue;
            }
        }
        return new TokenSeq(this.file, this.tokens, this.tokens.size() - 1);
    }

    /**
    * Get the next token in forward of the current token seq position. Although dont change the
    * position of the token seq
    */
    public Token next() {
        return this.tokens.get(this.index + 1);
    }

    /**
    * Get the string value of token from next().
    * @see next()
    */
    public String nextStr() {
        return this.tokens.get(this.index + 1).str();
    }


    public Token prev() {
        return this.tokens.get(this.index - 1);
    }

    public String prevStr() {
        return this.tokens.get(this.index - 1).str();
    }

    /**
    * Is the current token seq at the end of the seq.
    */
    public boolean eof() {
        return this.index >= this.tokens.size();
    }

    /**
    * For development purposes, e.g if something is not yet impleted just skip all the tokens till
    * the next declaration token is found (i.e. func, struct, module etc). If non found a token
    * seq at eof will be returned
    */
    public TokenSeq toNextDeclaration() {
        for (int i = this.index + 1; i < this.tokens.size(); i++) {
            Token token = this.tokens.get(i);
            if (token.isKeyword(Keyword.MODULE)) {
                return new TokenSeq(this.file, this.tokens, i);
            }
            if (token.isKeyword(Keyword.PACKAGE)) {
                return new TokenSeq(this.file, this.tokens, i);
            }
            if (token.isKeyword(Keyword.STRUCT)) {
                return new TokenSeq(this.file, this.tokens, i);
            }
            if (token.isKeyword(Keyword.FUNC)) {
                return new TokenSeq(this.file, this.tokens, i);
            }
        }
        return this.toLast();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.tokens.size(); i++) {
            builder.append(tokens.get(i).str());
        }
        return builder.toString();
    }

}
