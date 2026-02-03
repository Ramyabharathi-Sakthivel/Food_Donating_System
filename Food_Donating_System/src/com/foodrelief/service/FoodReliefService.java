package com.foodrelief.service;

import java.sql.Connection;

import com.foodrelief.bean.Donation;
import com.foodrelief.bean.Trip;
import com.foodrelief.dao.DonationDAO;
import com.foodrelief.dao.TripDAO;
import com.foodrelief.util.CapacityExceededException;
import com.foodrelief.util.DBUtil;
import com.foodrelief.util.DonationAlreadyAssignedException;
import com.foodrelief.util.ValidationException;

public class FoodReliefService {

	public Donation viewDonationDetails(String donationID) throws ValidationException {

		if (donationID == null || donationID.trim().isEmpty()) {
			throw new ValidationException();
		}

		DonationDAO dao = new DonationDAO();

		return dao.findDonation(donationID);
	}

	public java.util.List<Donation> viewDonationsByStatusAndArea(String status, String cityArea) {

		DonationDAO dao = new DonationDAO();

		return dao.viewDonationsByStatusAndArea(status, cityArea);
	}

	public boolean registerNewDonation(Donation donation) throws ValidationException {

		if (donation == null)
			throw new ValidationException();

		if (donation.getDonationID() == null || donation.getDonationID().trim().isEmpty()
				|| donation.getDonorName() == null || donation.getDonorName().trim().isEmpty()
				|| donation.getDonorPhone() == null || donation.getDonorPhone().trim().isEmpty()
				|| donation.getDonorAddress() == null || donation.getDonorAddress().trim().isEmpty()
				|| donation.getCityArea() == null || donation.getCityArea().trim().isEmpty()
				|| donation.getFoodType() == null || donation.getFoodType().trim().isEmpty()) {

			throw new ValidationException();
		}

		if (donation.getApproxQuantityKg() <= 0.0)
			throw new ValidationException();

	
		if (donation.getPreparedTime() == null || donation.getExpiryCutoffTime() == null
				|| !donation.getExpiryCutoffTime().after(donation.getPreparedTime())) {

			throw new ValidationException();
		}
		String ft = donation.getFoodType().toUpperCase();
		if (!(ft.equals("COOKED") || ft.equals("PACKAGED") || ft.equals("GROCERY") || ft.equals("OTHER"))) {

			throw new ValidationException();
		}

		
		if (donation.getDonationStatus() == null || donation.getDonationStatus().trim().isEmpty()) {
			donation.setDonationStatus("OPEN");
		}

	
		DonationDAO dao = new DonationDAO();
		return dao.insertDonation(donation);
	}

	public java.util.List<Trip> listTripsByVolunteerAndDate(String volunteerID, java.sql.Date date)
			throws ValidationException {


		if (volunteerID == null || volunteerID.trim().isEmpty() || date == null) {
			throw new ValidationException();
		}

		TripDAO dao = new TripDAO();

		return dao.findTripsByVolunteerAndDate(volunteerID, date);
	}

	public java.util.List<Trip> listTripsByDate(java.sql.Date date) throws ValidationException {

		if (date == null) {
			throw new ValidationException();
		}

		TripDAO dao = new TripDAO();

		return dao.findTripsByDate(date);
	}

