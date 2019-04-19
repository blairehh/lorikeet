package lorikeet.web;

public interface IncomingRequestErrorHandler {
    public void handle(IncomingRequest request, OutgoingResponse response, Throwable error);
}
