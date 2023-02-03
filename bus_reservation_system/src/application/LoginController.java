package application;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.busreservationsystem.controller.Administrator;
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

    
 		
	public void cancel(ActionEvent e)
	{
		Stage stage= (Stage) cancelid.getScene().getWindow();
		stage.close();
	}
	
	public void login(ActionEvent event) throws SQLException
	{
		if (nameid.getText().isBlank() == false && passid.getText().isBlank() == false)
		{
			loginverify(event);
		}
		else 
		{
			labelid.setText("Please enter username and password");
		}
}
	

	@SuppressWarnings("null")
	@FXML
	public void loginverify(ActionEvent event) throws SQLException {

}
		
//	public void start(Stage stage) {
//		try {
//			Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
//			Scene scene = new Scene(root);
//			stage.setScene(scene);
//			stage.show();
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}	

 @FXML
 public void tosignup(ActionEvent event) throws IOException {
  root = FXMLLoader.load(getClass().getResource("signup.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
 }
 
 public void toadmin(ActionEvent event) throws IOException {
  Parent root = FXMLLoader.load(getClass().getResource("admin1.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
 }
}
