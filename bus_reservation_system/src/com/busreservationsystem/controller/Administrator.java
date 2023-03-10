package com.busreservationsystem.controller;

import com.busreservationsystem.model.*;
import com.busreservationsystem.base.*;

import java.math.BigDecimal;
import java.sql.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import com.busreservationsystem.interfaces.FileStorage;
import java.math.*;

public class Administrator {

	private Bus bus; // new attribute
	private BusDriver driver; // new attribute

	public void addBus(Bus bus) {
		this.bus = bus;
	}

	public void editBus(Bus bus, Bus newBus) {
		// need testing
		Connection connection = null;
		PreparedStatement busEdit = null;
		PreparedStatement routeEdit = null;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);

			String sql = "UPDATE bus SET date = ?, departure_time = ?,"
					+ "arrival_time = ?, bus_ticket_price = ?, number_of_seats = ? WHERE bus_id = ?";

			busEdit = connection.prepareStatement(sql);
			Date date = Date.valueOf(newBus.getDate());
			busEdit.setDate(1, date);
			busEdit.setTime(2, Time.valueOf(newBus.getDepartureTime()));
			busEdit.setTime(3, Time.valueOf(newBus.getArrivalTime()));
			BigDecimal price = BigDecimal.valueOf(newBus.getBusTicketPrice()).setScale(2, RoundingMode.HALF_UP);
			busEdit.setBigDecimal(4, price);
			busEdit.setInt(5, newBus.getNumberOfSeats());
			busEdit.setInt(6, bus.getBusId());

			busEdit.executeUpdate();

			String routeIdSql = "SELECT route_id FROM bus WHERE bus_id = ?";

			PreparedStatement routeIdQuery = connection.prepareStatement(routeIdSql);

			routeIdQuery.setInt(1, bus.getBusId());

			ResultSet result = routeIdQuery.executeQuery();

