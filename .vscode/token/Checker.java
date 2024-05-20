import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Checker {
    public void checkassignmentorder(ArrayList<Token>Tokens,ArrayList<Token>Variables) throws Exception {
        for (Token token1 : Tokens) {
            for (Token token2 : Variables) {
                if ((token1.getValue().equals(token2.getValue()) && token1.line < token2.line)) {
                    //System.out.println("Token: " + token1.getValue() + " found on different lines. Line in Tokens list: " + token1.line + ", Line in Assigned Variables list: " + token2.line);
                    throw new Exception("Token: " + token1.getValue() + " used before its assignment. Used in line: " + token1.line + ", assigned in line: " + token2.line+"\nInvalid variable use, check for unassigned or undeclared variables.");
                }
            }
        }
    }
    // boolean checkassignmentexists(ArrayList<Token>Tokens,ArrayList<Token>Variables)throws Exception
    // {
    //     //
    //     List<Token> identifierTokens = Tokens.stream().filter(token -> token.getType() == TokenType.IDENTIFIER).distinct().collect(Collectors.toList());
    //     Set<String> K = new HashSet<>();
    //     List<Token> uniqueline=Tokens.stream().filter(token -> token.getType() == TokenType.IDENTIFIER).distinct().collect(Collectors.toList()).stream().filter(Token-> K.add(Token.getValue())).collect(Collectors.toList());
    //     //List<String> identifierTokens2 = Tokens.stream().filter(token -> token.getType() == TokenType.IDENTIFIER).map(token->token.tokenValue).distinct().collect(Collectors.toList());
    //     for(Token yes:uniqueline)System.out.println(yes.getValue()+" "+yes.getType()+" "+yes.line);
        

    //     // for(Token tokens: uniqueline)
    //     // {int found=0;
    //     //         for(Token vars: Variables)
    //     //         {
    //     //             if (vars.getValue().equals(tokens.getValue()))found=1;
    //     //         }
    //     //         if(found==0)
    //     //         throw new Exception("Used variable unassigned");return false;
    //     // }
    //     for(int i =0;i<uniqueline.size();i++)
    //     {int found =0;
    //         for(int j=0;j<Variables.size();i++)
    //         {
    //             if(uniqueline[i]==Variables[i])found=1;
    //         }
    //     }
    //     return true;
    // }
    public boolean checkassignmentexists(ArrayList<Token> Tokens, ArrayList<Token> Variables) throws Exception {
        List<Token> identifierTokens = Tokens.stream().filter(token -> token.getType() == TokenType.IDENTIFIER).distinct().collect(Collectors.toList());
        Set<String> uniqueIdentifiers = new HashSet<>();
        List<Token> uniqueLineTokens = identifierTokens.stream().filter(token -> uniqueIdentifiers.add(token.getValue())).collect(Collectors.toList());
       // for (Token yes : uniqueLineTokens) {System.out.print(yes.getValue() + " " + yes.getType() + " " + yes.line+" ");}System.out.println("");
       // for (Token no:Variables){System.out.print(no.getValue() + " " + no.getType() + " " + no.line+" ");}
        for (Token token : uniqueLineTokens) {
            boolean found = Variables.stream().anyMatch(var -> var.getValue().equals(token.getValue()));
            if (!found) {throw new Exception("Used variable unassigned: " + token.getValue());}
        }

        return true;
    }
}

