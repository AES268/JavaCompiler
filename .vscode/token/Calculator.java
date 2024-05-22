public class Calculator {
    public double evaluate(Node node)
    {
    if(node==null)
    {
        return 0;
    }
    if(node.left==null&&node.right==null)
    {
        return Double.parseDouble(node.data);
    }
    double leftValue=evaluate(node.left);
    double rightValue=evaluate(node.right);
    switch(node.data)
    {
        case "+": return leftValue+rightValue;
        case "-": return leftValue-rightValue;
        case "*": return leftValue*rightValue;
        case "/":if(rightValue==0){throw new UnsupportedOperationException("not divide by zero");}
        return leftValue/rightValue;
        default:throw new IllegalArgumentException("unkown operator"+node.data);
    }
    }
    
}
