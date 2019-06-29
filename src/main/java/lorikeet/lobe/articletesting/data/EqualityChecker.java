package lorikeet.lobe.articletesting.data;

public class EqualityChecker {

    public boolean checkEquality(Value a, Value b) {
        final Equality aEquality = a.equality(b);
        if (aEquality != Equality.UNKNOWN) {
            return aEquality == Equality.EQUAL;
        }
        return b.equality(a) == Equality.EQUAL;
    }

}
