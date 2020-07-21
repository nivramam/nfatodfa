/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    /*3 essentials for the Direct Method!*/    
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
        this.number_of_node = Integer.valueOf(symbol_ofnode);
        return this.number_of_node;
    }

    @Override
    public String toString() {
        return "firstpos" + firstpos; //To change body of generated methods, choose Tools | Templates.
    }   
}
class LeafNode extends SingleNTNode{
    private int num;
    List<Integer> followpos;
    public LeafNode(String symbol, int num) {
        super(symbol);
        /*leaf node has 0 children*/
        this.num=num;
        this.leftchild=null;
        this.rightchild=null;
        followpos = new ArrayList<>();
    }
    public List<Integer> getFollowpos() {return followpos;}
    public void setFollowpos(List<Integer> followpos) {this.followpos = followpos;}
    public int getNum(){ return this.num;} 
    public void addToFollowPos(int number){ followpos.add((Integer)num); }    
}