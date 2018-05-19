package lorikeet.lang;

import java.util.Objects;

public abstract class SpecType {

    public static boolean isKnownMismatch(SpecType a, SpecType b) {
        if (!(a instanceof Known) || !(a instanceof Known)) {
            return false;
        }

        return !a.equals(b);
    }

    public static class Known extends SpecType {
        private final Type type;

        public Known(Type type) {
            this.type = type;
        }

        public Type getType() {
            return this.type;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o == null || !this.getClass().equals(o.getClass())) {
                return false;
            }

            Known that = (Known)o;

            return Objects.equals(this.getType(), that.getType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.type);
        }

        @Override
        public String toString() {
            return String.format("Known{type=%s}", this.type);
        }
    }

    public static class ToBeKnown extends SpecType {
        private final Value clue;

        public ToBeKnown(Value value) {
            this.clue = value;
        }

        public Value getClue() {
            return this.clue;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o == null || !this.getClass().equals(o.getClass())) {
                return false;
            }

            ToBeKnown that = (ToBeKnown)o;

            return Objects.equals(this.getClue(), that.getClue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.clue);
        }

        @Override
        public String toString() {
            return String.format("ToBeKnown{clue=%s}", this.clue);
        }

    }

}
