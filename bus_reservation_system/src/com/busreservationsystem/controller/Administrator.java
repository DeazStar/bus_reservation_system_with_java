package com.busreservationsystem.controller;

import com.busreservationsystem.model.*;
import java.sql.*;
public class Administrator {
	public void addBus(Bus bus){
		String url = "jdbc:mysql://localhost:3306/busreservation_db";
		String user = "admin";
		String password = "Admin123$";

		try {

			// get a connection to databse 

			Connection connection = DriverManager.getConnection(url, user, password);

			// 2. create a statment 

			Statement statement = connection.createStatement();

			// execute sql query 

			String sql = "insert into bus(number_of_seats) " + "values ("+bus.getnumberOfSeats()+ ")";


			statement.executeUpdate(sql);

			System.out.println("Insertion complete");
		}catch (Exception exec){
			exec.printStackTrace();
		}
	}

	public void editBus(Bus bus){
		//implementation of eidtBus method
	}

	public void removeBus(Bus bus){
		//implementation of removeBus mehtod
	}

	public Bus searchBus(String busId){
		//implementation of searchBus method

		return null;
	}

	public void showSeats(Bus bus){
		//implementation of showSeats method
	}

	public void addDriver(Driver driver){
		// refine
	}

	public void editDriverInfo(Driver driver){
		//implementaion of editDriverInfo method
	}

	public void assignDriverToBus(Bus bus, Driver driver){
		//implementation of assignDriverToBus
	}
}
