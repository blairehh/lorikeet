package lorikeet.ecosphere.testing.article.type;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.CellFormType;
import lorikeet.ecosphere.testing.data.CellValue;

import java.util.Objects;

public class ActionArticle {

    private final CellValue cell;
    private final CellFormType formType;

    public ActionArticle(CellValue cell) {
        this.cell = cell;
        this.formType = null;
    }

    public ActionArticle(CellValue cell, CellFormType formType) {
        this.cell = cell;
        this.formType = formType;
    }

    public CellValue getCell() {
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

        return Objects.equals(this.getCell(), that.getCell())
            && Objects.equals(this.getFormType(), that.getFormType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCell(), this.getFormType());
    }
}
