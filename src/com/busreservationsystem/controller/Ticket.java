package com.busreservationsystem.controller;


import com.busreservationsystem.model.Bus;
import java.time.LocalDate;
import java.time.LocalTime;

public class Ticket {
	private String ticketId;
	private Bus bus;
	private int seatNumber;
	private LocalDate date;
	private LocalTime time;

	public Ticket(String ticketId, Bus bus, int seatNumber, LocalDate date, LocalTime time){

		this.ticketId = ticketId; // edit this
		this.bus = bus;
		this.seatNumber = seatNumber;
		this.date = date;
		this.time = time;
	}
}
