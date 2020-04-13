package lorikeet.http;

public enum HttpStatus {

    OK                                  (200),
    CREATED                             (201),
    ACCEPTED                            (202),
    NO_CONTENT                          (204),
    RESET_CONTENT                       (205),
    PARTIAL_CONTENT                     (206),
    MULTI_STATUS                        (207),
    ALREADY_REPORTED                    (208),

    MULTIPLE_CHOICES                    (300),
    MOVED_PERMANENTLY                   (301),
    FOUND                               (302),
    SEE_OTHER                           (303),
    NOT_MODIFIED                        (304),
    USE_PROXY                           (305),
    SWITCH_PROXY                        (306),
    TEMPORARY_REDIRECT                  (307),
    PERMANENT_REDIRECT                  (308),

    BAD_REQUEST                         (400),
    UNAUTHORIZED                        (401),
    PAYMENT_REQUIRED                    (402),
    FORBIDDEN                           (403),
    NOT_FOUND                           (404),
    METHOD_NOT_ALLOWED                  (405),
    NOT_ACCEPTABLE                      (406),
    PROXY_AUTHENTICATION_REQUIRED       (407),
    REQUEST_TIMEOUT                     (408),
    CONFLICT                            (409),
    GONE                                (410),
    LENGTH_REQUIRED                     (411),
    PRECONDITION_FAILED                 (412),
    PAYLOAD_TOO_LARGE                   (413),
    UNSUPPORTED_MEDIA_TYPE              (415),
    RANGE_NOT_SATISFIABLE               (416),
    EXPECTATION_FAILED                  (417),
    MISDIRECTED_REQUEST                 (421),
    UNPROCESSABLE_ENTITY                (422),
    LOCKED                              (423),
    FAILED_DEPENDENCY                   (424),
    TOO_EARLY                           (425),
    PRECONDITION_REQUIRED               (428),

    INTERNAL_SERVER_ERROR               (500),
    NOT_IMPLEMENTED                     (501),
    BAD_GATEWAY                         (502),
    SERVICE_UNAVAILABLE                 (503),
    GATEWAY_TIMEOUT                     (504),
    INSUFFICIENT_STORAGE                (507),
    LOOP_DETECTED                       (508),
    NOT_EXTENDED                        (510),
    NETWORK_AUTHENTICATION_REQUIRED     (511);

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}
