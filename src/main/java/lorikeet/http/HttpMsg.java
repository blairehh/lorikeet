package lorikeet.http;

import lorikeet.core.Bug;
import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.http.annotation.Delete;
import lorikeet.http.annotation.Get;
import lorikeet.http.annotation.Header;
import lorikeet.http.annotation.Headers;
import lorikeet.http.annotation.Patch;
import lorikeet.http.annotation.PathVar;
import lorikeet.http.annotation.Post;
import lorikeet.http.annotation.Put;
import lorikeet.http.annotation.Query;
import lorikeet.http.annotation.headers.*;
import lorikeet.http.error.AnnotatedHeadersMustBeOfTypeHeaderSet;
import lorikeet.http.error.FailedToConstructHttpMsg;
import lorikeet.http.error.HttpMethodDoesNotMatchRequest;
import lorikeet.http.error.HttpMsgMustHavePath;
import lorikeet.http.error.MsgTypeDidNotHaveAnnotatedCtor;
import lorikeet.http.error.UnsupportedHeaderValueType;
import lorikeet.http.error.UnsupportedPathValueType;
import lorikeet.http.error.UnsupportedQueryParameterValueType;
import lorikeet.http.internal.HeaderAnnotation;
import lorikeet.http.internal.HttpMsgPath;
import lorikeet.http.internal.IdentifierAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class HttpMsg<T> implements IncludableFallible<T> {
    private final IncomingHttpSgnl msg;
    private final Class<T> msgClass;

    public HttpMsg(IncomingHttpSgnl msg, Class<T> msgClass) {
        this.msg = msg;
        this.msgClass = msgClass;
    }

    @Override
    public Fallible<T> include() {
        return this.findCtor()
            .map(this::include)
            .orElse(new Bug<>(new MsgTypeDidNotHaveAnnotatedCtor(this.msgClass)));
    }

    @SuppressWarnings("unchecked")
    private Fallible<T> include(Constructor<T> ctor) {
        final IdentifierAnnotation id = this.retrieveIdentifierAnnotation()
            .orElse(null);
        if (id == null) {
            return new Err<>(new HttpMsgMustHavePath(this.msgClass));
        }

        if (this.msg.method() != id.method()) {
            return new Err<>(new HttpMethodDoesNotMatchRequest());
        }

        final Fallible<HttpMsgPath> pathResult = new UriPath(this.msg, id.uriPattern())
            .include();
        if (pathResult.failure()) {
            return (Fallible<T>)pathResult;
        }
        final HttpMsgPath msgPath = pathResult.orPanic();

        Object[] parameterValues = new Object[ctor.getParameters().length];
        for (int i = 0; i < ctor.getParameters().length; i++) {
            final Parameter parameter = ctor.getParameters()[i];
            final HeaderAnnotation header = this.retrieveHeaderAnnotation(parameter);
            if (header != null) {
                final Fallible<?> result = this.handleHeader(parameter, header);
                if (result.failure()) {
                    return (Fallible<T>) result;
                }
                parameterValues[i] = result.orPanic();
                continue;
            }
            final PathVar pathParam = parameter.getAnnotation(PathVar.class);
            if (pathParam != null) {
                final Fallible<?> result = this.handlePathVar(parameter, msgPath, pathParam);
                if (result.failure()) {
                    return (Fallible<T>) result;
                }
                parameterValues[i] = result.orPanic();
                continue;
            }
            final Query queryParam = parameter.getAnnotation(Query.class);
            if (queryParam != null) {
                final Fallible<?> result = this.handleQueryParam(parameter, queryParam);
                if (result.failure()) {
                    return (Fallible<T>) result;
                }
                parameterValues[i] = result.orPanic();
                continue;
            }
            final Headers headers = parameter.getAnnotation(Headers.class);
            if (headers != null) {
                if (!parameter.getType().equals(HeaderSet.class)) {
                    return new Err<>(new AnnotatedHeadersMustBeOfTypeHeaderSet(this.msgClass));
                }
                parameterValues[i] = this.msg.headers();
                continue;
            }
        }
        try {
            return new Ok<>(ctor.newInstance(parameterValues));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return new Bug<>(new FailedToConstructHttpMsg(this.msgClass, e));
        }
    }

    private Fallible<?> handleHeader(Parameter parameter, HeaderAnnotation header) {
        if (parameter.getType().equals(Integer.class) || parameter.getType().equals(int.class)) {
            return new IntHeader(this.msg, header.headerName()).include();
        }
        if (parameter.getType().equals(Double.class) || parameter.getType().equals(double.class)) {
            return new DoubleHeader(this.msg, header.headerName()).include();
        }
        if (parameter.getType().equals(Long.class) || parameter.getType().equals(long.class)) {
            return new LongHeader(this.msg, header.headerName()).include();
        }
        if (parameter.getType().equals(Boolean.class) || parameter.getType().equals(boolean.class)) {
            return new BoolHeader(this.msg, header.headerName()).include();
        }
        if (parameter.getType().equals(String.class)) {
            return new StringHeader(this.msg, header.headerName()).include();
        }
        return new Bug<>(new UnsupportedHeaderValueType(parameter.getType()));
    }

    private Fallible<?> handlePathVar(Parameter parameter, HttpMsgPath path, PathVar param) {
        if (parameter.getType().equals(Integer.class) || parameter.getType().equals(int.class)) {
            return new IntPathVar(path, param.value()).include();
        }
        if (parameter.getType().equals(Double.class) || parameter.getType().equals(double.class)) {
            return new DoublePathVar(path, param.value()).include();
        }
        if (parameter.getType().equals(Long.class) || parameter.getType().equals(long.class)) {
            return new LongPathVar(path, param.value()).include();
        }
        if (parameter.getType().equals(Boolean.class) || parameter.getType().equals(boolean.class)) {
            return new BoolPathVar(path, param.value()).include();
        }
        if (parameter.getType().equals(String.class)) {
            return new StringPathVar(path, param.value()).include();
        }
        return new Bug<>(new UnsupportedPathValueType(parameter.getType()));
    }

    private Fallible<?> handleQueryParam(Parameter parameter, Query param) {
        if (parameter.getType().equals(Integer.class) || parameter.getType().equals(int.class)) {
            return new IntQueryParam(this.msg, param.value()).include();
        }
        if (parameter.getType().equals(Double.class) || parameter.getType().equals(double.class)) {
            return new DoubleQueryParam(this.msg, param.value()).include();
        }
        if (parameter.getType().equals(Long.class) || parameter.getType().equals(long.class)) {
            return new LongQueryParam(this.msg, param.value()).include();
        }
        if (parameter.getType().equals(Boolean.class) || parameter.getType().equals(boolean.class)) {
            return new BoolQueryParam(this.msg, param.value()).include();
        }
        if (parameter.getType().equals(String.class)) {
            return new StringQueryParam(this.msg, param.value()).include();
        }
        return new Err<>(new UnsupportedQueryParameterValueType(parameter.getType()));
    }

    @SuppressWarnings("unchecked")
    private Optional<Constructor<T>> findCtor() {
        return Arrays.stream(this.msgClass.getConstructors())
            .filter((ctor) -> ctor.getDeclaredAnnotations().length != 0)
            .map((ctor) -> (Constructor<T>)ctor)
            .findFirst();
    }

    private Optional<IdentifierAnnotation> retrieveIdentifierAnnotation() {
        final Get get = this.msgClass.getAnnotation(Get.class);
        if (get != null) {
            return Optional.of(new IdentifierAnnotation(HttpMethod.GET, get.value()));
        }

        final Post post = this.msgClass.getAnnotation(Post.class);
        if (post != null) {
            return Optional.of(new IdentifierAnnotation(HttpMethod.POST, post.value()));
        }

        final Put put = this.msgClass.getAnnotation(Put.class);
        if (put != null) {
            return Optional.of(new IdentifierAnnotation(HttpMethod.PUT, put.value()));
        }

        final Patch patch = this.msgClass.getAnnotation(Patch.class);
        if (patch != null) {
            return Optional.of(new IdentifierAnnotation(HttpMethod.PATCH, patch.value()));
        }

        final Delete delete = this.msgClass.getAnnotation(Delete.class);
        if (delete != null) {
            return Optional.of(new IdentifierAnnotation(HttpMethod.DELETE, delete.value()));
        }

        return Optional.empty();
    }

    private HeaderAnnotation retrieveHeaderAnnotation(Parameter parameter) {
        final Header header = parameter.getAnnotation(Header.class);
        if (header != null) {
            return new HeaderAnnotation(header.value());
        }
        final Accept accept = parameter.getAnnotation(Accept.class);
        if (accept != null) {
            return new HeaderAnnotation(HeaderField.ACCEPT);
        }

        Annotation annotation = parameter.getAnnotation(AcceptCharset.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCEPT_CHARSET);
        }

        annotation = parameter.getAnnotation(AcceptDatetime.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCEPT_DATETIME);
        }

        annotation = parameter.getAnnotation(AcceptEncoding.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCEPT_ENCODING);
        }

        annotation = parameter.getAnnotation(AcceptLanguage.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCEPT_LANGUAGE);
        }

        annotation = parameter.getAnnotation(AcceptPatch.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCEPT_PATCH);
        }

        annotation = parameter.getAnnotation(AcceptRanges.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCEPT_RANGES);
        }

        annotation = parameter.getAnnotation(AccessControlAllowCredentials.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCESS_CONTROL_ALLOW_CREDENTIALS);
        }

        annotation = parameter.getAnnotation(AccessControlAllowHeaders.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCESS_CONTROL_ALLOW_HEADERS);
        }

        annotation = parameter.getAnnotation(AccessControlAllowMethods.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCESS_CONTROL_ALLOW_METHODS);
        }

        annotation = parameter.getAnnotation(AccessControlAllowOrigin.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCESS_CONTROL_ALLOW_ORIGIN);
        }

        annotation = parameter.getAnnotation(AccessControlExposeHeaders.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCESS_CONTROL_EXPOSE_HEADERS);
        }

        annotation = parameter.getAnnotation(AccessControlMaxAge.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCESS_CONTROL_MAX_AGE);
        }

        annotation = parameter.getAnnotation(AccessControlRequestHeaders.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCESS_CONTROL_REQUEST_HEADERS);
        }

        annotation = parameter.getAnnotation(AccessControlRequestMethod.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ACCESS_CONTROL_REQUEST_METHOD);
        }

        annotation = parameter.getAnnotation(Age.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.AGE);
        }

        annotation = parameter.getAnnotation(AIM.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.A_IM);
        }

        annotation = parameter.getAnnotation(Allow.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ALLOW);
        }

        annotation = parameter.getAnnotation(AltSvc.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ALT_SVC);
        }

        annotation = parameter.getAnnotation(Authorization.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.AUTHORIZATION);
        }

        annotation = parameter.getAnnotation(CacheControl.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CACHE_CONTROL);
        }

        annotation = parameter.getAnnotation(Connection.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONNECTION);
        }

        annotation = parameter.getAnnotation(ContentDisposition.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONTENT_DISPOSITION);
        }

        annotation = parameter.getAnnotation(ContentEncoding.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONTENT_ENCODING);
        }

        annotation = parameter.getAnnotation(ContentLanguage.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONTENT_LANGUAGE);
        }

        annotation = parameter.getAnnotation(ContentLength.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONTENT_LENGTH);
        }

        annotation = parameter.getAnnotation(ContentLocation.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONTENT_LOCATION);
        }

        annotation = parameter.getAnnotation(ContentMd5.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONTENT_MD5);
        }

        annotation = parameter.getAnnotation(ContentRange.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONTENT_RANGE);
        }

        annotation = parameter.getAnnotation(ContentSecurityPolicy.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONTENT_SECURITY_POLICY);
        }

        annotation = parameter.getAnnotation(ContentType.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.CONTENT_TYPE);
        }

        annotation = parameter.getAnnotation(Cookie.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.COOKIE);
        }

        annotation = parameter.getAnnotation(Date.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.DATE);
        }

        annotation = parameter.getAnnotation(DeltaBase.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.DELTA_BASE);
        }

        annotation = parameter.getAnnotation(Dnt.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.DNT);
        }

        annotation = parameter.getAnnotation(ETag.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ETAG);
        }

        annotation = parameter.getAnnotation(Expect.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.EXPECT);
        }

        annotation = parameter.getAnnotation(Expires.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.EXPIRES);
        }

        annotation = parameter.getAnnotation(Forwarded.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.FORWARDED);
        }

        annotation = parameter.getAnnotation(From.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.FROM);
        }

        annotation = parameter.getAnnotation(FrontEndHttps.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.FRONT_END_HTTPS);
        }

        annotation = parameter.getAnnotation(Host.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.HOST);
        }

        annotation = parameter.getAnnotation(Http2Settings.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.HTTP2_SETTINGS);
        }

        annotation = parameter.getAnnotation(IfMatch.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.IF_MATCH);
        }

        annotation = parameter.getAnnotation(IfModifiedSince.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.IF_MODIFIED_SINCE);
        }

        annotation = parameter.getAnnotation(IfRange.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.IF_RANGE);
        }

        annotation = parameter.getAnnotation(IfUnmodifiedSince.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.IF_UNMODIFIED_SINCE);
        }

        annotation = parameter.getAnnotation(IM.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.IM);
        }

        annotation = parameter.getAnnotation(LastModified.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.LAST_MODIFIED);
        }

        annotation = parameter.getAnnotation(Link.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.LINK);
        }

        annotation = parameter.getAnnotation(Location.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.LOCATION);
        }

        annotation = parameter.getAnnotation(MaxForwards.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.MAX_FORWARDS);
        }

        annotation = parameter.getAnnotation(Origin.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.ORIGIN);
        }

        annotation = parameter.getAnnotation(P3P.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.P3P);
        }

        annotation = parameter.getAnnotation(Pragma.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.PRAGMA);
        }

        annotation = parameter.getAnnotation(ProxyAuthenticate.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.PROXY_AUTHENTICATE);
        }

        annotation = parameter.getAnnotation(ProxyAuthorization.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.PROXY_AUTHORIZATION);
        }

        annotation = parameter.getAnnotation(ProxyConnection.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.PROXY_CONNECTION);
        }

        annotation = parameter.getAnnotation(PublicKeyPins.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.PUBLIC_KEY_PINS);
        }

        annotation = parameter.getAnnotation(Range.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.RANGE);
        }

        annotation = parameter.getAnnotation(Referer.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.REFERER);
        }

        annotation = parameter.getAnnotation(Refresh.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.REFRESH);
        }

        annotation = parameter.getAnnotation(RetryAfter.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.RETRY_AFTER);
        }

        annotation = parameter.getAnnotation(SaveData.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.SAVE_DATA);
        }

        annotation = parameter.getAnnotation(Server.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.SERVER);
        }

        annotation = parameter.getAnnotation(SetCookie.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.SET_COOKIE);
        }

        annotation = parameter.getAnnotation(Status.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.STATUS);
        }

        annotation = parameter.getAnnotation(StrictTransportSecurity.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.STRICT_TRANSPORT_SECURITY);
        }

        annotation = parameter.getAnnotation(Te.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.TE);
        }

        annotation = parameter.getAnnotation(TimingAllowOrigin.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.TIMING_ALLOW_ORIGIN);
        }

        annotation = parameter.getAnnotation(Tk.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.TK);
        }

        annotation = parameter.getAnnotation(Trailer.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.TRAILER);
        }

        annotation = parameter.getAnnotation(TransferEncoding.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.TRANSFER_ENCODING);
        }

        annotation = parameter.getAnnotation(Upgrade.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.UPGRADE);
        }

        annotation = parameter.getAnnotation(UpgradeInsecureRequests.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.UPGRADE_INSECURE_REQUESTS);
        }

        annotation = parameter.getAnnotation(UserAgent.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.USER_AGENT);
        }

        annotation = parameter.getAnnotation(Vary.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.VARY);
        }

        annotation = parameter.getAnnotation(Via.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.VIA);
        }

        annotation = parameter.getAnnotation(Warning.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.WARNING);
        }

        annotation = parameter.getAnnotation(WwwAuthenticate.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.WWW_AUTHENTICATE);
        }

        annotation = parameter.getAnnotation(XAttDeviceId.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_ATT_DEVICEID);
        }

        annotation = parameter.getAnnotation(XContentDuration.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_CONTENT_DURATION);
        }

        annotation = parameter.getAnnotation(XContentSecurityPolicy.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_CONTENT_SECURITY_POLICY);
        }

        annotation = parameter.getAnnotation(XContentTypeOptions.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_CONTENT_TYPE_OPTIONS);
        }

        annotation = parameter.getAnnotation(XCorrelationId.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_CORRELATION_ID);
        }

        annotation = parameter.getAnnotation(XCsrfToken.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_CSRF_TOKEN);
        }

        annotation = parameter.getAnnotation(XForwardedFor.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_FORWARDED_FOR);
        }

        annotation = parameter.getAnnotation(XForwardedHost.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_FORWARDED_HOST);
        }

        annotation = parameter.getAnnotation(XForwardedProto.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_FORWARDED_PROTO);
        }

        annotation = parameter.getAnnotation(XFrameOptions.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_FRAME_OPTIONS);
        }

        annotation = parameter.getAnnotation(XHttpMethodOverride.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_HTTP_METHOD_OVERRIDE);
        }

        annotation = parameter.getAnnotation(XPoweredBy.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_POWERED_BY);
        }

        annotation = parameter.getAnnotation(XRequestedWith.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_REQUESTED_WITH);
        }

        annotation = parameter.getAnnotation(XRequestId.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_REQUEST_ID);
        }

        annotation = parameter.getAnnotation(XUaCompatible.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_UA_COMPATIBLE);
        }

        annotation = parameter.getAnnotation(XUidh.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_UIDH);
        }

        annotation = parameter.getAnnotation(XWapProfile.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_WAP_PROFILE);
        }

        annotation = parameter.getAnnotation(XWebkitCsp.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_WEBKIT_CSP);
        }

        annotation = parameter.getAnnotation(XXssProtection.class);
        if (annotation != null) {
            return new HeaderAnnotation(HeaderField.X_XSS_PROTECTION);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpMsg<?> httpMsgOf = (HttpMsg<?>) o;

        return Objects.equals(this.msg, httpMsgOf.msg)
            && Objects.equals(this.msgClass, httpMsgOf.msgClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msg, this.msgClass);
    }
}
