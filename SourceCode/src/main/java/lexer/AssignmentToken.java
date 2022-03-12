package lexer;

public class AssignmentToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof AssignmentToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 24;
    }

    public String toString() {
        return "=";
    }
}
