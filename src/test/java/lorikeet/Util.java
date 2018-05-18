package lorikeet;

import org.apache.commons.io.IOUtils;

public class Util {
    public static String read(String file) {
        try {
            ClassLoader loader = Util.class.getClassLoader();
            return IOUtils.toString(loader.getResourceAsStream(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String trim(String value) {
        return value.trim().replaceAll("\\s+", " ").replaceAll("\\n", " ");
    }
}
