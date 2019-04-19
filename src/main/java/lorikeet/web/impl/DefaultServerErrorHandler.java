package lorikeet.web.impl;

import lorikeet.web.IncomingRequest;
import lorikeet.web.IncomingRequestErrorHandler;
import lorikeet.web.OutgoingResponse;

public class DefaultServerErrorHandler implements IncomingRequestErrorHandler {
    @Override
    public void handle(IncomingRequest request, OutgoingResponse response, Throwable error) {
        response.reply(500, "");
    }
}
