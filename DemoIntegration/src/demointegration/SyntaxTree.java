package demointegration;

import java.util.List;
import java.util.Stack;
/**
 *
 * @author lenovo
 */
public class SyntaxTree {
    private final String regex;
    private BTree btee;
    private SingleNTNode root; //the head of raw syntax tree
    private int numOfLeaves;
    private List<Integer> followPos[];
    private Operation op;
    
    public SyntaxTree(String regex)
    {
        this.regex = regex;
        this.btee = new BTree();
        /*build the empty BTree*/
        btee.generateTree(regex);
    }
    
    public void generateNullable(SingleNTNode node){
        if(node==null)
        {
            return;
        }
        // nullable for leaf node is FALSE
        if(node instanceof SingleNTNode && node.leftchild==null && node.rightchild==null)
        {
            node.setNullable(false);
            numOfLeaves+=1;
        }
        //calculate nullable if not leaf 
        if(node instanceof SingleNTNode && (node.leftchild!=null || node.rightchild==null))
        {
            SingleNTNode leftndoe = node.getLeftchild();
            SingleNTNode rightndoe = node.getRightchild();
            generateNullable(leftndoe);
            generateNullable(rightndoe);
            switch(node.getSymbol())
            {
                case "|":
                    node.setNullable(leftndoe.isNullable() | rightndoe.isNullable());
                    break;
                case "&":
                    node.setNullable(leftndoe.isNullable() & rightndoe.isNullable());
                    break;
                case "*":
                    node.setNullable(true);
                    break;
                case "E":
                    node.setNullable(true);
                default:
                    System.out.println("do nothing...");    
            }
        }
    }
    public void generateFLpos(SingleNTNode node){
        if(node==null)
        {
            return;
        }
        if(node instanceof SingleNTNode && node.leftchild==null && node.rightchild==null)
        {
            /*if node is leaf, add it's position to both first pos and last pos*/
            node.addToFirstPos(node.getNumberofNode());
            node.addToLastPos(node.getNumberofNode());
        }
        else
        {
            SingleNTNode left = node.getLeftchild();
            SingleNTNode right = node.getRightchild();
            generateFLpos(left);
            generateFLpos(right);
            switch(node.getSymbol()){
                case "|":
                    //for or no conditions need to be checked 
                    //get the previous symbol and succeeding symbol
                    node.addToFirstPos((int)right.getNumberofNode());
                    node.addToLastPos((int)left.getNumberofNode());
                    break;
                case "&":
                    //for &, isnullable(left) = true then both left and right
                    //if isnullable(left) = false then take left
                    if(left.isNullable()){
                        node.addToFirstPos((int)left.getNumberofNode());
                        node.addToFirstPos((int)right.getNumberofNode());
                        node.addToLastPos((int)left.getNumberofNode());
                        node.addToLastPos((int)right.getNumberofNode());
                    }
                    else{
                        node.addToFirstPos((int)left.getNumberofNode());
                        node.addToLastPos((int)left.getNumberofNode());
                    }
                    break;
                case "*":
                    //add firstpos and lastpos of left node
                    node.addToFirstPos((int)left.getNumberofNode());
                    node.addToLastPos((int)left.getNumberofNode());
                    break;
            }
        }
//        System.out.println(node);
    }
    public void generateFollowPos(SingleNTNode node) /*recursive function to find followpos till it's null*/
    {/*note: followpos based on for concat and * symbols*/
        if(node==null)
        {
            return;
        }
        op = new Operation();
        SingleNTNode left = node.getLeftchild();
        SingleNTNode right = node.getRightchild();
        Stack<SingleNTNode> newstack = new Stack<>();
        newstack.add(node);
        newstack.add(left);
        newstack.add(right);
        switch(node.getSymbol()){
            case "&":
                op.doConcatenate(newstack);
                break;
            case "*":
                op.doStar(newstack);
                break;
        }
        generateFollowPos(left);
        generateFollowPos(right);
    }
    public SingleNTNode getRoot(){return this.root;}
    public List<Integer>[] getFollowPos(){return this.followPos; }
    public int getNumOfLeaves(){return this.numOfLeaves;}

    @Override
    public String toString() {
        return "Follow pos:" + this.followPos + ""; //To change body of generated methods, choose Tools | Templates.
    }
    
}