	public boolean createTripForDonation(String donationID, String volunteerID, String vehicleType,
			double vehicleCapacityKg, java.sql.Timestamp assignedTime, String deliveryLocation, String deliveryNgoName)
			throws ValidationException, DonationAlreadyAssignedException, CapacityExceededException {

		Connection con = null;

		try {


			if (donationID == null || donationID.isEmpty() || volunteerID == null || volunteerID.isEmpty()
					|| vehicleType == null || vehicleType.isEmpty() || deliveryLocation == null
					|| deliveryLocation.isEmpty() || deliveryNgoName == null || deliveryNgoName.isEmpty()
					|| assignedTime == null || vehicleCapacityKg <= 0) {

				throw new ValidationException();
			}

			DonationDAO dao = new DonationDAO();
			TripDAO tdao = new TripDAO();


			Donation donation = dao.findDonation(donationID);
			if (donation == null)
				return false;


			if (!donation.getDonationStatus().equals("OPEN"))
				throw new DonationAlreadyAssignedException();


			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

			if (now.after(donation.getExpiryCutoffTime()))
				throw new ValidationException();


			if (vehicleCapacityKg < donation.getApproxQuantityKg())
				throw new CapacityExceededException();

			con = DBUtil.getDBConnection();
			con.setAutoCommit(false);

			Trip trip = new Trip();
			trip.setTripID(tdao.generateTripID());
			trip.setDonationID(donationID);
			trip.setVolunteerID(volunteerID);
			trip.setVehicleType(vehicleType);
			trip.setVehicleCapacityKg(vehicleCapacityKg);
			trip.setAssignedTime(assignedTime);
			trip.setTripStatus("PLANNED");
			trip.setDeliveredQuantityKg(0.0);

			boolean tripInserted = tdao.insertTrip(trip);
			boolean donationUpdated = dao.updateDonationStatus(donationID, "ASSIGNED");

			if (tripInserted && donationUpdated) {
				con.commit(); // 9. Commit
				return true;
			} else {
				con.rollback();
				return false;
			}

		} catch (Exception e) {
			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
			}
			return false;
		} finally {
			try {
				if (con != null)
					con.setAutoCommit(true);
			} catch (Exception ex) {
			}
		}
	}

	public boolean updateTripOutcome(int tripID, String newStatus, double deliveredQuantityKg, String notes)
			throws ValidationException {

		Connection con = null;

		try {
			if (tripID <= 0 || newStatus == null || newStatus.isEmpty() || deliveredQuantityKg < 0) {
				throw new ValidationException();
			}

			if (!(newStatus.equals("PLANNED") || newStatus.equals("IN_PROGRESS") || newStatus.equals("COMPLETED")
					|| newStatus.equals("CANCELLED"))) {
				throw new ValidationException();
			}

			TripDAO tdao = new TripDAO();
			DonationDAO ddao = new DonationDAO();

			Trip trip = tdao.findTrip(tripID);
			if (trip == null)
				return false;

			Donation donation = ddao.findDonation(trip.getDonationID());
			if (donation == null)
				return false;

			con = DBUtil.getDBConnection();
			con.setAutoCommit(false);

			boolean tripUpdated = tdao.updateTripStatusAndOutcome(tripID, newStatus, deliveredQuantityKg, notes);

			if (!tripUpdated) {
				con.rollback();
				return false;
			}

			if (newStatus.equals("COMPLETED")) {

				if (deliveredQuantityKg > 0) {
					ddao.updateDonationStatusAndRemarks(donation.getDonationID(), "DELIVERED", notes);
				} else {
					ddao.updateDonationStatusAndRemarks(donation.getDonationID(), "DISCARDED", notes);
				}
			}

			if (newStatus.equals("CANCELLED")) {
				ddao.updateDonationStatusAndRemarks(donation.getDonationID(), "CANCELLED", notes);
			}

			con.commit();
			return true;

		} catch (Exception e) {
			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
			}
			return false;
		} finally {
			try {
				if (con != null)
					con.setAutoCommit(true);
			} catch (Exception ex) {
			}
		}
	}

	public boolean closeOrCancelDonation(String donationID, java.sql.Timestamp referenceTime, String closeReason)
			throws ValidationException, DonationAlreadyAssignedException {

		try {

			if (donationID == null || donationID.isEmpty())
				throw new ValidationException();

			DonationDAO ddao = new DonationDAO();
			TripDAO tdao = new TripDAO();

			Donation donation = ddao.findDonation(donationID);
			if (donation == null)
				return false;

			if (donation.getDonationStatus().equals("DELIVERED") || donation.getDonationStatus().equals("DISCARDED")) {

				return ddao.updateDonationStatusAndRemarks(donationID, donation.getDonationStatus(), closeReason);
			}

			java.sql.Date d = new java.sql.Date(referenceTime.getTime());
			java.util.List<Trip> trips = tdao.findTripsByDate(d);

			for (Trip t : trips) {
				if (t.getDonationID().equals(donationID)) {
					if (t.getTripStatus().equals("PLANNED") || t.getTripStatus().equals("IN_PROGRESS")) {

						throw new DonationAlreadyAssignedException();
					}
				}
			}
			return ddao.updateDonationStatusAndRemarks(donationID, "CANCELLED", closeReason);

		} catch (ValidationException | DonationAlreadyAssignedException e) {
			throw e;
		} catch (Exception e) {
			return false;
		}
	}

}
