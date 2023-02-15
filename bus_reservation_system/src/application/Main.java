package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class Main extends Application 
{
	@FXML
    private Button cancelid;



	 public static void main(String[] args) {
	        launch(args);
	    } 
	@Override
    public void start(Stage primaryStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("welcome.fxml"));
            Scene scene = new Scene(parent);
            
            primaryStage.setTitle("AFRI BUS");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) 
        {
        	ex.printStackTrace();
        }
    }
	
	private Stage stage;
	private Scene scene;
	private Parent root;


 @FXML
 public void toAdminlogin(ActionEvent event) throws IOException {
  root = FXMLLoader.load(getClass().getResource("adminlogin.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
 }
 @FXML
 public void toCustomerlogin(ActionEvent event) throws IOException {
	
  root = FXMLLoader.load(getClass().getResource("login.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
  
 
 }
 
 public void cancel(ActionEvent e)
	{
		Stage stage= (Stage) cancelid.getScene().getWindow();
		stage.close();
	}
    
}
