package com.busreservationsystem.user;

import com.busreservationsystem.interfaces.FileStorage;
import com.busreservationsystem.base.Address;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;

public class CustomerManagment implements FileStorage {

	private Customer customer;
	// new
	private int customerId;
	private String username;
	private String password;

	public void signup(Customer customer, String username, String password) {
		// implementation of signIn method
		// signin changed to signup
		this.customer = customer;
		this.username = username;
		this.password = password;
	}

	public Customer login(String username, String password) {
		ArrayList<String> userInfo = this.load(username);
		if (userInfo == null) {
			return null;
		} else if (userInfo.get(0).equals(username) && userInfo.get(1).equals(password)) {
			System.out.println("Login successful");
			Connection connection = null;
			PreparedStatement statement = null;
			PreparedStatement addressInsert = null;
			Customer exsistingCustomer = new Customer();
			exsistingCustomer.setCustomerId(this.customerId);
			try {
				String url = "jdbc:mysql://localhost:3306/busreservation_db";
				String databaseName = "customer";
				String databasePassword = "Customer123$";
				connection = DriverManager.getConnection(url, databaseName, databasePassword);
				connection.setAutoCommit(false);

				String sql = "SELECT first_name, last_name, date_of_birth, "
						+ "email, phone_number, address_id FROM customer " + "WHERE customer_id = ?";

				statement = connection.prepareStatement(sql);
				statement.setInt(1, this.customerId);

				ResultSet result = statement.executeQuery();
				if (result.next()) {
					exsistingCustomer.setFirstName(result.getString("first_name"));
					exsistingCustomer.setLastName(result.getString("last_name"));
					exsistingCustomer.setDateOfBirth(result.getDate("date_of_birth").toLocalDate());
					exsistingCustomer.setEmail("email");
					exsistingCustomer.setEmail(result.getString("phone_number"));
					int addressId = result.getInt("address_id");

					Address address = new Address();
					address.setAddressId(addressId);

					String addressSql = "SELECT street_address, city, region FROM address " + "WHERE address_id = ?"; // finish
																														// it
																														// when
																														// chatGPT
																														// is
																														// available

					addressInsert = connection.prepareStatement(addressSql);
					addressInsert.setInt(1, addressId);

					ResultSet addressResult = addressInsert.executeQuery();

					if (addressResult.next()) {
						address.setStreetAddress(addressResult.getString("street_address"));
						address.setCity(addressResult.getString("city"));
						address.setRegion(addressResult.getString("region"));
					}

					exsistingCustomer.setAddress(address);

<<<<<<< HEAD
                    connection.commit();
                    
                    return exsistingCustomer;
                }
=======
					connection.commit();

					return exsistingCustomer;
				}
>>>>>>> 37e5222af0dab90cae0079ade93c1f60b4e39c72

			} catch (SQLException ex) {
				ex.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
					if (statement != null) {
						statement.close();
					}
					if (addressInsert != null) {
						addressInsert.close();
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		} else {
			System.out.println("access denied");
			return null;
		}

		return null;
	}

	public void logOut() {
		// implementation of logOut method
	}

	public void store() {
		Connection connection = null;
		PreparedStatement addressInsert = null;
		PreparedStatement customerInsert = null;
		PreparedStatement customerManagmentInsert = null;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";

			// connect to the database
			connection = DriverManager.getConnection(url, username, password);

			// Start the transaction
			connection.setAutoCommit(false);

			// Create the prepared statements for the address, customer and
			// customer_managment insert
			addressInsert = connection.prepareStatement(
					"INSERT INTO address " + "(street_address, city, region) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			customerInsert = connection.prepareStatement("INSERT INTO customer (first_name,"
					+ "last_name, gender, date_of_birth, email, phone_number, address_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			customerManagmentInsert = connection.prepareStatement("INSERT INTO "
					+ "customer_management (customer_id, customer_name, username, password) " + "VALUES (?, ?, ?, ?)");

			// insert the address information
			addressInsert.setString(1, customer.getAddress().getStreetAddress());
			addressInsert.setString(2, customer.getAddress().getCity());
			addressInsert.setString(3, customer.getAddress().getRegion());
			addressInsert.executeUpdate();

			// get the generated address_id
			ResultSet rsOne = addressInsert.getGeneratedKeys();

			if (rsOne.next()) {
				int addressId = rsOne.getInt(1);

				// insert the customer information
				customerInsert.setString(1, customer.getFirstName());
				customerInsert.setString(2, customer.getLastName());
				customerInsert.setString(3, customer.getGender());
				Date date = java.sql.Date.valueOf(customer.getDateOfBirth());
				customerInsert.setDate(4, date);
				customerInsert.setString(5, customer.getEmail());
				customerInsert.setString(6, customer.getPhoneNumber());
				customerInsert.setInt(7, addressId);
				customerInsert.executeUpdate();

				// get the generated customer_id
				ResultSet rsTwo = customerInsert.getGeneratedKeys();

				if (rsTwo.next()) {
					int customerId = rsTwo.getInt(1);

					// insert the customer_managment information
					customerManagmentInsert.setInt(1, customerId);
					customerManagmentInsert.setString(2, customer.getFirstName() + " " + customer.getLastName());
					customerManagmentInsert.setString(3, this.username);
					customerManagmentInsert.setString(4, this.password);
					customerManagmentInsert.executeUpdate();

				}
			}

			// commit the transaction
			connection.commit();

			System.out.println("user added to the database");

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				addressInsert.close();
				customerInsert.close();
				customerManagmentInsert.close();
				connection.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<String> load(String username) {
		Connection connection = null;
		PreparedStatement statement = null;
		ArrayList<String> info = new ArrayList<String>();
		String retrivedUsername = null;
		String retrivedPassword = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String databaseUsername = "customer";
			String password = "Customer123$";

			connection = DriverManager.getConnection(url, databaseUsername, password);

			String sql = "SELECT customer_id, username, password from customer_management " + "WHERE username = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);

			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				this.customerId = resultset.getInt("customer_id");
				retrivedUsername = resultset.getString("username");
				retrivedPassword = resultset.getString("password");
			}

			if (retrivedUsername == null) {
				return null;
			}
			info.add(retrivedUsername);
			info.add(retrivedPassword);

		} catch (SQLException exec) {
			exec.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

				if (connection != null) {
					statement.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return info;
	}

}
