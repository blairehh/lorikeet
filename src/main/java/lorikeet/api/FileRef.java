package lorikeet.api;

import lorikeet.core.Fallible;

import java.io.File;

public interface FileRef {
    Fallible<File> asFile();
    String asString();
}
