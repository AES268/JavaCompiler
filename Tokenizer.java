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
    public static boolean isNumeric(String str) {for (char c : str.toCharArray()) if (!Character.isDigit(c)) return false;return true;}
    boolean mathematicalexpression(String[] exp)
    {
    for(int i=3;i<exp.length;i++)
    if((i%2==0&&(isNumeric(exp[i])||isValididentifier(exp[i])||(i%2!=0&&isOperator(exp[i])))))
    return false;
    return true;
    }
    boolean isvalidchar(String[] exp)
    {   
        if (exp.length==2&&nextTokenType(exp[0])==TokenType.CHAR&&isValididentifier(exp[1]))return true;
        else if(exp.length==4&&nextTokenType(exp[0])==TokenType.CHAR&&isValididentifier(exp[1])&&isequal(exp[2])&&exp[3].length()==1)return true;
        return false;
    }
    boolean isvalidcond(String[] GO)
    {
        if (GO.length==2)
        {if(isValididentifier(GO[1])||isNumeric(GO[1]))return true;return false;}
        else if(GO.length==4)
        {if(isValididentifier(GO[1])&&nextTokenType(GO[2])==TokenType.CONDITION&&(isValididentifier(GO[3])||isNumeric(GO[3])))return true;return false;}
        else return false;
    }
    // TokenType conditions(String ar)
    // {
    //     if(ar.equals("And"))return TokenType.AND;else if (ar.equals("Or"))return TokenType.OR; else if (ar.equals("Equals"))return TokenType.EQUALS;
    //     else return TokenType.INVALID;
    // }
    boolean isvalidInt(String[]str)
    {
    if(isValididentifier(str[1])&&isequal(str[2])&&mathematicalexpression(str))
    return true;
    else return false;
    }
    public boolean isDataType(TokenType input)
    {
    if(input==TokenType.INTEGER||input==TokenType.DOUBLE||input==TokenType.LONG||input==TokenType.CHAR||input==TokenType.STRING)return true;return false;
    }
   boolean isequal(String input)
   {if(input.equals("=")||input.equals("Equals")||input.equals("equals")||input.equals("Is")||input.equals("is"))
   return true;
   return false;}
    boolean isLineType(TokenType input)
    {
        if(input==TokenType.PRINT||input==TokenType.IF)return true;return false;
    }
    public boolean isOperator(String input)
    {
        return (input.equals("Equals")||input.equals("-")||input.equals("+")||input.equals("*")||input.equals("/")
                ||input.equals("Plus")||input.equals("Minus")||input.equals("Times")||input.equals("=")||input.equals("By")||input.equals("Is")||input.equals("is"));
    }
    public boolean isValididentifier(String input)
    {
        if(nextTokenType(input)==TokenType.IDENTIFIER)return true;return false;
    }
    boolean isConditionStatement(String input)
    {
        if(input.equals("If"))return true;return false;
    }
    TokenType ConditionType(String tokenValue){
    if(tokenValue.equals("Or")||tokenValue.equals("or"))return TokenType.OR;
    else if(tokenValue.equals("And")||tokenValue.equals("and"))return TokenType.AND;
    else return TokenType.EQUALS;}
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
        //else if(input.length()==1)return TokenType.CHARACTER;
        else if(input.equals("If"))return TokenType.IF;
        else if(input.equals("Print")||input.equals("Print Line"))return TokenType.PRINT;  
        else if (isOperator(input))return TokenType.OPERATOR;
        else if(input.equals("Or")||input.equals("And")||input.equals("and")||input.equals("or")||input.equals("Is equal to"))return TokenType.CONDITION;
        else if(!Character.isDigit(input.charAt(0)))return TokenType.IDENTIFIER;
        else return TokenType.INVALID;
    }
    boolean isvalidprint(String[] str)
    {
        if (str.length>1)return true;return false;
    }
   
   
    public ArrayList<Token> tokenize(String input) throws IOException{
    ArrayList<Token> result = new ArrayList<Token>();
    String[] tokens2=input.trim().split("GO");
    for(String token:tokens2){
        System.out.println("\n"+token+"\n");
        String[] GO=token.trim().split(" ");
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

        else if (isDataType(nextTokenType(GO[0]))&&GO.length>2)
        {
            k2=LineType.ASSIGNMENT;
            System.out.println("Assignment statement");
        }
        else if (isDataType(nextTokenType(GO[0]))&&GO.length==2)
        {
            k2=LineType.DECLARATION;
            System.out.println("Declaration statement");
        }
        else if (nextTokenType(GO[0])==TokenType.PRINT)
        {
            k2=LineType.PRINT;
            System.out.println("Print statement");
        }

        else k2=LineType.INVALID;

        if(k2==LineType.ASSIGNMENT)
        {
                //if(GO.length>4){throw new Exception("ERROR! INVALID LINE DECLARATION, FORMAT:"+"\"TYPE,IDENTIFIER,OPERATOR,EXPRESSION\"");}
                if (nextTokenType(GO[0])==TokenType.CHAR&&!isvalidchar(GO))
                {throw new Exception("ERROR! Declared token type should be CHARACTER");}
                else if(nextTokenType(GO[0])==TokenType.CHAR&&isvalidchar(GO)&&GO.length>2)
                {if(token2==GO[3]){result.add(new Token(token2,TokenType.CHARACTER));result.add(new Token("GO", TokenType.GO));}else result.add(new Token(token2, nextTokenType(token2)));}
                else if(nextTokenType(GO[0])==TokenType.INTEGER&&!isvalidInt(GO)){throw new Exception("ERROR! Declared Integer assignment invalid");}
                else if(nextTokenType(GO[0])==TokenType.LONG&&!isvalidInt(GO)){throw new Exception("ERROR! Declared LONG assignment invalid");}
                else if(nextTokenType(token2)==TokenType.INVALID)throw new Exception("ERROR! INVALID TOKEN DETECTED");
                else {
                    if(i==GO.length-1) {result.add(new Token(token2, nextTokenType(token2)));
                        result.add(new Token("GO", TokenType.GO));}
                    else result.add(new Token(token2, nextTokenType(token2)));}
        }
        else if(k2==LineType.DECLARATION)
        {
                if(isDataType(nextTokenType(GO[0]))&&isValididentifier(GO[1]))result.add(new Token(token2, nextTokenType(token2)));
        }
        else if(k2==LineType.CONDITION)if(!isvalidcond(GO)) {throw new Exception("ERROR! INVALID CONDITION STATEMENT");}else {if(i==0)result.add(new Token(token2, nextTokenType(token2)));else result.add(new Token(token2, ConditionType(token2)));}
        else if(k2==LineType.PRINT){if(!isvalidprint(GO))throw new Exception("ERROR! INVALID PRINT STATEMENT");
        else if(i!=0) result.add(new Token(token2, TokenType.PRINTED));
        else result.add(new Token(token2, nextTokenType(token2)));}
        else {throw new Exception("Wrong or Invalid line type.");}

    }
} catch (Exception e) {
    e.printStackTrace();System.exit(1);
}
}
    return result;
    }



}
