package com.celluarautomaton.iomodule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

import org.apache.log4j.Logger;

import com.celluarautomaton.common.CommonException;
import com.celluarautomaton.common.Message;
import com.celluarautomaton.gridmodule.Cell;
import com.celluarautomaton.gridmodule.Point;
import com.celluarautomaton.statemodule.State;

public class GridIO extends AbstractIO<GridData> {
	private static final Logger logger = Logger.getLogger(GridIO.class);
	
	public List<GridData> read(BufferedReader reader)  throws CommonException, IOException {
		final List<Cell> cells = new ArrayList<Cell>();
		final GridData gridData = new GridData();
		gridData.setCells(cells);
		
		final List<GridData> gridDatas = new ArrayList<>();
		gridDatas.add(gridData);
		
		
		String line = null;
		if((line = reader.readLine()) == null) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		if(!line.startsWith("Grid")) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		if((line = reader.readLine()) == null) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		if(!line.isEmpty()) {
			throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
		}
		
		line = reader.readLine();
		int zoom = Integer.parseInt(line);
		
		line = reader.readLine();
		String[] positions = line.split(" ");
		int pointX = Integer.parseInt(positions[0]);
		int pointY = Integer.parseInt(positions[1]);
		Point2D gridPoint = new Point2D(pointX, pointY);
		
		gridData.setZoom(zoom);
		gridData.setGridPoint(gridPoint);
		line = reader.readLine();
		
		while((line = reader.readLine()) != null && !line.isEmpty()) {
			String[] params = line.split(" ");
			
			if(params.length == 3) {
				int x = Integer.parseInt(params[0]);
				int y = Integer.parseInt(params[1]);
				int stateId = Integer.parseInt(params[2]);
				
				if(stateId >= State.values().length) {
					throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
				}
				
				State state = State.values()[stateId];
				Cell cell = new Cell(x, y, state);
				cells.add(cell);
			} else {
				throw new CommonException(Message.FILE_FORMAT_EXCEPTION);
			}
		}
		
		return gridDatas;
	}
	
	@Override
	public List<GridData> loadFromFile(String filename) throws CommonException {
		List<GridData> gridDatas = null;
		BufferedReader reader = null;
		
		try {
			reader = getReader(filename);
			gridDatas = read(reader);
			
		} catch (Exception e) {
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
		
		return gridDatas;
	}
	
	public void write(PrintWriter writer, List<GridData> gridDatas) {
		GridData gridData = gridDatas.get(0);
		writer.println("Grid");
		writer.println();
		
		writer.println(gridData.getZoom());
		Point2D gridPoint = gridData.getGridPoint();
		writer.println((int)gridPoint.getX() + " " + (int)gridPoint.getY());
		for(Cell cell : gridData.getCells()) {
			writer.println();
			writer.print(cell.getPoint().getX());
			writer.print(" ");
			writer.print(cell.getPoint().getY());
			writer.print(" ");
			writer.print(cell.getState().getId());
		}
	}

	@Override
	public void saveToFile(String filename, List<GridData> gridDatas) throws CommonException {
		PrintWriter writer = null;
		try {
			writer = getWriter(filename);
			write(writer, gridDatas);
			
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
