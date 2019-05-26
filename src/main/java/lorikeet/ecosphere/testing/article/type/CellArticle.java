package lorikeet.ecosphere.testing.article.type;

import lorikeet.ecosphere.testing.data.CellValue;

import java.util.Objects;

public class CellArticle {
    private final CellValue cell;

    public CellArticle(CellValue cell) {
        this.cell = cell;
    }

    public CellValue getCell() {
        return this.cell;
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

        return Objects.equals(this.getCell(), that.getCell());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCell());
    }
}
