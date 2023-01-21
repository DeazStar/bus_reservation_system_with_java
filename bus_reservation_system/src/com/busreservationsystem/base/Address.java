package com.busreservationsystem.base;

public class Address {
	private int addressId;
	private String streetAddress;
	private String city;
	private String region;


	public Address(int addressId, String streetAddress, String city, String region){
		this.addressId = addressId;
		this.streetAddress = streetAddress;
		this.city = city;
		this.region = region;
	}

	//getter and setter methods
	public int getAddressId(){
		return this.addressId;
	}
}
