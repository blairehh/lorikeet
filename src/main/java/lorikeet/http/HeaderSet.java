package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;

import java.util.Objects;

public class HeaderSet {
    private final Dict<String, Seq<String>> headers;

    public HeaderSet() {
        this.headers = new DictOf<>();
    }

    public HeaderSet(Dict<String, Seq<String>> headers) {
        this.headers = headers;
    }


    public HeaderSet set(String name, String value) {
        if (name == null || name.isBlank() || value == null || value.isBlank()) {
            return this;
        }
        final Dict<String, Seq<String>> mod = this.headers.modify(
            name,
            (seq) -> seq.affix(value),
            () -> new SeqOf<>(value)
        );
        return new HeaderSet(mod);
    }

    public HeaderSet set(HeaderField field, String value) {
        return this.set(field.key(), value);
    }

    public String getAny(String name) {
        return this.headers.pick(name)
            .flatMap(Seq::first)
            .orElse("");
    }

    public Seq<String> getAll(String name) {
        return this.headers.pick(name)
            .orElse(new SeqOf<>());
    }

    public String getAny(HeaderField header) {
        return this.headers.pick(header.key())
            .flatMap(Seq::first)
            .orElse("");
    }

    public Seq<String> getAll(HeaderField header) {
        return this.headers.pick(header.key())
            .orElse(new SeqOf<>());
    }

    public String aIm() {
        return this.getAny(HeaderField.A_IM);
    }

    public HeaderSet aIm(String value) {
        return this.set(HeaderField.A_IM, value);
    }

    public String accept() {
        return this.getAny(HeaderField.ACCEPT);
    }

    public HeaderSet accept(String value) {
        return this.set(HeaderField.ACCEPT, value);
    }

    public String acceptCharset() {
        return this.getAny(HeaderField.ACCEPT_CHARSET);
    }

    public HeaderSet acceptCharset(String value) {
        return this.set(HeaderField.ACCEPT_CHARSET, value);
    }

    public String acceptDatetime() {
        return this.getAny(HeaderField.ACCEPT_DATETIME);
    }

    public HeaderSet acceptDatetime(String value) {
        return this.set(HeaderField.ACCEPT_DATETIME, value);
    }

    public String acceptEncoding() {
        return this.getAny(HeaderField.ACCEPT_ENCODING);
    }

    public HeaderSet acceptEncoding(String value) {
        return this.set(HeaderField.ACCEPT_ENCODING, value);
    }

    public String acceptLanguage() {
        return this.getAny(HeaderField.ACCEPT_LANGUAGE);
    }

    public HeaderSet acceptLanguage(String value) {
        return this.set(HeaderField.ACCEPT_LANGUAGE, value);
    }

    public String accessControlAllowOrigin() {
        return this.getAny(HeaderField.ACCESS_CONTROL_ALLOW_ORIGIN);
    }

    public HeaderSet accessControlAllowOrigin(String value) {
        return this.set(HeaderField.ACCESS_CONTROL_ALLOW_ORIGIN, value);
    }

    public String accessControlAllowCredentials() {
        return this.getAny(HeaderField.ACCESS_CONTROL_ALLOW_CREDENTIALS);
    }

    public HeaderSet accessControlAllowCredentials(String value) {
        return this.set(HeaderField.ACCESS_CONTROL_EXPOSE_HEADERS, value);
    }

    public String accessControlExposeHeaders() {
        return this.getAny(HeaderField.ACCESS_CONTROL_EXPOSE_HEADERS);
    }

    public HeaderSet accessControlExposeHeaders(String value) {
        return this.set(HeaderField.ACCESS_CONTROL_EXPOSE_HEADERS, value);
    }

    public String accessControlMaxAge() {
        return this.getAny(HeaderField.ACCESS_CONTROL_MAX_AGE);
    }

    public HeaderSet accessControlMaxAge(String value) {
        return this.set(HeaderField.ACCESS_CONTROL_MAX_AGE, value);
    }

