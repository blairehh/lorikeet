
package lorikeet.lobe;

import java.io.FileWriter;
import java.io.IOException;;

public class DefaultDiskResource implements DiskResource {
    public <R extends UsesDisk> DiskWriteResult<?> write(DiskWrite<R> write) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(write.fileName());
            writer.write(write.content());
            return new DiskWriteOk(null, write.content().length());
        } catch (IOException e) {
            return new DiskWriteErr(null, e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}