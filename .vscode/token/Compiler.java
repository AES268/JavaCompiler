import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
public class Compiler {
    ArrayList<Token> Token;
    Compiler(ArrayList<Token> Tokens)
    {
        Token=Tokens;
    }
    String Complete(ArrayList<Token> Token)
    {
        StringBuilder L=new StringBuilder();
        for(int i=0;i<Token.size();i++)
        {
            L.append(replaceToken(Token.get(i))+" ");
        }
        String K=L.toString();
        return K;
    }
    String Complete() {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < Token.size(); i++) {
            Token currentToken = Token.get(i);
            
            if (currentToken.tokenType == TokenType.PRINTED) {
                result.append("\"");

                while (i < Token.size() && Token.get(i).tokenType == TokenType.PRINTED) {
                    result.append(replaceToken(Token.get(i))).append(" ");
                    i++;
                }
                
                // Move the index back one, as the loop increments it
                i--;
                
                result.append("\" ");
            } else {
                result.append(replaceToken(currentToken)).append(" ");
            }
        }
        
        return result.toString().trim();
    }
    String replaceToken(Token token) {
        switch (token.getType()) {
            case IDENTIFIER: return token.getValue();
            case NUMBER: return token.getValue();
            case ADD: return "+";
            case SUB: return "-";
            case TIMES: return "*";
            case BY: return "/";
            case INTEGER: return "int";
            case CHAR: return "Character";
            case CHARACTER: return "'" + token.getValue() + "'";
            case EQUALSCHECK: return "==";
            case STRING: return "String";
            case PRINT: return "System.out.print()";
            case PRINTED: return token.getValue();
            case IF: return "if(";
            case AND: return "&&";
            case OR: return "||";
            case EQUALS: return "=";
            case EQUALSLESS: return "<=";
            case EQUALSMORE: return ">=";
            case DONE: return "}";
            case LESS: return "<";
            case THEN: return "){";
            case MORE: return ">";
            case GO: return ";";
            default: return "INVALID";
        }
    }
}
