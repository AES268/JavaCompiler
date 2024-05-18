import java.util.ArrayList;

public class Checker {
    ArrayList<Token>Tokens,Variables;
    Checker(ArrayList<Token>list1,ArrayList<Token>list2)
    {
        Tokens=list1;Variables=list2;
    }

    public void checkassignment() {
        for (Token token1 : Tokens) {
            for (Token token2 : Variables) {
                if (token1.getValue().equals(token2.getValue()) && token1.line != token2.line) {
                    System.out.println("Token: " + token1.getValue() + " found on different lines. Line in list1: " + token1.line + ", Line in list2: " + token2.line);
                }
            }
        }
    }
}
