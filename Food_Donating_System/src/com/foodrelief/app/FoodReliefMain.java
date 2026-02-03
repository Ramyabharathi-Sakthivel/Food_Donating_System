package com.foodrelief.app;

import com.foodrelief.bean.Donation;
import com.foodrelief.service.FoodReliefService;
import com.foodrelief.util.CapacityExceededException;
import com.foodrelief.util.DonationAlreadyAssignedException;
import com.foodrelief.util.ValidationException;

public class FoodReliefMain {
	private static FoodReliefService service = new FoodReliefService();

	public static void main(String[] args) {
		java.util.Scanner sc = new java.util.Scanner(System.in);
		System.out.println("--- Local Food Donation Coordination Console ---");
// DEMO 1: Register a Donation
		try {
			Donation d = new Donation();
			d.setDonationID("DN5007");
			d.setDonorName("Meenakshi Rao");
			d.setDonorPhone("9998887771");
			d.setDonorAddress("Flat 5C, Sunrise Apartments");

			d.setCityArea("Adyar");
			d.setFoodType("COOKED");
			d.setApproxQuantityKg(25.0);
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
			java.sql.Timestamp cutoff = new java.sql.Timestamp(System.currentTimeMillis() + 3L * 60 * 60 * 1000);
			d.setPreparedTime(now);
			d.setExpiryCutoffTime(cutoff);
			d.setPreferredPickupSlot("Within next 2 hours");
			d.setDonationStatus("OPEN");
			d.setRemarks("Vegetarian food in stainless steel containers");
			boolean ok = service.registerNewDonation(d);
			System.out.println(ok ? "DONATION REGISTERED" : "DONATION REGISTRATION FAILED");
		} catch (ValidationException e) {
			System.out.println("Validation Error: " + e.toString());
		} catch (Exception e) {
			System.out.println("System Error: " + e.getMessage());
		}
// DEMO 2: Create a Trip for the Donation
		try {
			java.sql.Timestamp assignedTime = new java.sql.Timestamp(System.currentTimeMillis());
			boolean ok = service.createTripForDonation("DN5007", "VL203", "TRUCK", 50.0, assignedTime,
					"Shelter Home B, Main Road", "Shelter Home B");
			System.out.println(ok ? "TRIP CREATED" : "TRIP CREATION FAILED");
		} catch (CapacityExceededException e) {
			System.out.println("Capacity Error: " + e.toString());
		} catch (DonationAlreadyAssignedException e) {
			System.out.println("Assignment Error: " + e.toString());
		} catch (ValidationException e) {
			System.out.println("Validation Error: " + e.toString());
		} catch (Exception e) {
			System.out.println("System Error: " + e.getMessage());
		}
		sc.close();
	}
}