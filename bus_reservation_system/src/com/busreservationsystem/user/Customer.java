package com.busreservationsystem.user;

import com.busreservationsystem.base.Person;
import com.busreservationsystem.controller.Ticket;
import com.busreservationsystem.controller.Reservation;
import com.busreservationsystem.base.Address;
import java.time.LocalDate;

public class Customer extends Person{
	private String userName;
	private String password;
	private Reservation reservation;
	private int seatNumber;
	private Ticket ticket;

	public Customer(String firstName, String lastName, LocalDate dateOfBirth,
				String email, String phoneNumber, Address address, String userName, String password){
		super(firstName, lastName, dateOfBirth, email, phoneNumber, address);
		this.userName = userName;
		this.password = password;
	}

	public void setReservation(Reservation reservation, int seatNumber){
		// setReservation method implementation 
	}

	public void viewReservation(){
		// viewReservation method implementation
	}

	public void cancelBooking(Ticket ticekt){
		// cancelBooking method implementation
	}

	//getter and setter methods 
}
