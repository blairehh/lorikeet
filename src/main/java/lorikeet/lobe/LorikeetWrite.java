package lorikeet.lobe;

public interface LorikeetWrite<R, O> {
    O junction(R resources);
}