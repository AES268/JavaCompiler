

public class Token
{
     String tokenValue;
     TokenType tokenType,stype1,stype2,stype3;
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
    Token(String tokenValue,TokenType tokenType,int line,TokenType stype1)
    {
        this.tokenValue=tokenValue;
        this.tokenType=tokenType;
        this.stype1=stype1;this.line=line;
    }
    Token(String tokenValue,TokenType tokenType,int line,TokenType stype1,TokenType stype2)
    {
        this.tokenValue=tokenValue;
        this.tokenType=tokenType;this.stype1=stype1;this.stype2=stype2;this.line=line;
    }
    Token(String tokenValue,TokenType tokenType,int line,TokenType stype1,TokenType stype2,TokenType stype3)
    {
        this.tokenValue=tokenValue;
        this.tokenType=tokenType;
        this.stype1=stype1;
        this.stype1=stype2;
        this.stype1=stype3;this.line=line;
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
