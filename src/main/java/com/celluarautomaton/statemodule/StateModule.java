package com.celluarautomaton.statemodule;

import java.util.ArrayList;
import java.util.List;

import com.celluarautomaton.statemodule.listener.StatesChangedListener;

public class StateModule {
	
	private List<State> states = null;
	private List<State> editorStates = null;
	private final List<StatesChangedListener> statesChangedListeners;
	
	public StateModule() {
		statesChangedListeners = new ArrayList<>();
		
		states = new ArrayList<State>();
		editorStates = new ArrayList<State>();
		
		List<State> stateList = new ArrayList<State>();
		stateList.add(State.IGNORED);
		stateList.add(State.EMPTY);
		stateList.add(State.ALIVE);
		stateList.add(State.DEAD);
		
		setStates(stateList);
	}
	
	public List<State> getStates() {
		return states;
	}
	
	public List<State> getEditorStates() {
		return editorStates;
	}
	
	public void setStates(List<State> states) {
		this.states.clear();
		this.states.addAll(states);
		
		editorStates.clear();
		editorStates.addAll(states);
		editorStates.remove(0);
		
		notiftStatesChangedListeners();
	}
	
	public Color[] getColors() {
		Color[] colors = new Color[states.size()];
		for(int i = 0; i < colors.length; i++) {
			colors[i] = states.get(i).getColor();
		}
		return colors;
	}
	
	public void addStatesChangedListener(StatesChangedListener listener) {
		statesChangedListeners.add(listener);
	}
	
	public void removeStatesChangedListener(StatesChangedListener listener) {
		statesChangedListeners.remove(listener);
	}
	
	public void notiftStatesChangedListeners() {
		for(StatesChangedListener listener : statesChangedListeners) {
			listener.onStatesChanged();
		}
	}
	
	
	
	
}
