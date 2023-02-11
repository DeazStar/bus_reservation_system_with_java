package application;

import com.busreservationsystem.base.Address;
import com.busreservationsystem.controller.Administrator;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.model.Route;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class DriverController implements Initializable {
    
    @FXML
    private Button AddDriverId;

    @FXML
    private TextField CityId;

    @FXML
    private DatePicker DateId;

    @FXML
    private TextField EmailId;

    @FXML
    private TextField FirstNameId;

    @FXML
    private TextField LastNameId;

    @FXML
    private TextField PhoneNumberId;

    @FXML
    private TextField RegionId;

    @FXML
    private TextField StreetId;

    @FXML
    private void add( ) {
        BusDriver driver = new BusDriver();
        Address address = new Address();
        
        address.setStreetAddress(StreetId.getText());
        address.setCity(CityId.getText());
        address.setRegion(RegionId.getText());
        
        driver.setFirstName(FirstNameId.getText());
        driver.setLastName(LastNameId.getText());
        driver.setDateOfBirth(DateId.getValue());
        driver.setEmail(EmailId.getText());
        driver.setPhoneNumber(PhoneNumberId.getText());
        driver.setAssignedBus(null);
        driver.setAddress(address);
        Administrator admin = new Administrator();
        admin.addDriver(driver);
        admin.saveDriver();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Driver added Succesfuly");
		alert.showAndWait();
    }
	private void refreshTable() {
		//create an observablelist to store data
	/*	ObservableList<BusDriver> data = FXCollections.observableArrayList();
		
		// fetch data from database and add it to the observable list
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            String url = "jdbc:mysql://localhost:3306/busreservation_db";
            String databaseUsername = "customer";
            String password = "Customer123$";
            
            connection = DriverManager.getConnection(url, databaseUsername, password);
            String sql = "SELECT `bus_driver`.`bus_driver_id`, `bus_driver`.`first_name`, "
                    + "`bus_driver`.`last_name`, `bus_driver`.`date_of_birth`, `bus_driver`.`email`, "
                    + "`bus_driver`.`phone_number` FROM `bus_driver` "
                    + "LEFT JOIN `address` ON `bus_driver`.`address_id` = `address`.`address_id` ";
            
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	int id = resultSet.getInt("bus_driver_id");
            	String firstName = resultSet.getString("first_name");
            	String lastName = resultSet.getString("last_name");
            	Date sqlDate = resultSet.getDate("date_of_birth");
            	LocalDate date = sqlDate.toLocalDate();
            	String email = resultSet.getString("email");
            	String phoneNumber = resultSet.getString("phone_number");
            	String streetAddress = resultSet.getString("address.street_address");
            	String city = resultSet.getString("address.city");
            	String region = resultSet.getString("address.region");
            	
            	Address address = new Address(streetAddress, city, region);
            	BusDriver driver = new BusDriver(driver, route, date, departureTime, arrivalTime, busTicketPrice, 
            			numberOfSeats);
            	bus.setBusId(id);
            	
            	data.add(bus);
            	
            }
            
        } catch(SQLException ex) {
        	ex.printStackTrace();
        }
        
        tableid.setItems(data);*/
		
		// add gender and try it again 
	}
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        loadData();
    }    
}
