package lorikeet.error;

import java.util.Objects;

public class CouldNotFindParameterSerializerCapability extends Exception {
    private final String parameterType;
    private final String context;

    public CouldNotFindParameterSerializerCapability(String parameterType, String context) {
        this.parameterType = parameterType;
        this.context = context;
    }

    public String getParameterType() {
        return this.parameterType;
    }

    public String getContext() {
        return this.context;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        CouldNotFindParameterSerializerCapability that = (CouldNotFindParameterSerializerCapability) o;

        return Objects.equals(this.getParameterType(), that.getParameterType())
            && Objects.equals(this.getContext(), that.getContext());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getParameterType(), this.getContext());
    }
}
