package parser;

public class InstanceDecToken implements Token {
    public final InstanceDec instanceDec;

    public InstanceDecToken(InstanceDec instanceDec) {
        this.instanceDec = instanceDec;
    }

    public int hashCode() {
        return instanceDec.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof InstanceDecToken) {
            final InstanceDecToken otherVar = (InstanceDecToken) other;
            return (instanceDec.equals(otherVar.instanceDec));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("InstanceDecToken(" + instanceDec.toString() + ")");
    }
}
