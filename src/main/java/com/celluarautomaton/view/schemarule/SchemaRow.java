package com.celluarautomaton.view.schemarule;

import javafx.scene.layout.HBox;

import com.celluarautomaton.rulemodule.schema.Schema;

public abstract class SchemaRow extends HBox {
	public abstract Schema getSchema();
	public abstract void showDeleteButton(boolean show);
}
