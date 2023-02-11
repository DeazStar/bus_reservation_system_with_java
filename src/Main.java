
import com.busreservationsystem.controller.Administrator;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.model.Route;
import com.busreservationsystem.base.Address;
import com.busreservationsystem.controller.Reservation;
import com.busreservationsystem.user.Customer;
import com.busreservationsystem.user.CustomerManagment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.*;

public class Main {

    public static void main(String[] args) {
       /* LocalDate birthdate = LocalDate.of(1995, 8, 12);
        Administrator admin = new Administrator();
        Address address = new Address("koyeFece", "Addis Ababa", "Addis Ababa");
        BusDriver driver = new BusDriver("Noad", "Ararsa", birthdate, "naodararsa7@gmail.com", "+251946612595",
                address, null);
        Route route = new Route("koye", "tulu dimtu");
        LocalDate date = LocalDate.now();
        LocalTime departureTIme = LocalTime.now();
        LocalTime arrivalTime = LocalTime.of(5, 55);
        Bus bus = new Bus(driver, route, date, departureTIme, arrivalTime, 12, 30);
        admin.addBus(bus);
        admin.store();*/

        // test sign up 
        
	/*LocalDate birthdate = LocalDate.of(1995, 8, 12);
        Address address = new Address("koyeFece", "Addis Ababa", "Addis Ababa");
        Customer customer = new Customer("Noad", "Ararsa", birthdate, "naodararsa7@gmail.com", "+251946612595", address);
        
        CustomerManagment user = new CustomerManagment();
        user.signup(customer, "deadstar", "password");
        user.store();*/
 /* test load() */
        /*CustomerManagment mangment = new CustomerManagment();
        
        Customer user = mangment.login("deadstar", "password");
        if (user == null)
        {
            System.out.println("access denied");
        }else {
            System.out.println(user.getFirstName());
        }
        Administrator admin = new Administrator();
        Bus bus = admin.searchBus(15);
        user.setReservation(2, bus, 11);*/
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
        /*Administrator admin = new Administrator();
        LocalDate birthdate = LocalDate.of(200, 8, 12);
        Address address = new Address("tuludimitu", "Addis Ababa", "Addis Ababa");
        BusDriver driver = new BusDriver("dead", "star", birthdate, "deadfromtheborn@gmail.com", "+251946612595",
                address, null);

        admin.addDriver(driver);
        admin.saveDriver();
*/
        /*Administrator admin = new  Administrator();
		if (admin.searchAssignedBus(8) == null) {
			System.out.println("no driver for this bus");
		} else {
			System.out.println(admin.searchAssignedBus(8).getAddress().getStreetAddress());
		}*/
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
 /*	LocalDate birthdate = LocalDate.of(1995, 8, 12);
		
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
        
       /* Bus bus = new Bus();
        Route route = new Route("Addis ababa", "koye fece");
        bus.setRoute(route);
        bus.setDate(LocalDate.now());
        bus.setDepartureTime(LocalTime.now());
        bus.setArrivalTime(LocalTime.now());
        bus.setBusTicketPrice(10.00);
        bus.setnumberOfSeats(35);
        Administrator admin = new Administrator();
        admin.addBus(bus);
        admin.store();*/
        /*Administrator admin = new Administrator();
        Bus bus = admin.searchBus(15);
        /*BusDriver driver = admin.searchDriver(8);
        admin.assignDriverToBus(bus, driver);*/
       
	/*Reservation reserve = new Reservation();
        LocalDate date = LocalDate.of(2023, 01, 20);
        LocalTime time = LocalTime.now();
        ArrayList<Bus> buses = reserve.showAvailableBus(date, time);
       if (buses.size() > 0) {
    System.out.println(buses.get(0));
} else {
    System.out.println("The ArrayList is empty");
}

        for (int i = 0; i < buses.size(); i++){
           System.out.println( buses.get(i));
        }
        
        //Administrator admin = new Administrator();
        //System.out.println(driver.getDriverId());
       // Bus bus = admin.searchBus(14);
        
        //System.out.println(bus.getBusId());
        //admin.assignDriverToBus(bus, driver);
      /*  LocalDate birthdate = LocalDate.of(200, 8, 12);
        Address address = new Address("tuludimitu", "Addis Ababa", "Addis Ababa");
        BusDriver newDriver = new BusDriver("Robera", "Ararsa", 
                birthdate, "robera@gmail.com", "+251946612595",
                address, null);
        admin.editDriverInfo(driver, newDriver);*/
     // admin.removeBus(bus);*/
     
     Reservation reserve = new Reservation();
     reserve.cancelReservation(1);
    }
}
