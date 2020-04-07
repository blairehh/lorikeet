package lorikeet.http;

import lorikeet.http.internal.HttpMsgPath;

public class LongPathVar extends NumberPathVar<Long> {
    public LongPathVar(HttpMsgPath path, String name) {
        super(path, name, Long::parseLong, Long.class);
    }
}
