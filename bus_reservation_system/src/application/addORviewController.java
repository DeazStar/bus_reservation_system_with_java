package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class addORviewController {
	private Stage stage;
	private Scene scene;

	@FXML
	public void toViewCustomer(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("viewCustomer.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void toAddBus(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void toAddDriver(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("driver.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void tologin(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
