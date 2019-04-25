package lorikeet.ecosphere;

public interface Crate {
    default Meta getMeta() {
        return Meta.none();
    }
}
