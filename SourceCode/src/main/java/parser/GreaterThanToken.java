package parser;

public class GreaterThanToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof GreaterThanToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 26;
    }

    public String toString() {
        return "GreaterThanToken";
    }
}