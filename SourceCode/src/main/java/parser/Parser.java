package parser;

// recursive-descent parsing: O(n^3) - in practice, usually O(n)
// recursive-descent parsing: lot of stack space (recursive!) - O(n); n: # of tokens
// you don't have to use this; alternatives: ANTLR, bison

// idea: follow the grammar, implement one method/function per production rule
// parseOp, parseExp, parseStmt, parseProgram
//
// exp ::= x | i | exp op exp
//
// # list of tokens
// tokens = [...]
//
//****/
// def parseExp(position_in_tokens):
//   # handle variables
//   # handle integers
//   # otherwise...
//   left_parse_result = parseExp(position_in_tokens)
//   op_parse_result = parseOp(left_parse_result.position)
//   right_parse_result = parseExp(op_parse_result.position)
//   return ParseResult(OpExp(left_parse_result.result,
//                            op_parse_result.result,
//                            right_parse_result.result),
//                      right_parse_result.position)
//
// I'm going to call these methods "parsers".
// Every parser only handles what it parses.
// Parsers will fail if they see input they don't recognize.
// Parsers start at a particular token (parameter - starting token).
//    - The starting token position is a parameter to the parser
// Parsers return a "ParseResult", which contains:
//    - Whatever it parsed in
//    - The position of the next token

import java.util.List;

