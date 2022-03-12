public class variableToken implements Token {
    
        public final String name;
    
        public variableToken(final String name) {
            this.name = name;
        }
    
        public int hashCode() {
            return name.hashCode();
        }
    
        public String toString() {
            // name: foo
            // Output: Variable(foo)
            return "Variable(" + name + ")";
        }
    
        public boolean equals(final Object other) {
            if (other instanceof variableToken) {
                final variableToken asVar = (variableToken)other;
                return name.equals(asVar.name);
            } else {
                return false;
            }
        }
    
}
