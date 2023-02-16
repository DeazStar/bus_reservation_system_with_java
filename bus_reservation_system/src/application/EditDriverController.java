package application;

import com.busreservationsystem.base.Address;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

public class EditDriverController implements Initializable {

	@FXML
	private TextField cityId; // done

	@FXML
	private DatePicker dateId;

	@FXML
	private TextField emailId; // done

	@FXML
	private RadioButton female; // doen

	@FXML
	private TextField firstNameId; // done

	@FXML
	private ToggleGroup gender; // done

	@FXML
	private TextField lastNameId; // done

	@FXML
	private RadioButton male; // done

	@FXML
	private TextField phoneId; // done

	@FXML
	private TextField regionId; // done

	@FXML
	private Button saveId;

	@FXML
	private TextField streetId; // done
	@FXML

	private BusDriver driver;

	public void setDriver() {
		this.driver = StaticCustomer.driver;

		System.out.println(driver.getFirstName());
	}

	public BusDriver getUpdateDriver() {
		BusDriver updatedDriver = new BusDriver();
		Address address = new Address();

		address.setStreetAddress(streetId.getText());
		address.setCity(cityId.getText());
		address.setRegion(regionId.getText());

		String genderValue = "";
		ToggleButton selected = (ToggleButton) gender.getSelectedToggle();
		if (selected.getText().equals("male")) {
			genderValue = "M";
		} else if (selected.getText().equals("female")) {
			genderValue = "F";
		}

		updatedDriver.setGender(genderValue);
		updatedDriver.setFirstName(firstNameId.getText());
		updatedDriver.setLastName(lastNameId.getText());
		updatedDriver.setDateOfBirth(dateId.getValue());
		updatedDriver.setEmail(emailId.getText());
		updatedDriver.setPhoneNumber(phoneId.getText());
		updatedDriver.setAssignedBus(null);
		updatedDriver.setAddress(address);
		return updatedDriver;
	}

	@FXML
	void handleSaveButtonHandle(ActionEvent event) {

		if (firstNameId.getText().isBlank() == true || lastNameId.getText().isBlank() == true
				|| streetId.getText().isBlank() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please Enter Full Bus Information");
			alert.showAndWait();
			return;
		}

		BusDriver updateDriver = getUpdateDriver();
		Stage stage = (Stage) saveId.getScene().getWindow();
		stage.close();

		// Set the result
		setResult(updateDriver);

	}

//    @FXML
//    private void handleSaveButtonAction(ActionEvent event) {
//    }

	private BusDriver result;

	public void setResult(BusDriver driver) {
		this.result = driver;
	}

	public BusDriver getResult() {
		return result;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// Perform initialization here
		setDriver();

		firstNameId.setText(this.driver.getFirstName());
		lastNameId.setText(this.driver.getLastName());
		emailId.setText(this.driver.getEmail());
		cityId.setText(this.driver.getAddress().getCity());
		phoneId.setText(this.driver.getPhoneNumber());
		streetId.setText(this.driver.getAddress().getStreetAddress());
		regionId.setText(this.driver.getAddress().getRegion());
		if (this.driver.getGender() == "M") {
			male.setSelected(true);
		} else {
			female.setSelected(true);
		}

		dateId.setValue(this.driver.getDateOfBirth());

	}

}