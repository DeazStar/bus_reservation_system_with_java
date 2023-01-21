package com.busreservationsystem.model;

import com.busreservationsystem.base.Person;
import com.busreservationsystem.base.Address;
import java.time.LocalDate;

public class BusDriver extends Person{
	private Bus assignedBus;
	private int driverId; // newly added
	public BusDriver(String firstName, String lastName, LocalDate dateOfBirth,
				String email, String phoneNumber, Address address, Bus assignedBus, int driverId){
		super(firstName, lastName, dateOfBirth, email, phoneNumber, address);
		this.assignedBus = assignedBus;
		this.driverId = driverId; // newly added
	}

	//setter method for driver_id
	public void setDriverId(int driverId){
		this.driverId = driverId;
	}

	//getter method for driver_id
	public int getDriverId(){
		return this.driverId;
	}
}
