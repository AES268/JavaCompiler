import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Tokenizer {
    public static void main(String[] args) throws Exception {

        String filePath = ".vscode\\token\\Input.txt";
        String testt = "";
        try {
            testt = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> listoftoken = null,listofVariables = null,assigned=null;
        try {
            listoftoken = tokenizer.tokenize(testt);
            assigned = tokenizer.getassignedvariables(testt);
            listofVariables = tokenizer.assignedvariables(testt);
            for (Token token : listoftoken) {
                System.out.println(token.getValue() + ":" + token.getType() + ":" + token.line + "\n");
            }
            for (Token token2 : assigned) {
                //System.out.println(token2.getValue() + ":" + token2.getType() + ":" + token2.line + "\n"+"AAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
            for (Token token : listofVariables) {
                System.out.println(token.getValue() + ":" + token.getType() + ":" + token.line + "\n");
            }
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        Checker Z=new Checker();
        Z.checkassignmentexists(listoftoken,listofVariables);
        Z.checkassignmentorder(listoftoken,listofVariables);
        Compiler Z2=new Compiler(listoftoken);
        System.out.println(Z2.Complete());

    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    boolean declared_mathematical_expression(String[] exp) {
        // StringBuilder Z=new StringBuilder();
        // for (String L: exp)
        // {
        //     Z.append(L+" ");
        // }
        // System.err.println(Z);
        for (int i = 3; i < exp.length; i++) {
            if ((i % 2 == 0 && (isNumeric(exp[i]) || isValididentifier(exp[i]) || (i % 2 != 0 && isOperator(exp[i]))))) {
                return false;
            }
        }
        return true;}

        boolean assigned_mathematical_expression(String[] exp) {
            StringBuilder Z=new StringBuilder();
            for (String L: exp)
            {
                Z.append(L+" ");
            }
            System.err.println(Z);
            for (int i = 2; i < exp.length; i++) {
                if ((i % 2 != 0 && (isNumeric(exp[i]) || isValididentifier(exp[i]) || (i % 2 == 0 && isOperator(exp[i]))))) {
                    return false;
                }
            }
            return true;}
            boolean assigned_mathematical_expressionNUMBERS(String[] exp) {
                StringBuilder Z=new StringBuilder();
                for (String L: exp)
                {
                    Z.append(L+" ");
                }
                System.err.println(Z);
                for (int i = 2; i < exp.length; i++) {
                    if ((i % 2 != 0 && (isNumeric(exp[i])) || (i % 2 == 0 && isOperator(exp[i])))) {
                        return false;
                    }
                }
                return true;}
                boolean declared_mathematical_expressionNUMBERS(String[] exp) {
                    // StringBuilder Z=new StringBuilder();
                    // for (String L: exp)
                    // {
                    //     Z.append(L+" ");
                    // }
                    // System.err.println(Z);
                    for (int i = 3; i < exp.length; i++) {
                        if ((i % 2 == 0 && (isNumeric(exp[i])) || (i % 2 != 0 && isOperator(exp[i])))){
                            return false;
                        }
                    }
                    return true;}
    

    boolean isvalidchar(String[] exp) {
        if (exp.length == 2 && nextTokenType(exp[0]) == TokenType.CHAR && isValididentifier(exp[1])) return true;
        else if (exp.length == 4 && nextTokenType(exp[0]) == TokenType.CHAR && isValididentifier(exp[1]) && isequal(exp[2]) && exp[3].length() == 1) return true;
        return false;
    }

    boolean isvalidcond(String[] GO) {
        int s=0,found=0;
        for(int i =0;i<GO.length;i++)if(GO[i].equals("then")){found=1;s=i-1;}
        if(found==1){
        if (s == 2) {if (isValididentifier(GO[1]) || isNumeric(GO[1])) return true;return false;} 
        else if (GO.length-1 > 2 && (s-1) % 2 == 0) {for (int i = 1; i < s; i++) {
            if ((i % 2 != 0 && (!isValididentifier(GO[i]) && !isNumeric(GO[i]))) || (i % 2 == 0 && (!isCondition(GO[i])))||!GO[GO.length-1].equals("done")) return false;}
            return true;
        } else return false;
    }
    else return false;
    }
    int thenlocation(String[] GO) {
        for(int i=0;i<GO.length;i++)if (GO[i].equals("then"))return i;return 0;
    }
    ArrayList<Token> isvalidthen(String[] GO,int line) {
        int then_location=0,done_location=0;StringBuilder result = new StringBuilder();
        while(!GO[then_location++].equals("then"));
        while(!GO[++done_location].equals("done"));
         for(int i=then_location;i<=done_location;i++)
         {result.append(GO[i]+" ");}
         //result.append("GO");
         //System.out.println(result);
         String input=result.toString();
         //System.out.println(input+"AAAAAAAAAAAAAAAAAAA");
         Tokenizer thenOutput=new Tokenizer();
         ArrayList<Token> IfResult=null;
         try{IfResult=thenOutput.tokenizeIf(input,line);}
         catch(IOException e){System.out.println("ERROR");}
         Collections.swap(IfResult, IfResult.size()-1, IfResult.size()-2);
         return IfResult;
        // int s=0,found=0;
        // for(int i =0;i<GO.length&&found!=1;i++)if(GO[i].equals("then")){found=1;s=i;}
        //         // StringBuilder result = new StringBuilder();
        // for(int i=0;i<)
        // ArrayList<Token> thenoutput=Tokenizer.tokenize();
    }

    boolean isvalidInt(String[] str) {
        if (isValididentifier(str[1]) && isequal(str[2]) && declared_mathematical_expression(str))
            return true;
        else return false;
    }

    public boolean isDataType(TokenType input) {
        if (input == TokenType.INTEGER || input == TokenType.DOUBLE || input == TokenType.LONG || input == TokenType.CHAR || input == TokenType.STRING) return true;
        return false;
    }

    boolean isequal(String input) {
        if (input.equals("=") || input.equals("Equals") || input.equals("equals") || input.equals("Is") || input.equals("is"))
            return true;
        return false;
    }
    TokenType isequalcheck(String input) {
        if (input.equals("=") || input.equals("Equals") || input.equals("equals") || input.equals("Is") || input.equals("is"))
            return TokenType.EQUALSCHECK;
            return TokenType.INVALID;
    }

    public boolean isOperator(String input) {
        return (input.equals("Equals") || input.equals("-") || input.equals("+") || input.equals("*") || input.equals("/")
                || input.equals("Plus") || input.equals("Minus") || input.equals("Times") || input.equals("=") || input.equals("By") || input.equals("Is") || input.equals("is"));
    }

    public boolean isValididentifier(String input) {
        if (nextTokenType(input) == TokenType.IDENTIFIER) return true;
        return false;
    }

    boolean isPrintStatement(String input) {
        if (input.equals("Print") || input.equals("Print Line")) return true;
        return false;
    }

    boolean isCondition(String input) {
        if (input.equals("Or") || input.equals("And") || input.equals("and") || input.equals("or") || input.equals("Equals") || input.equals("equals") || input.equals("EQLess") || input.equals("EQMore") || input.equals("MoreThan") || input.equals("LessThan")||input.equals("is")||input.equals("Is"))
            return true;
        return false;
    }

    public TokenType nextTokenType(String input) {
        if (input.equals("Integer")) return TokenType.INTEGER;
        else if (input.equals("String")) return TokenType.STRING;
        else if (input.equals("Character")) return TokenType.CHAR;
        else if (input.equals("Double")) return TokenType.DOUBLE;
        else if (input.equals("Long")) return TokenType.LONG;
        else if (isNumeric(input)) return TokenType.NUMBER;
        else if(input.equals("then"))return TokenType.THEN;
        else if (input.equals("If")) return TokenType.IF;
        else if (input.equals("Print") || input.equals("Print Line")) return TokenType.PRINT;
        //else if (isOperator(input)) return TokenType.OPERATOR;
        else if(input.equals("Plus")||input.equals("plus")||input.equals("+"))return TokenType.ADD;
        else if(input.equals("Minus")||input.equals("minus")||input.equals("-"))return TokenType.SUB;
        else if(input.equals("Times")||input.equals("times")||input.equals("*"))return TokenType.TIMES;
        else if(input.equals("By")||input.equals("by")||input.equals("/"))return TokenType.BY;
        else if(input.equals("done"))return TokenType.DONE;
        else if (input.equals("Or") || input.equals("or")) return TokenType.OR;
        else if (input.equals("And") || input.equals("and")) return TokenType.AND;
        else if (input.equals("Equals") || input.equals("equals")||input.equals("is") || input.equals("Is")||input.equals("=")) return TokenType.EQUALS;
        else if (input.equals("EQLess")) return TokenType.EQUALSLESS;
        else if (input.equals("LessThan")) return TokenType.LESS;
        else if (input.equals("EQMore")) return TokenType.EQUALSMORE;
        else if (input.equals("MoreThan")) return TokenType.MORE;
        else if (!Character.isDigit(input.charAt(0))) return TokenType.IDENTIFIER;
        else return TokenType.INVALID;
    }

    public ArrayList<Token> tokenize(String input) throws IOException {
        ArrayList<Token> result = new ArrayList<>();
        String[] tokens2 = input.trim().split("GO");
        int linenum = 0;
        try {
            if (!input.contains("GO")) throw new Exception("ERROR! Missing GO");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        for (String token : tokens2) {
            linenum++;
            //System.out.println("\n" + token + "\n");
            String[] GO = token.trim().split(" ");
            try {
                for (int i = 0; i < GO.length; i++) {
                    String token2 = GO[i];
                    LineType k2;
                    //System.out.println(GO[i]);
                    if (nextTokenType(GO[0]) == TokenType.IF) k2 = LineType.CONDITION;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length > 2) k2 = LineType.ASSIGNMENT;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length == 2) k2 = LineType.DECLARATION;
                    else if (nextTokenType(GO[0]) == TokenType.PRINT) k2 = LineType.PRINT;
                    else if(nextTokenType(GO[0])==TokenType.IDENTIFIER)k2=LineType.ASSIGNMENT_NO_DECLARATION;
                    else k2 = LineType.INVALID;
                    if (k2 == LineType.ASSIGNMENT) {
                        if (nextTokenType(GO[0]) == TokenType.CHAR && !isvalidchar(GO)) {
                            throw new Exception("ERROR! Declared token type should be CHARACTER");
                        } else if (nextTokenType(GO[0]) == TokenType.CHAR && isvalidchar(GO) && GO.length > 2) {
                            if (token2.equals(GO[3])) {
                                result.add(new Token(token2, TokenType.CHARACTER, linenum));
                                result.add(new Token("GO", TokenType.GO, linenum));
                            } else result.add(new Token(token2, nextTokenType(token2), linenum));
                        } else if (nextTokenType(GO[0]) == TokenType.INTEGER && !isvalidInt(GO)) {
                            throw new Exception("ERROR! Declared Integer assignment invalid");
                        } else if (nextTokenType(GO[0]) == TokenType.LONG && !isvalidInt(GO)) {
                            throw new Exception("ERROR! Declared LONG assignment invalid");
                        } else if (nextTokenType(token2) == TokenType.INVALID) {
                            throw new Exception("ERROR! INVALID TOKEN DETECTED");
                        } else if(nextTokenType(GO[0])==TokenType.STRING&&i==GO.length-1){result.add(new Token(token2, TokenType.PRINTED,linenum));
                        result.add(new Token("GO", TokenType.GO,linenum));}else if(nextTokenType(GO[0])==TokenType.STRING&&i>2){result.add(new Token(token2, TokenType.PRINTED,linenum));}
                        else {
                            if (i == GO.length - 1) {
                                result.add(new Token(token2, nextTokenType(token2), linenum));
                                result.add(new Token("GO", TokenType.GO, linenum));
                            } else {
                                if(nextTokenType(GO[0])==TokenType.INTEGER&&declared_mathematical_expressionNUMBERS(GO)&&i>2)
                                {StringBuilder k=new StringBuilder(); for(int h=3;h<GO.length;h++)
                                k.append(GO[h]+" ");String Expression=k.toString();Evaluator V= new Evaluator();int ExpressionResult=V.evaluate(Expression);System.out.println("Result of expression: "+ExpressionResult);
                                String as=ExpressionResult+"";Token Resultsa=new Token(as,nextTokenType(as),linenum);result.add(Resultsa);break;}
                                result.add(new Token(token2, nextTokenType(token2), linenum));
                            }
                        }
                    } 
                    else if(k2==LineType.ASSIGNMENT_NO_DECLARATION)
                    {
                        if(!isValididentifier(GO[0])||!isequal(GO[1])){throw new Exception("ERROR! INVALID ASSIGNMENT");}
                        else
                        {
                            result.add(new Token(token2,nextTokenType(token2),linenum));
                        }
                    }
                    else if (k2 == LineType.DECLARATION) {
                        if (isDataType(nextTokenType(GO[0])) && isValididentifier(GO[1])) {
                            if (i == GO.length - 1) {
                                result.add(new Token(token2, nextTokenType(token2), linenum));
                                result.add(new Token("GO", TokenType.GO, linenum));
                            } else {
                                result.add(new Token(token2, nextTokenType(token2), linenum));
                            }
                        }
                    } else if (k2 == LineType.CONDITION) {int then=thenlocation(GO);
                        if (!isvalidcond(GO)) {throw new Exception("ERROR! INVALID CONDITION STATEMENT");}
                        else if (i <= then) {
                            if(isequal(token2))result.add(new Token(token2,TokenType.EQUALSCHECK,linenum));
                            else result.add(new Token(token2, nextTokenType(token2), linenum));
                            //result.add(new Token("GO", TokenType.GO, linenum));
                        } 
                        else {
                            ArrayList<Token> restofIF = isvalidthen(GO,linenum);
                            for (int j = 0; j < restofIF.size(); j++) {result.add(restofIF.get(j));}//if(j==restofIF.size()-1){{result.add(new Token("done", TokenType.DONE,linenum));}}
                            break;
                        }
                    } else if (k2 == LineType.PRINT) {
                        if (GO.length < 2) throw new Exception("ERROR! INVALID PRINT STATEMENT");
                        else if (i != 0) result.add(new Token(token2, TokenType.PRINTED, linenum));
                        else result.add(new Token(token2, nextTokenType(token2), linenum));
                    } else {
                        throw new Exception("Wrong or Invalid line type.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return result;
    }
    public ArrayList<Token> getassignedvariables(String input) throws IOException {
        ArrayList<Token> result = new ArrayList<>();
        String[] tokens2 = input.trim().split("GO");
        int linenum = 0;
        try {
            if (!input.contains("GO")) throw new Exception("ERROR! Missing GO");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        for (String token : tokens2) {
            linenum++;
            //System.out.println("\n" + token + "\n");
            String[] GO = token.trim().split(" ");
            try {
                for (int i = 0; i < GO.length; i++) {
                    String token2 = GO[i];
                    LineType k2;
                    //System.out.println(GO[i]);
                    if (nextTokenType(GO[0]) == TokenType.IF) k2 = LineType.CONDITION;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length > 2) k2 = LineType.ASSIGNMENT;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length == 2) k2 = LineType.DECLARATION;
                    else if (nextTokenType(GO[0]) == TokenType.PRINT) k2 = LineType.PRINT;
                    else if(nextTokenType(GO[0])==TokenType.IDENTIFIER)k2=LineType.ASSIGNMENT_NO_DECLARATION;
                    else k2 = LineType.INVALID;
                    if (k2 == LineType.ASSIGNMENT) {
                        // if (nextTokenType(GO[0]) == TokenType.CHAR && !isvalidchar(GO)) {
                        //     throw new Exception("ERROR! Declared token type should be CHARACTER");
                        // } else if (nextTokenType(GO[0]) == TokenType.CHAR && isvalidchar(GO) && GO.length > 2) {
                        //     if (token2.equals(GO[3])) {
                        //         result.add(new Token(token2, TokenType.CHARACTER, linenum));
                        //         result.add(new Token("GO", TokenType.GO, linenum));
                        //     } else result.add(new Token(token2, nextTokenType(token2), linenum));
                        // } else if (nextTokenType(GO[0]) == TokenType.INTEGER && !isvalidInt(GO)) {
                        //     throw new Exception("ERROR! Declared Integer assignment invalid");
                        // } else if (nextTokenType(GO[0]) == TokenType.LONG && !isvalidInt(GO)) {
                        //     throw new Exception("ERROR! Declared LONG assignment invalid");
                        // } else if (nextTokenType(token2) == TokenType.INVALID) {
                        //     throw new Exception("ERROR! INVALID TOKEN DETECTED");
                        // } else if(nextTokenType(GO[0])==TokenType.STRING&&i>2){result.add(new Token(token2, TokenType.PRINTED,linenum));}
                        
                        // else {
                        //     if (i == GO.length - 1) {
                        //         result.add(new Token(token2, nextTokenType(token2), linenum));
                        //         result.add(new Token("GO", TokenType.GO, linenum));
                        //     } else {
                        //         result.add(new Token(token2, nextTokenType(token2), linenum));
                        //     }
                        // }
                    } 
                    else if(k2==LineType.ASSIGNMENT_NO_DECLARATION)
                    {
                        if(!isValididentifier(GO[0])||!isequal(GO[1])){throw new Exception("ERROR! INVALID ASSIGNMENT");}
                        else
                        {
                            if(token==GO[0]){
                            if(GO.length==3)
                            {
                                if(GO[2].length()==1)
                                {
                                    if(isNumeric(GO[2]))result.add(new Token(token2,nextTokenType(token2),linenum,TokenType.INTEGER,TokenType.STRING,TokenType.CHAR));
                                    else result.add(new Token(token2,nextTokenType(token2),linenum,TokenType.STRING,TokenType.CHAR));
                                }
                                else 
                                {
                                    if(isNumeric(GO[2]))result.add(new Token(token2,nextTokenType(token2),linenum,TokenType.INTEGER,TokenType.STRING));
                                    else result.add(new Token(token2,nextTokenType(token2),linenum,TokenType.STRING));
                                }
                            }
                           else if(GO.length>3)
                            {
                                if(assigned_mathematical_expression(GO))result.add(new Token(token2, nextTokenType(token2),linenum,TokenType.INTEGER,TokenType.STRING));
                                else result.add(new Token(token2, nextTokenType(token2),linenum,TokenType.STRING));

                            }
                        }
                    }
                    }
                    else if (k2 == LineType.DECLARATION) {
                        // if (isDataType(nextTokenType(GO[0])) && isValididentifier(GO[1])) {
                        //     if (i == GO.length - 1) {
                        //         result.add(new Token(token2, nextTokenType(token2), linenum));
                        //         result.add(new Token("GO", TokenType.GO, linenum));
                        //     } else {
                        //         result.add(new Token(token2, nextTokenType(token2), linenum));
                        //     }
                        // }
                    } else if (k2 == LineType.CONDITION) {
                        // int then=thenlocation(GO);
                        // if (!isvalidcond(GO)) {throw new Exception("ERROR! INVALID CONDITION STATEMENT");}
                        // else if (i <= then) {
                        //     if(isequal(token2))result.add(new Token(token2,TokenType.EQUALSCHECK,linenum));
                        //     else result.add(new Token(token2, nextTokenType(token2), linenum));
                        //     //result.add(new Token("GO", TokenType.GO, linenum));
                        // } 
                        // else {
                        //     ArrayList<Token> restofIF = isvalidthen(GO,linenum);
                        //     for (int j = 0; j < restofIF.size(); j++) {
                        //         //if(j==restofIF.size()-1){{result.add(new Token("done", TokenType.DONE,linenum));}}
                        //         result.add(restofIF.get(j));
                        //     }
                        // }
                    } 
                    else if (k2 == LineType.PRINT) {
                        // if (GO.length < 2) throw new Exception("ERROR! INVALID PRINT STATEMENT");
                        // else if (i != 0) result.add(new Token(token2, TokenType.PRINTED, linenum));
                        // else result.add(new Token(token2, nextTokenType(token2), linenum));
                    } else {
                        //throw new Exception("Wrong or Invalid line type.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return result;
    }
    public ArrayList<Token> tokenizeIf(String input,int Line) throws IOException {
        ArrayList<Token> result = new ArrayList<>();
        String[] tokens2 = input.trim().split("GO");
        int linenum = 0;
        // try {
        //     if (!input.contains("GO")) throw new Exception("ERROR! Missing GO");
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     System.exit(1);
        // }
        for (String token : tokens2) {
            linenum++;
            //System.out.println("\n" + token + "\n");
            String[] GO = token.trim().split(" ");
            try {
                for (int i = 0; i < GO.length; i++) {
                    String token2 = GO[i];
                    LineType k2;
                    //System.out.println(GO[i]);
                    if (nextTokenType(GO[0]) == TokenType.IF) k2 = LineType.CONDITION;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length > 2) k2 = LineType.ASSIGNMENT;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length == 2) k2 = LineType.DECLARATION;
                    else if (nextTokenType(GO[0]) == TokenType.PRINT) k2 = LineType.PRINT;
                    else if(nextTokenType(GO[0])==TokenType.IDENTIFIER)k2=LineType.ASSIGNMENT_NO_DECLARATION;
                    else k2 = LineType.INVALID;

                    if (k2 == LineType.ASSIGNMENT) {
                        if (nextTokenType(GO[0]) == TokenType.CHAR && !isvalidchar(GO)) {
                            throw new Exception("ERROR! Declared token type should be CHARACTER");
                        } 
                        else if (nextTokenType(GO[0]) == TokenType.CHAR && isvalidchar(GO) && GO.length > 2) {
                            if (token2.equals(GO[3])) {result.add(new Token(token2, TokenType.CHARACTER, Line));result.add(new Token("GO", TokenType.GO, Line));} 
                            else result.add(new Token(token2, nextTokenType(token2), Line));} 
                            
                            else if (nextTokenType(GO[0]) == TokenType.INTEGER && !isvalidInt(GO)) {throw new Exception("ERROR! Declared Integer assignment invalid");} 
                            else if (nextTokenType(GO[0]) == TokenType.LONG && !isvalidInt(GO)) {throw new Exception("ERROR! Declared LONG assignment invalid");} 
                            else if (nextTokenType(token2) == TokenType.INVALID) {throw new Exception("ERROR! INVALID TOKEN DETECTED");} 
                            else if(nextTokenType(GO[0])==TokenType.STRING&&i>2){result.add(new Token(token2, TokenType.PRINTED));}
                            else {
                            if (i == GO.length - 1) {
                                result.add(new Token(token2, nextTokenType(token2), Line));
                                result.add(new Token("GO", TokenType.GO, Line));
                            } else {
                                result.add(new Token(token2, nextTokenType(token2), Line));
                            }
                        }
                    } else if (k2 == LineType.DECLARATION) {
                        if (isDataType(nextTokenType(GO[0])) && isValididentifier(GO[1])) {
                            if (i == GO.length - 1) {
                                result.add(new Token(token2, nextTokenType(token2), Line));
                                result.add(new Token("GO", TokenType.GO, Line));
                            } else {
                                result.add(new Token(token2, nextTokenType(token2), Line));
                            }
                        }
                    }   
                    else if (k2 == LineType.CONDITION) {int then=thenlocation(GO);
                        if (!isvalidcond(GO)) {throw new Exception("ERROR! INVALID CONDITION STATEMENT");}
                        else if (i <= then) {
                            if(isequal(token2))result.add(new Token(token2,TokenType.EQUALSCHECK,linenum));
                            else result.add(new Token(token2, nextTokenType(token2), Line));
                            //result.add(new Token("GO", TokenType.GO, Line));
                        } 
                        else {
                            ArrayList<Token> restofIF = isvalidthen(GO,Line);
                            for (int j = 0; j < restofIF.size(); j++) {
                                result.add(restofIF.get(j));
                            }
                        }
                    } else if (k2 == LineType.PRINT) {
                        if (GO.length < 2) throw new Exception("ERROR! INVALID PRINT STATEMENT");
                        else if (i != 0) result.add(new Token(token2, TokenType.PRINTED, Line));
                        else result.add(new Token(token2, nextTokenType(token2), Line));
                    } else {
                        throw new Exception("Wrong or Invalid line type.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        //System.out.println("RESULT OF TOKENIZE IFFFFFF");
        for (Token token : result) {
            //System.out.println(token.getValue() + ":" + token.getType() + ":" + token.line + "\n");
        }
        return result;
    }
    public ArrayList<Token> tokenizeIf2(String input,int line) throws IOException {
        ArrayList<Token> result = new ArrayList<>();
        String[] tokens2 = input.trim().split("GO");
        int linenum = 0;
        try {
            if (!input.contains("GO")) throw new Exception("ERROR! Missing GO");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        for (String token : tokens2) {
            linenum++;
            //System.out.println("\n" + token + "\n");
            String[] GO = token.trim().split(" ");
            try {
                for (int i = 0; i < GO.length; i++) {
                    String token2 = GO[i];
                    LineType k2;
                    //System.out.println(GO[i]);
                    if (nextTokenType(GO[0]) == TokenType.IF) k2 = LineType.CONDITION;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length > 2) k2 = LineType.ASSIGNMENT;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length == 2) k2 = LineType.DECLARATION;
                    else if (nextTokenType(GO[0]) == TokenType.PRINT) k2 = LineType.PRINT;
                    else if(nextTokenType(GO[0])==TokenType.IDENTIFIER)k2=LineType.ASSIGNMENT_NO_DECLARATION;
                    else k2 = LineType.INVALID;
                    if (k2 == LineType.ASSIGNMENT) {
                        if (nextTokenType(GO[0]) == TokenType.CHAR && !isvalidchar(GO)) {
                            throw new Exception("ERROR! Declared token type should be CHARACTER");
                        } else if (nextTokenType(GO[0]) == TokenType.CHAR && isvalidchar(GO) && GO.length > 2) {
                            if (token2.equals(GO[3])) {
                                result.add(new Token(token2, TokenType.CHARACTER, line));
                                result.add(new Token("GO", TokenType.GO, line));
                            } else result.add(new Token(token2, nextTokenType(token2), line));
                        } else if (nextTokenType(GO[0]) == TokenType.INTEGER && !isvalidInt(GO)) {
                            throw new Exception("ERROR! Declared Integer assignment invalid");
                        } else if (nextTokenType(GO[0]) == TokenType.LONG && !isvalidInt(GO)) {
                            throw new Exception("ERROR! Declared LONG assignment invalid");
                        } else if (nextTokenType(token2) == TokenType.INVALID) {
                            throw new Exception("ERROR! INVALID TOKEN DETECTED");
                        } else if(nextTokenType(GO[0])==TokenType.STRING&&i>2){result.add(new Token(token2, TokenType.PRINTED,linenum));}
                        
                        else {
                            if (i == GO.length - 1) {
                                result.add(new Token(token2, nextTokenType(token2), line));
                                result.add(new Token("GO", TokenType.GO, line));
                            } else {
                                result.add(new Token(token2, nextTokenType(token2), line));
                            }
                        }
                    } 
                    else if(k2==LineType.ASSIGNMENT_NO_DECLARATION)
                    {
                        if(!isValididentifier(GO[0])||!isequal(GO[1])){throw new Exception("ERROR! INVALID ASSIGNMENT");}
                        else
                        {
                            result.add(new Token(token2,nextTokenType(token2),linenum));
                        }
                    }
                    else if (k2 == LineType.DECLARATION) {
                        if (isDataType(nextTokenType(GO[0])) && isValididentifier(GO[1])) {
                            if (i == GO.length - 1) {
                                result.add(new Token(token2, nextTokenType(token2), line));
                                result.add(new Token("GO", TokenType.GO, line));
                            } else {
                                result.add(new Token(token2, nextTokenType(token2), line));
                            }
                        }
                    } else if (k2 == LineType.CONDITION) {int then=thenlocation(GO);
                        if (!isvalidcond(GO)) {throw new Exception("ERROR! INVALID CONDITION STATEMENT");}
                        else if (i <= then) {
                            if(isequal(token2))result.add(new Token(token2,TokenType.EQUALSCHECK,linenum));
                            else result.add(new Token(token2, nextTokenType(token2), line));
                            //result.add(new Token("GO", TokenType.GO, line));
                        } 
                        else {
                            ArrayList<Token> restofIF = isvalidthen(GO,linenum);
                            for (int j = 0; j < restofIF.size(); j++) {
                                //if(j==restofIF.size()-1){{result.add(new Token("done", TokenType.DONE,linenum));}}
                                result.add(restofIF.get(j));
                            }
                        }
                    } else if (k2 == LineType.PRINT) {
                        if (GO.length < 2) throw new Exception("ERROR! INVALID PRINT STATEMENT");
                        else if (i != 0) result.add(new Token(token2, TokenType.PRINTED, line));
                        else result.add(new Token(token2, nextTokenType(token2), line));
                    } else {
                        throw new Exception("Wrong or Invalid line type.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return result;
    }

    public ArrayList<Token> assignedvariables(String input) throws IOException {
        ArrayList<Token> result = new ArrayList<>();
        String[] tokens2 = input.trim().split("GO");
        int linenum = 0;
        try {
            if (!input.contains("GO")) throw new Exception("ERROR! Missing GO");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        for (String token : tokens2) {
            linenum++;
            //System.out.println("\n" + token + "\n");
            String[] GO = token.trim().split(" ");
            try {
                for (int i = 0; i < GO.length; i++) {
                    String token2 = GO[i];
                    LineType k2;
                    //System.out.println(GO[i]);
                    if (nextTokenType(GO[0]) == TokenType.IF) k2 = LineType.CONDITION;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length > 2) k2 = LineType.ASSIGNMENT;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length == 2) k2 = LineType.DECLARATION;
                    else if (nextTokenType(GO[0]) == TokenType.PRINT) k2 = LineType.PRINT;
                    else k2 = LineType.INVALID;

                    if (k2 == LineType.ASSIGNMENT) {
                        if(nextTokenType(GO[0])==TokenType.INTEGER&&token2==GO[1]&&isValididentifier(token2))
                        {result.add(new Token(token2, nextTokenType(token2), linenum));}
                        else if(nextTokenType(GO[0])==TokenType.CHAR&&token2==(GO[1])&&isValididentifier(token2))
                        {result.add(new Token(token2, nextTokenType(token2), linenum));}
                        else if(nextTokenType(GO[0])==TokenType.STRING&&token2==GO[1]&&isValididentifier(token2))
                        {result.add(new Token(token2, nextTokenType(token2), linenum));}
                    } 
                    // else if (k2 == LineType.DECLARATION) {
                    //     if (isValididentifier(token2)) {
                    //         result.add(new Token(token2, nextTokenType(token2), linenum));
                    //     }
                    // } else if (k2 == LineType.CONDITION) {
                    //     if (isValididentifier(token2) || isNumeric(token2)) {
                    //         result.add(new Token(token2, nextTokenType(token2), linenum));
                    //     }
                    // } else if (k2 == LineType.PRINT) {
                    //     if (GO.length > 1) {
                    //         if (i != 0) {
                    //             result.add(new Token(token2, TokenType.PRINTED, linenum));
                    //         } else {
                    //             result.add(new Token(token2, nextTokenType(token2), linenum));
                    //         }
                    //     }
                    // }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return result;
    }
}
