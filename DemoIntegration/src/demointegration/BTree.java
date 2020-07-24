package demointegration;

import java.util.*;
/**
 *
 * @author lenovo
 */
public class BTree {
    /*using stack for node and operator - because the las added is needed for the operations*/
    Stack<Character> operats= new Stack<>();
    Stack<SingleNTNode> node = new Stack<>();
    private Operation opobj;
    private List<Character> OPList = new ArrayList<>();
    private boolean isInputChar(char ch) {
        if(OPList.contains(ch)){return false;}
        return true;
    }
    public void doGivenOper(){
        if(this.operats.size() > 0 )
        {
            opobj = new Operation();
            char charAt = operats.pop();
            switch (charAt) {
                case '|':
                    opobj.doUnion(node);
                    break;
                case '&':
                    opobj.doConcatenate(node);
                    break;
                case '*':
                    opobj.doStar(node);
                    break;
                default:
                    System.out.println("~" + charAt);
                    System.out.println("is an unknown symbol to the system!");
                    break;
            }
        }
    }
    public void generateTree(String RE){
        System.out.println();
        Character[] opsarray = {'*','|','&'};
        OPList.addAll(Arrays.asList(opsarray));
        node.clear();
        operats.clear();
        boolean isSymbol = false;
        for(int i=0; i<RE.length();i++)
        {
            if(RE.charAt(i)=='\\'){
                isSymbol = true;
                continue;
            }
            if(isSymbol)
            {
                operats.add(RE.charAt(i));
            }
            if(isInputChar(RE.charAt(i)))
            {
                char at = RE.charAt(i);
                String atString="";
                atString+=at;
                node.add(new SingleNTNode(atString));
            }
        }
        while(!operats.isEmpty()){
            doGivenOper();
        }
    }
}
class Operation {
    void doUnion(Stack<SingleNTNode> node){
        SingleNTNode node2 = node.pop();
        SingleNTNode node1 = node.pop();
        
        SingleNTNode root = new SingleNTNode("|");
        root.setLeft(node1);
        root.setRight(node2);
        node1.setParent(root);
        node2.setParent(root);
        
        node.push(root);
    }
    void doConcatenate(Stack<SingleNTNode> node){
        SingleNTNode node2 = node.pop();
        SingleNTNode node1 = node.pop();
        
        SingleNTNode root = new SingleNTNode("&");
        root.setLeft(node1);
        root.setRight(node2);
        node1.setParent(root);
        node2.setParent(root);
        node.push(root);
    }
    void doStar(Stack<SingleNTNode> node){
        SingleNTNode node2 = node.pop();
        SingleNTNode node1 = node.pop();
        
        SingleNTNode root = new SingleNTNode("*");
        root.setLeft(node1);
        root.setRight(node2);
        node1.setParent(root);
        node2.setParent(root);
        node.push(root);
    }
}