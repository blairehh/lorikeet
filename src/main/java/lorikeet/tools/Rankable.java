package lorikeet.tools;

public interface Rankable extends Comparable<Rankable> {
    int getRank();

    @Override
    default int compareTo(Rankable other) {
        if (this.getRank() == -1 && other.getRank() == -1) {
            return 0;
        }
        if (this.getRank() == -1) {
            return -1;
        }
        if (other.getRank() == -1) {
            return 1;
        }
        return other.getRank() - this.getRank();
    }
}
