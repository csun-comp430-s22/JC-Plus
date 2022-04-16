package parser;

import java.util.List;

public class Program implements Node {
    public final Stmt stmt;
    public List<ClassDef> classes;

    public Program(final Stmt stmt) {
        this.stmt = stmt;
    }
}
