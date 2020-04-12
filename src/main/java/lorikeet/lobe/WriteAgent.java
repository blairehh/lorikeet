package lorikeet.lobe;

public interface WriteAgent<R, O> {
    ResourceInsignia resourceInsignia();
    O junction(R resources);

    default WriteAgent<R, O> withSession(Object session) {
        return this;
    }
}