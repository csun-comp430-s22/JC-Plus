public class lengthToken implements Token {

    public boolean equals(final Object other) { // logic to make equals work for each token
        return other instanceof lengthToken;
    }

    public int hashCode() { // if two objects are not equal to eachother they will not return the same
                            // hashcode
        return 18;
    }

    public String toString() {
        return "len";
    }
}
