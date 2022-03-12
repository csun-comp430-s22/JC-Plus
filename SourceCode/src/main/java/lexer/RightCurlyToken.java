public class RightCurlyToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof RightCurlyToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 2;
    }

    public String toString() {
        return "}";
    }
}
