package parser;

public class BreakType implements Type {
    public int hashCode() {
        return 14;
    }

    public boolean equals(final Object other) {
        return other instanceof BreakType;
    }

    public String toString() {
        return "BreakType";
    }
}