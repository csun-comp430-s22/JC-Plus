public class LeftBracket implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof LeftBracket;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 1;
    }

    public String toString() {
        return "[";
    }
}
