package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.model.Route;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditBusController implements Initializable {

	@FXML
	private TextField arrivalTimeId;

	@FXML
	private DatePicker dateId;

	@FXML
	private TextField departureTimeId;

	@FXML
	private TextField destinationId;

	@FXML
	private TextField priceId;

	@FXML
	private Button saveButtonId;

	@FXML
	private TextField sourceId;

	@FXML
	private TextField totalSeatId;
	
	@FXML
	private ComboBox<Integer> assingnDriverId;
	
		
	private Bus bus;
	public void setBus() {
		this.bus = StaticCustomer.bus;

		System.out.println(bus.getBusId());
	}

	public Bus geteUpdateBus() {

		String source = sourceId.getText();
		String destination = destinationId.getText();
		int numberOfSeat = Integer.parseInt(totalSeatId.getText());
		LocalDate date = dateId.getValue();
		double price = Double.parseDouble(priceId.getText());
		LocalTime departureTime = LocalTime.parse(departureTimeId.getText(), DateTimeFormatter.ofPattern("HH:mm:ss"));
		LocalTime arrivalTime = LocalTime.parse(arrivalTimeId.getText(), DateTimeFormatter.ofPattern("HH:mm:ss"));

		Route route = new Route();
		route.setDestination(destination);
		route.setSource(source);

		BusDriver driver = new BusDriver();
		driver.setDriverId(assingnDriverId.getValue());

		Bus bus = new Bus(driver, route, date, departureTime, arrivalTime, price, numberOfSeat);

		return bus;

	}

	@FXML
	private void handleSaveButtonAction(ActionEvent event) {
		if (sourceId.getText().isBlank() == true || departureTimeId.getText().isBlank() == true
				|| departureTimeId.getText().isBlank() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please Enter Full Bus Information");
			alert.showAndWait();

			return;
		}

		// Get updated bus data from the form
		Bus updatedBus = geteUpdateBus();

		// Close the window
		Stage stage = (Stage) saveButtonId.getScene().getWindow();
		stage.close();

		// Set the result
		setResult(updatedBus);
	}

	private Bus result;

	public Bus getResult() {
		return result;
	}

	private void setResult(Bus bus) {
		result = bus;
	}

	public void initialize(URL url, ResourceBundle rb) {
		// Perform initialization here
		setBus();
		
		arrivalTimeId.setText((this.bus.getArrivalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
		dateId.setValue(this.bus.getDate());
		departureTimeId.setText((this.bus.getDepartureTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
		destinationId.setText(this.bus.getRoute().getDestination());
		sourceId.setText(this.bus.getRoute().getSource());
		priceId.setText(String.valueOf(this.bus.getBusTicketPrice()));
		totalSeatId.setText(String.valueOf(this.bus.getNumberOfSeats()));
		
		ObservableList<Bus> data = FXCollections.observableArrayList();

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			String dsurl = "jdbc:mysql://localhost:3306/busreservation_db";
			String databaseUsername = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(dsurl, databaseUsername, password);

			statement = connection.prepareStatement("select bus_driver_id from bus_driver");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				assingnDriverId.getItems().add(resultSet.getInt("bus_driver_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (this.bus.getDrivere() != null) {
			assingnDriverId.setValue(this.bus.getDrivere().getDriverId());	
		}

		
	}

}