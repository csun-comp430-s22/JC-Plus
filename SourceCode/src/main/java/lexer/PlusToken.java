package lexer;

public class PlusToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof PlusToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 7;
    }

    public String toString() {
        return "+";
    }
}