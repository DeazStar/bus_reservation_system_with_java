package application;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.busreservationsystem.controller.Administrator;
import com.busreservationsystem.user.CustomerManagment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.busreservationsystem.user.Customer;
import com.busreservationsystem.user.CustomerManagment;

public class LoginController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private Button cancelid;

	@FXML
	private Label labelid;

	@FXML
	private Button loginid;

	@FXML
	private TextField nameid;

	@FXML
	private PasswordField passid;

	@FXML
	private Button signupid;

	@FXML
	private Button back;

	public void cancel(ActionEvent e) {
		Stage stage = (Stage) cancelid.getScene().getWindow();
		stage.close();
	}
<<<<<<< HEAD
	
	public void login(ActionEvent event) throws SQLException, IOException {
		
		if (nameid.getText().isBlank() == true || passid.getText().isBlank() == true)
		{
=======

	public void login(ActionEvent event) throws SQLException {

		if (nameid.getText().isBlank() == true || passid.getText().isBlank() == true) {
>>>>>>> 37e5222af0dab90cae0079ade93c1f60b4e39c72
			labelid.setText("Please enter username and password");
			return;
		}

		while (StaticCustomer.customer == null) {
			if (nameid.getText().isBlank() == false && passid.getText().isBlank() == false) {
				CustomerManagment cmg = new CustomerManagment();
				StaticCustomer.customer = cmg.login(nameid.getText(), passid.getText());
<<<<<<< HEAD
					
				
				    		  Parent root = FXMLLoader.load(getClass().getResource("reservation.fxml"));
				    		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				    		  scene = new Scene(root);
				    		  stage.setScene(scene);
				    		  stage.show();
				    	
				    		 break;
=======
			} else {
				labelid.setText("Please enter username and password");
>>>>>>> 37e5222af0dab90cae0079ade93c1f60b4e39c72
			}

			if (StaticCustomer.customer == null) {
				labelid.setText("Please enter correct username and password");
			}
		}

		try {
			root = FXMLLoader.load(getClass().getResource("reserveORview.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	// @SuppressWarnings("null")
	// @FXML
	/*
	 * public void loginverify(ActionEvent event) throws SQLException, IOException {
	 * // Check if the login is valid // ...
	 * 
	 * // Set the customer object and close the window CustomerManagment cmg = new
	 * CustomerManagment(); Customer customer = cmg.login(nameid.getText(),
	 * passid.getText());
	 * 
	 * if (customer != null) { root =
	 * FXMLLoader.load(getClass().getResource("reservation.fxml")); stage =
	 * (Stage)((Node)event.getSource()).getScene().getWindow(); scene = new
	 * Scene(root); stage.setScene(scene); stage.show(); }
	 * 
	 * 
	 * 
	 * }
	 */

	@FXML
	public void tosignup(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("signup.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void toReservation(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("reservation.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void toadmin(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void ToWelcome(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}