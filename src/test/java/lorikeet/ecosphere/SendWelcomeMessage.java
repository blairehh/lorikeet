package lorikeet.ecosphere;

import lorikeet.ecosphere.articletesting.meta.Dbg;

import java.time.Instant;

public class SendWelcomeMessage implements Action2<Instant, String, String> {

    public Instant sendWelcomeMessage(String email, String message) {
        return Instant.now();
    }

    @Override
    public Instant invoke(String email, @Dbg("message") String message) {
        return this.sendWelcomeMessage(email, message);
    }

    @Override
    public void inject(Axon axon) {
        
    }
}
