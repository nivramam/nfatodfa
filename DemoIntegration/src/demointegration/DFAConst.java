package demointegration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class DFAConst {
    String reg;
    private static List<State> states;
    SyntaxTree syntree;
    private State current_state;
    public DFAConst(String reg) 
    {
        this.reg = reg;
        states = new ArrayList<>();
    }
    public String getRegex()
    {
        this.reg += '#';
        return this.reg;
    }
    public ArrayList<Character> getSymbols(String expr)
    {
        Character[] c = new Character[10];
        c[0] = '+';
        c[1] = '*';
        c[2] = '(';
        c[3] = ')';
        c[4] = '\\';
        c[5] = '.';        
        c[6] = '&';
        c[7] = '|';
        c[8] = '[';
        c[9] = ']';
        ArrayList<Character> symbols = new ArrayList<Character>();
        for (int i=0;i<expr.length();i++)
        {
            char atvalue = expr.charAt(i);
            for(int j=0;j<c.length;j++)
            {
                if(atvalue == c[j])
                {
                    symbols.add(atvalue);
                }
            }
        }
//        System.out.println("Following are the symbols that are used in the regular expression!");
//        for(int i=0;i<symbols.size();i++)
//        {
//            System.out.println(symbols.get(i));
//        }
        return symbols;
    }
    public void startProcess()
    {
        states = new ArrayList<>();
        this.reg = getRegex();
        
        for(int i=0;i<this.reg.length();i++)
        {
            String concat = "&";
            if(i%2==0 && i!=0)
            {
                this.reg+=concat;
            }
        }
        System.out.println("---___Forwarding to syntax tree builder___---");
        syntree = new SyntaxTree(reg);
        /* finding nullable & firstpos, lastposof the nodes */
        System.out.println("Debug statement");
        syntree.generateNullable(syntree.getRoot());
        syntree.generateFLpos(syntree.getRoot());
        
        System.out.println(syntree);
        System.out.println("Debug over");
        
        formDFAStates();
    }
    public void formDFAStates()
    {
        syntree.generateFollowPos(syntree.getRoot());
        SingleNTNode root = this.syntree.getRoot();
        State[] state = new State[syntree.getNumOfLeaves()+1];
        System.out.println("Forming DFA States");
        if(root!=null)
        {
            int rootID = root.number_of_node;
            if(state[0]==null)
                state[0] = new State(rootID);
            state[0].isStart=true;
            states.add(state[0]); 
//            for(int j=0;j<max;j++)
//                state[j].goNext(j+1, states);
        }
        for(int j=0;j<states.size();j++)
        {
            System.err.println("lo");
            System.out.println(states.get(j));
        }
    }
    
}
class State {
    int ID;
    String name;
    /*for opns that are different for final, start and normal nodes*/
    State nextState;
    public boolean isFinal,isStart;

    public State(int number) {
        ID = number;
        this.isFinal = false;
        this.isStart = false;
    }    
    public void goNext(int IDNext, List<State> states) {
      System.out.println("Player is in nextstate");
      this.nextState=states.get(IDNext);
    }
    public void printnextState()
    {
        System.out.println(this.nextState);
    }
    @Override
    public String toString() {
        System.out.println("----State-----Transition");
        return "source state: " + this.ID + "----next state!" + this.nextState;
    }
}

