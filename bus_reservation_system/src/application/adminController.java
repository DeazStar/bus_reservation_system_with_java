package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

import com.busreservationsystem.model.*;
import com.busreservationsystem.model.Route;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.controller.Administrator;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class adminController implements Initializable {

	@FXML
	private TableColumn<Bus, Time> arrtimeid;

	@FXML
	private TableColumn<Bus, Integer> busnoid;

	@FXML
	private TableColumn<Bus, Time> deptimeid;
	@FXML
	private TableColumn<Bus, Integer> destinationid;

	@FXML
	private TableColumn<BusDriver, Integer> driverid;

	@FXML
	private TableColumn<BusDriver, String> sourceid;

	@FXML
	private TableColumn<Bus, Integer> totalseatsid;

	@FXML
	private TableColumn<Bus, Integer> priceid;

	@FXML
	private DatePicker dateId;
	@FXML
	private TextField arrivaltime_TextField;

	@FXML
	private TextField deptime_TextField;

	@FXML
	private Button addbusid;

	@FXML
	private TextField destination_TextField;

	@FXML
	private TextField driver_TextField;

	@FXML
	private TextField source_TextField;

	@FXML
	private TableView<Bus> tableid;

	@FXML
	private TextField totald_TextField;
	@FXML
	private TextField price_TextField;
	
	@FXML
	private void add() {
		Bus bus = new Bus();
		Route route = new Route();

		// add data
		route.setSource(source_TextField.getText());
		route.setDestination(destination_TextField.getText());

		// add data to bus
		bus.setDriver(null);
		bus.setRoute(route);
		bus.setDate(dateId.getValue());
		bus.setDepartureTime(LocalTime.parse(deptime_TextField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")));
		bus.setArrivalTime(LocalTime.parse(arrivaltime_TextField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")));
		bus.setBusTicketPrice(Double.parseDouble(price_TextField.getText()));
		bus.setnumberOfSeats(Integer.parseInt(totald_TextField.getText()));

		Administrator admin = new Administrator();

		admin.addBus(bus);
		admin.store();
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Bus added Succesfuly");
		alert.showAndWait();
		

	}
	
	

	@FXML
	private void refreshTable() {
		/*
		 * try { BusList.clear();
		 * 
		 * query = "SELECT * FROM `bus`"; preparedStatement =
		 * connection.prepareStatement(query); resultSet =
		 * preparedStatement.executeQuery();
		 * 
		 * while (resultSet.next()){ BusList.add(new
		 * Bus(resultSet.getTime("departure_time"), resultSet.getTime("arrival_time"),
		 * resultSet.getInt("busTicketPrice"), resultSet.getInt("arrivalTime")));
		 * tableid.setItems(BusList);
		 * 
		 * }
		 * 
		 * 
		 * } catch (SQLException ex) { }
		 * 
		 * 
		 */
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		loadData();
	}

	public void loadData() {
		/* connection = DBhandler.getConnection(); */
		refreshTable();

		busnoid.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("busId"));
		driverid.setCellValueFactory(new PropertyValueFactory<BusDriver, Integer>("driver"));
		priceid.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("busTicketPrice"));
		deptimeid.setCellValueFactory(new PropertyValueFactory<Bus, Time>("departureTime"));
		arrtimeid.setCellValueFactory(new PropertyValueFactory<Bus, Time>("arrivalTime"));
		// sourceid.setCellValueFactory(new PropertyValueFactory<users,String>("type"));

	}
}
