package lorikeet.util;

import lorikeet.error.CompileError;
import lorikeet.parse.Parse;
import java.io.File;
import java.util.List;

public class ErrorConsole implements Console {

    // public void printReadError(File file) {
    //     this.printDivider();
    //     System.out.println("Read Error");
    //     System.out.println("File : " + file.getAbsolutePath());
    //     this.printDivider();
    // }
    //
    // public void printWriteError(File file) {
    //     this.printDivider();
    //     System.out.println("Write Error");
    //     System.out.println("File : " + file.getAbsolutePath());
    //     this.printDivider();
    // }

    public void printParseError(File file, Parse<?> parse) {
        this.printDivider();
    }

    public void output(List<CompileError> errors) {
        for (CompileError error : errors) {
            this.printDivider();
            error.outputTo(this);
        }
        this.printDivider();
    }

    @Override
    public void print(String value) {
        System.out.println(value);
    }

    @Override
    public void print(String fmt, Object... args) {
        System.out.println(String.format(fmt, args));
    }

    private void printDivider() {
        for (int i = 0; i < 80; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }
}
