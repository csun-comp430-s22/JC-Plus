package parser;

public class PlusToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof PlusToken;
    }

    public int hashCode() {
        return 7;
    }
    
    public String toString() {
        return "PlusToken";
    }
}
