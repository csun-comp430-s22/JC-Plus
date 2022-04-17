package parser;

public class ExtendsExp implements Exp {
    public int hashCode() { return 22; }
    
    public boolean equals(final Object other) {
        return other instanceof ExtendsExp;
    }
    public String toString() { return "ExtendsExp"; }
}