    public String accessControlAllowMethods() {
        return this.getAny(HeaderField.ACCESS_CONTROL_ALLOW_METHODS);
    }

    public HeaderSet accessControlAllowMethods(String value) {
        return this.set(HeaderField.ACCESS_CONTROL_ALLOW_METHODS, value);
    }

    public String accessControlAllowHeaders() {
        return this.getAny(HeaderField.ACCESS_CONTROL_ALLOW_HEADERS);
    }

    public HeaderSet accessControlAllowHeaders(String value) {
        return this.set(HeaderField.ACCESS_CONTROL_ALLOW_HEADERS, value);
    }

    public String accessControlRequestMethod() {
        return this.getAny(HeaderField.ACCESS_CONTROL_REQUEST_METHOD);
    }

    public HeaderSet accessControlRequestMethod(String value) {
        return this.set(HeaderField.ACCESS_CONTROL_REQUEST_METHOD, value);
    }

    public String accessControlRequestHeaders() {
        return this.getAny(HeaderField.ACCESS_CONTROL_REQUEST_HEADERS);
    }

    public HeaderSet accessControlRequestHeaders(String value) {
        return this.set(HeaderField.ACCESS_CONTROL_REQUEST_HEADERS, value);
    }

    public String acceptPatch() {
        return this.getAny(HeaderField.ACCEPT_PATCH);
    }

    public HeaderSet acceptPatch(String value) {
        return this.set(HeaderField.ACCEPT_PATCH, value);
    }

    public String acceptRanges() {
        return this.getAny(HeaderField.ACCEPT_RANGES);
    }

    public HeaderSet acceptRanges(String value) {
        return this.set(HeaderField.ACCEPT_RANGES, value);
    }

    public String age() {
        return this.getAny(HeaderField.AGE);
    }

    public HeaderSet age(String value) {
        return this.set(HeaderField.AGE, value);
    }

    public String allow() {
        return this.getAny(HeaderField.ALLOW);
    }

    public HeaderSet allow(String value) {
        return this.set(HeaderField.ALLOW, value);
    }

    public String altSvc() {
        return this.getAny(HeaderField.ALT_SVC);
    }

    public HeaderSet altSvc(String value) {
        return this.set(HeaderField.ALT_SVC, value);
    }
    
    public String authorization() {
        return this.getAny(HeaderField.AUTHORIZATION);
    }

    public HeaderSet authorization(String value) {
        return this.set(HeaderField.AUTHORIZATION, value);
    }

    public String cacheControl() {
        return this.getAny(HeaderField.CACHE_CONTROL);
    }

    public HeaderSet cacheControl(String value) {
        return this.set(HeaderField.CACHE_CONTROL, value);
    }

    public String connection() {
        return this.getAny(HeaderField.CONNECTION);
    }

    public HeaderSet connection(String value) {
        return this.set(HeaderField.CONNECTION, value);
    }

    public String contentDisposition() {
        return this.getAny(HeaderField.CONTENT_DISPOSITION);
    }

    public HeaderSet contentDisposition(String value) {
        return this.set(HeaderField.CONTENT_DISPOSITION, value);
    }
    
    public String contentEncoding() {
        return this.getAny(HeaderField.CONTENT_ENCODING);
    }

    public HeaderSet contentEncoding(String value) {
        return this.set(HeaderField.CONTENT_ENCODING, value);
    }

    public String contentLanguage() {
        return this.getAny(HeaderField.CONTENT_LANGUAGE);
    }

    public HeaderSet contentLanguage(String value) {
        return this.set(HeaderField.CONTENT_LANGUAGE, value);
    }

    public String contentLength() {
        return this.getAny(HeaderField.CONTENT_LENGTH);
    }

    public HeaderSet contentLength(String value) {
        return this.set(HeaderField.CONTENT_LENGTH, value);
    }

    public String contentLocation() {
        return this.getAny(HeaderField.CONTENT_LOCATION);
    }

    public HeaderSet contentLocation(String value) {
        return this.set(HeaderField.CONTENT_LOCATION, value);
    }

