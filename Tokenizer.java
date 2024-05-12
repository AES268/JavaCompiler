package token;

import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tokenizer
{
    
    public static void main(String[]args)
    {
        String filePath="Input.txt";
        String testt="";
        try{
            testt=new String(Files.readAllBytes(Paths.get(filePath)));
        }catch(IOException e){
            e.printStackTrace();
        }
        Tokenizer tokenizer=new Tokenizer();
        try{
        ArrayList<Token> listoftoken=tokenizer.tokenize(testt);
        for(Token token:listoftoken)
       {System.out.println(token.getValue()+":"+token.getType()+"\n");}
        }
        catch (IOException e)
        {
            System.out.println("ERROR");
        }
        }
    public static boolean isNumeric(String str) {
    for (char c : str.toCharArray()) {
        if (!Character.isDigit(c)) {return false;}
    }
    return true;
    }
    boolean mathematicalexpression(String[] exp)
    {
    for(int i=3;i<exp.length;i++)
    if((i%2==0&&(isNumeric(exp[i])||isValididentifier(exp[i])))||(i%2!=0&&isOperator(exp[i])))return false;
    return true;
    }
    boolean isvalidcond(String[] GO)
    {
        int x=GO.length;
        if (x==2)
        {if(isValididentifier(GO[1])||isNumeric(GO[1]))return true;return false;}
        else if(x==4)
        {if(isValididentifier(GO[1])&&nextTokenType(GO[2])==TokenType.CONDITION&&(isValididentifier(GO[3])||isNumeric(GO[3])))return true;return false;}
        else return false;
    }
    TokenType conditions(String ar)
    {
        if(ar.equals("And"))return TokenType.AND;else if (ar.equals("Or"))return TokenType.OR; else if (ar.equals("equals"))return TokenType.EQUALS;
        else return TokenType.INVALID;
    }
    boolean isvalidInt(String[]str)
    {
    if(nextTokenType(str[0])==TokenType.INTEGER&&isValididentifier(str[1])&&isOperator(str[2])&&mathematicalexpression(str))
    return true;
    else return false;
    }
    public boolean isDataType(TokenType input)
    {
    if(input==TokenType.INTEGER||input==TokenType.DOUBLE||input==TokenType.LONG||input==TokenType.CHAR||input==TokenType.STRING)return true;return false;
    }
    boolean isLineType(TokenType input)
    {
        if(input==TokenType.PRINT||input==TokenType.IF)return true;return false;
    }
    public boolean isOperator(String input)
    {
        return (input.equals("equals")||input.equals('-')||input.equals('+')||input.equals('*')||input.equals('/')
                ||input.equals("plus")||input.equals("minus")||input.equals("times")||input.equals('=')||input.equals("by"));
    }
    public boolean isValididentifier(String input)
    {
        if(isOperator(input)||isDataType(nextTokenType(input))||isLineType(nextTokenType(input)))return false;return true;
    }
    boolean isConditionStatement(String input)
    {
        if(input.equals("If"))return true;return false;
    }
    boolean isPrintStatement(String input)
    {
        if(input.equals("Print")||input.equals("Print Line"))return true;return false;
    }
    public TokenType nextTokenType(String input)
    {
        if(input.equals("Integer"))return TokenType.INTEGER;
        else if(input.equals("String"))return TokenType.STRING;
        else if(input.equals("Character"))return TokenType.CHAR;
        else if(input.equals("Double"))return TokenType.DOUBLE;
        else if(input.equals("Long"))return TokenType.LONG;
        else if(isNumeric(input))return TokenType.NUMBER;
        else if(input.length()==1)return TokenType.CHARACTER;
        else if(input.equals("If"))return TokenType.IF;
        else if(input.equals("Print")||input.equals("Print Line"))return TokenType.PRINT;  
        else if (isOperator(input))return TokenType.OPERATOR;
        else if(input.equals("Or")||input.equals("And")||input.equals("Equals"))return TokenType.CONDITION;
        else return TokenType.INVALID;
    }
    public ArrayList<Token> tokenize(String input) throws IOException{
    ArrayList<Token> result = new ArrayList<Token>();
    String[] tokens2=input.split("GO");
    for(String token:tokens2){
        System.out.println("\n"+token+"\n");
        String[] GO=token.split(" ");
try {
    for (int i = 0; i < GO.length; i++) {
        String token2 = GO[i];
        LineType k2;
        System.out.println(GO[i]);

        if (nextTokenType(GO[0])==TokenType.IF)
        {
            k2=LineType.CONDITION;
            System.out.println("Condition statement");
        }

        else if (isDataType(nextTokenType(GO[0])))
        {
            k2=LineType.ASSIGNMENT;
            System.out.println("Assignment statement");
        }

        else if (nextTokenType(GO[0])==TokenType.PRINT)
        {
            k2=LineType.PRINT;
            System.out.println("Print statement");
        }

        else k2=LineType.INVALID;

        if(k2==LineType.ASSIGNMENT)
        {
                if (nextTokenType(GO[0])==TokenType.CHAR&&nextTokenType(GO[3])!=TokenType.CHARACTER)
                {throw new Exception("ERROR! Declared token type should be CHARACTER");}
                else if(nextTokenType(GO[0])==TokenType.INTEGER&&!isvalidInt(GO)){throw new Exception("ERROR! Declared Integer assignment invalid");}
                else {result.add(new Token(token2, nextTokenType(token2)));}
        }
        else if(k2==LineType.CONDITION)
        {
            if(!isvalidcond(GO)) {throw new Exception("ERROR! INVALID CONDITION STATEMENT");}
        }
    }
} catch (Exception e) {
    e.printStackTrace();System.exit(1);
}
}
    return result;
    }



}
