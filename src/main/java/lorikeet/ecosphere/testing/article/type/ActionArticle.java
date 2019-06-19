package lorikeet.ecosphere.testing.article.type;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.CellFormType;
import lorikeet.ecosphere.testing.data.CellDefinition;

import java.util.Objects;

public class ActionArticle {

    private final String filePath;
    private final String name;
    private final String documentation;
    private final CellDefinition cell;
    private final CellFormType formType;

    public ActionArticle(CellDefinition cell) {
        this.filePath = "";
        this.name = null;
        this.documentation = null;
        this.cell = cell;
        this.formType = null;
    }

    public ActionArticle(String filePath, String name, String documentation, CellDefinition cell, CellFormType formType) {
        this.filePath = filePath;
        this.name = name;
        this.documentation = documentation;
        this.cell = cell;
        this.formType = formType;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Opt<String> getName() {
        return Opt.ofNullable(this.name);
    }

    public Opt<String> getDocumentation() {
        return Opt.ofNullable(this.documentation);
    }

    public CellDefinition getCell() {
        return this.cell;
    }

    public Opt<CellFormType> getFormType() {
        return Opt.ofNullable(this.formType);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        ActionArticle that = (ActionArticle) o;

        return Objects.equals(this.getFilePath(), that.getFilePath())
            && Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getDocumentation(), that.getDocumentation())
            && Objects.equals(this.getCell(), that.getCell())
            && Objects.equals(this.getFormType(), that.getFormType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getFilePath(), this.getName(), this.getDocumentation(), this.getCell(), this.getFormType());
    }
}
