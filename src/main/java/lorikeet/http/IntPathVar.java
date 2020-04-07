package lorikeet.http;

import lorikeet.http.internal.HttpMsgPath;

public class IntPathVar extends NumberPathVar<Integer> {
    public IntPathVar(HttpMsgPath path, String name) {
        super(path, name, Integer::parseInt, Integer.class);
    }
}
