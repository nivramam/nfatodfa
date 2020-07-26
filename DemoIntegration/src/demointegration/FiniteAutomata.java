/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demointegration;

import java.util.*;

/**
 *
 * @author Sreharine Dhileeban
 */
public class FiniteAutomata {
    int states;
    List<Character> inputs;
    List<Map<Character, List<Integer>>> transitions;
    int initialState;
    List<Integer> finalStates;
    
    public FiniteAutomata()
    {
        this(0, new ArrayList<>(), new ArrayList<>(), -1, new ArrayList<>());
    }
    
    public FiniteAutomata(int st, List<Character> in, List<Map<Character, List<Integer>>> trans, int init, List<Integer> fin) {
		states = st;
		inputs = in;
		transitions = trans;
		initialState = init;
		finalStates = fin;
    }
    
    public FiniteAutomata(Scanner input) {
        String line = input.nextLine();
	states = Integer.parseInt(line);	
	inputs = new ArrayList<>();
	for (String s : input.nextLine().trim().split("\\s+")) {
		inputs.add(s.charAt(0));
	}
	inputs.add(' ');
        transitions = new ArrayList<>();
	for (int i = 0; i < states; i++) {
            line = input.nextLine();
            Map<Character, List<Integer>> transitionState = new HashMap<>();
            for (Character c : inputs) {
		List<Integer> nextState = new ArrayList<>();
		line = parseTransition(line, nextState);
		transitionState.put(c, nextState);
            }
            transitions.add(transitionState);
	}
	initialState = Integer.parseInt(input.nextLine());
	finalStates = new ArrayList<>();
		parseTransition(input.nextLine(), finalStates);
    }
    
    public String parseTransition(String line, List<Integer> state) {
	line = line.substring(line.indexOf("{") + 1, line.length());
        if (!(line.charAt(0) == '}')) {
            while (line.indexOf(",") < line.indexOf("}") && line.indexOf(",") > 0) {
		state.add(Integer.parseInt(line.substring(0, line.indexOf(","))));
		line = line.substring(line.indexOf(",") + 1, line.length());
            }
		state.add(Integer.parseInt(line.substring(0, line.indexOf("}"))));
	}
	return line.substring(line.indexOf("}") + 1, line.length());
    }
    
    public FiniteAutomata NFAtoDFA()
    {
        FiniteAutomata dfa = new FiniteAutomata();
	dfa.initialState = this.initialState;
	List<List<Integer>> newStates = new ArrayList<>();
	List<Integer> lambdaClosure = new ArrayList<>();
	calculateEpsilonClosure(0, lambdaClosure);
	newStates.add(lambdaClosure);	
	List<Character> dfaInputs = new ArrayList<>(this.inputs);
	// removing epsilon for DFA
	dfaInputs.remove(Character.valueOf(' '));	
	List<Integer> current = new ArrayList<>(lambdaClosure);
	int totalState = 0, finalState = 0;
	
	while (current != null) {
            Map<Character, List<Integer>> dfaTransition = new HashMap<>();
            for (Character i : dfaInputs) {
		List<Integer> tempStates = new ArrayList<>();
		for (Integer currentState : current) {
                    for (Integer transitionState : this.transitions.get(currentState).get(i)) {
			calculateEpsilonClosure(transitionState, tempStates);
                    }
		}
		List<Integer> dfaState = new ArrayList<>();
		Collections.sort(tempStates);
		if (!newStates.contains(tempStates)) {
                    newStates.add(tempStates);
                    dfaState.add(++totalState);
		}
		else {
                    dfaState.add(newStates.indexOf(tempStates));
		}
		dfaTransition.put(i, dfaState);
		}
            
			dfa.states++;
			dfa.transitions.add(dfaTransition);
			
			for (int s : finalStates) {
                            if (current.contains(s)) {
				if (!(dfa.finalStates.contains(s))) {
                                    dfa.finalStates.add(finalState);
					break;
				}
                            }
			}
			if (++finalState >= newStates.size()) {
				current = null;
			}
			else {
				current = newStates.get(finalState);
			}
		}
		dfa.inputs = dfaInputs;
		return dfa;
    }
    
    public void calculateEpsilonClosure(int state, List<Integer> lambdaClosure) {
		Map<Character, List<Integer>> stateTransitions = transitions.get(state);
		if (lambdaClosure.contains(state)) {}
		else if (stateTransitions.containsKey(' ')) {
			lambdaClosure.add(state);
			for (Integer i : stateTransitions.get(' ')) {
				calculateEpsilonClosure(i, lambdaClosure);
			}
		}
    }
    
    public List<Integer> getTransitionState(char input, int state)
    {
		return this.transitions.get(state).get(input);
    }
}
