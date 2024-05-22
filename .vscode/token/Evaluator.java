import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {
    
    // Method to evaluate an infix expression
    public int evaluate(String expression) {
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }
    
    // Method to convert an infix expression to a postfix expression
    private String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<String> stack = new Stack<>();
        
        StringTokenizer tokenizer = new StringTokenizer(infix, "+-*/() ", true);
        
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) {
                continue;
            }
            
            if (isNumber(token)) {
                postfix.append(token).append(' ');
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.append(stack.pop()).append(' ');
                }
                stack.pop(); // Pop '(' from the stack
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && precedence(token) <= precedence(stack.peek())) {
                    postfix.append(stack.pop()).append(' ');
                }
                stack.push(token);
            }
        }
        
        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(' ');
        }
        
        return postfix.toString().trim();
    }
    
    // Method to evaluate a postfix expression
    private int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        
        StringTokenizer tokenizer = new StringTokenizer(postfix);
        
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            
            if (isOperator(token)) {
                int b = stack.pop();
                int a = stack.pop();
                switch (token) {
                    case "+":
                    case "Plus":
                    case "plus":
                        stack.push(a + b);
                        break;
                    case "-":case "Minus":case "minus":
                        stack.push(a - b);
                        break;
                    case "*":case "Times":case "times":
                        stack.push(a * b);
                        break;
                    case "/":case "By":case "by":
                        stack.push(a / b);
                        break;
                }
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        
        return stack.pop();
    }
    
    // Method to check if a string is a number
    private boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean isOperator(String input) {
        return (input.equals("Equals") || input.equals("-") || input.equals("+") || input.equals("*") || input.equals("/")
                || input.equals("Plus") || input.equals("Minus") || input.equals("Times") || input.equals("=") || input.equals("By") || input.equals("Is") || input.equals("is"));
    }
    
    // Method to get precedence of operators
    private int precedence(String token) {
        switch (token) {
            case "+":
            case "-":
            case "Plus":
            case "plus":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return -1;
        }
    }
    
    // Main method to test the Evaluator
    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        
        String expression1 = "18 + 4 * 5";
        String expression2 = "18 Plus 4 * 5";
        String expression3 = "18 plus 4 * 5";
        
        System.out.println("Expression: " + expression1 + " = " + evaluator.evaluate(expression1));
        System.out.println("Expression: " + expression2 + " = " + evaluator.evaluate(expression2));
        System.out.println("Expression: " + expression3 + " = " + evaluator.evaluate(expression3));
    }
}
