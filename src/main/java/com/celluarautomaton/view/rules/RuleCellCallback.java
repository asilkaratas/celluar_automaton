package com.celluarautomaton.view.rules;

import com.celluarautomaton.rulemodule.Rule;

public interface RuleCellCallback {
	void onEdit(Rule rule);
	void onRemove(Rule rule);
	void onMove(int from, int to);
}
