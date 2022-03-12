package lexer;
import java.util.List;
import java.util.ArrayList;

public class Tokenizer {

    private final String input;
    private int offset;

    public Tokenizer(final String input) {
        this.input = input;
        offset = 0;
    }

    public void skipWhitespace() {
        while (offset<input.length() &&
        Character.isWhitespace((input.charAt(offset)))) {
            offset++;
        }
    }
  // returns null if it wasn't an integer token
  public IntegerToken tryTokenizeInteger() {
    skipWhitespace();

    // 12345
    String number = "";

    while (offset < input.length() &&
           Character.isDigit(input.charAt(offset))) {
        number += input.charAt(offset);
        offset++;
    }

    if (number.length() > 0) {
        // convert string to an integer
        return new IntegerToken(Integer.parseInt(number));
    } else {
        return null;
    }
}
  // returns null if it fails to read in any variable or keyword
  public Token tryTokenizeVariableOrKeyword() {
    skipWhitespace();
    
    String name = "";

    // idea: read one character at a time
    // when we are out of characters, check what the name is
    // if the name is special (e.g., "true"), emit the special token for it (e.g., TrueToken)
    // if the name isn't special (e.g., "foo"), emit a variable token for it (e.g., VariableToken("foo"))
    //
    // First character of the variable: letter
    // Every subsequent character: letter or a digit
    //

    if (offset < input.length() &&
        Character.isLetter(input.charAt(offset))) {
        name += input.charAt(offset);
        offset++;

        while (offset < input.length() &&
               Character.isLetterOrDigit(input.charAt(offset))) {
            name += input.charAt(offset);
            offset++;
        }

        // by this point, `name` holds a potential variable
        // `name` could be "true"
        if (name.equals("this")) {
            return new ThisToken();
        } else if (name.equals("class")) {
            return new ClassToken();
        }else if (name.equals("break")) {
            return new BreakToken();
        } else if (name.equals("extends")) {
            return new ExtendsToken();
        }  else if (name.equals("Int")) {
            return new IntToken();
        } else if (name.equals("len")) {
            return new LengthToken();
        } else if (name.equals("new")) {
            return new NewToken();
        } else if (name.equals("println")) {
            return new PrintToken();
        } else if (name.equals("return")) {
            return new ReturnToken();
        }else if (name.equals("Void")) {
            return new VoidToken();
        } else if (name.equals("while")) {
            return new WhileToken();
        }  else if (name.equals("if")) {
            return new IfToken();
        } else if (name.equals("else")) {
            return new ElseToken();
        } else {
            return new VariableToken(name);
        }
    } else {
        return null;
    }
}

// returns null if it couldn't read in a symbol
public Token tryTokenizeSymbol() {
    skipWhitespace();
    Token retval = null;
    
    if (input.startsWith("(", offset)) {
        offset += 1;
        retval = new LeftParenthesisToken();
    } else if (input.startsWith(")", offset)) {
        offset += 1;
        retval = new RightParenthesisToken();
    } else if (input.startsWith("[", offset)) {
        offset += 1;
        retval = new LeftBracketToken();
    } else if (input.startsWith("]", offset)) {
        offset += 1;
        retval = new RightBracketToken();
    }  else if (input.startsWith("{", offset)) {
        offset += 1;
        retval = new LeftCurlyToken();
    } else if (input.startsWith("}", offset)) {
        offset += 1;
        retval = new RightCurlyToken();
    }else if (input.startsWith("-", offset)) {
        offset += 1;
        retval = new MinusToken();
    } else if (input.startsWith("/", offset)) {
        offset += 1;
        retval = new DivisionToken();
    }else if (input.startsWith("*", offset)) {
        offset += 1;
        retval = new MultiplicationToken();
    }else if (input.startsWith("+", offset)) {
        offset += 1;
        retval = new PlusToken();
    } else if (input.startsWith(";", offset)) {
        offset += 1;
        retval = new SemiColonToken();
    }
    else if (input.startsWith("==", offset)) {
            offset += 2;
            retval = new EqualsToken();
    
    } else if (input.startsWith("=", offset)) {
        offset += 1;
        retval = new AssignmentToken();
    }  else if (input.startsWith("<", offset)) {
        offset += 1;
        retval = new LessThanToken();
    } else if (input.startsWith(">", offset)) {
        offset += 1;
        retval = new GreaterThanToken();
    } else if (input.startsWith("!=", offset)) {
        offset += 2;
        retval = new NotEqualToken();
    } else if (input.startsWith(".", offset)) {
        offset += 1;
        retval = new DotToken();
    } 

    return retval;
}

// returns null if there are no more tokens left
public Token tokenizeSingle() throws TokenizerException {
    Token retval = null;
    skipWhitespace();
    if (offset < input.length() &&
        (retval = tryTokenizeVariableOrKeyword()) == null &&
        (retval = tryTokenizeInteger()) == null &&
        (retval = tryTokenizeSymbol()) == null) {
        throw new TokenizerException();
    }

    return retval;
}
public List<Token> tokenize() throws TokenizerException {
    final List<Token> tokens = new ArrayList<Token>();
    Token token = tokenizeSingle();

    while (token != null) {
        tokens.add(token);
        token = tokenizeSingle();
    }

    return tokens;
}

}
