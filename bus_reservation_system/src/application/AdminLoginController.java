package application;

import java.io.IOException;
import java.sql.SQLException;

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

public class AdminLoginController 
{
	
		private Stage stage;
		private Scene scene;
		private Parent root;

	    @FXML
	    private Button back;

	    @FXML
	    private Label labelid;

	    @FXML
	    private Button loginid;

	    @FXML
	    private TextField nameid;

	    @FXML
	    private PasswordField passid;

	   
	    @FXML
	    void login(ActionEvent event) 
	    {
	    	if (nameid.getText().isBlank() == false && passid.getText().isBlank() == false)
			{
				try {
					if (nameid.getText().equals("admin") && passid.getText().equals("123"))
					{
						
							  Parent root = FXMLLoader.load(getClass().getResource("addORview.fxml"));
							  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
							  scene = new Scene(root);
							  stage.setScene(scene);
							  stage.show();
					}
					else 
					{
						labelid.setText("invalid login");
					}
					
									
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
			{
				labelid.setText("Please enter username and password");
			}
	    }
     @FXML	
	   public void ToWelcome(ActionEvent event) throws IOException {
	    		  Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
	    		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    		  scene = new Scene(root);
	    		  stage.setScene(scene);
	    		  stage.show();
	    	
	    }
}
	   
