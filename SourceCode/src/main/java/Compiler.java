/* 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import lexer.Tokenizer;
import lexer.TokenizerException;
import parser.Parser;
import parser.Program;
import parser.ParseErrorException;
import typechecker.Typechecker;
import typechecker.TypeErrorException;
import code_generator.CodeGenerator;
import code_generator.CodeGeneratorException;

public class Compiler {
    public static void printUsage() {
        System.out.println("Takes the following params:");
        System.out.println("-Input filename (.oop)");
        System.out.println("-Output filename (.js)");
    }

    public static String fileContentsAsString(final String inputFilename) throws IOException {
        final StringBuilder builder = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void compile(final String inputFilename,
            final String outputFilename)
            throws IOException,
            TokenizerException,
            ParseErrorException,
            TypeErrorException,
            CodeGeneratorException {
        final String input = fileContentsAsString(inputFilename);
        final Program program = Parser.parse(Tokenizer.tokenize(input));
        Typechecker.typecheck(program);
        final PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(outputFilename)));
        try {
            CodeGenerator.generateCode(program, output);
        } finally {
            output.close();
        }
    }

    public static void main(final String[] args)
            throws IOException,
            TokenizerException,
            ParseErrorException,
            TypeErrorException,
            CodeGeneratorException {
        if (args.length != 2) {
            printUsage();
        } else {
            compile(args[0], args[1]);
        }
    }
} */