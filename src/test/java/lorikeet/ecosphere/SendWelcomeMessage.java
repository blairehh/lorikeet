package lorikeet.ecosphere;

import java.time.Instant;

public class SendWelcomeMessage implements Edict1<Instant, String> {

    public Instant sendWelcomeMessage(String email) {
        return Instant.now();
    }

    @Override
    public Instant invoke(String email) {
        return this.sendWelcomeMessage(email);
    }

    @Override
    public void inject(Plug plug) {
        
    }

    @Override
    public Meta getMeta() {
        return Meta.parameters("email");
    }
}
