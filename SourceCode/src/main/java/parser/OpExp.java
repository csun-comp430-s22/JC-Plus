package parser;
// 1 + 3
// new OpExp(new IntegerExp(1), new PlusOp(), new IntegerExp(3))
// toString result: "OpExp(Integer(1), PlusOp, IntegerExp(3))"
//
// 2 - (1 + 3)
// new OpExp(new IntegerExp(2),
//           new MinusOp(),
//           new OpExp(new IntegerExp(1),
//                     new PlusOp(),
//                     new IntegerExp(3))
//
public class OpExp implements Exp {
    public final Exp left;
    public final Op op;
    public final Exp right;

    public OpExp(final Exp left,
                 final Op op,
                 final Exp right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public boolean equals(final Object other) {
        if (other instanceof OpExp) {
            final OpExp otherExp = (OpExp)other;
            return (left.equals(otherExp.left) &&
                    op.equals(otherExp.op) &&
                    right.equals(otherExp.right));
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (left.hashCode() +
                op.hashCode() +
                right.hashCode());
    }
    
    public String toString() {
        return ("OpExp(" +
                left.toString() + ", " +
                op.toString() + ", " +
                right.toString() + ")");
    }
}
