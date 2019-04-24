package lorikeet.container;

public interface Container {
    default Meta getMeta() {
        return Meta.none();
    }
}
