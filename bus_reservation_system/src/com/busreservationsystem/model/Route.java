package com.busreservationsystem.model;

public class Route {
	private int routeId;
	private String source;
	private String destination;

	public Route(int routeId, String source, String destination){
		this.routeId = routeId;
		this.source = source;
		this.destination = destination;
	}

	//getter and setter method 

	//setter method for routeId
	public void setRouteId(int routeId){
		this.routeId = routeId;
	}

	//getter method for routeId
	public int getRouteId(){
		return this.routeId;
	}

	//setter method for source
	public void setSource(String source){
		this.source = source;
	}

	//getter method for source
	public String getSource(){
		return this.source;
	}

	//setter method for destination
	public void  setDestination(String destination){
		this.destination = destination;
	}

	//getter method for destination
	public String getDestination(){
		return this.destination;
	}
}

