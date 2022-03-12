
public class integerToken implements Token {
    public final int value;

    public integerToken(final int value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        if (other instanceof integerToken) {
            final integerToken asInt = (integerToken)other;
            return value == asInt.value;
        } else {
            return false;
        }
    }

    // Two objects: obj1 and obj2
    //
    // // for any two objects obj1 and obj2, this should never trip
    // // an assertion:
    // if (obj1.equals(obj2)) {
    //   assert(obj1.hashCode() == obj2.hashCode()); // should always be ok
    // }
    //
    // public int hashCode() {
    //   return 0;
    // }
    //
    // Keys in a hash table
    // -hashCode that always returns a constant: O(n)
    // -hashCode that does something more complex: O(1) (typically)
    public int hashCode() {
        return value;
    }

    public String toString() {
        return "IntegerToken(" + value + ")";
    }
}