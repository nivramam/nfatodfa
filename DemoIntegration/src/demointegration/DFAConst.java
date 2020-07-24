package demointegration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 * this class is driver for direct method - DFA from the regex 
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
    public void startProcess()
    {
        this.reg = getRegex();
        
        System.out.println("---___Forwarding to syntax tree builder___---");
        syntree = new SyntaxTree(reg);
        /* finding nullable & firstpos, lastposof the nodes -from the syntac tree built above  */
        syntree.generateNullable(syntree.getRoot());
        syntree.generateFLpos(syntree.getRoot());
        System.out.println(syntree);
               
        formDFAStates();
    }
    public void formDFAStates()
    {
        syntree.generateFollowPos(syntree.getRoot());
        SingleNode root = this.syntree.getRoot();
        System.out.println("Forming DFA States");
        if(root!=null)
        {
 
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
    public void setnextState(State setnext)
    {
        this.nextState=setnext;
    }
    @Override
    public String toString() {
        System.out.println("----State-----Transition");
        return "source state: " + this.ID + "----next state!" + this.nextState;
    }
}