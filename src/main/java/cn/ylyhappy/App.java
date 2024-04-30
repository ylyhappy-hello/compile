package cn.ylyhappy;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import cn.ylyhappy.lexer.Lexer;
import cn.ylyhappy.lexer.Token;
import cn.ylyhappy.lexer.Token.Kind;

public class App {
    private static final String DEBUG_FILE = "res/ylyhappy/test.toy";

    public static void main(String[] args) {
        System.out.println("hello world");
        try (FileInputStream sourcecode = new FileInputStream(DEBUG_FILE)) {
            BufferedInputStream sourcecodeBufferSteam = new BufferedInputStream(sourcecode, 10000);
            Lexer l = new Lexer(sourcecodeBufferSteam);
            Token t = null;
            do {
                t = l.nextToken();
                System.out.println(t);
            } while (t.kind != Kind.EOF);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
