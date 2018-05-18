package lorikeet.lang;

import java.util.Collections;
import java.util.List;

import lorikeet.error.CompileError;
import lorikeet.lang.SourceFile;


public class LorikeetSource {

  private final List<SourceFile> results;
  private final List<CompileError> compileError;

  public LorikeetSource(List<SourceFile> sourceFiles, List<CompileError> errors) {
      this.results = sourceFiles;
      this.compileError = errors;

  }

  public boolean succeded() {
    return !this.results.isEmpty();
  }

  public boolean failed() {
    return !this.succeded();
  }

  public List<SourceFile> getResults() {
    return this.results;
  }

  public List<CompileError> getErrors() {
      return this.compileError;
  }

  public CompileError getErrors(int index) {
      return this.compileError.get(index);
  }

}
