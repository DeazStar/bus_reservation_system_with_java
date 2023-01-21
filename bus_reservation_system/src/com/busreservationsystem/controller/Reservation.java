package com.busreservationsystem.controller;

import com.busreservationsystem.user.Customer;
import com.busreservationsystem.model.Bus;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
	private int seatNumber;
	private LocalDate date;
	private LocalTime time;
	private Bus bus;
	private Customer customer;


	public Reservation(Customer customer, Bus bus){
		this.customer = customer;
		this.bus = bus;
	}



	public boolean isBusAvailability(){
		//implementation of checkAvailability method

		return false;
	}

	public boolean isSeatAvailable(int seatNumber){
		//implementation of the isSeatAvailable method

		return false;
	}

	public boolean confirmPayment(double amount){
		//implementation of the confirmPayment method

		return false;
	}

	public void reserveSeat(){
		//implementation of the reserveSeat method
	}

	public void cancelReservation(String ticketId){
		//implementation of the cancelReservation

		// refine it
	}
}

