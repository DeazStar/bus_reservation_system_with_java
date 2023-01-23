package com.busreservationsystem.user;

import com.busreservationsystem.base.Person;
import com.busreservationsystem.controller.Ticket;
import com.busreservationsystem.controller.Reservation;
import com.busreservationsystem.base.Address;
import java.time.LocalDate;

public class Customer extends Person{
	/*private String userName;
	private String password;*/
/*	private int customer_id;*/
	private Reservation reservation;
	private int seatNumber;
	private Ticket ticket;

	public Customer(String firstName, String lastName, LocalDate dateOfBirth,
				String email, String phoneNumber, Address address){
		super(firstName, lastName, dateOfBirth, email, phoneNumber, address);
		/*this.userName = userName;
		this.password = password;*/
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
	/*public int getCustomerId() {
		return this.customer_id;
	}
	*/
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public Address getAddress() {
		return this.address;
	}
}
