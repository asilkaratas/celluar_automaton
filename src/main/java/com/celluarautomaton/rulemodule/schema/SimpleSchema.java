package com.celluarautomaton.rulemodule.schema;

public class SimpleSchema extends Schema {
	
	private int row;
	private int column;
	
	public SimpleSchema() {
		row = 1;
		column = 1;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getIndex() {
		return (row - 1) * 5 + (column - 1);
	}

	public SimpleSchema clone() {
		SimpleSchema simpleSchema = new SimpleSchema();
		simpleSchema.setRow(row);
		simpleSchema.setColumn(column);
		simpleSchema.setState(getState());
		return simpleSchema;
	}

	
}
