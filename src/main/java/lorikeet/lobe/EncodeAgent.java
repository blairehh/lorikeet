package lorikeet.lobe;

import java.util.Optional;

public interface EncodeAgent<R, O> {
    ResourceInsignia resourceInsignia();

    O junction(R resources);

    default Optional<String> mediaType() {
        return Optional.empty();
    }

    default EncodeAgent<R, O> withSession(Object session) {
        return this;
    }
}
