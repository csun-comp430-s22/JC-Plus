package parser;

public class ReturnToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof ReturnToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 15;
    }

    public String toString() {
        return "ReturnToken";
    }
}