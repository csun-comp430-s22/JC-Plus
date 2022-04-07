package parser;
import java.util.List;
   
public class MethodCallExp implements Exp {
        public final MethodName fname;
        public final List<Exp> params;
    
        public MethodCallExp(final MethodName fname,
                               final List<Exp> params) {
            this.fname = fname;
            this.params = params;
        }
    
        public int hashCode() {
            return (fname.hashCode() +
                    params.hashCode());
        }
    
        public boolean equals(final Object other) {
            if (other instanceof MethodCallExp) {
                final MethodCallExp temp= (MethodCallExp)other;
                return (fname.equals(temp.fname) &&
                        params.equals(temp.params));
            } else {
                return false;
            }
        }
    
        public String toString() {
            return ("MethodCallExp(" + fname.toString() + ", " +
                    params.toString() + ")");
        }
    }  

