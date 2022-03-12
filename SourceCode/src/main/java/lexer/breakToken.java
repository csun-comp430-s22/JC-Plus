package lexer;

public class BreakToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof BreakToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 14;
    }

    public String toString() {
        return "break";
    }
}
