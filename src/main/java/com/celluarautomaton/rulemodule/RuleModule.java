package com.celluarautomaton.rulemodule;

import java.util.ArrayList;
import java.util.List;

import com.celluarautomaton.rulemodule.listener.RulesChangedListener;

public class RuleModule {
	
	private final List<RulesChangedListener> rulesChangesListeners;
	private final List<Rule> rules;
	
	public RuleModule() {
		rules = new ArrayList<Rule>();
		rulesChangesListeners = new ArrayList<RulesChangedListener>();
	}
	
	public void calculatePatternValues() {
		for(Rule rule : rules) {
			rule.calculatePatternValue();
		}
	}
	
	public void addRule(Rule rule) {
		rules.add(rule);
		rule.calculatePatternValue();
		notifyRulesChanged();
	}
	
	public void removeRule(Rule rule) {
		rules.remove(rule);
		notifyRulesChanged();
	}
	
	public void replaceRule(Rule original, Rule rule) {
		int index = rules.indexOf(original);
		rules.remove(index);
		rules.add(index, rule);
		notifyRulesChanged();
	}
	
	public void moveRule(int from, int to) {
		Rule rule = rules.remove(from);
		rules.add(to, rule);
		notifyRulesChanged();
	}
	
	public void setRules(List<Rule> newRules) {
		rules.clear();
		rules.addAll(newRules);
		calculatePatternValues();
		notifyRulesChanged();
	}
	
	public void removeAllRules() {
		rules.clear();
		notifyRulesChanged();
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	
	public void addRulesChangedListener(RulesChangedListener listener) {
		if(!rulesChangesListeners.contains(listener)) {
			rulesChangesListeners.add(listener);
		}
	}
	
	public void notifyRulesChanged() {
		for(RulesChangedListener listener : rulesChangesListeners) {
			listener.onRulesChanged();
		}
	}
}
