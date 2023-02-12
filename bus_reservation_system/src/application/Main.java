package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class Main extends Application 
{
	 
	@Override
    public void start(Stage primaryStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("reservation.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setTitle("SELAM BUS");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) 
        {
        	ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
