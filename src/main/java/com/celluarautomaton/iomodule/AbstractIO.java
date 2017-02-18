package com.celluarautomaton.iomodule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

import com.celluarautomaton.common.CommonException;

public abstract class AbstractIO <T> {
	//private static final Logger logger = Logger.getLogger(AbstractIO.class);
	
	protected BufferedReader getReader(String filename) throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
		return reader;
	}
	
	protected PrintWriter getWriter(String filename) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File(filename));;
		return writer;
	}
	
	
	public abstract List<T> loadFromFile(String filename) throws CommonException;
	public abstract void saveToFile(String filename, List<T> items) throws CommonException;
}
