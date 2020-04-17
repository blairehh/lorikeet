package lorikeet.http;

import java.util.function.Supplier;

public interface HttpReplier extends Supplier<HttpReply> {
    HttpReply reply();

    @Override
    default HttpReply get() {
        return this.reply();
    }
}