import lexer.LeftBracketToken;
import lexer.RightBracketToken;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Parser {
    private final List<Token> tokens;

    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }

    public ParseResult<Variable> parseVariableExp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof Variable) {
            return new ParseResult<Variable>(new Variable(token.toString()), position + 1);
        } else {
            throw new ParseException("expected Variable; received: " + token);
        }
    }

    public Token getToken(final int position) throws ParseException {
        if (position >= 0 && position < tokens.size()) {
            return tokens.get(position);
        } else {
            throw new ParseException("Invalid token position: " + position);
        }
    }

    public void assertTokenHereIs(final int position, final Token expected) throws ParseException {
        final Token received = getToken(position);
        if (!expected.equals(received)) {
            throw new ParseException("expected: " + expected + "; received: " + received);
        }
    }

    // primary_exp ::= x | i | `(` exp `)`
    public ParseResult<Exp> parsePrimaryExp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof Variable) {
            final String name = ((Variable) token).name;
            return new ParseResult<Exp>(new VariableExp(new Variable(name)), position + 1);
        } else if (token instanceof IntLiteralToken) {
            final int value = ((IntLiteralToken) token).value;
            return new ParseResult<Exp>(new IntLiteralExp(value), position + 1);
        } else if (token instanceof LeftParenToken) {
            final ParseResult<Exp> inParens = parseExp(position + 1);
            assertTokenHereIs(inParens.position, new RightParenToken());
            return new ParseResult<Exp>(inParens.result,
                    inParens.position + 1);
        }
        /*
         * if (token instanceof ClassToken) {
         * return new ParseResult<ClassName>(new ClassName(token.toString()), position +
         * 1);
         * } else {
         * throw new ParseException("expected className; received: " + token);
         * }
         * }
         * else if (token instanceof LeftBracketToken) { //might need to add this
         * somewhere but not primary exp
         * final ParseResult<Exp> inParens = parseExp(position + 1);
         * assertTokenHereIs(inParens.position, new RightBracketToken());
         * return new ParseResult<Exp>(inParens.result,
         * inParens.position + 1);
         * }
         */
        else {
            throw new ParseException("Expected primary expression; received: " + token);
        }
    } // parsePrimaryExp

    // exp ::= equals_exp
    public ParseResult<Exp> parseExp(final int position) throws ParseException {
        return parseEqualsExp(position);
    }

    // secondary_exp ::= this | len(exp); | new type[exp] | exp.methodname(exp*)
    public ParseResult<Exp> parseSecondaryExp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof ThisToken) { // this | might need to change so its used to do something like
            assertTokenHereIs(position + 1, new DotToken()); // this.variableName
            final ParseResult<Exp> exp = parseExp(position + 2);
            return new ParseResult<Exp>(new ThisExp(), exp.position + 1);
        } else if (token instanceof LengthToken) { // len(exp);
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Exp> exp = parseExp(position + 2);
            assertTokenHereIs(exp.position, new RightParenToken());
            assertTokenHereIs(exp.position + 1, new SemicolonToken());
            return new ParseResult<Exp>(new LengthExp(),
                    exp.position + 2);
        } else if (token instanceof NewToken) { // new type[exp]
            final ParseResult<Type> type = parseType(position + 1); // this should be the type right?,
            // no create parse type function, create based on proposal
            assertTokenHereIs(type.position, new parser.LeftBracketToken());
            final ParseResult<Exp> exp = parseExp(type.position + 1);
            assertTokenHereIs(exp.position, new parser.RightBracketToken());
            return new ParseResult<Exp>(new NewArrayDeclarationExp(type.result, exp.result),
                    exp.position + 2);
        } else if (token instanceof Exp) {
            final ParseResult<Exp> exp = parseExp(position);
            assertTokenHereIs(exp.position + 1, new DotToken());
            final ParseResult<Exp> methodName = parseMethodName(exp.position + 2);
            assertTokenHereIs(methodName.position, new LeftParenToken());

            final List<Exp> exps = new ArrayList<Exp>();
            int curPosition = position + 1;
            boolean shouldRun = true;
            while (shouldRun) {
                try {
                    final ParseResult<Exp> tempExp = parseExp(curPosition);
                    exps.add(tempExp.result);
                    curPosition = tempExp.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            }

            assertTokenHereIs(exp.position + 1, new RightParenToken());
            return new ParseResult<Exp>(new ExpMethodNameStmt(exp.result, methodName.result, exps), // Implement
                                                                                                    // exp.methodname
                                                                                                    // stmt???
                    exp.position + 2);
        } else {
            throw new ParseException("Expected secondary expression; received: " + token);
        }

    } // parseSecondaryExp

    // parsemethodName ::= methodName |
    public ParseResult<Exp> parseMethodName(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof MethodName) {
            final String name = ((MethodName) token).name;
            return new ParseResult<Exp>(new MethodNameType(new MethodName(name)), position + 1);
        } else {
            throw new ParseException("expected Int or Void; received: " + token);
        }
    }
    // parsemethodName

    public ParseResult<Exp> parseClass(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof ClassNameToken) {
            final String name = ((ClassNameToken) token).name;
            return new ParseResult<Exp>(new ClassNameExp(new ClassNameToken(name)), position + 1);
        } else {
            throw new ParseException("expected className; received: " + token);
        }
    }

    // additive_op ::= + | - | * | /
    public ParseResult<Op> parseAdditiveOp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof PlusToken) {
            return new ParseResult<Op>(new PlusOp(), position + 1);
        } else if (token instanceof MinusToken) {
            return new ParseResult<Op>(new MinusOp(), position + 1);
        } else if (token instanceof MultiplicationToken) {
            return new ParseResult<Op>(new MultiplicationOp(), position + 1);
        } else if (token instanceof DivisionToken) {
            return new ParseResult<Op>(new DivisionOp(), position + 1);
        } else {
            throw new ParseException("expected + or -; received: " + token);
        }
    } // parseAdditiveOp

    // additive_exp ::= primary_exp (additive_op primary_exp)*
    // 1 + 2
    //
    // 1 + 2
    public ParseResult<Exp> parseAdditiveExp(final int position) throws ParseException {
        ParseResult<Exp> current = parsePrimaryExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                final ParseResult<Op> additiveOp = parseAdditiveOp(current.position);
                final ParseResult<Exp> anotherPrimary = parsePrimaryExp(additiveOp.position);
                current = new ParseResult<Exp>(new OpExp(current.result,
                        additiveOp.result,
                        anotherPrimary.result),
                        anotherPrimary.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return current;
    } // parseAdditiveExp

    // comparison_op ::= > | < | == | !=
    public ParseResult<Op> parseComparisonOp(final int position) throws ParseException {
        final Token token = getToken(position);

        if (token instanceof GreaterThanToken) {
            return new ParseResult<Op>(new GreaterThanOp(), position + 1);
        } else if (token instanceof LessThanToken) {
            return new ParseResult<Op>(new LessThanOp(), position + 1);
        } else if (token instanceof EqualsToken) {
            return new ParseResult<Op>(new EqualsOp(), position + 1);
        } else if (token instanceof NotEqualToken) {
            return new ParseResult<Op>(new NotEqualOp(), position + 1);
        } else {
            throw new ParseException("expected > or < or == or !=; received: " + token);
        }
    } // parseComparisonOp

    // less_than_exp ::= additive_exp (`<` additive_exp)*
    public ParseResult<Exp> parseLessThanExp(final int position) throws ParseException {
        ParseResult<Exp> current = parseAdditiveExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                final ParseResult<Op> comparisonOp = parseComparisonOp(current.position);
                final ParseResult<Exp> other = parseAdditiveExp(current.position + 1);
                current = new ParseResult<Exp>(new OpExp(current.result,
                        comparisonOp.result,
                        other.result),
                        other.position);

            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return current;
    } // parseLessThanExp

    // equals_exp ::= less_than_exp (`==` less_than_exp)*
    public ParseResult<Exp> parseEqualsExp(final int position) throws ParseException {
        ParseResult<Exp> current = parseLessThanExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                assertTokenHereIs(current.position, new EqualsToken());
                final ParseResult<Exp> other = parseLessThanExp(current.position + 1);
                current = new ParseResult<Exp>(new OpExp(current.result,
                        new EqualsOp(),
                        other.result),
                        other.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return current;
    } // parseEqualsExp

    // parse_type ::= Int |
    // parse_type ::= Int |
    public ParseResult<Type> parseType(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof VoidToken) {
            return new ParseResult<Type>(new VoidType(), position + 1);
        } else if (token instanceof IntToken) {
            return new ParseResult<Type>(new IntType(), position + 1);
        } else if (token instanceof VarDec) {
            final ParseResult<Type> type = parseType(position);
            if (type.result.toString() == "Int") {
                return new ParseResult<Type>(new VoidType(), position + 1);
            } else if (type.result.toString() == "Void") {
                return new ParseResult<Type>(new IntType(), position + 1);
            } else {

                throw new ParseException("expected Vardec; received: " + token);
            }
        } else {
            throw new ParseException("expected Int or Void; received: " + token);
        }
    } // parseType
      // parse_Variable ::= var

    // type var
    public ParseResult<Stmt> parseVardec(final int position) throws ParseException {
        final Token token = getToken(position);
        // if Int x ; = "husdhu";
        if (token instanceof TypeToken) { // parse type function and then check if next position is variable
            // assertTokenHereIs(position + 2, new VariableToken()); // skipping
            // whitespace??
            // TODO something should be here??
            final ParseResult<Type> type = parseType(position);
            final ParseResult<Variable> variable = parseVariableExp(type.position + 2);
            TypeToken t = new TypeToken(type.result.toString());
            VarDec a = new VarDec(t, variable.result);
            return new ParseResult<Stmt>(new VarDecStmt(a, new IntLiteralExp(0)), variable.position);

            /*
             * assertTokenHereIs(guard.position, new RightParenToken());
             * final ParseResult<Stmt> trueBranch = parseStmt(guard.position + 1);
             * assertTokenHereIs(trueBranch.position, new ElseToken());
             * final ParseResult<Stmt> falseBranch = parseStmt(trueBranch.position + 1);
             * return new ParseResult<Stmt>(new IfExp(guard.result,
             * trueBranch.result,
             * falseBranch.result),
             * falseBranch.position);
             */

        } else {
            throw new ParseException("expected vardec; received: " + token);
        }
    } // parseVardec

    // stmt ::= if (exp) stmt else stmt | while (exp) stmt break stmt | { stmt* } |
    // println(exp); | exp.methodname(exp*) | var = exp; | return exp; | return;
    public ParseResult<Stmt> parseStmt(final int position) throws ParseException {
        final Token token = getToken(position);
        // if
        if (token instanceof IfToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Exp> guard = parseExp(position + 2);
            assertTokenHereIs(guard.position, new RightParenToken());
            final ParseResult<Stmt> trueBranch = parseStmt(guard.position + 1);
            assertTokenHereIs(trueBranch.position, new ElseToken());
            final ParseResult<Stmt> falseBranch = parseStmt(trueBranch.position);
            return new ParseResult<Stmt>(new IfExp(guard.result,
                    trueBranch.result,
                    falseBranch.result),
                    falseBranch.position + 1);
        } else if (token instanceof WhileToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Exp> guard = parseExp(position + 2);
            assertTokenHereIs(guard.position, new RightParenToken());
            final ParseResult<Stmt> body = parseStmt(guard.position + 1);
            return new ParseResult<Stmt>(new WhileStmt(guard.result,
                    body.result), body.position);

        } else if (token instanceof LeftCurlyToken) {
            final List<Stmt> stmts = new ArrayList<Stmt>();
            int curPosition = position + 1;
            boolean shouldRun = true;
            while (shouldRun) {
                try {
                    final ParseResult<Stmt> stmt = parseStmt(curPosition);
                    stmts.add(stmt.result);
                    curPosition = stmt.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            }
            return new ParseResult<Stmt>(new BlockStmt(stmts),
                    curPosition);
        } else if (token instanceof PrintlnToken) { // println(exp);
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Exp> exp = parseExp(position + 2);
            assertTokenHereIs(exp.position, new RightParenToken());
            assertTokenHereIs(exp.position + 1, new SemicolonToken());
            return new ParseResult<Stmt>(new PrintlnStmt(exp.result),
                    exp.position + 2);
        } else if (token instanceof Variable) {

            //ParseResult<Variable> var = parseVariableExp(position);
            if (getToken(position+1).toString() != " ") { // var = exp;
                assertTokenHereIs(position + 1, new AssignmentToken()); // +2 because we are skipping whitespace
                final ParseResult<Exp> exp = parseExp(position + 2);
                assertTokenHereIs(exp.position, new SemicolonToken());
                return new ParseResult<Stmt>(new AssignmentStmt(new Variable(token.toString()), exp.result),
                        exp.position + 2);
            } else { // var[exp] = exp; //TODO: change true to a
                // statement that will have ,, we can
                // use nested conditionals
                // leftBracketToken after position 1
                assertTokenHereIs(position + 3, new parser.LeftBracketToken());
                final ParseResult<Exp> exp = parseExp(position + 3);
                assertTokenHereIs(exp.position, new parser.RightBracketToken());
                assertTokenHereIs(exp.position + 2, new AssignmentToken());
                final ParseResult<Exp> exp2 = parseExp(position + 5);
                assertTokenHereIs(exp2.position, new SemicolonToken());
                VariableExp v = new VariableExp(new Variable(token.toString()));
                ArrayAssignment ar = new ArrayAssignment(v, exp.result, exp2.result);
                return new ParseResult<Stmt>(ar,
                        exp2.position + 2);
            }
        } else if (token instanceof ReturnToken) {
            if (getToken(position + 1) instanceof Exp) { // return exp
                final ParseResult<Exp> exp = parseExp(position + 1); // +2 because we are skipping 1 whitespace
                assertTokenHereIs(exp.position, new SemicolonToken());
                return new ParseResult<Stmt>(new ReturnNonVoidStmt(exp.result),
                        exp.position + 2);
            } else { // return;
                assertTokenHereIs(position + 1, new SemicolonToken());
                return new ParseResult<Stmt>(new ReturnVoidStmt(),
                        position + 2);
            }
        } else {
            throw new ParseException("expected statement; received: " + token);
        }

    } // parseStmt

    // type methodname( vardec*) stmt
    public ParseResult<MethodDef> parseMethodDef(final int position) throws ParseException {
        final Token token = getToken(position);
        // type
        if (token instanceof TypeToken) { // type methodname( vardec* ) stmt
            final ParseResult<Type> type = parseType(position);
            String a = getToken(position + 2).toString();
            MethodName name = new MethodName(a);
            assertTokenHereIs(position + 3, new LeftParenToken());
            final List<VarDec> stmts = new ArrayList<VarDec>();
            int curPosition = position + 4;
            boolean shouldRun = true;
            while (shouldRun) {
                try {
                    final ParseResult<Stmt> vardec = parseVardec(curPosition);
                    TypeToken t = new TypeToken(type.result.toString());
                    stmts.add(new VarDec(t, new Variable(vardec.result.toString())));
                    curPosition = vardec.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            }

            assertTokenHereIs(curPosition, new RightParenToken());
            final ParseResult<Stmt> stmt = parseStmt(curPosition + 1);
            // TODO: add something here
            return new ParseResult<MethodDef>(new MethodDef(type.result, name, stmts, stmt.result), // Implement
                                                                                                    // exp.methodname
                                                                                                    // stmt?????
                    curPosition + 2);
        } else {
            throw new ParseException("expected MethodDef; received: " + token);
        }
    } // parseMethodDef

    // instancedec
    // vardec
    public ParseResult<VarDec> parseInstancedec(final int position) throws ParseException {
        final Token token = getToken(position);
        // if Int x ; = "husdhu";
        if (token instanceof VarDec) { // parse type function and then check if next position is variable
            // assertTokenHereIs(position + 2, new VariableToken()); // skipping
            // whitespace??
            // TODO something should be here??
            final ParseResult<Type> type = parseType(position);
            final ParseResult<Variable> variable = parseVariableExp(type.position + 2);
            // VarDec a = new VarDec(type.result, variable.result);
            TypeToken t = new TypeToken(type.result.toString());
            return new ParseResult<VarDec>(new VarDec(t, variable.result), variable.position);

            /*
             * assertTokenHereIs(guard.position, new RightParenToken());
             * final ParseResult<Stmt> trueBranch = parseStmt(guard.position + 1);
             * assertTokenHereIs(trueBranch.position, new ElseToken());
             * final ParseResult<Stmt> falseBranch = parseStmt(trueBranch.position + 1);
             * return new ParseResult<Stmt>(new IfExp(guard.result,
             * trueBranch.result,
             * falseBranch.result),
             * falseBranch.position);
             */

        } else {
            throw new ParseException("expected Instancedec; received: " + token);
        }
    } // parseInstancedec

    // classdef
    public ParseResult<ClassDef> parseClassdef(final int position) throws ParseException {
        final Token token = getToken(position);

        // type
        if (token instanceof ClassToken) { // class classname extends classname
            // { instancedec*
            // constructor(vardec*) stmt* // vardecs are comma-sep
            // methoddef* }

            final ParseResult<Exp> className = parseClass(position + 1); // skip whitespace after class
            assertTokenHereIs(className.position, new ExtendsToken());
            final ParseResult<Exp> extendsClassName = parseClass(className.position + 1);

            assertTokenHereIs(extendsClassName.position, new parser.LeftCurlyToken());

            // instancedec*
            final List<VarDec> instanceVariables = new ArrayList<VarDec>();
            int curPosition = extendsClassName.position + 1;
            boolean shouldRun = true;
            while (shouldRun) {
                try {
                    final ParseResult<VarDec> vardec = parseInstancedec(curPosition);

                    // TypeToekn type = vardec.result.type;
                    // TypeToken t = new TypeToken(type.result.toString());
                    instanceVariables.add(vardec.result);
                    curPosition = vardec.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            } // instancedec*

            String a = getToken(curPosition + 1).toString();
            assertTokenHereIs(curPosition + 1, new ClassNameToken(a));
            final ParseResult<Exp> constructorName = parseClass(curPosition + 1);
            assertTokenHereIs(constructorName.position, new LeftParenToken());

            // vardec*
            final List<VarDec> constructorArguments = new ArrayList<VarDec>();
            curPosition = constructorName.position + 2;
            shouldRun = true;
            while (shouldRun) {
                try {
                    final ParseResult<VarDec> vardec = parseInstancedec(curPosition);

                    constructorArguments.add(vardec.result);
                    curPosition = vardec.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            } // vardec*

            assertTokenHereIs(curPosition, new RightParenToken());

            // stmt*
            final List<Stmt> superBody = new ArrayList<Stmt>();
            curPosition = curPosition + 2;
            shouldRun = true;
            while (shouldRun) {
                try {
                    final ParseResult<Stmt> stmt = parseStmt(curPosition);
                    superBody.add(stmt.result);
                    curPosition = stmt.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            } // stmt*

            // methodDef*
            final List<MethodDef> methods = new ArrayList<MethodDef>();
            curPosition = curPosition + 1;
            shouldRun = true;
            while (shouldRun) {
                try {
                    final ParseResult<MethodDef> method = parseMethodDef(curPosition);

                    // Type type = vardec.result.type;
                    methods.add(method.result);
                    curPosition = method.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            } // methodDef*

            assertTokenHereIs(curPosition, new parser.RightCurlyToken()); // }

            return new ParseResult<ClassDef>(new ClassDef(className.result, extendsClassName.result, instanceVariables,
                    constructorArguments, superBody, methods), // Implement
                                                               // exp.methodname
                                                               // stmt?????
                    curPosition + 1);

        } else {
            throw new ParseException("expected classDef; received: " + token);
        }
    } // parseClassdef

    /*
     * // parser for op
     * // op ::= + | - | < | ==
     * public ParseResult<Op> parseOp(final int position) throws ParseException {
     * final Token token = getToken(position);
     * // with pattern matching (in Scala)
     * // token match {
     * // case PlusToken => ParseResult(PlusOp(), position + 1)
     * // case MinusToken => ParseResult(MinusOp(), position + 1)
     * // ...
     * // }
     * if (token instanceof PlusToken) {
     * return new ParseResult<Op>(new PlusOp(), position + 1);
     * } else if (token instanceof MinusToken) {
     * return new ParseResult<Op>(new MinusOp(), position + 1);
     * } else if (token instanceof LessThanToken) {
     * return new ParseResult<Op>(new LessThanOp(), position + 1);
     * } else if (token instanceof EqualsToken) {
     * return new ParseResult<Op>(new EqualsOp(), position + 1);
     * } else {
     * throw new ParseException("expected operator; received: " + token);
     * }
     * }
     * 
     * // recursive descent parsing can't handle a grammar with left-recursion
     * // exp ::= x | i | exp op exp
     * // ^ ^
     * // | |
     * // left |
     * // right
     * public ParseResult<Exp> parseExp(final int position) throws ParseException {
     * final Token token = getToken(position);
     * if (token instanceof VariableToken) {
     * final String name = ((VariableToken)token).name;
     * return new ParseResult<Exp>(new VariableExp(name), position + 1);
     * } else if (token instanceof IntegerToken) {
     * final int value = ((IntegerToken)token).value;
     * return new ParseResult<Exp>(new IntegerExp(value), position + 1);
     * } else {
     * // Issue #1: operator precedence
     * // Issue #2: ??? - hint: this is recursive code
     * // Have base cases, have a recursive case.
     * final ParseResult<Exp> left = parseExp(position);
     * final ParseResult<Op> op = parseOp(left.position);
     * final ParseResult<Exp> right = parseExp(op.position);
     * return new ParseResult<Exp>(new OpExp(left.result,
     * op.result,
     * right.result),
     * right.position);
     * }
     * }
     */
}
