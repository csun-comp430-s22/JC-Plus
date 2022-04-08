package parser;

import java.util.List;

public class ClassDef {
    public final ClassName className;
    public final ClassName extendsClassName;
    public final List<VarDec> instanceVariables;
    public final List<VarDec> constructorArguments;
    public final List<Stmt> superBody;
    public final List<MethodDef> methods;

    public ClassDef(final ClassName className,
            final ClassName extendsClassName,
            final List<VarDec> instanceVariables,
            final List<VarDec> constructorArguments,
            final List<Stmt> superBody,
            final List<MethodDef> methods) {
        this.className = className;
        this.extendsClassName = extendsClassName;
        this.instanceVariables = instanceVariables;
        this.constructorArguments = constructorArguments;
        this.superBody = superBody;
        this.methods = methods;
    }

    public int hashCode() {
        return (className.hashCode() +
                extendsClassName.hashCode() +
                instanceVariables.hashCode() +
                constructorArguments.hashCode() +
                superBody.hashCode() +
                methods.hashCode());
    }

    public boolean equals(final Object other) {
        if (other instanceof ClassDef) {
            final ClassDef otherClass = (ClassDef) other;
            return (className.equals(otherClass.className) &&
                    extendsClassName.equals(otherClass.extendsClassName) &&
                    instanceVariables.equals(otherClass.instanceVariables) &&
                    constructorArguments.equals(otherClass.constructorArguments) &&
                    superBody.equals(otherClass.superBody) &&
                    methods.equals(otherClass.methods));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("ClassDef(" + className.toString() + ", " +
                extendsClassName.toString() + ", " +
                instanceVariables.toString() + ", " +
                constructorArguments.toString() + ", " +
                superBody.toString() + ", " +
                methods.toString());
    }
}