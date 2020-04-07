package lorikeet.http;

import lorikeet.core.Bug;
import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.foreign.UrlMatch;
import lorikeet.foreign.UrlPattern;
import lorikeet.http.error.BadUriPattern;
import lorikeet.http.error.UriPatternDoesNotMatchUri;
import lorikeet.lobe.IncomingHttpMsg;

import java.net.URI;
import java.util.regex.PatternSyntaxException;

public class UriPath implements IncludableFallible<URI> {
    private final IncomingHttpMsg msg;
    private final String uriPattern;

    public UriPath(IncomingHttpMsg msg, String uriPattern) {
        this.msg = msg;
        this.uriPattern = uriPattern;
    }

    @Override
    public Fallible<URI> include() {
        if (this.uriPattern == null || this.uriPattern.isBlank()) {
            return new Err<>(new BadUriPattern(this.uriPattern));
        }
        try {
            final UrlPattern pattern = new UrlPattern(this.uriPattern);
            final UrlMatch match = pattern.match(this.msg.uri().toString());
            if (match == null) {
                return new Err<>(new UriPatternDoesNotMatchUri(this.uriPattern, this.msg.uri().toASCIIString()));
            }
            return new Ok<>(this.msg.uri());
        } catch (PatternSyntaxException e) {
            return new Bug<>(new BadUriPattern(this.uriPattern, e));
        }
    }

}
