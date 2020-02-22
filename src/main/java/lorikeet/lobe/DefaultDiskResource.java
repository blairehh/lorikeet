
package lorikeet.lobe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.charset.StandardCharsets;;

public class DefaultDiskResource implements DiskResource {
    public <R extends UsesDisk> DiskWriteResult write(DiskWrite<R> write) {
        return write.fileRef().asFile()
            .map(file -> this.write(file, write))
            .orGive(exception -> new DiskWriteErr(write.fileRef(), exception));
    }

    private <R extends UsesDisk> DiskWriteResult write(File file, DiskWrite<R> write) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(write.content().getBytes(StandardCharsets.UTF_8));
            return new DiskWriteOk(write.fileRef(), write.content().length());
        } catch (IOException e) {
            return new DiskWriteErr(write.fileRef(), e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}