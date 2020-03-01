package lorikeet.lobe;

public interface WriteAgent<R, O> {
    O junction(R resources);
}