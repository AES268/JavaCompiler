
package token;

import java.util.ArrayList;
import java.util.Stack;


public class ExpressionTree {
    Node root;
    
    public ExpressionTree()
    {
        root=null;
    }
    public void addNode(String data)
    {
        root=addNodeRec(root,data);
    }
    private Node addNodeRec(Node current,String data)
    {
        if(current==null)
        {
            return new Node(data);
        }
        return current;
    }
    public void traverseInOrder(Node node)
    {
        if(node !=null)
        {
            traverseInOrder(node.left);
            System.out.print(node.data+" ");
            traverseInOrder(node.right);
        }
    }
    private int getPriority(String operator)
    {
        switch(operator)
        {
            case "+":
            case "-":
                return 1;
            case "*":
            case"/":
                return 2;
            default:
                return -1;
        }
    }
    public void buildTree(ArrayList<Token> tokens) {
    Stack<Node> nodes = new Stack<>();
    Stack<String> operators = new Stack<>();

    for (Token token : tokens) {
        if (token.getType() == TokenType.NUMBER) {
            nodes.push(new Node(token.getValue()));
        } else if (token.getType() == TokenType.OPERATOR) {
            while (!operators.isEmpty() && getPriority(operators.peek()) >= getPriority(token.getValue())) {
                if (nodes.size() < 2) {
                    throw new IllegalArgumentException("number not correct");
                }
                Node right = nodes.pop();
                Node left = nodes.pop();
                Node opNode = new Node(operators.pop());
                opNode.left = left;
                opNode.right = right;
                nodes.push(opNode);
            }
            operators.push(token.getValue());
        }
    }

    while (!operators.isEmpty()) {
        if (nodes.size() < 2) {
            throw new IllegalArgumentException("number not correct ");
        }
        Node right = nodes.pop();
        Node left = nodes.pop();
        Node opNode = new Node(operators.pop());
        opNode.left = left;
        opNode.right = right;
        nodes.push(opNode);
    }

    if (!nodes.isEmpty()) {
        root = nodes.pop();
    } else {
        throw new IllegalArgumentException("tree nor correct");
    }
}
    }