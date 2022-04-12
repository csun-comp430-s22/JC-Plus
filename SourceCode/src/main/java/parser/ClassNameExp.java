package parser;

public class ClassNameExp implements Exp {
    public final ClassNameToken className;

    public ClassNameExp(final ClassNameToken className2) {
        this.className = className2;
    }

    public int hashCode() {
        return className.hashCode();
    }

    public boolean equals(final Object other) {
        return (other instanceof ClassNameExp &&
                className.equals(((ClassNameExp) other).className));
    }

    public String toString() {
        return "ClassNameExp(" + className.toString() + ")";
    }
}