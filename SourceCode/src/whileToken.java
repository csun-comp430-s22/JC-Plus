public class whileToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof whileToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 13;
    }

    public String toString() {
        return "while";
    }
}