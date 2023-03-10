package com.busreservationsystem.user;

import com.busreservationsystem.base.Person;
import com.busreservationsystem.controller.Ticket;
import com.busreservationsystem.controller.Reservation;
import com.busreservationsystem.base.Address;
import com.busreservationsystem.model.Bus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends Person {

	/*
	 * private String userName; private String password;
	 */
	private int customerId;
	private int seatNumber;
	// private Ticket ticket;

	public Customer(String firstName, String lastName, String gender, LocalDate dateOfBirth, String email,
			String phoneNumber, Address address) {
		super(firstName, lastName, gender, dateOfBirth, email, phoneNumber, address);
		/*
		 * this.userName = userName; this.password = password;
		 */
	}

	public Customer() {
	}

	public void setReservation(int seatNumber, Bus bus, double price) {
		// setReservation method implementation
		Reservation reservation = new Reservation();
		reservation.reserveSeat(this, seatNumber, bus, price);
	}

	public void viewReservation() {
		// viewReservation method implementation
	}

	public void cancelBooking(int reservationId) {
		System.out.println(reservationId);
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String databaseUsername = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, databaseUsername, password);
			String sql = "DELETE FROM reservation WHERE reservation_id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, reservationId);
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted == 1) {
				System.out.println("Reservation deleted successfully.");
			} else {
				System.out.println("Failed to delete reservation.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	// getter and setter methods

	public int getCustomerId() {
		return this.customerId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public ArrayList<Bus> showAvaliableBuses() {
		ArrayList<Bus> buses = new ArrayList<Bus>();
		Reservation reservation = new Reservation();
		return null;
	}

}
