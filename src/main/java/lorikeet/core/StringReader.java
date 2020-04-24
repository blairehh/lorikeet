package lorikeet.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class StringReader {
    private final InputStream inputStream;

    public StringReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Fallible<String> readAll() {
        try {
            final int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];
            final StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(this.inputStream, StandardCharsets.UTF_8);
            int charsRead;
            while((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
                out.append(buffer, 0, charsRead);
            }
            return new Ok<>(out.toString());
        } catch (IOException ioe) {
            return new Err<>(ioe);
        }
    }
}
