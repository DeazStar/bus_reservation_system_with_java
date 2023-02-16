package com.busreservationsystem.controller;

import com.busreservationsystem.user.Customer;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.model.Route;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/*
add setter and getter for date , time , bus and customer;
that's all i added to the file!

*/
public class Reservation {

	private int seatNumber;
	private LocalDate date;
	private LocalTime time;
	private Bus bus;
	private Customer customer;

	public Reservation(Customer customer, Bus bus) {
		this.customer = customer;
		this.bus = bus;
	}

	public Reservation() {
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;

	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public LocalTime getTime() {
		return time;
	}

	public Bus getBus() {
		return bus;
	}

	public Customer getCustomer() {
		return customer;
	}

	// we may not need this
	public boolean isBusAvailability() {
		// implementation of checkAvailability method

		return false;
	}

	public boolean isSeatAvailable(Bus bus, int seatNumber) {
		// implementation of the isSeatAvailable method
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);
			String query = "SELECT seat FROM reservation WHERE bus_id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, bus.getBusId());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getInt("seat") == seatNumber) {
					return false;
				}
			}

			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void processPayment(Bus bus, double amount) {
		// change it to try and catch
		double change = bus.getBusTicketPrice() - amount;

		if (change < 0) {
			System.out.println("card declined");
		} else {
			System.out.println("here is your change " + change);
		}
	}

	/*
	 * public boolean confirmPayment(Bus bus, double amount) { //implementation of
	 * the confirmPayment method if (bus.getBusTicketPrice() )
	 * 
	 * return false; }
	 */
	public void reserveSeat(Customer customer, int seatNumber, Bus bus, double payment) {
		// implementaion
		LocalDate today = LocalDate.now();
		LocalTime time = LocalTime.now();
		this.processPayment(bus, payment);
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);

			String sql = "INSERT INTO reservation(customer_id, seat," + " bus_id, date, time) VALUES (?, ?, ?, ?, ?)";

			statement = connection.prepareStatement(sql);
			statement.setInt(1, customer.getCustomerId());
			statement.setInt(2, seatNumber);
			statement.setInt(3, bus.getBusId());
			statement.setDate(4, Date.valueOf(today));
			statement.setTime(5, Time.valueOf(time));
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException exc) {
				exc.printStackTrace();
			}
		}

	}

	public void cancelReservation(int reservationId) {
		// implementation of the cancelReservation
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);
			String query = "DELETE FROM reservation WHERE reservation_id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, reservationId);
			statement.executeUpdate();

			connection.commit();
			// refine it
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// new method

	public boolean isFullyBooked(int busId, LocalDate date, int numberOfSeat) {
		Connection connection = null;
		PreparedStatement statement = null;
		int bookedSeats = 0;
		int totalSeats = 0;
		boolean fullyBooked = false;
		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, username, password);

			// Get the number of booked seats for the specified bus on the specified date
			String bookedSeatsSql = "SELECT COUNT(*) FROM reservation WHERE bus_id = ?"; /* AND date = ?"; */
			statement = connection.prepareStatement(bookedSeatsSql);
			statement.setInt(1, busId);
//            statement.setDate(2, Date.valueOf(date));
			ResultSet bookedSeatsResult = statement.executeQuery();
			if (bookedSeatsResult.next()) {
				bookedSeats = bookedSeatsResult.getInt(1);
			}

			if (bookedSeats >= numberOfSeat) {
				fullyBooked = true;
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fullyBooked;
	}

	// new method
	public ArrayList<Bus> showAvailableBus(LocalDate date, String source, String destination) {
		// it works but and tested
		// what about the seat ?
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement routeRecover = null;
		ArrayList<Bus> buses = new ArrayList<Bus>();
		try {
			BusDriver driver = new BusDriver();
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String username = "customer";
			String password = "Customer123$";

			connection = DriverManager.getConnection(url, username, password);

			String busSql = "SELECT *" + " FROM bus " + " JOIN route ON bus.route_id = route.route_id "
					+ " WHERE bus.date>=? and route.source = ? and route.destination=?";
//          "SELECT * FROM bus WHERE driver_id IS NOT "
//          + "NULL AND (date > ? AND source = ? AND destination = ?)";
			statement = connection.prepareStatement(busSql);
			statement.setDate(1, Date.valueOf(date));
			statement.setString(2, source);
			statement.setString(3, destination);

			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				Bus bus = new Bus();
				bus.setBusId(resultset.getInt("bus_id"));
				int driverId = resultset.getInt("driver_id");
				Administrator admin = new Administrator();
				driver = admin.searchAssignedBus(bus.getBusId());
//      driver.setAssignedBus(bus);
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
//            driver is not added 
//      bus.setDriver(resultset.getDrivere("bus_driver.first_name"));
				bus.setDate(resultset.getDate("date").toLocalDate());
				bus.setDepartureTime(resultset.getTime("departure_time").toLocalTime());
				bus.setArrivalTime(resultset.getTime("arrival_time").toLocalTime());
				bus.setBusTicketPrice(resultset.getDouble("bus_ticket_price"));
				bus.setnumberOfSeats(resultset.getInt("number_of_seats"));

				if (!this.isFullyBooked(bus.getBusId(), date, bus.getNumberOfSeats())) {
					buses.add(bus);
				}
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

		return buses;
	}

}