package lorikeet.lobe;

public interface ProvidesLogging extends UsesLogging {
    LoggingResource provideLogging();

    @Override
    default LoggingResource useLogging() {
        return provideLogging();
    }
}