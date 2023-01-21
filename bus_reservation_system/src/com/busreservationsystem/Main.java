package com.busreservationsystem;

import com.busreservationsystem.base.Address;
import com.busreservationsystem.controller.Administrator;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.model.Route;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
	public static void main(String[] args) {
        LocalDate birthdate = LocalDate.of(1995, 8, 12);
        Administrator admin = new  Administrator();
        Address address = new Address(1, "koyeFece", "Addis Ababa", "Addis Ababa");
        BusDriver driver = new BusDriver("Noad", "Ararsa", birthdate, "naodararsa7@gmail.com", "+251946612595", 
        address, null, 1);
        Route route = new Route(1, "koye", "tulu dimtu");
        LocalTime departureTIme = LocalTime.now();
        LocalTime arrivalTime = LocalTime.of(5, 55);
        Bus bus = new Bus(driver, route, departureTIme, arrivalTime, 12.25, 30);
        admin.addBus(bus);
	}
}
