
import com.busreservationsystem.controller.Administrator;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.model.Route;
import com.busreservationsystem.base.Address;
import com.busreservationsystem.user.Customer;
import com.busreservationsystem.user.CustomerManagment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
 /*       LocalDate birthdate = LocalDate.of(1995, 8, 12);
        Administrator admin = new  Administrator();
        Address address = new Address("koyeFece", "Addis Ababa", "Addis Ababa");
        BusDriver driver = new BusDriver("Noad", "Ararsa", birthdate, "naodararsa7@gmail.com", "+251946612595", 
        address, null, 1);
        Route route = new Route(1, "koye", "tulu dimtu");
        LocalTime departureTIme = LocalTime.now();
        LocalTime arrivalTime = LocalTime.of(5, 55);
        Bus bus = new Bus(driver, route, departureTIme, arrivalTime, 12, 30);
        admin.addBus(bus);*/
		
		// test sign up 
		
		/*
		LocalDate birthdate = LocalDate.of(1995, 8, 12);
        Address address = new Address("koyeFece", "Addis Ababa", "Addis Ababa");
        Customer customer = new Customer("Noad", "Ararsa", birthdate, "naodararsa7@gmail.com", "+251946612595", address);
        
        CustomerManagment user = new CustomerManagment();
        user.signup(customer, "deadstar", "password");
        user.store();*/
		
		/* test load() */
        CustomerManagment user = new CustomerManagment();
		ArrayList <String>userInfo = user.load("deadstar");
		
		for (int i = 0; i < userInfo.size(); i++) {
			System.out.println(userInfo.get(i));
		}
		
		user.login("deadstar", "password");
		
	}
}
