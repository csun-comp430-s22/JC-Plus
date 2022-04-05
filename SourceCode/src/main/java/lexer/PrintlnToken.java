package lexer;

public class PrintlnToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof PrintlnToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 6;
    }

    public String toString() {
        return "println";
    }
}
