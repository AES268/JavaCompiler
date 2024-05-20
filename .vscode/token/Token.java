

public class Token
{
     String tokenValue;
     TokenType tokenType;
     int line;
    Token(String tokenValue,TokenType tokenType)
    {
        this.tokenValue=tokenValue;
        this.tokenType=tokenType;
    }
    Token(String tokenValue,TokenType tokenType,int line)
    {
        this.tokenValue=tokenValue;
        this.tokenType=tokenType;
        this.line=line;
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
