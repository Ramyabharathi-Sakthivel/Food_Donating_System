package com.foodrelief.bean;


public class Trip {

	private int tripID;
	private String donationID;
	private String volunteerID;
	private String vehicleType;
	private double vehicleCapacityKg;
	private java.sql.Timestamp assignedTime;
	private java.sql.Timestamp pickupTime;
	private String deliveryLocation;
	private String deliveryNgoOrShelterName;
	private double deliveredQuantityKg;
	private String tripStatus;
	private String notes;
	

	public Trip() {
		// TODO Auto-generated constructor stub
	}

	public int getTripID() {
		return tripID;
	}

	public void setTripID(int tripID) {
		this.tripID = tripID;
	}

	public String getDonationID() {
		return donationID;
	}

	public void setDonationID(String donationID) {
		this.donationID = donationID;
	}

	public String getVolunteerID() {
		return volunteerID;
	}

	public void setVolunteerID(String volunteerID) {
		this.volunteerID = volunteerID;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public double getVehicleCapacityKg() {
		return vehicleCapacityKg;
	}

	public void setVehicleCapacityKg(double vehicleCapacityKg) {
		this.vehicleCapacityKg = vehicleCapacityKg;
	}

	public java.sql.Timestamp getAssignedTime() {
		return assignedTime;
	}

	public void setAssignedTime(java.sql.Timestamp assignedTime) {
		this.assignedTime = assignedTime;
	}

	public java.sql.Timestamp getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(java.sql.Timestamp pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getDeliveryLocation() {
		return deliveryLocation;
	}

	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}

	public String getDeliveryNgoOrShelterName() {
		return deliveryNgoOrShelterName;
	}

	public void setDeliveryNgoOrShelterName(String deliveryNgoOrShelterName) {
		this.deliveryNgoOrShelterName = deliveryNgoOrShelterName;
	}

	public double getDeliveredQuantityKg() {
		return deliveredQuantityKg;
	}

	public void setDeliveredQuantityKg(double deliveredQuantityKg) {
		this.deliveredQuantityKg = deliveredQuantityKg;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
