package lorikeet.http;

import lorikeet.http.internal.HttpMsgPath;

public class DoublePathVar extends NumberPathVar<Double> {
    public DoublePathVar(HttpMsgPath path, String name) {
        super(path, name, Double::parseDouble, Double.class);
    }
}
