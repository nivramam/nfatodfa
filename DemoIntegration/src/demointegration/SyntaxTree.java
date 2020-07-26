package demointegration;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 *
 * @author lenovo
 * this class generates nulable, first pos, last pos and follow pos for the node 
 */
public class SyntaxTree {
    private final String regex;
    private BTree btee;
    private SingleNode root; //the head of raw syntax tree
    private int numOfLeaves;
    private List<List<Integer>> followPos =  new ArrayList<>();
    
    public SyntaxTree(String regex)
    {
        this.regex = regex;
        this.btee = new BTree();
        /*build the empty BTree*/
        
        btee.generateTree(regex);
    }
    public boolean generateNullable(SingleNode node){
        this.root=node;
        if(node==null)
        {
           System.exit(0);
        }
        // nullable for leaf node is FALSE
        if(node instanceof SingleNode && node.leftchild==null && node.rightchild==null)
        {
            node.setNullable(false);
            numOfLeaves+=1;
            System.out.println(numOfLeaves);
        }
        //calculate nullable if not leaf 
        if(node instanceof SingleNode && (node.leftchild!=null || node.rightchild!=null))
        {
            SingleNode leftndoe = node.getLeftchild();
            SingleNode rightndoe = node.getRightchild();
            boolean leftN = generateNullable(leftndoe);
            boolean rightN = generateNullable(rightndoe);
            switch(node.getSymbol())
            {
                case "|":
                    node.setNullable(leftN | rightN);
                    break;
                case "&":
                    node.setNullable(leftN & rightN);
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
        return node.nullable;
    }
    public void generateFLpos(SingleNode node){
        this.root=node;
        if(node==null)
        {
            return;
        }
        if(node instanceof SingleNode && node.leftchild==null && node.rightchild==null)
        {
            /*if node is leaf, add it's position to both first pos and last pos*/
            node.addToFirstPos(node.getNumberofNode());
            node.addToLastPos(node.getNumberofNode());
        }
        else
        {
            SingleNode left = node.getLeftchild();
            SingleNode right = node.getRightchild();
            generateFLpos(left);
            generateFLpos(right);
            switch(node.getSymbol()){
                case "+":
                    //for or no conditions need to be checked 
                    //get the previous symbol and succeeding symbol
                    node.addToFirstPos((int)right.getNumberofNode());
                    node.addToFirstPos((int)left.getNumberofNode());
                    node.addToLastPos((int)left.getNumberofNode());
                    node.addToLastPos((int)right.getNumberofNode());
                    break;
                case "&":
                    //for &, isnullable(left) = true then both left and right
                    //if isnullable(left) = false then take left
                    if(left.isNullable()){
                        node.addToFirstPos((int)left.getNumberofNode());
                        node.addToFirstPos((int)right.getNumberofNode());
                    }
                    else{
                        node.addToFirstPos((int)left.getNumberofNode());
                    }
                    if(right.isNullable())
                    {
                        node.addToLastPos((int)left.getNumberofNode());
                        node.addToLastPos((int)right.getNumberofNode());
                    }
                    else{
                        node.addToLastPos((int)right.getNumberofNode());
                    }
                    break;
                case "*":
                    //add firstpos and lastpos of left node
                    node.addToFirstPos((int)left.getNumberofNode());
                    node.addToLastPos((int)left.getNumberofNode());
                    break;
            }
        }
    }
    public void generateFollowPos(SingleNode node) /*recursive function to find followpos till it's null*/
    {/*note: followpos based on for concat and * symbols*/
        this.root=node;
        if(node==null)
        {
            return;
        }
//        if last node, followpos =null
        if(node.getSymbol()=="#")
        {
            this.followPos=null;
            return;
        }
        switch(node.getSymbol())
        {
            case "&":
                SingleNode left = node.getLeftchild();
                SingleNode right = node.getRightchild();
                for(Integer i : left.lastpos){
                    List<Integer> follAnd =  right.firstpos;
                    followPos.add(i, follAnd); 
                }
                
                break;
            case "*":
                SingleNode n = node.getLeftchild();
                for(Integer i : n.lastpos){
                    List<Integer> follStar =  n.firstpos;
                    followPos.add(i, follStar); 
                }
                break;
        }
    }
    public SingleNode getRoot(){return this.root;}
    public List<List<Integer>> getFollowPos(){return this.followPos; }
    public int getNumOfLeaves(){return this.numOfLeaves;}

    @Override
    public String toString() {
        return "Syntax tree generated: no: of leaves = " + getNumOfLeaves(); //To change body of generated methods, choose Tools | Templates.
    }
}
