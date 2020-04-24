package lorikeet.lobe;

public interface DecodeAgent<R, O> {
    ResourceInsignia resourceInsignia();
    O junction(R resources);

    default DecodeAgent<R, O> withSession(Object session) {
        return this;
    }
}
