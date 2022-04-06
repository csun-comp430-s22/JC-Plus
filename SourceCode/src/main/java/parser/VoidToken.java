package parser;

public class VoidToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof VoidToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 16;
    }

    public String toString() {
        return "VoidToken";
    }
}
