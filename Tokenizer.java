package token;

import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tokenizer
{
    
    public static void main(String[]args)
    {
        String filePath="src/Input.txt";
        String testt="";
        try{
            testt=new String(Files.readAllBytes(Paths.get(filePath)));
        }catch(IOException e){
            e.printStackTrace();
        }
        //Integer x equals 6
        //Character b equals 5
        //String[] tokens=testt.split(" ");
        //for (String token : tokens)
        //{System.out.print(token + ", ");}
        //System.out.print("\n\n\n");
        
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
        //ExpressionTree tree=tokenizer.buildExpressionTree(listoftoken);
        //Calculator calculator=new Calculator();
        //double result=calculator.evaluate(tree.root);

        //tree.traverseInOrder(tree.root);
        //System.out.println("\nresult="+result);
        //Integer Integer x equals 5
    }
    public static boolean isNumeric(String str) {
    for (char c : str.toCharArray()) {
        if (!Character.isDigit(c)) {return false;}
    }
    return true;
}
    public boolean isDataType(TokenType input)
    {
       if (input!=TokenType.NUMBER&&input!=TokenType.OPERATOR&&input!=TokenType.CHARACTER)return true;
       return false;
    }
    public boolean isOperator(String input)
    {
        return (input.equals("equals")||input.equals('-')||input.equals('+')||input.equals('*')||input.equals('/')
                ||input.equals("plus")||input.equals("minus")||input.equals("times")||input.equals('=')||input.equals("by"));
    }
    public TokenType nextTokenType(String input)
    {
    //Character.isAlphabetic(input.charAt(0))
        if(input.equals("Integer"))return TokenType.INTEGER;

        else if(input.equals("String"))return TokenType.STRING;

        else if(input.equals("Character"))return TokenType.CHAR;

        else if(input.equals("Double"))return TokenType.DOUBLE;
        
        else if(input.equals("Long"))return TokenType.LONG;

        else if(isNumeric(input))return TokenType.NUMBER;
        
        else if(input.equals("if"))return TokenType.IF;
        
        else if(input.equals("Print")||input.equals("Print Line"))return TokenType.PRINT;
        
        else if(input.length()==1)return TokenType.CHARACTER;
//        else if(Character.isWhitespace(nextChar))
//        {
//            return TokenType.WHITESPACE;
//        }
        else if (isOperator(input))
        {
            return TokenType.OPERATOR;
        }
        else
        {
            return TokenType.INVALID;
        }
    }
    public ArrayList<Token> tokenize(String input) throws IOException{
    ArrayList<Token> result = new ArrayList<Token>();
    String[] tokens=input.split(" ");
    String[] tokens2=input.split("GO");
    int stop=0;
//        for(String token:tokens2){
//            System.out.println(token);}
        
    for(String token:tokens2){
        System.out.println(token+"\n");
        String[] GO=token.split(" ");
try {
    for (int i = 0; i < GO.length; i++) {
        String token2 = GO[i];
        TokenType k;LineType k2=LineType.ASSIGNMENT;
        if (nextTokenType(GO[0])==TokenType.IF)
        k2=LineType.CONDITION;
        else if (isDataType(nextTokenType(GO[0])))
        k2=LineType.ASSIGNMENT;
        else if (nextTokenType(GO[0])==TokenType.PRINT)
        k2=LineType.PRINT;
        else if(nextTokenType(GO[0])==TokenType.INVALID)k2=LineType.INVALID;
        if(k2==LineType.ASSIGNMENT)
        {
                if (i == 0 && !isDataType(nextTokenType(token2))) {
                throw new Exception("Invalid data type declaration.");
                 }
                //Character k equals kasfgjasdg
                else if (nextTokenType(GO[0])==TokenType.CHAR&&nextTokenType(GO[3])!=TokenType.CHARACTER)
                {throw new Exception("ERROR! Declared token type should be CHARACTER");}
                result.add(new Token(token2, nextTokenType(token2)));
        }
    }
} catch (Exception e) {
    e.printStackTrace();System.exit(1);
}
}
//    for(Token token4:result)
//    {
//        System.out.println(token4.tokenValue+" "+token4.tokenType);
//    }
    return result;
    }
    
    
//    public ExpressionTree buildExpressionTree(ArrayList<Token> tokens) 
//    {
//        ExpressionTree tree = new ExpressionTree();
//        tree.buildTree(tokens);
//        return tree;
//    }
    

}
