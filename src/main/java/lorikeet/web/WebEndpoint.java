package lorikeet.web;

public interface WebEndpoint {
    public EndpointAction receive(IncomingRequest request, OutgoingResponse response);
}
