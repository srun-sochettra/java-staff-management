package controller;

import model.HourlySalaryEmployee;
import model.SalariedEmployee;
import model.StaffMember;
import model.Volunteer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    // ── INSERT ────────────────────────────────────────────────────────────────

    public void insertStaff(StaffMember staff) throws SQLException {
        Connection        conn  = DatabaseHelper.getConnection();
        PreparedStatement pstmt;

        if (staff instanceof Volunteer) {
            Volunteer v = (Volunteer) staff;
            pstmt = conn.prepareStatement(
                    "INSERT INTO staff_members (id, type, name, address, salary) VALUES (?,?,?,?,?)");
            pstmt.setInt   (1, v.getId());
            pstmt.setString(2, "Volunteer");
            pstmt.setString(3, v.getName());
            pstmt.setString(4, v.getAddress());
            pstmt.setDouble(5, v.getSalary());

        } else if (staff instanceof SalariedEmployee) {
            SalariedEmployee se = (SalariedEmployee) staff;
            pstmt = conn.prepareStatement(
                    "INSERT INTO staff_members (id, type, name, address, salary, bonus) VALUES (?,?,?,?,?,?)");
            pstmt.setInt   (1, se.getId());
            pstmt.setString(2, "SalariedEmployee");
            pstmt.setString(3, se.getName());
            pstmt.setString(4, se.getAddress());
            pstmt.setDouble(5, se.getSalary());
            pstmt.setDouble(6, se.getBonus());

        } else {
            HourlySalaryEmployee he = (HourlySalaryEmployee) staff;
            pstmt = conn.prepareStatement(
                    "INSERT INTO staff_members (id, type, name, address, hour_worked, rate) VALUES (?,?,?,?,?,?)");
            pstmt.setInt   (1, he.getId());
            pstmt.setString(2, "HourlySalaryEmployee");
            pstmt.setString(3, he.getName());
            pstmt.setString(4, he.getAddress());
            pstmt.setInt   (5, he.getHourWorked());
            pstmt.setDouble(6, he.getRate());
        }

        pstmt.executeUpdate();
        pstmt.close();
    }

    // ── SELECT ALL ────────────────────────────────────────────────────────────

    public List<StaffMember> getAllStaff() throws SQLException {
        List<StaffMember> list = new ArrayList<>();
        String sql = "SELECT * FROM staff_members ORDER BY id";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                StaffMember s = mapRow(rs);
                if (s != null) list.add(s);
            }
        }
        return list;
    }

    // ── SELECT BY ID ──────────────────────────────────────────────────────────

    public StaffMember getById(int id) throws SQLException {
        String sql = "SELECT * FROM staff_members WHERE id = ?";
        try (Connection        conn = DatabaseHelper.getConnection();
             PreparedStatement ps   = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    // ── EXISTS BY ID ─────────────────────────────────────────────────────────

    public boolean existsById(int id) throws SQLException {
        String sql = "SELECT 1 FROM staff_members WHERE id = ?";
        try (Connection        conn = DatabaseHelper.getConnection();
             PreparedStatement ps   = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    // ── UPDATE FIELD ─────────────────────────────────────────────────────────

    public boolean updateField(int id, String column, Object value) throws SQLException {
        String sql = "UPDATE staff_members SET " + column + " = ? WHERE id = ?";
        try (Connection        conn = DatabaseHelper.getConnection();
             PreparedStatement ps   = conn.prepareStatement(sql)) {

            if      (value instanceof Double)  ps.setDouble(1, (Double)  value);
            else if (value instanceof Integer) ps.setInt   (1, (Integer) value);
            else                               ps.setString(1, value.toString());

            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    public boolean removeById(int id) throws SQLException {
        String sql = "DELETE FROM staff_members WHERE id = ?";
        try (Connection        conn = DatabaseHelper.getConnection();
             PreparedStatement ps   = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── ROW MAPPER ───────────────────────────────────────────────────────────

    private StaffMember mapRow(ResultSet rs) throws SQLException {
        int    id      = rs.getInt   ("id");
        String type    = rs.getString("type");
        String name    = rs.getString("name");
        String address = rs.getString("address");

        switch (type) {
            case "Volunteer":
                return new Volunteer(id, name, address, rs.getDouble("salary"));
            case "SalariedEmployee":
                return new SalariedEmployee(id, name, address,
                        rs.getDouble("salary"), rs.getDouble("bonus"));
            case "HourlySalaryEmployee":
                return new HourlySalaryEmployee(id, name, address,
                        rs.getInt("hour_worked"), rs.getDouble("rate"));
            default:
                return null;
        }
    }
}