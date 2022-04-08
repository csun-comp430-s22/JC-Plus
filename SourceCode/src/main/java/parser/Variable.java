package parser;

public class Variable implements Token {
    public final String name;

    public Variable(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof Variable &&
                name.equals(((Variable)other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "VariableToken(" + name + ")";
    }
}
