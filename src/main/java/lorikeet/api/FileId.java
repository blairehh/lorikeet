package lorikeet.api;

import lorikeet.core.Fallible;

import java.io.File;

public interface FileId {
    Fallible<File, ?> generateFile();
}