    public String contentMd5() {
        return this.getAny(HeaderField.CONTENT_MD5);
    }

    public HeaderSet contentMd5(String value) {
        return this.set(HeaderField.CONTENT_MD5, value);
    }

    public String contentType() {
        return this.getAny(HeaderField.CONTENT_TYPE);
    }

    public HeaderSet contentType(String value) {
        return this.set(HeaderField.CONTENT_TYPE, value);
    }

    public String contentRange() {
        return this.getAny(HeaderField.CONTENT_RANGE);
    }

    public HeaderSet contentRange(String value) {
        return this.set(HeaderField.CONTENT_RANGE, value);
    }

    public String cookie() {
        return this.getAny(HeaderField.COOKIE);
    }

    public HeaderSet cookie(String value) {
        return this.set(HeaderField.COOKIE, value);
    }

    public String date() {
        return this.getAny(HeaderField.DATE);
    }

    public HeaderSet date(String value) {
        return this.set(HeaderField.DATE, value);
    }

    public String deltaBase() {
        return this.getAny(HeaderField.DELTA_BASE);
    }

    public HeaderSet deltaBase(String value) {
        return this.set(HeaderField.DELTA_BASE, value);
    }

    public String etag() {
        return this.getAny(HeaderField.ETAG);
    }

    public HeaderSet etag(String value) {
        return this.set(HeaderField.ETAG, value);
    }

    public String expect() {
        return this.getAny(HeaderField.EXPECT);
    }

    public HeaderSet expect(String value) {
        return this.set(HeaderField.EXPECT, value);
    }

    public String expires() {
        return this.getAny(HeaderField.EXPIRES);
    }

    public HeaderSet expires(String value) {
        return this.set(HeaderField.EXPIRES, value);
    }

    public String forwarded() {
        return this.getAny(HeaderField.FORWARDED);
    }

    public HeaderSet forwarded(String value) {
        return this.set(HeaderField.FORWARDED, value);
    }

    public String from() {
        return this.getAny(HeaderField.FROM);
    }

    public HeaderSet from(String value) {
        return this.set(HeaderField.FROM, value);
    }

    public String host() {
        return this.getAny(HeaderField.HOST);
    }

    public HeaderSet host(String value) {
        return this.set(HeaderField.HOST, value);
    }

    public String http2Settings() {
        return this.getAny(HeaderField.HTTP2_SETTINGS);
    }

    public HeaderSet http2Settings(String value) {
        return this.set(HeaderField.HTTP2_SETTINGS, value);
    }

    public String im() {
        return this.getAny(HeaderField.IM);
    }

    public HeaderSet im(String value) {
        return this.set(HeaderField.IM, value);
    }

    public String ifMatch() {
        return this.getAny(HeaderField.IF_MATCH);
    }

    public HeaderSet ifMatch(String value) {
        return this.set(HeaderField.IF_MATCH, value);
    }

    public String ifModifiedSince() {
        return this.getAny(HeaderField.IF_MODIFIED_SINCE);
    }

    public HeaderSet ifModifiedSince(String value) {
        return this.set(HeaderField.IF_MODIFIED_SINCE, value);
    }

    public String ifNoneMatch() {
        return this.getAny(HeaderField.IF_NONE_MATCH);
    }

    public HeaderSet ifNoneMatch(String value) {
        return this.set(HeaderField.IF_NONE_MATCH, value);
    }

    public String ifRange() {
        return this.getAny(HeaderField.IF_RANGE);
    }

    public HeaderSet ifRange(String value) {
        return this.set(HeaderField.IF_RANGE, value);
    }

    public String ifUnmodifiedSince() {
        return this.getAny(HeaderField.IF_UNMODIFIED_SINCE);
    }

    public HeaderSet ifUnmodifiedSince(String value) {
        return this.set(HeaderField.IF_UNMODIFIED_SINCE, value);
    }

    public String lastModified() {
        return this.getAny(HeaderField.LAST_MODIFIED);
    }

    public HeaderSet lastModified(String value) {
        return this.set(HeaderField.LAST_MODIFIED, value);
    }
    
