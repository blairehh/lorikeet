package lorikeet.web;

import lorikeet.Seq;
import lorikeet.web.impl.Default404Handler;
import lorikeet.web.impl.DefaultServerErrorHandler;

public interface WebRouter {
    public WebDispatcher getDispatcher();

    default Seq<IncomingRequestInterceptor> getIncomingRequestInterceptors() {
        return Seq.empty();
    }

    default public IncomingRequestHandler get404Handler() {
        return new Default404Handler();
    }

    default public IncomingRequestErrorHandler getServerErrorHandler() {
        return new DefaultServerErrorHandler();
    }
}
