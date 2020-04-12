package lorikeet.http;

public interface OutgoingHttpSgnl {
    OutgoingHttpSgnl statusCode(int statusCode);
    OutgoingHttpSgnl header(String name, String value);
    OutgoingHttpSgnl writeBody(String content);
}
