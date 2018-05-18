package lorikeet.util;

import lorikeet.transpile.JavaFile;

import java.util.Collection;
import java.util.List;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;


public class Disk {

    public static final String UTF8     = "UTF-8";


    public void writeAll(String to, List<JavaFile> files) {
        files.forEach(file -> { this.write(to, file); });
    }

    public void write(String to, JavaFile javaFile) {
        final String filePath = String.format("%s%s%s", to, javaFile.getSubDirectory(), javaFile.getName());
        final File file = new File(filePath);
        try {
            FileUtils.writeStringToFile(file, javaFile.getContents(), UTF8);
        } catch (IOException ioe) {
            System.out.println(String.format("\nCould not write file %s\n", file.getAbsolutePath()));
        }
    }

    public Collection<File> listAllLorikeetFiles(String from) {
        return FileUtils.listFiles(new File(from), new String[]{"lk"}, true);
    }

    public String read(File file) throws IOException {
        return FileUtils.readFileToString(file, UTF8);
    }
}
