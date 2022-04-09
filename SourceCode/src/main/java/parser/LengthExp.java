package parser;

public class LengthExp implements Exp {
    public int hashCode() { return 18; }
    
    public boolean equals(final Object other) {
        return other instanceof LengthExp;
    }
    public String toString() { return "LengthExp"; }
}