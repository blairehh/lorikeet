package lorikeet.web;

public interface IncomingRequestHandler {
    public EndpointAction handle(IncomingRequest request, OutgoingResponse response);
}
