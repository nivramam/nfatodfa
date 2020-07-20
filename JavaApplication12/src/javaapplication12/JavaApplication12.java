/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Sreharine Dhileeban
 */
public class JavaApplication12 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException{
        // TODO code application logic here
        File file=new File("D:\\sws\\sem6\\PCD\\package\\JavaApplication12\\nfa1.txt");
        Scanner s1=new Scanner(file);
        FiniteAutomata nfa=new FiniteAutomata(s1);
    //    System.out.println(nfa);
        FiniteAutomata dfa = nfa.NFAtoDFA();
        System.out.print("Input Symbols: ");
	for (char c : nfa.inputs) {
	if (c != ' ')
            System.out.print(c + " ");
	}
	System.out.println("\n------");
	for (int i = 0; i < nfa.states; i++) {
            System.out.print(i + ":   ");
			//System.out.print(nfa.transitions.get(i));
            for (int j = 0; j < nfa.inputs.size(); j++) {
		System.out.print("(" + nfa.inputs.get(j) + "," + nfa.getTransitionState(nfa.inputs.get(j), i) + ") ");
            }
            System.out.println();
	}
        System.out.println("------");
        System.out.println(nfa.initialState + ":   Initial State");
	if (nfa.finalStates.size() > 1) {
            for (int i = 0; i < nfa.finalStates.size()-1; i++) {
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
		for (int i = 0; i < dfa.finalStates.size()-1; i++) {
			System.out.print(dfa.finalStates.get(i) + ", ");
		}
	}
	System.out.print(dfa.finalStates.get(0));
        System.out.print(":   Accepting State(s)\n");
         //       System.out.println(dfa);
    }
    
}
