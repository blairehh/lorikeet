package lorikeet.coding;

import lorikeet.lobe.EncodeAgent;
import lorikeet.lobe.ResourceInsignia;
import lorikeet.lobe.UsesLogging;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class Base64Encode<R extends UsesLogging> implements EncodeAgent<R, String> {
    private final byte[] data;

    public Base64Encode(byte[] data) {
        this.data = data;
    }

    public Base64Encode(String str) {
        this.data = str.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String junction(R resources) {
        return new String(Base64.getEncoder().encode(this.data));
    }

    @Override
    public ResourceInsignia resourceInsignia() {
        // @TODO create insignia for this
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Base64Encode<?> that = (Base64Encode<?>) o;

        return Objects.equals(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
