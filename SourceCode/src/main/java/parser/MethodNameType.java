package parser;

public class MethodNameType  implements Exp{ //Int methodname
    public final MethodName name;

    public MethodNameType(final MethodName methodName) {
        this.name = methodName;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(final Object other) {
        return (other instanceof MethodName &&
                name.equals(((MethodName)other).name));
    }
    
    public String toString() {
        return "MethodName(" + name.toString() + ")";
    }
}
