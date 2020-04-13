package lorikeet.http;

import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.Ok;
import lorikeet.http.annotation.StatusCode;
import lorikeet.http.error.StatusCodeAnnotationOnClassMustHaveCodeSpecified;

public class MsgToHttpOutgoingSgnl {

    public Fallible<OutgoingHttpSgnl> toOugoing(Object msg, OutgoingHttpSgnl sgnl) {
        final Class<?> msgType = msg.getClass();

        final StatusCode statusCode = msgType.getAnnotation(StatusCode.class);
        if (statusCode != null) {
            if (statusCode.value() == -1) {
                return new Err<>(new StatusCodeAnnotationOnClassMustHaveCodeSpecified(msgType));
            }
            sgnl.statusCode(statusCode.value());
        }

        return new Ok<>(sgnl);
    }
}
