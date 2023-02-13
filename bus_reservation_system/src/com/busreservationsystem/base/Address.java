package com.busreservationsystem.base;

public class Address {
	/*private int addressId;*/
	private String streetAddress;
	private String city;
	private String region;
	private int addressId;


	public Address(String streetAddress, String city, String region){
		/*this.addressId = addressId;*/
		this.streetAddress = streetAddress;
		this.city = city;
		this.region = region;
	}
	
	public Address() {}
	//getter and setter methods
	/*public int getAddressId(){
		return this.addressId;
	}*/
	
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
	public int getAddressId() {
		return this.addressId;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public String getStreetAddress() {
		return this.streetAddress;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getRegion() {
		return this.region;
	}
	
}
