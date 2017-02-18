package com.celluarautomaton.iomodule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.celluarautomaton.common.CommonException;
import com.celluarautomaton.common.Message;
import com.celluarautomaton.statemodule.Color;
import com.celluarautomaton.statemodule.State;

public class StateIO extends AbstractIO <State> {
	private static final Logger logger = Logger.getLogger(StateIO.class);

	public List<State> read(BufferedReader reader)  throws CommonException, IOException {
		final List<State> states = new ArrayList<State>();
		String line = null;
		
		if((line = reader.readLine()) == null) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		if(!line.startsWith("States")) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		if((line = reader.readLine()) == null) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		if(!line.isEmpty()) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		int index = 0;
		while((line = reader.readLine()) != null && !line.isEmpty()) {
			String[] params = line.split(" ");
			
			if(params.length == 2) {
				int id = Integer.parseInt(params[0]);
				
				if(index != id) {
					throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
				}
				
				index ++;
				int colorValue = Long.valueOf(params[1], 16).intValue();
				Color color = new Color(colorValue);
				
				State state = State.values()[id];
				state.setColor(color);
				states.add(state);
			} else {
				throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
			}
		}
		
		if(states.size() != 4) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		return states;
	}
	
	@Override
	public List<State> loadFromFile(String filename) throws CommonException {
		List<State> states = null;
		BufferedReader reader = null;
		
		try {
			reader = getReader(filename);
			
			states = read(reader);
		} catch (IOException | NumberFormatException e) {
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage());
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		
		return states;
	}
	
	public void write(PrintWriter writer, List<State> states) {
		writer.println("States");
		writer.println();
		for(State state : states) {
			writer.print(state.getId());
			writer.print(" ");
			writer.println(state.getColor().toString());
		}
	}
	
	@Override
	public void saveToFile(String filename, List<State> states) throws CommonException {
		PrintWriter writer = null;
		try {
			writer = getWriter(filename);
			write(writer, states);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage());
		} finally {
			if(writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
	}

}
