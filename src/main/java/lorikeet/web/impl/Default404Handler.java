package lorikeet.web.impl;

import lorikeet.web.IncomingRequest;
import lorikeet.web.IncomingRequestHandler;
import lorikeet.web.OutgoingResponse;

public class Default404Handler implements IncomingRequestHandler {
    @Override
    public void handle(IncomingRequest request, OutgoingResponse response) {
        response.reply(404, request.getURI().toASCIIString() + " not found");
    }
}
