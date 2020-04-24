package lorikeet.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StringInputStream extends InputStream {

    private final ByteArrayInputStream inputStream;

    public StringInputStream(String content) {
        this.inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.inputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.inputStream.read(b, off, len);
    }

    @Override
    public byte[] readAllBytes() throws IOException {
        return this.inputStream.readAllBytes();
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        return this.inputStream.readNBytes(len);
    }

    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
        return this.inputStream.readNBytes(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return this.inputStream.skip(n);
    }

    @Override
    public void skipNBytes(long n) throws IOException {
        this.inputStream.skipNBytes(n);
    }

    @Override
    public int available() throws IOException {
        return this.inputStream.available();
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        this.inputStream.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.inputStream.reset();
    }

    @Override
    public boolean markSupported() {
        return this.inputStream.markSupported();
    }

    @Override
    public long transferTo(OutputStream out) throws IOException {
        return this.inputStream.transferTo(out);
    }

    @Override
    public int read() throws IOException {
        return this.inputStream.read();
    }
}
