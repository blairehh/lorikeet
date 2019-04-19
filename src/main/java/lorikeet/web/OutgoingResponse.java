package lorikeet.web;

public interface OutgoingResponse {
    public void reply(int statusCode, String content);
}
