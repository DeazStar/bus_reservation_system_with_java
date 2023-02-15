package com.busreservationsystem.Exception;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InvalidDateFormatException extends Exception {
	  public InvalidDateFormatException() {
		  
		  super("Invalid date format");
		  
			

			  Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Invalid Date");
	            alert.setHeaderText("The date you entered is not valid.");
	            alert.setContentText("Please enter a valid date in the format: " + datePicker.getPromptText());
	            alert.showAndWait();
			
	  }
}
