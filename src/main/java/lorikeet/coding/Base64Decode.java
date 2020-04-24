package lorikeet.coding;

import lorikeet.lobe.DecodeAgent;
import lorikeet.lobe.ResourceInsignia;
import lorikeet.lobe.UsesLogging;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Decode<R extends UsesLogging> implements DecodeAgent<R, String> {
    private final byte[] data;

    public Base64Decode(byte[] data) {
        this.data = data;
    }

    public Base64Decode(String str) {
        this.data = str.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String junction(R resources) {
        return new String(Base64.getDecoder().decode(this.data));
    }

    @Override
    public ResourceInsignia resourceInsignia() {
        return null;
    }
}
