package cn.ylyhappy.lexer;

public class Token
{
    public enum Kind
    {
        Add, // +
        Assign, // =
        EOF, // End of file
        ID, // Identifier

        Lbrace, // {
        Lparen, // (
        NUM, // Integer literal
        Rbrace, // }
        Rparen, // )
        Semi, // ;
        Sub, // -
        Times, // *
        DollarNum, // $1 $2
        Launch, // =>

        
        Print, // print, we just treat it as a key word
        Int, // int
        Equation, // ceate and compute equation
        Tan, // get tan value
    }

    public Kind kind; // the kind of the token
    public String lexeme; // extra lexeme of the token
    public int lineNum; // the line number of the token

    public Token(Kind kind, int lineNum)
    {
        this.kind = kind;
        this.lineNum = lineNum;
    }

    public Token(Kind kind, int lineNum, String lexeme)
    {
        this.kind = kind;
        this.lineNum = lineNum;
        this.lexeme = lexeme;
    }

    @Override
    public String toString()
    {
        return "Token_" + this.kind.toString()
                + (lexeme == null ? "" : " : " + this.lexeme)
                + " : at line " + this.lineNum;
    }
}
