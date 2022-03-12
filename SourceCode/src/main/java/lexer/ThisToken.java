package lexer;

public class ThisToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof ThisToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 19;
    }

    public String toString() {
        return "this";
    }
}