    public String link() {
        return this.getAny(HeaderField.LINK);
    }

    public HeaderSet link(String value) {
        return this.set(HeaderField.LINK, value);
    }

    public String location() {
        return this.getAny(HeaderField.LOCATION);
    }

    public HeaderSet location(String value) {
        return this.set(HeaderField.LOCATION, value);
    }

    public String maxForwards() {
        return this.getAny(HeaderField.MAX_FORWARDS);
    }

    public HeaderSet maxForwards(String value) {
        return this.set(HeaderField.MAX_FORWARDS, value);
    }

    public String origin() {
        return this.getAny(HeaderField.ORIGIN);
    }

    public HeaderSet origin(String value) {
        return this.set(HeaderField.ORIGIN, value);
    }
    
    public String p3p() {
        return this.getAny(HeaderField.P3P);
    }

    public HeaderSet p3p(String value) {
        return this.set(HeaderField.P3P, value);
    }
    
    public String pragma() {
        return this.getAny(HeaderField.PRAGMA);
    }

    public HeaderSet pragma(String value) {
        return this.set(HeaderField.PRAGMA, value);
    }

    public String proxyAuthorization() {
        return this.getAny(HeaderField.PROXY_AUTHORIZATION);
    }

    public HeaderSet proxyAuthorization(String value) {
        return this.set(HeaderField.PROXY_AUTHORIZATION, value);
    }

    public String proxyAuthenticate() {
        return this.getAny(HeaderField.PROXY_AUTHENTICATE);
    }

    public HeaderSet proxyAuthenticate(String value) {
        return this.set(HeaderField.PROXY_AUTHENTICATE, value);
    }

    public String publicKeyPins() {
        return this.getAny(HeaderField.PUBLIC_KEY_PINS);
    }

    public HeaderSet publicKeyPins(String value) {
        return this.set(HeaderField.PUBLIC_KEY_PINS, value);
    }

    public String range() {
        return this.getAny(HeaderField.RANGE);
    }

    public HeaderSet range(String value) {
        return this.set(HeaderField.RANGE, value);
    }

    public String referer() {
        return this.getAny(HeaderField.REFERER);
    }

    public HeaderSet referer(String value) {
        return this.set(HeaderField.REFERER, value);
    }

    public String retryAfter() {
        return this.getAny(HeaderField.RETRY_AFTER);
    }

    public HeaderSet retryAfter(String value) {
        return this.set(HeaderField.RETRY_AFTER, value);
    }

    public String server() {
        return this.getAny(HeaderField.SERVER);
    }

    public HeaderSet server(String value) {
        return this.set(HeaderField.SERVER, value);
    }

    public String setCookie() {
        return this.getAny(HeaderField.SET_COOKIE);
    }

    public HeaderSet setCookie(String value) {
        return this.set(HeaderField.SET_COOKIE, value);
    }

    public String strictTransportSecurity() {
        return this.getAny(HeaderField.STRICT_TRANSPORT_SECURITY);
    }

    public HeaderSet strictTransportSecurity(String value) {
        return this.set(HeaderField.STRICT_TRANSPORT_SECURITY, value);
    }

    public String te() {
        return this.getAny(HeaderField.TE);
    }

    public HeaderSet te(String value) {
        return this.set(HeaderField.TE, value);
    }

    public String tk() {
        return this.getAny(HeaderField.TK);
    }

    public HeaderSet tk(String value) {
        return this.set(HeaderField.TK, value);
    }

    public String trailer() {
        return this.getAny(HeaderField.TRAILER);
    }

    public HeaderSet trailer(String value) {
        return this.set(HeaderField.TRAILER, value);
    }

    public String transferEncoding() {
        return this.getAny(HeaderField.TRANSFER_ENCODING);
    }

    public HeaderSet transferEncoding(String value) {
        return this.set(HeaderField.TRANSFER_ENCODING, value);
    }

    public String userAgent() {
        return this.getAny(HeaderField.USER_AGENT);
    }

