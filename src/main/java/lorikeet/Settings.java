package lorikeet;

public class Settings {

    private final String sourceDirectory;
    private final String buildDirectory;
    private final String javaDirectory;

    public Settings(String source, String build) {
        this.sourceDirectory = source;
        this.buildDirectory = build.endsWith("/")
            ? build
            : build + "/";
        this.javaDirectory = this.buildDirectory + "java/";
    }

    public String getSourceDirectory() {
        return this.sourceDirectory;
    }

    public String getBuildDirectory() {
        return this.buildDirectory;
    }

    public String getJavaDirectory() {
        return this.javaDirectory;
    }
}
