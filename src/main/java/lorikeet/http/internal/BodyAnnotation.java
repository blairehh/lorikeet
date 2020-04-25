package lorikeet.http.internal;

// @TODO add equals and hashCode
public class BodyAnnotation {
    private final String mediaType;

    public BodyAnnotation(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaType() {
        return mediaType;
    }
}
