package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.model.Route;

import java.net.URL;
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

		BusDriver driver = null;

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
	}

}