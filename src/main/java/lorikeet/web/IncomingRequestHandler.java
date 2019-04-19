package lorikeet.web;

public interface IncomingRequestHandler {
    public void handle(IncomingRequest request, OutgoingResponse response);
}
