package com.busreservationsystem.model;

import com.busreservationsystem.base.Person;
import com.busreservationsystem.base.Address;
import java.time.LocalDate;

public class BusDriver extends Person {

    private Bus assignedBus;
    private int driverId;// new attribute

    /*private int driverId; // newly added*/
    public BusDriver(String firstName, String lastName, String gender, LocalDate dateOfBirth,
            String email, String phoneNumber, Address address, Bus assignedBus) {
        super(firstName, lastName, gender, dateOfBirth, email, phoneNumber, address);
        this.assignedBus = assignedBus;
        /*this.driverId = driverId; // newly added*/
    }

    public BusDriver() {
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getDriverId() {
        return this.driverId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    //getter method for the firstName

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // getter method for the lastName
    public String getLastName() {
        return this.lastName;
    }
    
    
    public void setGender(String gender) {
    	this.gender = gender;
    }
    
    public String getGender() {
    	return this.gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    // getter method for the dateOfBirth

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // getter method for the email

    public String getEmail() {
        return this.email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAssignedBus(Bus assignedBus) {
        this.assignedBus = assignedBus;
    }

    public Bus getAssignedBus() {
        return this.assignedBus;
    }
}