    public HeaderSet userAgent(String value) {
        return this.set(HeaderField.USER_AGENT, value);
    }

    public String upgrade() {
        return this.getAny(HeaderField.UPGRADE);
    }

    public HeaderSet upgrade(String value) {
        return this.set(HeaderField.UPGRADE, value);
    }
    
    public String vary() {
        return this.getAny(HeaderField.VARY);
    }

    public HeaderSet vary(String value) {
        return this.set(HeaderField.VARY, value);
    }

    public String via() {
        return this.getAny(HeaderField.VIA);
    }

    public HeaderSet via(String value) {
        return this.set(HeaderField.VIA, value);
    }

    public String warning() {
        return this.getAny(HeaderField.WARNING);
    }

    public HeaderSet warning(String value) {
        return this.set(HeaderField.WARNING, value);
    }

    public String wwwAuthenticate() {
        return this.getAny(HeaderField.WWW_AUTHENTICATE);
    }

    public HeaderSet wwwAuthenticate(String value) {
        return this.set(HeaderField.WWW_AUTHENTICATE, value);
    }

    public String xFrameOptions() {
        return this.getAny(HeaderField.X_FRAME_OPTIONS);
    }

    public HeaderSet xFrameOptions(String value) {
        return this.set(HeaderField.X_FRAME_OPTIONS, value);
    }

    // Non Standard

    public String upgradeInsecureRequests() {
        return this.getAny(HeaderField.UPGRADE_INSECURE_REQUESTS);
    }

    public HeaderSet upgradeInsecureRequests(String value) {
        return this.set(HeaderField.UPGRADE_INSECURE_REQUESTS, value);
    }

    public String xRequestedWith() {
        return this.getAny(HeaderField.X_REQUESTED_WITH);
    }

    public HeaderSet xRequestedWith(String value) {
        return this.set(HeaderField.X_REQUESTED_WITH, value);
    }

    public String dnt() {
        return this.getAny(HeaderField.DNT);
    }

    public HeaderSet dnt(String value) {
        return this.set(HeaderField.DNT, value);
    }

    public String xForwardedFor() {
        return this.getAny(HeaderField.X_FORWARDED_FOR);
    }

    public HeaderSet xForwardedFor(String value) {
        return this.set(HeaderField.X_FORWARDED_FOR, value);
    }

    public String xForwardedHost() {
        return this.getAny(HeaderField.X_FORWARDED_HOST);
    }

    public HeaderSet xForwardedHost(String value) {
        return this.set(HeaderField.X_FORWARDED_HOST, value);
    }

    public String xForwardedProto() {
        return this.getAny(HeaderField.X_FORWARDED_PROTO);
    }

    public HeaderSet xForwardedProto(String value) {
        return this.set(HeaderField.X_FORWARDED_PROTO, value);
    }

    public String frontEndHttps() {
        return this.getAny(HeaderField.FRONT_END_HTTPS);
    }

    public HeaderSet frontEndHttps(String value) {
        return this.set(HeaderField.FRONT_END_HTTPS, value);
    }

    public String xHttpMethodOverride() {
        return this.getAny(HeaderField.X_HTTP_METHOD_OVERRIDE);
    }

    public HeaderSet xHttpMethodOverride(String value) {
        return this.set(HeaderField.X_HTTP_METHOD_OVERRIDE, value);
    }

    public String xAttDeviceId() {
        return this.getAny(HeaderField.X_ATT_DEVICEID);
    }

    public HeaderSet aAttDeviceId(String value) {
        return this.set(HeaderField.X_ATT_DEVICEID, value);
    }

    public String xWapProfile() {
        return this.getAny(HeaderField.X_WAP_PROFILE);
    }

    public HeaderSet xWapProfile(String value) {
        return this.set(HeaderField.X_WAP_PROFILE, value);
    }

    public String proxyConnection() {
        return this.getAny(HeaderField.PROXY_CONNECTION);
    }

    public HeaderSet proxyConnection(String value) {
        return this.set(HeaderField.PROXY_CONNECTION, value);
    }

