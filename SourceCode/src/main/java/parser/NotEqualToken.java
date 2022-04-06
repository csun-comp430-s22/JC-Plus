package parser;

public class NotEqualToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof NotEqualToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 28;
    }

    public String toString() {
        return "NotEqualToken";
    }
}


