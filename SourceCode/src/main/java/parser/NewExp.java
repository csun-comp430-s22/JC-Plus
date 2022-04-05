package parser;

public class NewExp implements Exp {
    public final String name;

    public NewExp(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof NewExp &&
                name.equals(((NewExp) other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "NewExp(" + name + ")";
    }
}
