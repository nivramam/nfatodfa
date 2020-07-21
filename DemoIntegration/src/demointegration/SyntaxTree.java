/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demointegration;

import java.util.List;
import java.util.Set;
import java.util.Stack;
/**
 *
 * @author lenovoN
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
        if(node instanceof LeafNode)
        {
            node.setNullable(false);
            numOfLeaves+=1;
        }
        //calculate nullable if not leaf 
        if(!(node instanceof LeafNode))
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
        if(node instanceof LeafNode)
        {
            LeafNode lnode = (LeafNode) node;
            node.addToFirstPos(lnode.getNum());
            node.addToLastPos(lnode.getNum());
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
        System.err.println(node);
    }
    public void generateFollowPos(SingleNTNode node)
    {
        /*note: followpos only for concat and * symbols. not anything else.*/
        if(node==null)
        {
            return;
        }
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
        return "Hello" + followPos; //To change body of generated methods, choose Tools | Templates.
    }
    
}