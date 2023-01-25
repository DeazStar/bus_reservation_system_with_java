package com.busreservationsystem.controller;

import com.busreservationsystem.model.*;
import com.busreservationsystem.base.*;

import java.math.BigDecimal;
import java.sql.*;
import com.busreservationsystem.interfaces.FileStorage;
import java.math.*;

public class Administrator implements FileStorage {
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

			String sql = "UPDATE bus SET departure_time = ?,"
					+ "arrival_time = ?, bus_ticket_price = ?, number_of_seats = ? " + "WHERE bus_id = ?";

			busEdit = connection.prepareStatement(sql);
			busEdit.setTime(1, Time.valueOf(newBus.getDepartureTime()));
			busEdit.setTime(2, Time.valueOf(newBus.getArrivalTime()));
			BigDecimal price = BigDecimal.valueOf(newBus.getBusTicketPrice()).setScale(2, RoundingMode.HALF_UP);
			busEdit.setBigDecimal(3, price);
			busEdit.setInt(4, newBus.getnumberOfSeats());
			busEdit.setInt(5, bus.getBusId());

			busEdit.executeUpdate();

			String routeSql = "UPDATE route source = ?, destination = ? WHERE route_id = ?";
			routeEdit = connection.prepareStatement(routeSql);
			routeEdit.setString(1, newBus.getRoute().getSource());
			routeEdit.setString(2, newBus.getRoute().getDestination());
			routeEdit.setInt(3, bus.getRoute().getRouteId());

			routeEdit.executeUpdate();

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
		// need testing
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

			String sql = "DELETE FROM bus WHERE bus_id = ?";
			busEdit = connection.prepareStatement(sql);
			busEdit.setInt(1, bus.getBusId());
			busEdit.executeUpdate();

			String routeSql = "DELETE from route WHERE route_id = ?";
			routeEdit = connection.prepareStatement(routeSql);
			routeEdit.setInt(1, bus.getRoute().getRouteId());
			routeEdit.executeUpdate();

			String driverSql = "DELETE from driver WHERE assigned_bus = ?";
			driverEdit = connection.prepareStatement(driverSql);
			driverEdit.setInt(1, bus.getBusId());
			driverEdit.executeUpdate();

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

	public Bus searchBus(int busId) {
		// implementation of searchBus method
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
				driver.setAssignedBus(bus);
				bus.setDriver(driver);
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
		// will be implemented after javafx cuz i don't know if system.out.println will print 
		//something on the screen or the Terminal
	}
	
	public void showSeats(Bus bus) {
		// implementation of showSeats method
	}

	public void addDriver(BusDriver driver) {
		// refine
		this.driver = driver;
	}

	public void editDriverInfo(BusDriver driver, BusDriver newDriver) {
		Connection connection = null;
		PreparedStatement addressEdit = null;
		PreparedStatement driverEdit = null;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);

			String sql = "UPDATE SET address street_address = ?," + "city = ?, region = ? " + "WHERE address_id = ?";

			addressEdit = connection.prepareStatement(sql);
			addressEdit.setString(1, newDriver.getAddress().getStreetAddress());
			addressEdit.setString(2, newDriver.getAddress().getCity());
			addressEdit.setString(3, newDriver.getAddress().getRegion());
			addressEdit.setInt(4, driver.getAddress().getAddressId());

			addressEdit.executeUpdate();

			String driverSql = "UPDATE SET bus_driver first_name = ?, last_name = ?, "
					+ "date_of_birth = ?, email = ?, phone_number = ? WHERE bus_driver_id = ?";
			driverEdit = connection.prepareStatement(driverSql);
			driverEdit.setString(1, newDriver.getFirstName());
			driverEdit.setString(2, newDriver.getLastName());
			Date date = java.sql.Date.valueOf(newDriver.getDateOfBirth());
			driverEdit.setDate(3, date);
			driverEdit.setString(4, newDriver.getEmail());
			driverEdit.setString(5, newDriver.getPhoneNumber());
			driverEdit.setInt(6, driver.getDriverId());

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
	}

	public void assignDriverToBus(Bus bus, BusDriver driver) {

		Connection connection = null;
		PreparedStatement driverEdit = null;
		PreparedStatement busEdit = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);

			String sql = "UPDATE SET assigned_bus = ? " + "WHERE bus_driver_id = ?";

			driverEdit = connection.prepareStatement(sql);
			driverEdit.setInt(1, bus.getBusId());
			driverEdit.setInt(2, driver.getDriverId());

			driverEdit.executeUpdate();

			String busSql = "UPDATE SET driver_id = ? WHERE bus_id = ?";

			busEdit = connection.prepareStatement(busSql);
			busEdit.setInt(1, driver.getDriverId());
			busEdit.setInt(2, bus.getBusId());

			busEdit.executeUpdate();

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
				if (driverEdit != null) {
					driverEdit.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public BusDriver searchDriver(int driverId) {
		//need testing
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

			String sql = "SELECT * from bus_driver " + "WHERE driver_id = ?";

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
				
				if (busId == 0)
				{
					driver.setAssignedBus(null);
				}
				else 
				{
					String busSql = "SELECT * FROM bus WHERE bus_id = ?";
					busRecover = connection.prepareStatement(busSql);
					busRecover.setInt(1,  busId);
					
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
						
						bus.setDepartureTime(resultset.getTime("departure_time").toLocalTime());
						bus.setArrivalTime(resultset.getTime("arrival_time").toLocalTime());
						bus.setBusTicketPrice(resultset.getDouble("bus_ticket_price"));
						bus.setnumberOfSeats(resultset.getInt("number_of_seats"));
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

	public void store() {
		Connection connection = null;
		PreparedStatement busInsert = null;
		PreparedStatement routeInsert = null;

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
					+ "departure_time, arrival_time, bus_ticket_price, number_of_seats) " + "VALUES(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

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
			Time departureTime = Time.valueOf(bus.getDepartureTime());
			Time arrivalTime = Time.valueOf(bus.getArrivalTime());
			busInsert.setTime(3, departureTime);
			busInsert.setTime(4, arrivalTime);
			BigDecimal price = BigDecimal.valueOf(bus.getBusTicketPrice()).setScale(2, RoundingMode.HALF_UP);
			busInsert.setBigDecimal(5, price);
			busInsert.setInt(6, bus.getnumberOfSeats());
			busInsert.executeUpdate();

			ResultSet busKey = busInsert.getGeneratedKeys();
			busKey.next();
			bus.setBusId(busKey.getInt(1));
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
		// don't forget to test it tmrw

	}

	public void saveDriver() {
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

			driverInsert = connection.prepareStatement(
					"INSERT INTO bus_driver (" + "first_name, last_name, date_of_birth, email, phone_number, "
							+ "address_id, assigned_bus) VALUES (?, ?, " + "?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

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
				Date date = java.sql.Date.valueOf(driver.getDateOfBirth());
				driverInsert.setDate(3, date);
				driverInsert.setString(4, driver.getEmail());
				driverInsert.setString(5, driver.getPhoneNumber());
				driverInsert.setInt(6, address_id);
				driverInsert.setObject(7, null, Types.INTEGER);
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
