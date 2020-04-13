package lorikeet.http;

import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.Ok;
import lorikeet.http.annotation.StatusCode;
import lorikeet.http.error.BadHttpStatusCodeFieldType;
import lorikeet.http.error.FailedToReadStatusCodeField;
import lorikeet.http.error.StatusCodeAnnotationOnClassMustHaveCodeSpecified;
import lorikeet.http.error.StatusCodeFiledCanNotBeNull;

import java.lang.reflect.Field;

public class MsgToHttpOutgoingSgnl {

    public Fallible<OutgoingHttpSgnl> toOugoing(Object msg, OutgoingHttpSgnl sgnl) {
        final Class<?> msgType = msg.getClass();

        {
            final StatusCode statusCode = msgType.getAnnotation(StatusCode.class);
            if (statusCode != null) {
                if (statusCode.value() == -1) {
                    return new Err<>(new StatusCodeAnnotationOnClassMustHaveCodeSpecified(msgType));
                }
                sgnl.statusCode(statusCode.value());
            }
        }

        for (int i = 0; i < msgType.getDeclaredFields().length; i++) {
            final Field field = msgType.getDeclaredFields()[i];
            field.setAccessible(true);
            final StatusCode statusCode = field.getAnnotation(StatusCode.class);
            if (statusCode != null) {
                final Fallible<Integer> result = this.handleStatusCode(field, msg, msgType);

                if (result.failure()) {
                    return new Err<>((Err<Integer>)result);
                }

                sgnl.statusCode(result.orPanic());
                continue;
            }
        }

        return new Ok<>(sgnl);
    }

    private Fallible<Integer> handleStatusCode(Field field, Object msg, Class<?> msgType) {
        try {
            if (field.getType().equals(int.class)) {
                return new Ok<>((Integer)field.get(msg));
            }
            if (field.getType().equals(HttpStatus.class)) {
                final HttpStatus status = (HttpStatus)field.get(msg);
                if (status == null) {
                    return new Err<>(new StatusCodeFiledCanNotBeNull(msgType, field.getName()));
                }
                return new Ok<>(status.code());
            }
            return new Err<>(new BadHttpStatusCodeFieldType(msgType, field.getName()));
        } catch (IllegalAccessException iae) {
            return new Err<>(new FailedToReadStatusCodeField(msgType, field.getName(), iae));
        }
    }
}
