package parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import lexer.SemiColonToken;
import lexer.VariableToken;

public class ParserTest {
        @Test
        public void testEqualsOpExp() {
                // 1 + 1 == 1 + 1
                final OpExp first = new OpExp(new IntLiteralExp(1),
                                new PlusOp(),
                                new IntLiteralExp(1));
                final OpExp second = new OpExp(new IntLiteralExp(1),
                                new PlusOp(),
                                new IntLiteralExp(1));
                assertEquals(first, second);
        }

        @Test
        public void testTypeInt() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new IntToken()));
                assertEquals(new ParseResult<Type>(new IntType(), 1), parser.parseType(0));
        }

        @Test
        public void testTypeVoid() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new VoidToken()));
                assertEquals(new ParseResult<Type>(new VoidType(), 1), parser.parseType(0));
        }

        @Test
        public void testPrimaryVariable() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new Variable("x")));
                assertEquals(new ParseResult<Exp>(new VariableExp(new Variable("x")),
                                1),
                                parser.parsePrimaryExp(0));
        }

        @Test
        public void testVarDecStmt() throws ParseException {
                // type var
                final Parser parser = new Parser(Arrays.asList(new IntToken(), new Variable("x"), new AssignmentToken(),
                                new IntLiteralToken(0)));
                assertEquals(
                                new ParseResult<Stmt>(
                                                new VarDecStmt(new VarDec(new IntType(), new Variable("x")),
                                                                new IntLiteralExp(0)),
                                                0),
                                parser.parseVardecStmt(0));
        }

        @Test
        public void testVarDecStmtVoid() throws ParseException {
                // type var
                final Parser parser = new Parser(Arrays.asList(new VoidToken(), new Variable("x"),
                                new AssignmentToken(), new IntLiteralToken(0)));
                assertEquals(
                                new ParseResult<Stmt>(
                                                new VarDecStmt(new VarDec(new VoidType(), new Variable("x")),
                                                                new IntLiteralExp(0)),
                                                0),
                                parser.parseVardecStmt(0));
        }

        @Test
        public void testNotVarDecStmt() throws ParseException {
                // type var
                final Parser parser = new Parser(Arrays.asList(new Variable("x"), new IntToken(), new Variable("x"),
                                new AssignmentToken(), new IntLiteralToken(0)));
                assertEquals(
                                new ParseResult<Stmt>(
                                                new VarDecStmt(new VarDec(new IntType(), new Variable("x")),
                                                                new IntLiteralExp(0)),
                                                0),
                                parser.parseVardecStmt(0));
        }

        @Test
        public void testVarDecInt() throws ParseException {
                // type var
                final Parser parser = new Parser(Arrays.asList(new IntToken(), new Variable("x")));
                assertEquals(
                                new ParseResult<VarDec>(
                                                new VarDec(new IntType(), new Variable("x")),
                                                0),
                                parser.parseVardec(0));
        }

        @Test
        public void testVarDecVoid() throws ParseException {
                // type var
                final Parser parser = new Parser(Arrays.asList(new VoidToken(), new Variable("x")));
                assertEquals(
                                new ParseResult<VarDec>(
                                                new VarDec(new VoidType(), new Variable("x")),
                                                0),
                                parser.parseVardec(0));
        }

        @Test
        public void testNotVarDec() throws ParseException {
                // type var
                final Parser parser = new Parser(Arrays.asList(new Variable("x"), new IntToken(), new Variable("x")));
                assertEquals(
                                new ParseResult<VarDec>(
                                                new VarDec(new IntType(), new Variable("x")),
                                                0),
                                parser.parseVardec(0));
        }

        @Test
        public void testInstanceDec() throws ParseException {

                // instancedec
                // vardec
                VarDec vardec = new VarDec(new IntType(), new Variable("f"));
                final Parser parser = new Parser(
                                Arrays.asList(new TypeToken("Int"), new Variable("f"), new EqualsToken(),
                                                new IntLiteralToken(0)));

                assertEquals(
                                new ParseResult<Stmt>(
                                                new InstanceDec(new VarDec(new IntType(), new Variable("f")),
                                                                new IntLiteralExp(0)),
                                                0),
                                parser.parseInstanceDec(0));
        }

        @Test
        public void testPrimaryInteger() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
                assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                                parser.parsePrimaryExp(0));
        }

        @Test
        public void testPrimaryIntegerLiteral() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
                assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                                parser.parsePrimaryExp(0));
        }

        @Test
        public void testClassName() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new ClassNameToken("asdfga")));
                assertEquals(new ParseResult<Exp>(new ClassNameExp(new ClassNameToken("asdfga")), 1),
                                parser.parseClass(0));
        }

        @Test
        public void testClassNameError() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
                assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                                parser.parseClass(0));
        }

        @Test
        public void testMethodName() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new MethodName("asdfga")));
                assertEquals(new ParseResult<Exp>(new MethodNameType(new MethodName("asdfga")), 1),
                                parser.parseMethodName(0));
        }

        @Test
        public void testMethodNameError() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
                assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                                parser.parseMethodName(0));
        }

        @Test
        public void testPrimaryLeftParent() throws ParseException {
                final Parser parser = new Parser(
                                Arrays.asList(new LeftParenToken(), new IntLiteralToken(123), new RightParenToken()));
                assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                                parser.parsePrimaryExp(0));
        }

        @Test
        public void testSecondaryThis() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new ThisToken(), new DotToken(), new Variable("f")));
                assertEquals(new ParseResult<Exp>(new ThisExp(), 1),
                                parser.parseSecondaryExp(0));
        }

        @Test
        public void testSecondaryLength() throws ParseException {
                final Parser parser = new Parser(
                                Arrays.asList(new LengthToken(), new LeftParenToken(), new Variable("f"),
                                                new RightParenToken(), new SemicolonToken()));
                assertEquals(new ParseResult<Exp>(new LengthExp(), 1),
                                parser.parseSecondaryExp(0));
        }

        /*
         * @Test
         * public void testSecondaryNew() throws ParseException {
         * final Parser parser = new Parser(Arrays.asList(new NewToken(), new
         * TypeToken("Int"), new LeftBracketToken(), new Variable("f"), new
         * RightBracketToken()));
         * assertEquals(new ParseResult<Exp>(new NewArrayDeclarationExp(new TypeExp(new
         * TypeToken("Int")), new VariableExp( new Variable("f")) ), 1),
         * parser.parseSecondaryExp(0));
         * }
         */

        @Test
        public void testSecondaryNew() throws ParseException {

                final Parser parser = new Parser(Arrays.asList(new NewToken(), new IntToken(), new LeftBracketToken(),
                                new Variable("f"), new RightBracketToken()));
                assertEquals(
                                new ParseResult<Exp>(new NewArrayDeclarationExp(new TypeExp(new TypeToken("Int")),
                                                new VariableExp(new Variable("f"))), 1),
                                parser.parseSecondaryExp(0));
        }


        
        @Test
        public void testSecondaryExp() throws ParseException { //exp.methodname(vardec*)
                //x.bob(0)
                //

                final Parser parser = new Parser(Arrays.asList(new Variable("x"), new DotToken(), 
               new MethodName("bob"), new LeftParenToken(),
                                new IntLiteralToken(0), new RightParenToken()));
                
                                List<Exp> params = new ArrayList<Exp>();
                                params.add(new IntLiteralExp(0));
                assertEquals(
                                new ParseResult<Exp>(
                                        new ExpMethodNameExp(
                                                new VariableExp(new Variable("x")),
                                                new MethodName("bob"),
                                                params
                                                
                                      
                                                
                                                ), 1),
                                parser.parseSecondaryExp(0));
        }

        @Test
        public void testSecondaryError() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
                assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                                parser.parseSecondaryExp(0));
        }

        @Test
        public void testPrimaryParens() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new LeftParenToken(),
                                new IntLiteralToken(123),
                                new RightParenToken()));
                assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 3),
                                parser.parsePrimaryExp(0));
        }

        @Test
        public void testAdditiveOpPlus() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new PlusToken()));
                assertEquals(new ParseResult<Op>(new PlusOp(), 1),
                                parser.parseAdditiveOp(0));
        }

        @Test
        public void testAdditiveOpMinus() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new MinusToken()));
                assertEquals(new ParseResult<Op>(new MinusOp(), 1),
                                parser.parseAdditiveOp(0));
        }

        @Test
        public void testAdditiveOpMultiplication() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new MultiplicationToken()));
                assertEquals(new ParseResult<Op>(new MultiplicationOp(), 1),
                                parser.parseAdditiveOp(0));
        }

        @Test
        public void testIfToken() throws ParseException {

                // if (exp) stmt else stmt
                // if (0) return; else return;
                final Parser parser = new Parser(Arrays.asList(
                                new IfToken(), new LeftParenToken(), new IntLiteralToken(0), new RightParenToken(),
                                new ReturnToken(),
                                new SemicolonToken(), new ElseToken(), new ReturnToken(), new SemicolonToken()));

                assertEquals(new ParseResult<Stmt>(new IfStmt(
                                new IntLiteralExp(0), new ReturnVoidStmt(), new ReturnVoidStmt()), 1),
                                parser.parseStmt(0));
        }

        @Test
        public void testWhileToken() throws ParseException {

                // while (exp) stmt
                // while (0) break;
                final Parser parser = new Parser(Arrays.asList(
                                new WhileToken(), new LeftParenToken(), new IntLiteralToken(0), new RightParenToken(),
                                new BreakToken(),
                                new SemicolonToken()));

                assertEquals(new ParseResult<Stmt>(new WhileStmt(
                                new IntLiteralExp(0), new BreakStmt()), 1),
                                parser.parseStmt(0));
        }

        @Test
        public void testBlockStmt() throws ParseException {

                // { stmt* }
                // return 0;
                final Parser parser = new Parser(Arrays.asList(
                                new LeftCurlyToken(), new ReturnToken(), new IntLiteralToken(0), new SemicolonToken(),
                                new RightCurlyToken()));

                assertEquals(new ParseResult<Stmt>(new WhileStmt(
                                new IntLiteralExp(0), new ReturnNonVoidStmt(new IntLiteralExp(0))), 1),
                                parser.parseStmt(0));
        }

        @Test
        public void testPrintLn() throws ParseException {

                // Println(exp);
                final Parser parser = new Parser(Arrays.asList(
                                new PrintlnToken(), new LeftParenToken(), new IntLiteralToken(0),
                                new RightParenToken(), new SemicolonToken()));

                assertEquals(new ParseResult<Stmt>(new PrintlnStmt(
                                new IntLiteralExp(0)), 1),
                                parser.parseStmt(0));
        }

        @Test
        public void testArrayAssignment() throws ParseException {

                // var[exp] = exp;
                final Parser parser = new Parser(Arrays.asList(
                                new Variable("f"), new LeftBracketToken(), new IntLiteralToken(0),
                                new RightBracketToken(),
                                new AssignmentToken(), new IntLiteralToken(1),
                                new SemicolonToken()));

                VariableExp v = new VariableExp(new Variable("f"));
                ArrayAssignment a = new ArrayAssignment(v, new IntLiteralExp(0), new IntLiteralExp(1));
                assertEquals(new ParseResult<Stmt>(a, 1),
                                parser.parseStmt(0));
        }

        @Test
        public void testAssignment() throws ParseException {

                // var = exp;
                final Parser parser = new Parser(Arrays.asList(
                                new Variable("f"),
                                new AssignmentToken(), new IntLiteralToken(1),
                                new SemicolonToken()));

                AssignmentStmt a = new AssignmentStmt(new Variable("f"), new IntLiteralExp(1));
                assertEquals(new ParseResult<Stmt>(a, 1),
                                parser.parseStmt(0));
        }

        @Test
        public void testReturnVoid() throws ParseException {

                // return;
                final Parser parser = new Parser(Arrays.asList(
                                new ReturnToken(),

                                new SemicolonToken()));

                ReturnVoidStmt a = new ReturnVoidStmt();
                assertEquals(new ParseResult<Stmt>(a, 1),
                                parser.parseStmt(0));
        }

        @Test
        public void testReturnNonVoid() throws ParseException {

                // return 0;
                final Parser parser = new Parser(Arrays.asList(
                                new ReturnToken(),
                                new IntLiteralToken(0),
                                new SemicolonToken()));

                ReturnNonVoidStmt a = new ReturnNonVoidStmt(new IntLiteralExp(0));
                assertEquals(new ParseResult<Stmt>(a, 1),
                                parser.parseStmt(0));
        }

        
        @Test
        public void testMethodDefVoid() throws ParseException {

                // type methodname(vardec*) stmt

                List<VarDec> arguments = new ArrayList<VarDec>();
                arguments.add(new VarDec(new IntType(), new Variable("x")));
                // arguments.add(new VarDec(new TypeToken("Int"), new Variable("y")));

                final Parser parser = new Parser(Arrays.asList(
                                new VoidToken(), new MethodName("bob"), new LeftParenToken(),
                                new IntToken(), new Variable("x"),

                                new RightParenToken(), new ReturnToken(), new SemicolonToken())

                );

                assertEquals(new ParseResult<MethodDef>(new MethodDef(
                                new VoidType(), new MethodName("bob"),
                                arguments, new ReturnVoidStmt()

                ), 1),
                                parser.parseMethodDef(0));
        }


        @Test
        public void testMethodDefInt() throws ParseException {

                // type methodname(vardec*) stmt

                List<VarDec> arguments = new ArrayList<VarDec>();
                arguments.add(new VarDec(new IntType(), new Variable("x")));
                // arguments.add(new VarDec(new TypeToken("Int"), new Variable("y")));

                final Parser parser = new Parser(Arrays.asList(
                                new IntToken(), new MethodName("bob"), new LeftParenToken(),
                                new IntToken(), new Variable("x"),

                                new RightParenToken(), new ReturnToken(), new SemicolonToken())

                );

                assertEquals(new ParseResult<MethodDef>(new MethodDef(
                                new IntType(), new MethodName("bob"),
                                arguments, new ReturnVoidStmt()

                ), 1),
                                parser.parseMethodDef(0));
        }

        @Test
        public void testClassDefNoAsteriskVersion() throws ParseException {

                // class classname extends classname {
                // instancedec
                // constructor(vardec*) stmt // vardecs are comma-sep
                // methoddef }

                InstanceDec instanceDec = new InstanceDec(new VarDec(
                        new IntType(), new Variable("a")), new IntLiteralExp(0));

                List<VarDec> vardecs = new ArrayList<VarDec>();
                vardecs.add(new VarDec(new IntType(), new Variable("x")));
                vardecs.add(new VarDec(new IntType(), new Variable("x")));

                List<VarDec> instanceVariables = new ArrayList<VarDec>();
                vardecs.add(new VarDec(new IntType(), new Variable("a")));
                vardecs.add(new VarDec(new IntType(), new Variable("a")));

                MethodDef methodDef = new MethodDef(new IntType(), new MethodName("testMethod"),
                                vardecs, new ReturnVoidStmt());

                MethodDefToken methodDefToken = new MethodDefToken(methodDef);
                VarDec varDec = new VarDec(new IntType(), new Variable("a"));

                final List<Stmt> superBody = new ArrayList<Stmt>();
                superBody.add(new ReturnVoidStmt());

                final List<MethodDef> methods = new ArrayList<MethodDef>();
                methods.add(methodDef);

                final Parser parser = new Parser(Arrays.asList(
                                new ClassToken(), new ClassNameToken("test"), new ExtendsToken(),
                                new ClassNameToken("bob"),
                                new LeftCurlyToken(), new InstanceDecToken(instanceDec), new ClassNameToken("test"),
                                new LeftParenToken(), varDec,
                                new RightParenToken(), new ReturnToken(), methodDefToken,
                                new RightCurlyToken())

                );

                assertEquals(new ParseResult<ClassDef>(new ClassDef(
                        new ClassNameToken("bob"),
                        new ClassNameToken("test"),
                                instanceVariables, vardecs, superBody, methods

                ), 1),
                                parser.parseClassdef(0));
        }

        @Test
        public void testNotClassDef() throws ParseException {

                // class classname extends classname {
                // instancedec*
                // constructor(vardec*) stmt* // vardecs are comma-sep
                // methoddef* }

                InstanceDec instanceDec = new InstanceDec(new VarDec(
                        new IntType(), new Variable("a")), new IntLiteralExp(0));

                List<VarDec> vardecs = new ArrayList<VarDec>();
                vardecs.add(new VarDec(new IntType(), new Variable("x")));
                vardecs.add(new VarDec(new IntType(), new Variable("x")));

                List<VarDec> instanceVariables = new ArrayList<VarDec>();
                vardecs.add(new VarDec(new IntType(), new Variable("a")));
                vardecs.add(new VarDec(new IntType(), new Variable("a")));

                MethodDef methodDef = new MethodDef(new IntType(), new MethodName("testMethod"),
                                vardecs, new ReturnVoidStmt());

                MethodDefToken methodDefToken = new MethodDefToken(methodDef);
                VarDec varDec = new VarDec(new IntType(), new Variable("a"));

                final List<Stmt> superBody = new ArrayList<Stmt>();
                superBody.add(new ReturnVoidStmt());

                final List<MethodDef> methods = new ArrayList<MethodDef>();
                methods.add(methodDef);

                final Parser parser = new Parser(Arrays.asList(new ReturnToken(),
                                new ClassToken(), new ClassNameToken("test"), new ExtendsToken(),
                                new ClassNameToken("bob"),
                                new LeftCurlyToken(), new InstanceDecToken(instanceDec), new ClassNameToken("test"),
                                new LeftParenToken(), varDec,
                                new RightParenToken(), new ReturnToken(), methodDefToken,
                                new RightCurlyToken())

                );

                assertEquals(new ParseResult<ClassDef>(new ClassDef(
                                new ClassNameToken("bob"),
                                new ClassNameToken("test"),
                                instanceVariables, vardecs, superBody, methods

                ), 1),
                                parser.parseClassdef(0));
        }

        @Test
        public void testAdditiveOpDivision() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new DivisionToken()));
                assertEquals(new ParseResult<Op>(new DivisionOp(), 1),
                                parser.parseAdditiveOp(0));
        }

        @Test
        public void testAdditiveExpOnlyPrimary() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(123)));
                assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
                                parser.parseAdditiveExp(0));
        }

        @Test
        public void testAdditiveExpSingleOperator() throws ParseException {
                // 1 + 2
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                new PlusToken(),
                                new IntLiteralToken(2)));
                assertEquals(new ParseResult<Exp>(new OpExp(new IntLiteralExp(1),
                                new PlusOp(),
                                new IntLiteralExp(2)),
                                3),
                                parser.parseAdditiveExp(0));
        }

        @Test
        public void testAdditiveExpMultiOperator() throws ParseException {
                // 1 + 2 - 3 ==> (1 + 2) - 3
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                new PlusToken(),
                                new IntLiteralToken(2),
                                new MinusToken(),
                                new IntLiteralToken(3)));
                final Exp expected = new OpExp(new OpExp(new IntLiteralExp(1),
                                new PlusOp(),
                                new IntLiteralExp(2)),
                                new MinusOp(),
                                new IntLiteralExp(3));
                assertEquals(new ParseResult<Exp>(expected, 5),
                                parser.parseAdditiveExp(0));
        }

        @Test
        public void testComparisonOpLessThan() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new LessThanToken()));
                assertEquals(new ParseResult<Op>(new LessThanOp(), 1),
                                parser.parseComparisonOp(0));
        }

        @Test
        public void testComparisonOpGreaterThan() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new GreaterThanToken()));
                assertEquals(new ParseResult<Op>(new GreaterThanOp(), 1),
                                parser.parseComparisonOp(0));
        }

        @Test
        public void testComparisonOpEquals() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new EqualsToken()));
                assertEquals(new ParseResult<Op>(new EqualsOp(), 1),
                                parser.parseComparisonOp(0));
        }

        @Test
        public void testComparisonOpNotEquals() throws ParseException {
                final Parser parser = new Parser(Arrays.asList(new NotEqualToken()));
                assertEquals(new ParseResult<Op>(new NotEqualOp(), 1),
                                parser.parseComparisonOp(0));
        }

        /*
         * @Test
         * public void testComparisonOpOnly() throws ParseException {
         * final Parser parser = new Parser(Arrays.asList(new IntToken(123)));
         * assertEquals(new ParseResult<Exp>(new IntLiteralExp(123), 1),
         * parser.parseComparisonOp(0));
         * }
         */
        @Test
        public void testLessThanSingleOperator() throws ParseException {
                // 1 < 2
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                new LessThanToken(),
                                new IntLiteralToken(2)));
                final Exp expected = new OpExp(new IntLiteralExp(1),
                                new LessThanOp(),
                                new IntLiteralExp(2));
                assertEquals(new ParseResult<Exp>(expected, 3),
                                parser.parseLessThanExp(0));
        }

        @Test
        public void testLessThanMultiOperator() throws ParseException {
                // 1 < 2 < 3 ==> (1 < 2) < 3
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                new LessThanToken(),
                                new IntLiteralToken(2),
                                new LessThanToken(),
                                new IntLiteralToken(3)));
                final Exp expected = new OpExp(new OpExp(new IntLiteralExp(1),
                                new LessThanOp(),
                                new IntLiteralExp(2)),
                                new LessThanOp(),
                                new IntLiteralExp(3));
                assertEquals(new ParseResult<Exp>(expected, 5),
                                parser.parseLessThanExp(0));
        }

        @Test
        public void testLessThanMixedOperator() throws ParseException {
                // 1 < 2 + 3 ==> 1 < (2 + 3)
                final Parser parser = new Parser(Arrays.asList(new IntLiteralToken(1),
                                new LessThanToken(),
                                new IntLiteralToken(2),
                                new PlusToken(),
                                new IntLiteralToken(3)));
                final Exp expected = new OpExp(new IntLiteralExp(1),
                                new LessThanOp(),
                                new OpExp(new IntLiteralExp(2),
                                                new PlusOp(),
                                                new IntLiteralExp(3)));
                assertEquals(new ParseResult<Exp>(expected, 5),
                                parser.parseLessThanExp(0));
        }

}