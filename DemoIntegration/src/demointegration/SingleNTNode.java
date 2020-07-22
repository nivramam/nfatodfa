package demointegration;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author lenovoN
 */
public class SingleNTNode {
    String symbol_ofnode;
    SingleNTNode ancestor, leftchild, rightchild;   
    List<Integer> firstpos, lastpos;
    Integer number_of_node; //number of the node - convert from the String - symbol of node. 
    boolean nullable = false;

    public SingleNTNode(String symbolofnode) {
        this.symbol_ofnode = symbolofnode;
        this.firstpos = new ArrayList<>();
        this.lastpos = new ArrayList<>();
        nullable = false;
    }    
    public void insertFirst(int number){this.firstpos.add(number);}
    public void insertLast(int number){this.lastpos.add(number);}  
    
    public void setLeft(SingleNTNode left){this.leftchild = left;}
    public void setRight(SingleNTNode right){this.rightchild = right;}
    public void setParent(SingleNTNode parent){this.ancestor = parent;}

    public SingleNTNode getLeftchild() {return leftchild;}
    public SingleNTNode getRightchild() {return rightchild;}
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
    
    @Override
    public String toString() {
        return "firstpos" + firstpos; //To change body of generated methods, choose Tools | Templates.
    }   
}