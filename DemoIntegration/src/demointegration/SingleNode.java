package demointegration;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author lenovoN
 * this class is for the characteristics of a single node in the tree 
 * it's useful - since these parameters are for all nodes
 */
public class SingleNode {
    String symbol_ofnode;
    SingleNode ancestor, leftchild, rightchild;   
    List<Integer> firstpos, lastpos;
    Integer number_of_node; //number of the node - convert from the String - symbol of node. 
    public boolean nullable = false;

    public SingleNode(String symbolofnode) {
        this.symbol_ofnode = symbolofnode;
        this.firstpos = new ArrayList<>();
        this.lastpos = new ArrayList<>();
        this.leftchild=null;
        this.rightchild=null;
        nullable = false;
    }    
    /*getters and setters for the characteristics*/
    public void setLeft(SingleNode left){this.leftchild = left;}
    public void setRight(SingleNode right){this.rightchild = right;}
    public void setParent(SingleNode parent){this.ancestor = parent;}
    public SingleNode getLeftchild() {return leftchild;}
    public SingleNode getRightchild() {return rightchild;}
    public String getSymbol() {return symbol_ofnode;}
    public boolean isNullable(){return nullable;}
    public void setNullable(boolean nullable){this.nullable = nullable;}
    public void addToFirstPos(int number){firstpos.add((Integer)number);}
    public void addToLastPos(int number){lastpos.add((Integer)number);}
    public Integer getNumberofNode(){
        if(symbol_ofnode!="/" || symbol_ofnode!="&" || symbol_ofnode!="*"){
            this.number_of_node = Integer.valueOf(symbol_ofnode);
        }
        return this.number_of_node;
    }
}
