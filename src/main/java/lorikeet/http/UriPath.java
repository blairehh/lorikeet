package lorikeet.http;

import lorikeet.core.DictOf;
import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.foreign.UrlMatch;
import lorikeet.foreign.UrlPattern;
import lorikeet.http.error.BadUriPattern;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.error.UriPatternDoesNotMatchUri;
import lorikeet.http.internal.HttpMsgPath;
import lorikeet.http.internal.IncomingHttpSgnlStrop;

import java.util.Map;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class UriPath implements IncomingHttpSgnlStrop<HttpMsgPath> {
    private final String uriPattern;

    public UriPath(String uriPattern) {
        this.uriPattern = uriPattern;
    }

    @Override
    public FallibleResult<HttpMsgPath, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
        if (this.uriPattern == null || this.uriPattern.isBlank()) {
            return new ErrResult<>(new BadUriPattern(this.uriPattern));
        }
        try {
            final UrlPattern pattern = new UrlPattern(this.uriPattern);
            final UrlMatch match = pattern.match(request.uri().toString());
            if (match == null) {
                return new ErrResult<>(new UriPatternDoesNotMatchUri(this.uriPattern, request.uri().toASCIIString()));
            }
            final Map<String, String> variables = match.parameterSet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return new OkResult<>(new HttpMsgPath(request.uri(), new DictOf<>(variables)));
        } catch (PatternSyntaxException e) {
            return new ErrResult<>(new BadUriPattern(this.uriPattern, e));
        }
    }

}
