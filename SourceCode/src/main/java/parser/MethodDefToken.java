package parser;

public class MethodDefToken implements Token {
    public final MethodDef methodDef;

    public MethodDefToken(MethodDef methodDef) {
        this.methodDef = methodDef;
    }

    public int hashCode() {
        return methodDef.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof MethodDefToken) {
            final MethodDefToken otherVar = (MethodDefToken) other;
            return (methodDef.equals(otherVar.methodDef));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("MethodDefToken(" + methodDef.toString() + ")");
    }
}
