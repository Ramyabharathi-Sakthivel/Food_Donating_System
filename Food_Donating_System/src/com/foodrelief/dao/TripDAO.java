package com.foodrelief.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.foodrelief.bean.Trip;
import com.foodrelief.util.DBUtil;

public class TripDAO {

    public int generateTripID() {

        Connection connection = DBUtil.getDBConnection();
        String query = "SELECT trip_seq1.NEXTVAL FROM dual";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        return 0;
    }

    public boolean insertTrip(Trip t) {

        Connection connection = DBUtil.getDBConnection();

        String query = "INSERT INTO trip_table1 VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, t.getTripID());
            ps.setString(2, t.getDonationID());
            ps.setString(3, t.getVolunteerID());
            ps.setString(4, t.getVehicleType());
            ps.setDouble(5, t.getVehicleCapacityKg());

            if (t.getAssignedTime() != null)
                ps.setTimestamp(6, t.getAssignedTime());                                          
            else
                ps.setNull(6, Types.TIMESTAMP);

            ps.setNull(7, Types.TIMESTAMP);   

            ps.setString(8, t.getDeliveryLocation());
            ps.setString(9, t.getDeliveryNgoOrShelterName());
            ps.setDouble(10, 0.0);
            ps.setString(11, t.getTripStatus());
            ps.setString(12, t.getNotes());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Trip findTrip(int tripID) {

        Connection connection = DBUtil.getDBConnection();
        String query = "SELECT * FROM trip_table1 WHERE trip_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, tripID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Trip t = new Trip();

                t.setTripID(rs.getInt("trip_id"));
                t.setDonationID(rs.getString("donation_id"));
                t.setVolunteerID(rs.getString("volunteer_id"));
                t.setVehicleType(rs.getString("vehicle_type"));
                t.setVehicleCapacityKg(rs.getDouble("vehicle_capacity"));
                t.setAssignedTime(rs.getTimestamp("assigned_time"));
                t.setPickupTime(rs.getTimestamp("pickup_time"));
                t.setDeliveryLocation(rs.getString("delivery_location"));
                t.setDeliveryNgoOrShelterName(rs.getString("delivery_ngo_shelter_name"));
                t.setDeliveredQuantityKg(rs.getDouble("delivery_quantity"));
                t.setTripStatus(rs.getString("trip_status"));
                t.setNotes(rs.getString("notes"));

                return t;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public List<Trip> findTripsByVolunteerAndDate(String volunteerID, java.sql.Date date) {

        List<Trip> list = new ArrayList<>();
        Connection connection = DBUtil.getDBConnection();

        String query = "SELECT * FROM trip_table1 "
                + "WHERE volunteer_id = ? "
                + "AND (TRUNC(assigned_time) = ? OR TRUNC(pickup_time) = ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, volunteerID);
            ps.setDate(2, date);
            ps.setDate(3, date);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Trip t = new Trip();

                t.setTripID(rs.getInt("trip_id"));
                t.setDonationID(rs.getString("donation_id"));
                t.setVolunteerID(rs.getString("volunteer_id"));
                t.setVehicleType(rs.getString("vehicle_type"));
                t.setVehicleCapacityKg(rs.getDouble("vehicle_capacity"));
                t.setAssignedTime(rs.getTimestamp("assigned_time"));
                t.setPickupTime(rs.getTimestamp("pickup_time"));
                t.setDeliveryLocation(rs.getString("delivery_location"));
                t.setDeliveryNgoOrShelterName(rs.getString("delivery_ngo_shelter_name"));
                t.setDeliveredQuantityKg(rs.getDouble("delivery_quantity"));
                t.setTripStatus(rs.getString("trip_status"));
                t.setNotes(rs.getString("notes"));

                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }

    public List<Trip> findTripsByDate(java.sql.Date date) {

        List<Trip> list = new ArrayList<>();
        Connection connection = DBUtil.getDBConnection();

        String query = "SELECT * FROM trip_table1 "
                + "WHERE TRUNC(assigned_time) = ? "
                + "OR TRUNC(pickup_time) = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setDate(1, date);
            ps.setDate(2, date);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Trip t = new Trip();

                t.setTripID(rs.getInt("trip_id"));
                t.setDonationID(rs.getString("donation_id"));
                t.setVolunteerID(rs.getString("volunteer_id"));
                t.setVehicleType(rs.getString("vehicle_type"));
                t.setVehicleCapacityKg(rs.getDouble("vehicle_capacity"));
                t.setAssignedTime(rs.getTimestamp("assigned_time"));
                t.setPickupTime(rs.getTimestamp("pickup_time"));
                t.setDeliveryLocation(rs.getString("delivery_location"));
                t.setDeliveryNgoOrShelterName(rs.getString("delivery_ngo_shelter_name"));
                t.setDeliveredQuantityKg(rs.getDouble("delivery_quantity"));
                t.setTripStatus(rs.getString("trip_status"));
                t.setNotes(rs.getString("notes"));

                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }

    public boolean updateTripStatusAndOutcome(int tripID, String newStatus, double deliveredQty, String notes) {

        Connection connection = DBUtil.getDBConnection();

        String query = "UPDATE trip_table1 "
                + "SET trip_status = ?, delivery_quantity = ?, notes = ? "
                + "WHERE trip_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, newStatus);
            ps.setDouble(2, deliveredQty);
            ps.setString(3, notes);
            ps.setInt(4, tripID);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
