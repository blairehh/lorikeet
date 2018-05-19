package lorikeet.lang;

import lorikeet.sdk.types.Str;
import lorikeet.sdk.types.Int;
import lorikeet.sdk.types.Dec;
import lorikeet.sdk.types.Bol;

import java.util.Objects;
import java.util.Optional;

public abstract class Value implements Expressionable {

    public static class StrLiteral extends Value {
        private final String value;

        public StrLiteral(String value) {
            this.value = value;
        }

        @Override
        public Optional<Type> getExpressionType() {
            return Optional.of(Str.type());
        }

        public String getValue() {
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o == null || !this.getClass().equals(o.getClass())) {
                return false;
            }

            StrLiteral that = (StrLiteral)o;

            return Objects.equals(this.getValue(), that.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public String toString() {
            return String.format("StrLiteral{value=%s}", this.value);
        }
    }

    public static class IntLiteral extends Value {
        private final String value;

        public IntLiteral(String value) {
            this.value = value;
        }

        @Override
        public Optional<Type> getExpressionType() {
            return Optional.of(Int.type());
        }

        public String getValue() {
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o == null || !this.getClass().equals(o.getClass())) {
                return false;
            }

            IntLiteral that = (IntLiteral)o;

            return Objects.equals(this.getValue(), that.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public String toString() {
            return String.format("IntLiteral{value=%s}", this.value);
        }
    }

    public static class DecLiteral extends Value {
        private final String value;

        public DecLiteral(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        @Override
        public Optional<Type> getExpressionType() {
            return Optional.of(Dec.type());
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o == null || !this.getClass().equals(o.getClass())) {
                return false;
            }

            DecLiteral that = (DecLiteral)o;

            return Objects.equals(this.getValue(), that.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override
        public String toString() {
            return String.format("DecLiteral{value=%s}", this.value);
        }
    }

    public static class BolLiteral extends Value {
        private final String value;

        public BolLiteral(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        @Override
        public Optional<Type> getExpressionType() {
            return Optional.of(Bol.type());
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o == null || !this.getClass().equals(o.getClass())) {
                return false;
            }

            BolLiteral that = (BolLiteral)o;

            return Objects.equals(this.getValue(), that.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value);
        }
        
        @Override
        public String toString() {
            return String.format("BolLiteral{value=%s}", this.value);
        }
    }

    public static class Variable extends Value {
        private final boolean isParameter;
        private final String name;
        private final Type type;

        public Variable(boolean isParam, String name, Type type) {
            this.isParameter = isParam;
            this.name = name;
            this.type = type;
        }

        public boolean isParameter() {
            return this.isParameter;
        }

        public String getName() {
            return this.name;
        }

        public Type getType() {
            return this.type;
        }

        @Override
        public Optional<Type> getExpressionType() {
            return Optional.ofNullable(this.type);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o == null || !this.getClass().equals(o.getClass())) {
                return false;
            }

            Variable that = (Variable)o;

            return Objects.equals(this.isParameter(), that.isParameter())
                && Objects.equals(this.getName(), that.getName())
                && Objects.equals(this.getType(), that.getType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.isParameter, this.name, this.type);
        }

        @Override
        public String toString() {
            return String.format(
                "Value.Variable{parameter=%s name=%s type=%s}",
                this.isParameter,
                this.name,
                this.type
            );
        }
    }

}
