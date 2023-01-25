
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
		/*
        CustomerManagment user = new CustomerManagment();
		ArrayList <String>userInfo = user.load("deadstar");
		
		for (int i = 0; i < userInfo.size(); i++) {
			System.out.println(userInfo.get(i));
		}
		
		user.login("smt", "password");
		*/
		/*LocalDate birthdate = LocalDate.of(1995, 8, 12);
        Administrator admin = new  Administrator();
        Address address = new Address("koyeFece", "Addis Ababa", "Addis Ababa");
		BusDriver driver = new BusDriver("Noad", "Ararsa", birthdate, "naodararsa7@gmail.com", "+251946612595", 
		        address, null);
        Route route = new Route("koye", "tulu dimtu");
        LocalTime departureTIme = LocalTime.now();
        LocalTime arrivalTime = LocalTime.of(5, 55);
        Bus bus = new Bus(driver, route, departureTIme, arrivalTime, 12, 30);
        admin.addBus(bus);
        admin.addDriver(driver);
        admin.store();*/
		/*
		Administrator admin = new  Administrator();
		LocalDate birthdate = LocalDate.of(200, 8, 12);
		Address address = new Address("tuludimitu", "Addis Ababa", "Addis Ababa");
		BusDriver driver = new BusDriver("dead", "star", birthdate, "deadfromtheborn@gmail.com", "+251946612595", 
			        address, null);
		
		admin.addDriver(driver);
		admin.saveDriver();*/
		
		/*Administrator admin = new  Administrator();
		if (admin.searchAssignedBus(6) == null) {
			System.out.println("no driver for this bus");
		} else {
			System.out.println(admin.searchAssignedBus(6).getAddress().getStreetAddress());
		}
		/*System.out.println(admin.searchAssignedBus(0));
		System.out.println(drive.getAddress().getStreetAddress());*/
	
		
		/*Administrator admin = new  Administrator();
		System.out.println(admin.searchBus(6));
		if (admin.searchBus(0) == null) {
			System.out.println("no bus");
		} 
		else {
			Bus bus = admin.searchBus(0);
			System.out.println(bus.getBusId());
			
		}*/
		/*
		LocalDate birthdate = LocalDate.of(1995, 8, 12);
		
        Administrator admin = new  Administrator();
        Address address = new Address("koyeFece", "Addis Ababa", "Addis Ababa");
		BusDriver driver = new BusDriver("Noad", "Ararsa", birthdate, "naodararsa7@gmail.com", "+251946612595", 
		        address, null);
        Route route = new Route("koye", "tulu dimtu");
        LocalTime departureTIme = LocalTime.now();
        LocalTime arrivalTime = LocalTime.of(5, 55);
        Bus bus = new Bus(driver, route, departureTIme, arrivalTime, 12, 30);
        admin.addBus(bus);
        admin.addDriver(driver);
        admin.store();
        System.out.println(bus.getBusId());
        admin.saveDriver();
        System.out.println(driver.getDriverId());
        */
		
		// test edit bus
		
		
        
	}
}
