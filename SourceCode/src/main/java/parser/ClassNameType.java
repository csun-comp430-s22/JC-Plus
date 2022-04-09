package parser;

public class ClassNameType implements Exp {
    public final ClassName className;

    public ClassNameType(final ClassName className2) {
        this.className = className2;
    }

    public int hashCode() {
        return className.hashCode();
    }

    public boolean equals(final Object other) {
        return (other instanceof ClassNameType &&
                className.equals(((ClassNameType) other).className));
    }

    public String toString() {
        return "ClassNameType(" + className.toString() + ")";
    }
}