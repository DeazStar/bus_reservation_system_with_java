package com.busreservationsystem.model;

import com.busreservationsystem.user.Customer;
import java.time.LocalTime;
import java.time.LocalDate;

import java.time.LocalTime;

public class Bus {

	private BusDriver driver;
	private Route route;
	private LocalDate date;
	private LocalTime departureTime;
	private LocalTime arrivalTime;
	private double busTicketPrice;
	private int numberOfSeats;
	private Customer[] reservedSeats;
	private int busId; // newely added attritbure

	public Bus(BusDriver driver, Route route, LocalDate date, LocalTime departureTime, LocalTime arrivalTime,
			double busTicketPrice, int numberOfSeats) {
		// this.busId = busId;
		this.driver = driver;
		this.route = route;
		this.date = date;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.busTicketPrice = busTicketPrice;
		this.numberOfSeats = numberOfSeats;
		this.reservedSeats = new Customer[numberOfSeats];
	}

	// add a null constructor
	public Bus() {
	}

	// setter method for bus Id
	/*
	 * public void setBusId(String busId){ this.busId = busId; }
	 * 
	 * //getter method for bus id public String getBusId(){ return this.busId; }
	 */
	public int getBusId() {
		return this.busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;

	}

	// setter method for driver
	public void setDriver(BusDriver driver) {
		this.driver = driver;
	}

	// getter method for driver
	public BusDriver getDrivere() {
		return this.driver;
	}

	// setter method for route
	public void setRoute(Route route) {
		this.route = route;
	}

	// getter method for route
	public Route getRoute() {
		return this.route;
	}

	// setter method for date
	public void setDate(LocalDate date) {
		this.date = date;
	}

	// getter method for date
	public LocalDate getDate() {
		return this.date;
	}
	// setter method for deaprtureTime

	public void setDepartureTime(LocalTime departurTime) {
		this.departureTime = departurTime;
	}

	// getter method for departureTiem
	public LocalTime getDepartureTime() {
		return this.departureTime;
	}

	// setter method for arrivalTIme
	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	// getter method for arrivalTime
	public LocalTime getArrivalTime() {
		return this.arrivalTime;
	}

	// setter method for busTicketPrice
	public void setBusTicketPrice(double busTicketPrice) {
		this.busTicketPrice = busTicketPrice;
	}

	// getter method for busTicketPrice
	public double getBusTicketPrice() {
		return this.busTicketPrice;
	}

	// setter method for numberOfSeats
	public void setnumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	// getter method for numberOfSeats
	public int getNumberOfSeats() {
		return this.numberOfSeats;
	}
}
