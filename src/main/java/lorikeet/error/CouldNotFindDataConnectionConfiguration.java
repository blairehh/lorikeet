package lorikeet.error;

public class CouldNotFindDataConnectionConfiguration extends LorikeetException {

    private final Class<?> configurationClass;

    public CouldNotFindDataConnectionConfiguration(Class<?> configurationClass) {
        super(CouldNotFindDataConnectionConfiguration.class);
        this.configurationClass = configurationClass;
    }

    @Override
    public boolean isFatal() {
        return true;
    }
}
