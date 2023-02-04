package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.busreservationsystem.base.Address;
import com.busreservationsystem.base.Person;
import com.busreservationsystem.controller.Administrator;
import com.busreservationsystem.user.Customer;
import com.busreservationsystem.user.CustomerManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
//import javafx.scene.control.Text;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private TextField city;

    @FXML
    private DatePicker dateofbirth;

    @FXML
    private RadioButton female;

    @FXML
    private TextField firstname;

    @FXML
    private ToggleGroup gender;

    @FXML
    private TextField lastid;
    
    @FXML
    private TextField email;

    @FXML
    private RadioButton male;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phoneid;

    @FXML
    private TextField region;

    @FXML
    private TextField street;

    //@FXML
   // private Text successid;

    @FXML
    private TextField username;
  
	
	private Stage stage;
	private Scene scene;
	private Parent root;


	
	
    @FXML
    void tologin(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {

   
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));	
		root = loader.load();
		
		LoginController scene1Controller = loader.getController();
		scene1Controller.login(event);
		
		//root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
    }
    @FXML
    void signup(ActionEvent event) throws ClassNotFoundException, SQLException {
    	Customer customer = new Customer();
    	customer.setFirstName(firstname.getText());
    	customer.setLastName(lastid.getText());
    	
    	String genderValue = "";
    	ToggleButton selected = (ToggleButton) gender.getSelectedToggle();
    	if (selected.getText().equals("male")) {
    	    genderValue = "M";
    	} else if (selected.getText().equals("female")) {
    	    genderValue = "F";
    	}
    	
    	customer.setGender(genderValue);
    	customer.setDateOfBirth(dateofbirth.getValue());
    	customer.setEmail(email.getText());
    	customer.setPhoneNumber(phoneid.getText());
    	
    	Address address = new Address();
    	address.setStreetAddress(street.getText());
    	address.setCity(city.getText());
    	address.setRegion(region.getText());
    	
    	customer.setAddress(address);
    	
    	CustomerManagment managment = new CustomerManagment();
    	
    	managment.signup(customer, username.getText(), password.getText());
    	
    	managment.store();
    	
    	
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setContentText("Are you sure ?");
         Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == ButtonType.OK) {

             try {
                 Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
                 Scene scene = new Scene(parent);
                 Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                 window.setScene(scene);
                 window.show();

             } catch (IOException e) {
                 e.printStackTrace();
             }

         }
    }
	
}