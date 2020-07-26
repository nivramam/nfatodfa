package demointegration;

import java.util.*;
/**
 *
 * @author lenovo
 * this class is to generate the tree as having operators and nodes - for ease of syntex tree operations
 */
public class BTree {
    /*using stack for node and operator - because the last added is needed for the operations*/
    Stack<Character> operats= new Stack<>();
    Stack<SingleNode> node = new Stack<>();
    private Operation opobj;
    private List<Character> OPList = new ArrayList<>();
    private boolean isInputNodeChar(char ch) {
        if(OPList.contains(ch)){return false;}
        return true;
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
            if(RE.charAt(i)=='/' || RE.charAt(i)=='&' || RE.charAt(i)=='*'){
                isSymbol = true;
            }
            if(isSymbol)
            {
                operats.add(RE.charAt(i));
            }
            if(isInputNodeChar(RE.charAt(i)))
            {
                char at = RE.charAt(i);
                node.add(new SingleNode(String.valueOf(at)));
            }
        }
        while(!operats.isEmpty()){
            doGivenOper();
        }
    }
    public void doGivenOper(){
        if(this.operats.size() > 0 )
        {
            opobj = new Operation();
            char charAt = operats.pop();
            switch (charAt) {
                case '+':
                    opobj.formUnionNode(node);
                    break;
                case '&':
                    opobj.formConcatenateNode(node);
                    break;
                case '*':
                    opobj.formStarNode(node);
                    break;
            }
        }
    }
}
class Operation {
    /*this is a class dedicated to add the nodes properly - from stack into tree*/
    void formUnionNode(Stack<SingleNode> node){
        SingleNode node2 = node.pop();
        SingleNode node1 = node.pop();
        
        SingleNode root = new SingleNode("|");
        root.setLeft(node1);
        root.setRight(node2);
        node1.setParent(root);
        node2.setParent(root);
        
        node.push(root);
    }
    void formConcatenateNode(Stack<SingleNode> node){
        SingleNode node2 = node.pop();
        SingleNode node1 = node.pop();
        
        SingleNode root = new SingleNode("&");
        root.setLeft(node1);
        root.setRight(node2);
        node1.setParent(root);
        node2.setParent(root);
        node.push(root);
    }
    void formStarNode(Stack<SingleNode> node){
        SingleNode node2 = node.pop();
        SingleNode node1 = node.pop();

        SingleNode root = new SingleNode("*");
        root.setLeft(node1);
        root.setRight(node2);
        node1.setParent(root);
        node2.setParent(root);
        node.push(root);
    }
}
