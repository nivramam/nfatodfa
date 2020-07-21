/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demointegration;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author User
 */
public class Nfa {
    
    ArrayList<Moves> mov;
    int scnt;

    public Nfa() {
        scnt=1;
        mov=new ArrayList();
        Moves m=new Moves(0,1);
        mov.add(m);
    }

    public Nfa(char ch) {
        scnt=1;
        mov=new ArrayList();
        Moves m=new Moves(0,ch, 1);
        mov.add(m);
    }

    public Nfa(ArrayList<Moves> mv, int c) {
        mov = mv;
        scnt = c;
    }

    public ArrayList<Moves> getMoves() {
        return mov;
    }

    public int Statecnt() {
        return scnt;
    }

    public static Nfa UnionOfRE(Nfa a, Nfa b) {
        ArrayList<Moves> lista = new ArrayList<>(a.getMoves());
        ArrayList<Moves> listb = new ArrayList<>(b.getMoves());
        ArrayList<Moves> listc = new ArrayList<>();
        int acnt = a.Statecnt();
        int bcnt = b.Statecnt();
        
        //Initial state to a
        for(Moves m : lista) {   //1 a 2
            Moves p = new Moves(m.Startstate()+1,m.Value(),m.Endstate()+1);
            listc.add(p);
        }
        
        //0 e 1
        Moves initial_a = new Moves(0,1);
        listc.add(initial_a);
        
        // initial state to b
        for(Moves m : listb) {    //3 b 4
            Moves m1= new Moves(m.Startstate()+acnt+2, m.Value(), m.Endstate()+acnt+2);
            listc.add(m1);
        }
        
        //0 e 3
        Moves initial_b = new Moves(0,acnt+2);
        listc.add(initial_b);
        
        //a to final state
        Moves final_a = new Moves(acnt+1,acnt+bcnt+3);
        listc.add(final_a);//2 e 5
        
        //b to final state
        Moves final_b = new Moves(acnt+bcnt+2,acnt+bcnt+3);
        listc.add(final_b);//4 e 5

        Nfa n= new Nfa(listc,acnt+bcnt+3);
        return n;
    }

    public static Nfa ConcatenationOfRE(Nfa a, Nfa b) {
        ArrayList<Moves> lista = new ArrayList<>(a.getMoves());
        ArrayList<Moves> listb = new ArrayList<>(b.getMoves());
        ArrayList<Moves> listc = new ArrayList<>();
        int acnt = a.Statecnt();
        int bcnt = b.Statecnt();
        //0 a 1
        for(Moves m : lista) {
            Moves m1 = new Moves(m.Startstate(), m.Value(), m.Endstate());
            listc.add(m1);
        }
        //1 b 2
        for(Moves m : listb) {
            Moves m1 = new Moves(m.Startstate()+ acnt, m.Value(), m.Endstate()+ acnt);
            listc.add(m1);
        }

        Nfa n= new Nfa(listc, acnt + bcnt);
        return n;
    }

    public static Nfa Kleeneclosure(Nfa a) {
        ArrayList<Moves> lista = new ArrayList<>(a.getMoves());
        ArrayList<Moves> listc = new ArrayList<>();
        int acnt = a.Statecnt();
        //1 a 2
        for(Moves m : lista) {
            Moves m1 = new Moves(m.Startstate()+ 1, m.Value(), m.Endstate()+ 1);
            listc.add(m1);
        }
        //2 e 1
        Moves fin_in = new Moves(acnt+1,1);
        listc.add(fin_in);
        //0 e 1
        Moves in_in = new Moves(0, 1);
        listc.add(in_in);
        //2 e 3
        Moves fin_fin = new Moves(acnt+1,acnt+2);
        listc.add(fin_fin);
        //0 e 3
        Moves in_fin = new Moves(0,acnt+2);
        listc.add(in_fin);

        Nfa n= new Nfa(listc, acnt+2);
        return n;
    }

    public void displayNfa() {
        System.out.println("\n===Non-Deterministic Finite Automata of given Regular Expression===");
        System.out.println("Initial state\t:0");
        System.out.println("Final State\t:" + scnt+"\n");
        mov.forEach((edge) -> edge.Display());
        System.out.println("---------------------");
    }

    public Nfa(String str) {
        Nfa c = Nfa.ThompsonMethod(str);
        mov = c.getMoves();//initial state id (0,1)
        scnt = c.Statecnt();//scnt=1
    }

    public static Nfa ThompsonMethod(String reg) {
        //adding paranthesis to string
        reg = "(" + reg + ")";
        Stack<Object> stk1 = new Stack<>();
        //Iterate until length of string
        for(int i = 0; i < reg.length(); i++) {
            switch(reg.charAt(i)) {
                //push the characters of string into stack until it reaches ')' or '*'
                case '(':
                    stk1.push('(');
                    break;
                case '+':
                    stk1.push('+');
                    break;
                case '*':
                    Nfa x = Nfa.Kleeneclosure((Nfa) stk1.pop());
                    stk1.push(x);
                    break;
                case ')':
                    Stack<Object> Stack2= new Stack<>();
                    //if ')' found then pop a character out of string 
                    Object ch = stk1.pop();
                    //System.out.println(ch);
                    //pops out until it reaches '('
                    while(ch instanceof Nfa || (char) ch != '(') {
                        //store it in another stack
                        Stack2.push(ch);
                        ch = stk1.pop();
                    }
                    Object s1 = Stack2.pop();// b pops out
                    while(!Stack2.empty()) {
                        Object s2 = Stack2.pop();
                        //System.out.println(s2);
                        //if second character is '+' then a union b
                        if (!( s2 instanceof Nfa)) {
                            s1 = Nfa.UnionOfRE((Nfa) s1, (Nfa) Stack2.pop());
                        } else {
                            //else concatenation
                            s1 = Nfa.ConcatenationOfRE((Nfa) s1, (Nfa) s2);
                        }
                    }
                    stk1.push((Nfa) s1);
                    break;
                default:
                    Nfa one = new Nfa(reg.charAt(i));
                    stk1.push(one);
                    break;
            }
        }
        return (Nfa) stk1.pop();
    }
}