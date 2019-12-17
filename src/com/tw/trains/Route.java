package com.tw.trains;


/**
 * 路线
 * @author elf
 *
 */
public class Route {

	private Town startTown;
	private Town endTown;
	
	/*
	 * 路线所有节点
	 */
	private String routesTown;
	
	private int countTowns;
	
	private int distance;
	
	
	public Route(Town startTown, Town endTown) {
		super();
		this.startTown = startTown;
		this.endTown = endTown;
	}

	public Route(Town startTown, Town endTown, String routesTown) {
		this.startTown = startTown;
		this.endTown = endTown;
		this.routesTown = routesTown;
	}
	
	public Route(Town startTown, Town endTown, String routesTown, int distance) {
		super();
		this.startTown = startTown;
		this.endTown = endTown;
		this.routesTown = routesTown;
		this.distance = distance;
	}

	public Town getStartTown() {
		return startTown;
	}
	public void setStartTown(Town startTown) {
		this.startTown = startTown;
	}
	public Town getEndTown() {
		return endTown;
	}
	public void setEndTown(Town endTown) {
		this.endTown = endTown;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getRoutes() {
		return routesTown;
	}
	public void setRoutes(String routes) {
		this.routesTown = routes;
	}



	public String getRoutesTown() {
		return routesTown;
	}

	public void setRoutesTown(String routesTown) {
		this.routesTown = routesTown;
	}

	public int getCountTowns() {
		return countTowns;
	}

	public void setCountTowns(int countTowns) {
		this.countTowns = countTowns;
	}

	@Override
	public String toString() {
		return "Route [startTown=" + startTown + ", endTown=" + endTown
				+ ", routesTown=" + routesTown + ", countTowns=" + countTowns
				+ ", distance=" + distance + "]";
	}

	
	
	
}
