/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

import com.busreservationsystem.base.Address;
import java.lang.*;
import com.busreservationsystem.model.Bus;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.busreservationsystem.model.*;
import com.busreservationsystem.model.Route;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.controller.*;
import com.busreservationsystem.user.Customer;
import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author HP
 */

public class viewReservationController implements Initializable {
	@FXML
	private TableColumn<Reservation, Integer> busId;

	@FXML
	private TableColumn<Reservation, String> emailId;

	@FXML
	private TableColumn<Reservation, String> firstNameId;

	@FXML
	private TableColumn<Reservation, String> lastNameId;

	@FXML
	private TableColumn<Reservation, Integer> seatId;

	@FXML
	private TableColumn<Reservation, LocalDate> dateId;

	@FXML
	private TableColumn<Reservation, String> destinationId;
	@FXML
	private TableColumn<Reservation, String> souceId;

	@FXML
	private TableColumn<Reservation, LocalTime> timeId;

	@FXML
	private TableColumn<Reservation, LocalDate> date2Id;

	@FXML
	private TableView<Reservation> tableId;

	private int reservationId;

	private void refreshTable() {
		ObservableList<Reservation> data = FXCollections.observableArrayList();
//    
		// fetch data from database and add it to the observable list
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String databaseUsername = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, databaseUsername, password);
			String sql = "SELECT reservation.reservation_id, customer.first_name, customer.last_name, customer.email, reservation.bus_id, reservation.seat, \r\n"
					+ "       route.source, route.destination, bus.date, reservation.date, reservation.time\r\n"
					+ "FROM reservation\r\n" + "INNER JOIN bus ON bus.bus_id = reservation.bus_id\r\n"
					+ "INNER JOIN customer ON reservation.customer_id = customer.customer_id\r\n"
					+ "INNER JOIN route ON route.route_id = bus.route_id\r\n" + "WHERE reservation.customer_id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, StaticCustomer.customer.getCustomerId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				this.reservationId = resultSet.getInt("reservation.reservation_id");
				String firstName = resultSet.getString("customer.first_name");
				String lastName = resultSet.getString("customer.last_name");
				int busId = resultSet.getInt("bus_id");
				int seat = resultSet.getInt("reservation.seat");
				System.out.println(seat);
				Date sqlDate = resultSet.getDate("bus.date");
				LocalDate date = sqlDate.toLocalDate();
				Date sqlDate2 = resultSet.getDate("reservation.date");
				LocalDate date2 = sqlDate2.toLocalDate();
				Time sqlTime = resultSet.getTime("reservation.time");
				LocalTime time = sqlTime.toLocalTime();
				String email = resultSet.getString("customer.email");
				String source = resultSet.getString("route.source");
				String destination = resultSet.getString("route.destination");

				Route route = new Route(source, destination);
				Customer customer = new Customer();
				customer.setFirstName(firstName);
				customer.setLastName(lastName);
				customer.setEmail(email);
				Bus bus = new Bus();
				bus.setDate(date);
				bus.setRoute(route);
				bus.setBusId(busId);
				Reservation reserve = new Reservation(customer, bus);
				reserve.setSeatNumber(seat);
				System.out.println(reserve.getSeatNumber());
				reserve.setDate(date2);
				reserve.setTime(time);

				data.add(reserve);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		tableId.setItems(data);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Reservation reserve = new Reservation();
		TableColumn<Reservation, Void> actionCol = new TableColumn<>("Action");
		Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>> cellFactory = new Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>>() {
			public TableCell<Reservation, Void> call(final TableColumn<Reservation, Void> param) {
				final TableCell<Reservation, Void> cell = new TableCell<Reservation, Void>() {
					private final Button deleteButton = new Button("Cancel");

					{
						deleteButton.setOnAction((ActionEvent event) -> {

							StaticCustomer.customer.cancelBooking(reservationId);
							refreshTable();
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							HBox buttons = new HBox();
							buttons.getChildren().addAll(deleteButton);
							setGraphic(buttons);
						}
					}
				};
				return cell;
			}
		};

		actionCol.setCellFactory(cellFactory);// cpy
		tableId.getColumns().addAll(actionCol);// cpy

		souceId.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getBus().getRoute().getSource()));
		destinationId.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getBus().getRoute().getDestination()));
		seatId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSeatNumber()));
		firstNameId.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName()));
		lastNameId.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getLastName()));
		emailId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getEmail()));
		dateId.setCellValueFactory(
				cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getBus().getDate()));
		date2Id.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getDate()));
		timeId.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalTime>(cellData.getValue().getTime()));
		busId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBus().getBusId()));
		try {
			refreshTable();
		} catch (NullPointerException e) {
			Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("An error occurred while loading the table");
			alert.setContentText(
					"The table could not be loaded due to a null pointer exception. Please check the code for any errors and try again.");
			alert.showAndWait();
		}

	}

}