package lorikeet.lobe;

public interface EncodeAgent<R, O> {
    ResourceInsignia resourceInsignia();
    O junction(R resources);

    default EncodeAgent<R, O> withSession(Object session) {
        return this;
    }
}