    public String xUidh() {
        return this.getAny(HeaderField.X_UIDH);
    }

    public HeaderSet xUidh(String value) {
        return this.set(HeaderField.X_UIDH, value);
    }

    public String xCsrfToken() {
        return this.getAny(HeaderField.X_CSRF_TOKEN);
    }

    public HeaderSet xCsrfToken(String value) {
        return this.set(HeaderField.X_CSRF_TOKEN, value);
    }

    public String xRequestId() {
        return this.getAny(HeaderField.X_REQUEST_ID);
    }

    public HeaderSet xRequestId(String value) {
        return this.set(HeaderField.X_REQUEST_ID, value);
    }

    public String xCorrelationId() {
        return this.getAny(HeaderField.X_CORRELATION_ID);
    }

    public HeaderSet xCorrelationId(String value) {
        return this.set(HeaderField.X_CORRELATION_ID, value);
    }

    public String saveData() {
        return this.getAny(HeaderField.SAVE_DATA);
    }

    public HeaderSet saveData(String value) {
        return this.set(HeaderField.SAVE_DATA, value);
    }

    public String contentSecurityPolicy() {
        return this.getAny(HeaderField.CONTENT_SECURITY_POLICY);
    }

    public HeaderSet contentSecurityPolicy(String value) {
        return this.set(HeaderField.CONTENT_SECURITY_POLICY, value);
    }

    public String xContentSecurityPolicy() {
        return this.getAny(HeaderField.X_CONTENT_SECURITY_POLICY);
    }

    public HeaderSet xContentSecurityPolicy(String value) {
        return this.set(HeaderField.X_CONTENT_SECURITY_POLICY, value);
    }

    public String xWebkitCsp() {
        return this.getAny(HeaderField.X_WEBKIT_CSP);
    }

    public HeaderSet xWebkitCsp(String value) {
        return this.set(HeaderField.X_WEBKIT_CSP, value);
    }

    public String refresh() {
        return this.getAny(HeaderField.REFRESH);
    }

    public HeaderSet refresh(String value) {
        return this.set(HeaderField.REFRESH, value);
    }

    public String status() {
        return this.getAny(HeaderField.STATUS);
    }

    public HeaderSet status(String value) {
        return this.set(HeaderField.STATUS, value);
    }

    public String timingAllowOrigin() {
        return this.getAny(HeaderField.TIMING_ALLOW_ORIGIN);
    }

    public HeaderSet timingAllowOrigin(String value) {
        return this.set(HeaderField.TIMING_ALLOW_ORIGIN, value);
    }

    public String xContentDuration() {
        return this.getAny(HeaderField.X_CONTENT_DURATION);
    }

    public HeaderSet xContentDuration(String value) {
        return this.set(HeaderField.X_CONTENT_DURATION, value);
    }

    public String xContentTypeOptions() {
        return this.getAny(HeaderField.X_CONTENT_TYPE_OPTIONS);
    }

    public HeaderSet xContentTypeOptions(String value) {
        return this.set(HeaderField.X_CONTENT_TYPE_OPTIONS, value);
    }

    public String xPoweredBy() {
        return this.getAny(HeaderField.X_POWERED_BY);
    }

    public HeaderSet xPoweredBy(String value) {
        return this.set(HeaderField.X_POWERED_BY, value);
    }

    public String xUaCompatible() {
        return this.getAny(HeaderField.X_UA_COMPATIBLE);
    }

    public HeaderSet xUaCompatible(String value) {
        return this.set(HeaderField.X_UA_COMPATIBLE, value);
    }

    public String xXssProtection() {
        return this.getAny(HeaderField.X_XSS_PROTECTION);
    }

    public HeaderSet xXssProtection(String value) {
        return this.set(HeaderField.X_XSS_PROTECTION, value);
    }

    public Dict<String, Seq<String>> toDic() {
        return this.headers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HeaderSet headerSet = (HeaderSet) o;

        return Objects.equals(this.headers, headerSet.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headers);
    }
}
