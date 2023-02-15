package com.busreservationsystem.Exception;

public class InvalidTimeFormatException extends Exception {
	  public InvalidTimeFormatException() {
	    super("Invalid time format. Time should be in HH:mm:ss format.");
	  }
}
