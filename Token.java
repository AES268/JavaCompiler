
package token;


public class Token
{
    public  String tokenValue;
    public  TokenType tokenType;
    public Token(String tokenValue,TokenType tokenType)
    {
        this.tokenValue=tokenValue;
        this.tokenType=tokenType;
    }
    public TokenType getType()
    {
        return tokenType;
    }
    public String getValue()
    {
        return tokenValue;
    }
}
