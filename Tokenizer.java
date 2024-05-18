package token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Tokenizer {

    public static void main(String[] args) {
        String filePath = "Input.txt";
        String testt = "";
        try {
            testt = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> listoftoken,listofVariables;
        try {
            listoftoken = tokenizer.tokenize(testt);
            listofVariables = tokenizer.tokenize2(testt);
            for (Token token : listoftoken) {
                System.out.println(token.getValue() + ":" + token.getType() + ":" + token.line + "\n");
            }
            for (Token token : listofVariables) {
                System.out.println(token.getValue() + ":" + token.getType() + ":" + token.line + "\n");
            }
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        //new Checker(listoftoken,listofVariables);
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    boolean mathematicalexpression(String[] exp) {
        for (int i = 3; i < exp.length; i++) {
            if ((i % 2 == 0 && (isNumeric(exp[i]) || isValididentifier(exp[i]) || (i % 2 != 0 && isOperator(exp[i]))))) {
                return false;
            }
        }
        return true;
    }

    boolean isvalidchar(String[] exp) {
        if (exp.length == 2 && nextTokenType(exp[0]) == TokenType.CHAR && isValididentifier(exp[1])) return true;
        else if (exp.length == 4 && nextTokenType(exp[0]) == TokenType.CHAR && isValididentifier(exp[1]) && isequal(exp[2]) && exp[3].length() == 1) return true;
        return false;
    }

    boolean isvalidcond(String[] GO) {
        if (GO.length == 2) {
            if (isValididentifier(GO[1]) || isNumeric(GO[1])) return true;
            return false;
        } else if (GO.length > 2 && GO.length % 2 == 0) {
            for (int i = 1; i < GO.length; i++) {
                if ((i % 2 != 0 && (!isValididentifier(GO[i]) && !isNumeric(GO[i]))) || (i % 2 == 0 && (!isCondition(GO[i])))) return false;
            }
            return true;
        } else return false;
    }

    boolean isvalidInt(String[] str) {
        if (isValididentifier(str[1]) && isequal(str[2]) && mathematicalexpression(str))
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
        if (input.equals("Or") || input.equals("And") || input.equals("and") || input.equals("or") || input.equals("Equals") || input.equals("equals") || input.equals("EQLess") || input.equals("EQMore") || input.equals("MoreThan") || input.equals("LessThan"))
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
        else if (input.equals("If")) return TokenType.IF;
        else if (input.equals("Print") || input.equals("Print Line")) return TokenType.PRINT;
        else if (isOperator(input)) return TokenType.OPERATOR;
        else if (input.equals("Or") || input.equals("or")) return TokenType.OR;
        else if (input.equals("And") || input.equals("and")) return TokenType.AND;
        else if (input.equals("Equals") || input.equals("equals")) return TokenType.EQUALS;
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
            System.out.println("\n" + token + "\n");
            String[] GO = token.trim().split(" ");
            try {
                for (int i = 0; i < GO.length; i++) {
                    String token2 = GO[i];
                    LineType k2;
                    System.out.println(GO[i]);
                    if (nextTokenType(GO[0]) == TokenType.IF) k2 = LineType.CONDITION;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length > 2) k2 = LineType.ASSIGNMENT;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length == 2) k2 = LineType.DECLARATION;
                    else if (nextTokenType(GO[0]) == TokenType.PRINT) k2 = LineType.PRINT;
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
                        } else {
                            if (i == GO.length - 1) {
                                result.add(new Token(token2, nextTokenType(token2), linenum));
                                result.add(new Token("GO", TokenType.GO, linenum));
                            } else {
                                result.add(new Token(token2, nextTokenType(token2), linenum));
                            }
                        }
                    } else if (k2 == LineType.DECLARATION) {
                        if (isDataType(nextTokenType(GO[0])) && isValididentifier(GO[1])) {
                            if (i == GO.length - 1) {
                                result.add(new Token(token2, nextTokenType(token2), linenum));
                                result.add(new Token("GO", TokenType.GO, linenum));
                            } else {
                                result.add(new Token(token2, nextTokenType(token2), linenum));
                            }
                        }
                    } else if (k2 == LineType.CONDITION) {
                        if (!isvalidcond(GO)) {
                            throw new Exception("ERROR! INVALID CONDITION STATEMENT");
                        } else {
                            result.add(new Token(token2, nextTokenType(token2), linenum));
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

    public ArrayList<Token> tokenize2(String input) throws IOException {
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
            System.out.println("\n" + token + "\n");
            String[] GO = token.trim().split(" ");
            try {
                for (int i = 0; i < GO.length; i++) {
                    String token2 = GO[i];
                    LineType k2;
                    System.out.println(GO[i]);
                    if (nextTokenType(GO[0]) == TokenType.IF) k2 = LineType.CONDITION;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length > 2) k2 = LineType.ASSIGNMENT;
                    else if (isDataType(nextTokenType(GO[0])) && GO.length == 2) k2 = LineType.DECLARATION;
                    else if (nextTokenType(GO[0]) == TokenType.PRINT) k2 = LineType.PRINT;
                    else k2 = LineType.INVALID;

                    if (k2 == LineType.ASSIGNMENT) {
                        if (isValididentifier(token2)) {
                            result.add(new Token(token2, nextTokenType(token2), linenum));
                        }
                    } else if (k2 == LineType.DECLARATION) {
                        if (isValididentifier(token2)) {
                            result.add(new Token(token2, nextTokenType(token2), linenum));
                        }
                    } else if (k2 == LineType.CONDITION) {
                        if (isValididentifier(token2) || isNumeric(token2)) {
                            result.add(new Token(token2, nextTokenType(token2), linenum));
                        }
                    } else if (k2 == LineType.PRINT) {
                        if (GO.length > 1) {
                            if (i != 0) {
                                result.add(new Token(token2, TokenType.PRINTED, linenum));
                            } else {
                                result.add(new Token(token2, nextTokenType(token2), linenum));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return result;
    }
}
