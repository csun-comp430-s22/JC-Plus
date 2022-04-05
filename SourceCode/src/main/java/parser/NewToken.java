package parser;

public class NewToken implements Token {
    public final String name;

    public NewToken(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof NewToken &&
                name.equals(((NewToken) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "NewToken(" + name + ")";
    }
}
