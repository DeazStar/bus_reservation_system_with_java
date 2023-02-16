package com.busreservationsystem.base;

import java.time.LocalDate;

public class Person {
	protected String firstName;
	protected String lastName;
	protected String gender;
	protected LocalDate dateOfBirth;
	protected String email;
	protected String phoneNumber;
	protected Address address;

	public Person(String firstName, String lastName, String gender, LocalDate dateOfBirth, String email,
			String phoneNumber, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public Person() {
	}
}
