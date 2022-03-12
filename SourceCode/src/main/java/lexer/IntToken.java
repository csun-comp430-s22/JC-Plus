package lexer;

public class IntToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof IntToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 17;
    }

    public String toString() {
        return "Int";
    }
}
