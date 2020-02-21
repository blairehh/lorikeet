
package lorikeet;

import lorikeet.lobe.DiskWrite;

public class WriteFile implements DiskWrite<Tutorial> {

    private final String fileName;
    private final String content;

    public WriteFile(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

    @Override
    public String fileName() {
        return this.fileName;
    }

    @Override
    public String content() {
        return this.content;
    }

}