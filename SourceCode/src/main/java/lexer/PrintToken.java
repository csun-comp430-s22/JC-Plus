package lexer;

public class PrintToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof PrintToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 6;
    }

    public String toString() {
        return "println";
    }
}