			if (result.next()) {
				int routeId = result.getInt("route_id");
				String routeSql = "UPDATE route SET source = ?, destination = ? WHERE route_id = ?";
				routeEdit = connection.prepareStatement(routeSql);
				routeEdit.setString(1, newBus.getRoute().getSource());
				routeEdit.setString(2, newBus.getRoute().getDestination());
				routeEdit.setInt(3, routeId);

				System.out.println(routeEdit.executeUpdate());
			}

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (busEdit != null) {
					busEdit.close();
				}
				if (routeEdit != null) {
					routeEdit.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeBus(Bus bus) {
		// tested no furture testing required
		Connection connection = null;
		PreparedStatement busEdit = null;
		PreparedStatement routeEdit = null;
		PreparedStatement driverEdit = null;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);

			// SET FOREIGN KEY_CHECKS=0
			Statement disableFKCheck = connection.createStatement();
			disableFKCheck.execute("SET FOREIGN_KEY_CHECKS=0");
			String routeIdSql = "SELECT route_id FROM bus WHERE bus_id = ?";

			PreparedStatement routeIdQuery = connection.prepareStatement(routeIdSql);

			routeIdQuery.setInt(1, bus.getBusId());
			System.out.println(bus.getBusId());

			ResultSet result = routeIdQuery.executeQuery();

			if (result.next()) {
				int routeId = result.getInt("route_id");
				System.out.println(routeId);
				String routeSql = "DELETE from route WHERE route_id = ?";
				routeEdit = connection.prepareStatement(routeSql);
				routeEdit.setInt(1, routeId);
				routeEdit.executeUpdate();
			}

			String sql = "DELETE FROM bus WHERE bus_id = ?";
			busEdit = connection.prepareStatement(sql);
			busEdit.setInt(1, bus.getBusId());
			busEdit.executeUpdate();

			String driverSql = "UPDATE bus_driver SET assigned_bus = null " + "WHERE assigned_bus = ?";
			driverEdit = connection.prepareStatement(driverSql);
			driverEdit.setInt(1, bus.getBusId());
			driverEdit.executeUpdate();
			// SET FOREIGN_KEY_CHECKS=1;
			Statement enableFKCheck = connection.createStatement();
			enableFKCheck.execute("SET FOREIGN_KEY_CHECKS=1");

			connection.commit();

		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (busEdit != null) {
					busEdit.close();
				}
				if (routeEdit != null) {
					routeEdit.close();
				}
				if (driverEdit != null) {
					driverEdit.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeDriver(BusDriver driver) {
		// tested no furture testing required
		Connection connection = null;
		PreparedStatement busEdit = null;
		PreparedStatement addressEdit = null;
		PreparedStatement driverEdit = null;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);

			// SET FOREIGN KEY_CHECKS=0
			Statement disableFKCheck = connection.createStatement();
			disableFKCheck.execute("SET FOREIGN_KEY_CHECKS=0");
			String addressIdSql = "SELECT address_id FROM bus_driver WHERE bus_driver_id = ?";

			PreparedStatement addressIdQuery = connection.prepareStatement(addressIdSql);

			addressIdQuery.setInt(1, driver.getDriverId());
			System.out.println(driver.getDriverId());

			ResultSet result = addressIdQuery.executeQuery();

			if (result.next()) {
				int addressId = result.getInt("address_id");
				System.out.println(addressId);
				String addressSql = "DELETE from address WHERE address_id = ?";
				addressEdit = connection.prepareStatement(addressSql);
				addressEdit.setInt(1, addressId);
				addressEdit.executeUpdate();
			}

			String sql = "DELETE FROM bus_driver WHERE bus_driver_id = ?";
			driverEdit = connection.prepareStatement(sql);
			driverEdit.setInt(1, driver.getDriverId());
			driverEdit.executeUpdate();

			String busSql = "UPDATE bus SET driver_id = null " + "WHERE driver_id = ?";
			busEdit = connection.prepareStatement(busSql);
			busEdit.setInt(1, driver.getDriverId());
			busEdit.executeUpdate();
			// SET FOREIGN_KEY_CHECKS=1;
			Statement enableFKCheck = connection.createStatement();
			enableFKCheck.execute("SET FOREIGN_KEY_CHECKS=1");

			connection.commit();

		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (driverEdit != null) {
					driverEdit.close();
				}
				if (addressEdit != null) {
					addressEdit.close();
				}
				if (busEdit != null) {
					busEdit.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Bus searchBus(int busId) {
		// implementation of searchBus method
		// tested there may be need for testing on the edge cases
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement routeRecover = null;
		Bus bus = new Bus();
		try {
			BusDriver driver = new BusDriver();
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";

			connection = DriverManager.getConnection(url, username, password);

			String busSql = "SELECT * FROM bus " + "WHERE bus_id = ?";

			statement = connection.prepareStatement(busSql);
			statement.setInt(1, busId);

			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				bus.setBusId(resultset.getInt("bus_id"));
				int driverId = resultset.getInt("driver_id");
				driver = this.searchAssignedBus(busId);
				if (driver != null) {
					driver.setAssignedBus(bus);
					bus.setDriver(driver);
				}
				int routeId = resultset.getInt("route_id");

				String routeSql = "SELECT * FROM route " + "WHERE route_id = ?";

				routeRecover = connection.prepareStatement(routeSql);
				routeRecover.setInt(1, routeId);

				ResultSet routeSet = routeRecover.executeQuery();

				while (routeSet.next()) {
					Route route = new Route();
					route.setRouteId(routeSet.getInt("route_id"));
					route.setSource(routeSet.getString("source"));
					route.setDestination(routeSet.getString("destination"));

					bus.setRoute(route);
				}

				bus.setDepartureTime(resultset.getTime("departure_time").toLocalTime());
				bus.setArrivalTime(resultset.getTime("arrival_time").toLocalTime());
				bus.setBusTicketPrice(resultset.getDouble("bus_ticket_price"));
				bus.setnumberOfSeats(resultset.getInt("number_of_seats"));

			}

		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (routeRecover != null) {
					routeRecover.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (bus.getBusId() == 0) {
			return null;
		} else {
			return bus;
		}
	}

	public void showBus() {
		// will be implemented after javafx cuz i don't know if system.out.println will
		// print
		// something on the screen or the Terminal
	}

	public void showSeats(Bus bus) {
		// implementation of showSeats method
	}

	public void addDriver(BusDriver driver) {
		// tested no need for furthur testing
		this.driver = driver;
	}

	public void editDriverInfo(BusDriver driver, BusDriver newDriver) {
		// tested no need for furthur testing
		Connection connection = null;
		PreparedStatement addressEdit = null;
		PreparedStatement driverEdit = null;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);

			String addressIdSql = "select address_id from bus_driver where bus_driver_id = ?";
			PreparedStatement addressIdQuery = connection.prepareStatement(addressIdSql);
			addressIdQuery.setInt(1, driver.getDriverId());

			ResultSet result = addressIdQuery.executeQuery();
			if (result.next()) {
				int addressId = result.getInt("address_id");

				String sql = "UPDATE address SET street_address = ?," + "city = ?, region = ? "
						+ "WHERE address_id = ?";

				addressEdit = connection.prepareStatement(sql);
				addressEdit.setString(1, newDriver.getAddress().getStreetAddress());
				addressEdit.setString(2, newDriver.getAddress().getCity());
				addressEdit.setString(3, newDriver.getAddress().getRegion());
				addressEdit.setInt(4, addressId);

				addressEdit.executeUpdate();

			}
			String driverSql = "UPDATE bus_driver SET first_name = ?, last_name = ?, "
					+ "date_of_birth = ?, email = ?, phone_number = ? ,gender = ? WHERE bus_driver_id = ?";
			driverEdit = connection.prepareStatement(driverSql);
			driverEdit.setString(1, newDriver.getFirstName());
			driverEdit.setString(2, newDriver.getLastName());
			Date date = java.sql.Date.valueOf(newDriver.getDateOfBirth());
			driverEdit.setDate(3, date);
			driverEdit.setString(4, newDriver.getEmail());
			driverEdit.setString(5, newDriver.getPhoneNumber());
			driverEdit.setString(6, newDriver.getGender());
			driverEdit.setInt(7, driver.getDriverId());
			driverEdit.executeUpdate();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (addressEdit != null) {
					addressEdit.close();
				}
				if (driverEdit != null) {
					driverEdit.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

//            String sql = "UPDATE address SET street_address = ?,"
//                    + "city = ?, region = ? " + "WHERE address_id = ?";
//
//            addressEdit = connection.prepareStatement(sql);
//            addressEdit.setString(1, newDriver.getAddress().getStreetAddress());
//            addressEdit.setString(2, newDriver.getAddress().getCity());
//            addressEdit.setString(3, newDriver.getAddress().getRegion());
//            addressEdit.setInt(4, driver.getAddress().getAddressId());
//
//            addressEdit.executeUpdate();
	}

	// beign edited
	public void assignDriverToBus(int busId, int driverId) {
		// tested no furture testing required
		Connection connection = null;
		PreparedStatement driverEdit = null;
		PreparedStatement busEdit = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);

			String busSql = "UPDATE bus SET driver_id = ? WHERE bus_id = ?";
			busEdit = connection.prepareStatement(busSql);
			busEdit.setInt(1, driverId);
			busEdit.setInt(2, busId);
			busEdit.executeUpdate();

			String sql = "UPDATE bus_driver SET assigned_bus = ? WHERE bus_driver_id = ?";
			driverEdit = connection.prepareStatement(sql);
			driverEdit.setInt(1, busId);
			driverEdit.setInt(2, driverId);
			driverEdit.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (busEdit != null) {
					busEdit.close();
				}
				if (driverEdit != null) {
					driverEdit.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public BusDriver searchDriver(int driverId) {
		// teseted no furthure testing required
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement addressRecover = null;
		PreparedStatement busRecover = null;
		BusDriver driver = new BusDriver();

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";

			connection = DriverManager.getConnection(url, username, password);

			connection.setAutoCommit(false);

			String sql = "SELECT * from bus_driver " + "WHERE bus_driver_id = ?";

			statement = connection.prepareStatement(sql);
			statement.setInt(1, driverId);

			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				driver.setDriverId(resultset.getInt("bus_driver_id"));
				driver.setFirstName(resultset.getString("first_name"));
				driver.setLastName(resultset.getString("last_name"));
				driver.setDateOfBirth(resultset.getDate("date_of_birth").toLocalDate());
				driver.setEmail(resultset.getString("email"));
				driver.setPhoneNumber(resultset.getString("phone_number"));

				int addressId = resultset.getInt("address_id");

				Address address = new Address();

				String addressSql = "SELECT * FROM address " + "WHERE address_id = ? ";

				addressRecover = connection.prepareStatement(addressSql);
				addressRecover.setInt(1, addressId);

				ResultSet addressSet = addressRecover.executeQuery();

				while (addressSet.next()) {
					address.setAddressId(addressSet.getInt("address_id"));
					address.setStreetAddress(addressSet.getString("street_address"));
					address.setCity(addressSet.getString("city"));
					address.setRegion(addressSet.getString("region"));
				}

				driver.setAddress(address);
				int busId = resultset.getInt("assigned_bus");

				if (busId == 0) {
					driver.setAssignedBus(null);
				} else {
					String busSql = "SELECT * FROM bus WHERE bus_id = ?";
					busRecover = connection.prepareStatement(busSql);
					busRecover.setInt(1, busId);

					ResultSet busSet = busRecover.executeQuery();

					while (busSet.next()) {
						Bus bus = new Bus();

						bus.setBusId(busSet.getInt("bus_id"));
						int driver_id = busSet.getInt("driver_id");
						int routeId = busSet.getInt("route_id");

						String routeSql = "SELECT * FROM route WHERE route_id = ?";
						PreparedStatement routeStmt = connection.prepareStatement(routeSql);
						routeStmt.setInt(1, routeId);

						ResultSet routeSet = routeStmt.executeQuery();

						while (routeSet.next()) {
							Route route = new Route();
							route.setRouteId(routeSet.getInt("route_id"));
							route.setSource(routeSet.getString("source"));
							route.setDestination(routeSet.getString("destination"));

							bus.setRoute(route);
						}

						bus.setDepartureTime(busSet.getTime("departure_time").toLocalTime());
						bus.setArrivalTime(busSet.getTime("arrival_time").toLocalTime());
						bus.setBusTicketPrice(busSet.getDouble("bus_ticket_price"));
						bus.setnumberOfSeats(busSet.getInt("number_of_seats"));
					}
					driver.setAssignedBus(bus);
				}

				connection.commit();

			}
		} catch (SQLException exc) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			exc.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (addressRecover != null) {
					addressRecover.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (driver.getDriverId() == 0) {
			return null;
		} else {
			return driver;
		}

	}

	public BusDriver searchAssignedBus(int assignedBusId) {
		// tested no furture testing required
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement addressRecover = null;
		/* PreparedStatement busRecover = null; */
		BusDriver driver = new BusDriver();

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";

			connection = DriverManager.getConnection(url, username, password);

			String sql = "SELECT * from bus_driver " + "WHERE assigned_bus = ?";

			statement = connection.prepareStatement(sql);
			statement.setInt(1, assignedBusId);

			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				driver.setDriverId(resultset.getInt("bus_driver_id"));
				driver.setFirstName(resultset.getString("first_name"));
				driver.setLastName(resultset.getString("last_name"));
				driver.setDateOfBirth(resultset.getDate("date_of_birth").toLocalDate());
				driver.setEmail(resultset.getString("email"));
				driver.setPhoneNumber(resultset.getString("phone_number"));

				int addressId = resultset.getInt("address_id");

				Address address = new Address();

				String addressSql = "SELECT * FROM address " + "WHERE address_id = ? ";

				addressRecover = connection.prepareStatement(addressSql);
				addressRecover.setInt(1, addressId);

				ResultSet addressSet = addressRecover.executeQuery();

				while (addressSet.next()) {
					address.setAddressId(addressSet.getInt("address_id"));
					address.setStreetAddress(addressSet.getString("street_address"));
					address.setCity(addressSet.getString("city"));
					address.setRegion(addressSet.getString("region"));
				}

				driver.setAddress(address);
				driver.setAssignedBus(null);

			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (addressRecover != null) {
					addressRecover.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (driver.getDriverId() == 0) {
			return null;
		} else {
			return driver;
		}
	}

	public int store() {
		// testd no need for furture testing
		Connection connection = null;
		PreparedStatement busInsert = null;
		PreparedStatement routeInsert = null;
		int busId = 0;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			// connect to the database
			connection = DriverManager.getConnection(url, username, password);

			// Start the transaction
			connection.setAutoCommit(false);

			// create a preparestatement for route info and bus info
			routeInsert = connection.prepareStatement("INSERT INTO route (source, destination)" + " VALUES(?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			busInsert = connection.prepareStatement("INSERT INTO bus (driver_id, route_id, "
					+ "date, departure_time, arrival_time, bus_ticket_price, number_of_seats) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			// insert data into route table
			routeInsert.setString(1, bus.getRoute().getSource());
			routeInsert.setString(2, bus.getRoute().getDestination());
			routeInsert.executeUpdate();

			ResultSet routeKey = routeInsert.getGeneratedKeys();
			routeKey.next();
			int route_id = routeKey.getInt(1);

			// insert data into bus table
			busInsert.setObject(1, null, Types.INTEGER);
			busInsert.setInt(2, route_id);
			Date date = Date.valueOf(bus.getDate());
			Time departureTime = Time.valueOf(bus.getDepartureTime());
			Time arrivalTime = Time.valueOf(bus.getArrivalTime());
			busInsert.setDate(3, date);
			busInsert.setTime(4, departureTime);
			busInsert.setTime(5, arrivalTime);
			BigDecimal price = BigDecimal.valueOf(bus.getBusTicketPrice()).setScale(2, RoundingMode.HALF_UP);
			busInsert.setBigDecimal(6, price);
			busInsert.setInt(7, bus.getNumberOfSeats());
			busInsert.executeUpdate();

			ResultSet busKey = busInsert.getGeneratedKeys();
			busKey.next();
			busId = busKey.getInt(1);
			bus.setBusId(busId);
			// commit the transaction

			connection.commit();

		} catch (SQLException exce) {
			exce.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				busInsert.close();
				routeInsert.close();
				/*
				 * driverInsert.close(); addressInsert.close();
				 */
				connection.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
			}
		}

		return busId;
	}

	public void saveDriver() {
		// tested no need for furthur testing
		Connection connection = null;
		PreparedStatement addressInsert = null;
		PreparedStatement driverInsert = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";

			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);

			// create a prepared statement for driver and address
			addressInsert = connection.prepareStatement(
					"INSERT INTO address (" + "street_address, city, region) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			driverInsert = connection.prepareStatement("INSERT INTO bus_driver (first_name, last_name, "
					+ "gender, date_of_birth, email, phone_number, address_id, "
					+ "assigned_bus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			// insert the address info
			addressInsert.setString(1, driver.getAddress().getStreetAddress());
			addressInsert.setString(2, driver.getAddress().getCity());
			addressInsert.setString(3, driver.getAddress().getRegion());
			addressInsert.executeUpdate();

			// get the generated address_id
			ResultSet addressKey = addressInsert.getGeneratedKeys();
			if (addressKey.next()) {
				int address_id = addressKey.getInt(1);

				// insert the driver info
				driverInsert.setString(1, driver.getFirstName());
				driverInsert.setString(2, driver.getLastName());
				driverInsert.setString(3, driver.getGender());
				Date date = java.sql.Date.valueOf(driver.getDateOfBirth());
				driverInsert.setDate(4, date);
				driverInsert.setString(5, driver.getEmail());
				driverInsert.setString(6, driver.getPhoneNumber());
				driverInsert.setInt(7, address_id);
				driverInsert.setObject(8, null, Types.INTEGER);
				driverInsert.executeUpdate();

				ResultSet driverKey = driverInsert.getGeneratedKeys();
				driverKey.next();

				driver.setDriverId(driverKey.getInt(1));

				connection.commit();

			}

		} catch (SQLException exce) {
			exce.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				try {
					addressInsert.close();
					driverInsert.close();
					connection.close();
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		}

	}
}
