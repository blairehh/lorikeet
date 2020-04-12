package lorikeet.lobe;

import lorikeet.core.Seq;
import lorikeet.core.SeqOf;

import java.util.function.Function;
import java.util.function.Predicate;

public class DefaultTract<R extends UsesLogging> implements Tract<R> {

    private final R resources;
    private final Seq<TractSession> sessions;

    public DefaultTract(R resources) {
        this.resources = resources;
        this.sessions = new SeqOf<>();
    }

    private DefaultTract(R resources, Seq<TractSession> sessions) {
        this.resources = resources;
        this.sessions = sessions;
    }

    @Override
    public Tract<R> session(TractSession session) {
        return new DefaultTract<>(this.resources, this.sessions.affix(session));
    }

    @Override
    public <O> O write(WriteAgent<R, O> write) {
        final Predicate<TractSession> belongsToSession = (sessions)
            -> sessions.resourceInsignia().equals(write.resourceInsignia());

        final Function<TractSession, O> executeWithSession = (session)
            -> write.withSession(session.sessionObject()).junction(this.resources);

        return this.sessions
            .pickFirst(belongsToSession)
            .map(executeWithSession)
            .orElseGet(() -> write.junction(this.resources));
    }

    @Override
    public <O> O invoke(LorikeetAction<R, O> action) {
        return action.junction(this);
    }

    @Override
    public void log(String fmt, Object...vars) {
        this.resources.useLogging().log(LogGrade.INFO, fmt, vars);
    }
}