package lorikeet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IO {
    public static void out(Object... values) {
        final String value = Stream.of(values)
            .map(Object::toString)
            .collect(Collectors.joining("\t"));
        System.out.println(value);
    }

    public static void outf(String fmt, Object... values) {
        System.out.println(String.format(fmt, values));
    }

    public static Err<String> readResource(File file) {
        try {
            return read(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return Err.failure(e);
        }

    }

    public static Err<String> readResource(String fileName) {
        final String resourceFileName = fileName.startsWith("/")
            ? fileName
            : "/" + fileName;
        final InputStream inputStream = IO.class.getResourceAsStream(resourceFileName);
        if (inputStream == null) {
            return Err.failure(new FileNotFoundException(resourceFileName));
        }
        return read(inputStream);
    }

    public static Err<String> read(InputStream inputStream) {
        final Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return Err.of(scanner.hasNext() ? scanner.next() : "");
    }
}
