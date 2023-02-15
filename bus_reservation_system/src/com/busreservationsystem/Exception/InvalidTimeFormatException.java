package com.busreservationsystem.Exception;

import javafx.scene.control.Alert;

public class InvalidTimeFormatException extends Exception {
	  public InvalidTimeFormatException() {
		  
		  super("Invalid time format. Time should be in HH:mm:ss format.");
		  
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setContentText("Invalid time format. Time should be in HH:mm:ss format.");
			alert.showAndWait();

			
	  }
}
