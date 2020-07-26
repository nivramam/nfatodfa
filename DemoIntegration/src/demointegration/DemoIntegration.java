/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demointegration;

import java.util.Scanner;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author lenovo
 */
public class DemoIntegration {

    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        System.out.println("MENU");
        System.out.println("1. RE -> NFA");
        System.out.println("2. NFA -> DFA");
        System.out.println("3. RE -> DFA (using direct method)");
        Scanner inp = new Scanner(System.in);
        String ch = "y";
        while (ch == "y") {
            int option = inp.nextInt();
            switch (option) {
                case 1:
                    System.out.println("=========== RE to NFA ==========");
                    //Input format if union operator a+b, concatenation ab, closure a*
                    System.out.println("Enter the Regular Expression : ");
                    //scanning input
                    Scanner s = new Scanner(System.in);
                    String exp = s.nextLine();
                    Nfa n = new Nfa(exp);
                    //In output 'e' represents epsilon
                    n.displayNfa();
                    break;
                case 2:
                    File file = new File("C:\\Users\\lenovo\\Documents\\NetBeansProjects\\DemoIntegration\\src\\files\\nfa1.txt");
                    Scanner s1 = new Scanner(file);
                    FiniteAutomata nfa = new FiniteAutomata(s1);
                    //    System.out.println(nfa);
                    FiniteAutomata dfa = nfa.NFAtoDFA();
                    System.out.print("Input Symbols: ");
                    for (char c : nfa.inputs) {
                        if (c != ' ') {
                            System.out.print(c + " ");
                        }
                    }
                    System.out.println("\n------");
                    for (int i = 0; i < nfa.states; i++) {
                        System.out.print(i + ":   ");
                        for (int j = 0; j < nfa.inputs.size(); j++) {
                            System.out.print("(" + nfa.inputs.get(j) + "," + nfa.getTransitionState(nfa.inputs.get(j), i) + ") ");
                        }
                        System.out.println();
                    }
                    System.out.println("------");
                    System.out.println(nfa.initialState + ":   Initial State");
                    if (nfa.finalStates.size() > 1) {
                        for (int i = 0; i < nfa.finalStates.size() - 1; i++) {
                            System.out.print(nfa.finalStates.get(i) + ", ");
                        }
                    }
                    System.out.print(nfa.finalStates.get(0));
                    System.out.print(":   Accepting State(s)\n\n");

                    System.out.println("To DFA:");
                    System.out.print(" Symbol:");
                    for (Character c : dfa.inputs) {
                        System.out.print("   " + c);
                    }
                    System.out.println("\n --------------");
                    for (int i = 0; i < dfa.states; i++) {
                        System.out.print("     " + i + ":");
                        for (Character c : dfa.inputs) {
                            System.out.print("   " + dfa.getTransitionState(c, i).get(0));
                        }
                        System.out.println();
                    }
                    System.out.println(" --------------");
                    System.out.println(dfa.initialState + ":   Initial State");
                    if (dfa.finalStates.size() > 1) {
                        for (int i = 0; i < dfa.finalStates.size() - 1; i++) {
                            System.out.print(dfa.finalStates.get(i) + ", ");
                        }
                    }
                    System.out.print(dfa.finalStates.get(0));
                    System.out.print(":   Accepting State(s)\n");
                    //       System.out.println(dfa);
                    break;
                case 3:
                    System.out.println("=========== Direct Method ==========");
                    Scanner sc = new Scanner(System.in);
                    String regex = "";
                    regex = sc.next();
                    System.out.println(regex + ":input!\n");
                    System.out.println("VALIDATION OF REGEX");
                    /*perform validation*/
                    if (validateRegex(regex)) {
                        DFAConst dfaconstr = new DFAConst(regex);
                        System.out.println(dfaconstr.getRegex());
                        dfaconstr.startProcess();
                    }
                    break;
                default:
                    System.err.println("You're about to exit!");
                    java.lang.System.exit(0);
                    ch = inp.next();
            }
        }
    }
    public static boolean validateRegex(String expr) {
        if (expr.contains("..") || expr.contains("**") || expr.contains("\\") || expr.contains("&&") || expr.contains("~")) {
            System.err.println("This regular expression rejected!");
            return false;
        }
        System.out.println("This regular expression accepted!!");
        return true;
    }

}
