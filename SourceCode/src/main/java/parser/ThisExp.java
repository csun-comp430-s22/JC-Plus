package parser;

public class ThisExp implements Exp {
    public int hashCode() { return 19; }
    
    public boolean equals(final Object other) {
        return other instanceof ThisExp;
    }
    public String toString() { return "ThisExp"; }
}