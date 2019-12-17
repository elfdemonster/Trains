package com.tw.trains.data;

import com.tw.trains.Town;

public enum TownsEnum {
	
	//Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
	AB("A-B", new Town("A", "A"), new Town("B", "B"), 5),
	BC("B-C", new Town("B", "B"), new Town("C", "C"), 4),
	CD("C-D", new Town("C", "C"), new Town("D", "D"), 8),
	DC("D-C", new Town("D", "D"), new Town("C", "C"), 8),
	DE("D-E", new Town("D", "D"), new Town("E", "E"), 6),
	AD("A-D", new Town("A", "A"), new Town("D", "D"), 5),
	CE("C-E", new Town("C", "C"), new Town("E", "E"), 2),
	EB("E-B", new Town("E", "E"), new Town("B", "B"), 3),
	AE("A-E", new Town("A", "A"), new Town("E", "E"), 7),
	
	;

	private Town start;
	private Town end;
	private int distance;
	private String routes;
	
	private TownsEnum(String routes, Town start, Town end, int distance)
	{
		this.routes = routes;
		this.start = start;
		this.end = end;
		this.distance = distance;
	}

	public Town getStart() {
		return start;
	}

	public void setStart(Town start) {
		this.start = start;
	}

	public Town getEnd() {
		return end;
	}

	public void setEnd(Town end) {
		this.end = end;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getRoutes() {
		return routes;
	}

	public void setRoutes(String routes) {
		this.routes = routes;
	}
	
	
}
