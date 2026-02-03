package com.foodrelief.bean;

import java.sql.Timestamp;

public class Donation {

	private String donationID;
	private String donorName;
	private String donorPhone;
	private String donorAddress;
	private String cityArea;
	private String foodType;
	private double approxQuantityKg;
	private Timestamp preparedTime;
	private Timestamp expiryCutoffTime;
	private String preferredPickupSlot;
	private String donationStatus;
	private String remarks;

	public String getDonationID() {
		return donationID;
	}

	public void setDonationID(String donationID) {
		this.donationID = donationID;
	}

	public String getDonorName() {
		return donorName;
	}

	public void setDonorName(String donorName) {
		this.donorName = donorName;
	}

	public String getDonorPhone() {
		return donorPhone;
	}

	public void setDonorPhone(String donorPhone) {
		this.donorPhone = donorPhone;
	}

	public String getDonorAddress() {
		return donorAddress;
	}

	public void setDonorAddress(String donorAddress) {
		this.donorAddress = donorAddress;
	}

	public String getCityArea() {
		return cityArea;
	}

	public void setCityArea(String cityArea) {
		this.cityArea = cityArea;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public double getApproxQuantityKg() {
		return approxQuantityKg;
	}

	public void setApproxQuantityKg(double approxQuantityKg) {
		this.approxQuantityKg = approxQuantityKg;
	}

	public Timestamp getPreparedTime() {
		return preparedTime;
	}

	public void setPreparedTime(Timestamp preparedTime) {
		this.preparedTime = preparedTime;
	}

	public Timestamp getExpiryCutoffTime() {
		return expiryCutoffTime;
	}

	public void setExpiryCutoffTime(Timestamp expiryCutoffTime) {
		this.expiryCutoffTime = expiryCutoffTime;
	}

	public String getPreferredPickupSlot() {
		return preferredPickupSlot;
	}

	public void setPreferredPickupSlot(String preferredPickupSlot) {
		this.preferredPickupSlot = preferredPickupSlot;
	}

	public String getDonationStatus() {
		return donationStatus;
	}

	public void setDonationStatus(String donationStatus) {
		this.donationStatus = donationStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
