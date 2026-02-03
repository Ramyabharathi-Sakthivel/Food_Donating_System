package com.foodrelief.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foodrelief.bean.Donation;
import com.foodrelief.util.DBUtil;

public class DonationDAO {

    public Donation findDonation(String donationID) {

        Connection connection = DBUtil.getDBConnection();
        String query = "SELECT * FROM donation_table1 WHERE donation_id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, donationID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Donation d = new Donation();

                d.setDonationID(rs.getString("donation_id"));
                d.setDonorName(rs.getString("donor_name"));
                d.setDonorPhone(rs.getString("donor_phone"));
                d.setDonorAddress(rs.getString("donor_address"));
                d.setCityArea(rs.getString("city_area"));
                d.setFoodType(rs.getString("food_type"));
                d.setApproxQuantityKg(rs.getDouble("approx_quantity"));
                d.setPreparedTime(rs.getTimestamp("prepared_time"));
                d.setExpiryCutoffTime(rs.getTimestamp("expiry_cutoff_time"));
                d.setPreferredPickupSlot(rs.getString("preferred_pickup_place"));
                d.setDonationStatus(rs.getString("donation_status"));
                d.setRemarks(rs.getString("remarks"));

                return d;
            }
        } catch (SQLException e) { }
        return null;
    }

    public List<Donation> viewDonationsByStatusAndArea(String status, String cityArea) {

        List<Donation> list = new ArrayList<>();
        Connection connection = DBUtil.getDBConnection();

        String query = "SELECT * FROM donation_table1 WHERE "
                + "(? IS NULL OR donation_status=?) "
                + "AND (? IS NULL OR city_area=?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, status);
            ps.setString(2, status);
            ps.setString(3, cityArea);
            ps.setString(4, cityArea);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Donation d = new Donation();

                d.setDonationID(rs.getString("donation_id"));
                d.setDonorName(rs.getString("donor_name"));
                d.setDonorPhone(rs.getString("donor_phone"));
                d.setDonorAddress(rs.getString("donor_address"));
                d.setCityArea(rs.getString("city_area"));
                d.setFoodType(rs.getString("food_type"));
                d.setApproxQuantityKg(rs.getDouble("approx_quantity"));
                d.setPreparedTime(rs.getTimestamp("prepared_time"));
                d.setExpiryCutoffTime(rs.getTimestamp("expiry_cutoff_time"));
                d.setPreferredPickupSlot(rs.getString("preferred_pickup_place"));
                d.setDonationStatus(rs.getString("donation_status"));
                d.setRemarks(rs.getString("remarks"));

                list.add(d);
            }
        } catch (SQLException e) { }
        return list;
    }

    public boolean insertDonation(Donation d) {

        Connection connection = DBUtil.getDBConnection();

        String query = "INSERT INTO donation_table1 "
                + "(donation_id, donor_name, donor_phone, donor_address, "
                + "city_area, food_type, approx_quantity, "
                + "prepared_time, expiry_cutoff_time, "
                + "preferred_pickup_place, donation_status, remarks) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, d.getDonationID());
            ps.setString(2, d.getDonorName());
            ps.setString(3, d.getDonorPhone());
            ps.setString(4, d.getDonorAddress());
            ps.setString(5, d.getCityArea());
            ps.setString(6, d.getFoodType());
            ps.setDouble(7, d.getApproxQuantityKg());

            ps.setTimestamp(8, d.getPreparedTime());      
            ps.setTimestamp(9, d.getExpiryCutoffTime());  

            ps.setString(10, d.getPreferredPickupSlot());
            ps.setString(11, d.getDonationStatus());
            ps.setString(12, d.getRemarks());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public boolean updateDonationStatus(String donationID, String newStatus) {

        Connection connection = DBUtil.getDBConnection();
        String query = "UPDATE donation_table1 SET donation_status=? WHERE donation_id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newStatus);
            ps.setString(2, donationID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateDonationStatusAndRemarks(String donationID, String newStatus, String remarks) {

        Connection connection = DBUtil.getDBConnection();
        String query = "UPDATE donation_table1 SET donation_status=?, remarks=? WHERE donation_id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newStatus);
            ps.setString(2, remarks);
            ps.setString(3, donationID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
