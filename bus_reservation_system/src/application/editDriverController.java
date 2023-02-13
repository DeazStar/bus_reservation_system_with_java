package application;

import com.busreservationsystem.base.Address;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
public class editDriverController implements Initializable{

    @FXML
    private TextField cityId;

    @FXML
    private DatePicker dateId;

    @FXML
    private TextField emailId;

    @FXML
    private RadioButton female;

    @FXML
    private TextField firstNameId;

    @FXML
    private ToggleGroup gender;

    @FXML
    private TextField lastNameId;

    @FXML
    private RadioButton male;

    @FXML
    private TextField phoneId;

    @FXML
    private TextField regionId;

    @FXML
    private Button saveId;

    @FXML
    private TextField streetId;
    @FXML
    
    
    public BusDriver getUpdateDriver(){
        BusDriver driver = new BusDriver();
        Address address = new Address();
        
        address.setStreetAddress(streetId.getText());
        address.setCity(cityId.getText());
        address.setRegion(regionId.getText());
        
        String genderValue = "";
        ToggleButton selected = (ToggleButton) gender.getSelectedToggle();
        if (selected.getText().equals("male")){
            genderValue = "M";
        }
        else if (selected.getText().equals("female")){
            genderValue = "F";
        }
                
        driver.setGender(genderValue);	
        driver.setFirstName(firstNameId.getText());
        driver.setLastName(lastNameId.getText());
        driver.setDateOfBirth(dateId.getValue());
        driver.setEmail(emailId.getText());
        driver.setPhoneNumber(phoneId.getText());
        driver.setAssignedBus(null);
        driver.setAddress(address);
        return driver;
    }
    
    @FXML
    void handleSaveButtonHandle(ActionEvent event) {
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
    
    public void setResult(BusDriver driver){
        this.result = driver;
    }
    public BusDriver getResult(){
        return result;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 // Perform initialization here
    }

    
}
