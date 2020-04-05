package lorikeet.http;

import lorikeet.foreign.UrlMatch;
import lorikeet.foreign.UrlPattern;
import lorikeet.lobe.IncomingHttpMsg;

public class UriPath {
    private final IncomingHttpMsg msg;
    private final String uriPattern;

    public UriPath(IncomingHttpMsg msg, String uriPattern) {
        this.msg = msg;
        this.uriPattern = uriPattern;
    }

    public void include() {
        final UrlPattern pattern = new UrlPattern(this.uriPattern);
        final UrlMatch match = pattern.match(this.msg.uri().toString());
        if (match == null) {
            System.out.println("match");
        } else {
            System.out.println("no match");
        }
    }

}
