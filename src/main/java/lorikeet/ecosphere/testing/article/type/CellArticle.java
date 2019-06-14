package lorikeet.ecosphere.testing.article.type;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.CellFormType;
import lorikeet.ecosphere.testing.data.CellValue;

import java.util.Objects;

public class CellArticle {
    private final CellValue cell;
    private final CellFormType cellFormType;

    public CellArticle(CellValue cell) {
        this.cell = cell;
        this.cellFormType = null;
    }

    public CellArticle(CellValue cell, CellFormType cellFormType) {
        this.cell = cell;
        this.cellFormType = cellFormType;
    }

    public CellValue getCell() {
        return this.cell;
    }

    public Opt<CellFormType> getCellFormType() {
        return Opt.ofNullable(this.cellFormType);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        CellArticle that = (CellArticle) o;

        return Objects.equals(this.getCell(), that.getCell())
            && Objects.equals(this.getCellFormType(), that.getCellFormType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCell(), this.getCellFormType());
    }
}
