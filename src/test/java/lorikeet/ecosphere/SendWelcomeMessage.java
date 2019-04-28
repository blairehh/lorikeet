package lorikeet.ecosphere;

import lorikeet.ecosphere.meta.Tag;

import java.time.Instant;

public class SendWelcomeMessage implements Edict2<Instant, String, String> {

    public Instant sendWelcomeMessage(String email, String message) {
        return Instant.now();
    }

    @Override
    public Instant invoke(String email, @Tag("message") String message) {
        return this.sendWelcomeMessage(email, message);
    }

    @Override
    public void inject(Plug plug) {
        
    }

    @Override
    public Meta getMeta() {
        return Meta.parameters("email");
    }
}
