package lorikeet.web;

import lorikeet.web.impl.Default404Handler;

public interface WebRouter {
    public WebDispatcher getDispatcher();

    default public IncomingRequestHandler get404Handler() {
        return new Default404Handler();
    }
}
