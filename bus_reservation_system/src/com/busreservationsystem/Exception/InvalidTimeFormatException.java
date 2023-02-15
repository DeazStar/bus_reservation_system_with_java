package com.busreservationsystem.Exception;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InvalidTimeFormatException extends Exception {
	  public InvalidTimeFormatException() {
		  
		  super("Invalid time format. Time should be in HH:mm:ss format.");
		  
		  Alert alert = new Alert(AlertType.ERROR);
		    alert.setContentText("Invalid time format. Time should be in HH:mm:ss format.");
			alert.showAndWait();

			
	  }
}
