package cn.ylyhappy.lexer;

import cn.ylyhappy.lexer.Token.Kind;

import java.io.IOException;
import java.io.InputStream;

public class Lexer {
    private InputStream fstream; // input stream of the file
    private int lineNum;

    public Lexer(InputStream fstream) {
        this.fstream = fstream;
        this.lineNum = 1;
    }

    public Token nextToken() {
        Token token = null;
        try {
            token = nextTokenInternal();
        } catch (IOException e) {
            System.out.println("A IO exception!");
            e.printStackTrace();
        }

        return token;
    }

    private Token nextTokenInternal() throws IOException {
        int c = this.fstream.read();
        if (-1 == c) {
            return new Token(Kind.EOF, lineNum);
        }

        // skip all kinds of blanks
        while (' ' == c || '\t' == c || '\r' == c || '\n' == c) {
            if ('\n' == c) {
                lineNum++;
            }
            c = this.fstream.read();
        }

        // deal with comments
        if ('/' == c) {
            c = fstream.read();
            if ('/' == c) {
                while ('\n' != c) {
                    c = this.fstream.read();
                }
                lineNum++;
                return nextTokenInternal(); // tail recursion
            } else {
                System.out.println("Comment should begin with \"//\"");
                System.out.println("Error is found at line " + lineNum);
                System.exit(1);
            }
        }
        // deel with doller
        if ('$' == c) {
            Token t = nextTokenInternal();
            if (t.kind == Kind.NUM) {
                return new Token(Kind.DollarNum, lineNum, '$' + t.lexeme);
            } else {
                System.out.println("$ can not with other type token");
                System.out.println("Error is found at line " + lineNum);
                System.exit(1);
            }
        }

        switch (c) {
            case '+':
                return new Token(Kind.Add, lineNum);
            case '=':
                c = fstream.read();
                if (c == '>') {
                    return new Token(Kind.Launch, lineNum);
                } else {
                    return new Token(Kind.Assign, lineNum);
                }
            case '{':
                return new Token(Kind.Lbrace, lineNum);
            case '(':
                return new Token(Kind.Lparen, lineNum);
            case '}':
                return new Token(Kind.Rbrace, lineNum);
            case ')':
                return new Token(Kind.Rparen, lineNum);
            case ';':
                return new Token(Kind.Semi, lineNum);
            case '-':
                return new Token(Kind.Sub, lineNum);
            case '*':
                return new Token(Kind.Times, lineNum);
            default:
                StringBuilder sb = new StringBuilder();
                sb.append((char) c);
                while (true) {
                    this.fstream.mark(1);
                    c = this.fstream.read();
                    if (-1 != c && ' ' != c && '\t' != c
                            && '\n' != c && '\r' != c
                            && !isSpecialCharacter(c)) {
                        sb.append((char) c);
                    } else {
                        this.fstream.reset();
                        break;
                    }
                }
                switch (sb.toString()) {
                    case "int":
                        return new Token(Kind.Int, lineNum);
                    case "print":
                        return new Token(Kind.Print, lineNum);
                    case "equation":
                        return new Token(Kind.Equation, lineNum);
                    case "Tan":
                        return new Token(Kind.Tan, lineNum);
                    default:
                        if (isNumber(sb.toString())) {
                            return new Token(Kind.NUM, lineNum, sb.toString());
                        } else if (isIdentifier(sb.toString())) {
                            return new Token(Kind.ID, lineNum, sb.toString());
                        } else {
                            System.out.println("This is an illegal identifier at line " + lineNum);
                            System.exit(1);
                            return null;
                        }
                }

        }
    }

    private static boolean isSpecialCharacter(int c) {
        return '+' == c || '&' == c || '=' == c || ',' == c || '.' == c
                || '{' == c || '(' == c || '<' == c || '!' == c
                || '}' == c || ')' == c || ';' == c || ':' == c
                || '-' == c || '*' == c;
    }

    private static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private static boolean isIdentifier(String str) {
        return str.charAt(0) >= 'a' || str.charAt(0) <= 'z'
                || str.charAt(0) >= 'A' || str.charAt(0) <= 'Z'
                || str.charAt(0) == '_';
    }

}
