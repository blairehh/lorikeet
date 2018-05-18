package lorikeet.lang;

import java.io.File;
import java.util.Objects;

public abstract class Origin {


    public static class Source extends Origin {

        private final File file;

        public Source(File file) {
            this.file = file;
        }

        public File getFile() {
            return this.file;
        }

        @Override
        public String toString() {
            return "source file " + this.file.getAbsolutePath();
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o == null || !this.getClass().equals(o.getClass())) {
                return false;
            }

            Source that = (Source)o;

            return Objects.equals(this.getFile(), that.getFile());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.file);
        }
    }

    public static class Sdk extends Origin {
        private final String version;

        public Sdk(String version) {
            this.version = version;
        }

        public String getVersion() {
            return this.version;
        }

        @Override
        public String toString() {
            return "Lorikeet SDK " + this.version;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o == null || !this.getClass().equals(o.getClass())) {
                return false;
            }

            Sdk that = (Sdk)o;

            return Objects.equals(this.getVersion(), that.getVersion());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.version);
        }
    }
}
