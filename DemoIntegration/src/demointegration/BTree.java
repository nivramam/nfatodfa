package demointegration;

import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author lenovoN
 */
public class BTree {
    /*using stack for node and operator - because the las added is needed for the operations*/
    Stack<Character> operats= new Stack<>();
    Stack<SingleNTNode> node = new Stack<>();
    private Operation opobj;
    private List<Character> input = new ArrayList<>();
    private List<Character> OPList = new ArrayList<>();
    
    private boolean isInputChar(char ch)
    {
        if(OPList.contains(ch)){
            return false;
        }
        return true;
    }
    private String addConcatandAugment(String RE)
    {
        ArrayList<Character> strTOlist = new ArrayList<>(RE.chars().mapToObj(i->Character.valueOf((char)i)).collect(Collectors.toList()));
//        for(int i=1;i<RE.length()-1;i++)
//        {
//            if(i%2==0)
//            {
//                strTOlist.add(i+1,'&');
//            }
//        }
        strTOlist.add(RE.length(),'&');
        StringBuilder sb = new StringBuilder();
        for(Character ch : strTOlist)
        {
            sb.append(ch); 
        }
        String newRE = sb.toString();
        return newRE;
    }
    public void doGivenOper(){
        if(this.operats.size() > 0 )
        {
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
        
        Character ch[] = new Character[52];
        for(int i = 65;i<=90;i++)
        {
            ch[i - 65] = (char)i;
            ch[i - 65 + 26] = (char)(i + 32);
            
        }
        //concatenating all the symbols
//        RE = addConcatandAugment(RE);
//        System.out.println(RE + "is the augmented regular expression");
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