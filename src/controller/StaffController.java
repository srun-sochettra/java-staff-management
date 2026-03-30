package controller;

import model.HourlySalaryEmployee;
import model.SalariedEmployee;
import model.StaffMember;
import model.Volunteer;
import view.StaffView;

import java.sql.SQLException;
import java.util.List;

public class StaffController {

    private final StaffView view;
    private final StaffDAO  dao;

    public StaffController(StaffView view, StaffDAO dao) {
        this.view = view;
        this.dao  = dao;
    }

    public void run() {
        boolean running = true;
        while (running) {
            view.printMainMenu();
            int opt = view.readInt("==> Enter option: ");
            switch (opt) {
                case 1: handleInsert();  break;
                case 2: handleDisplay(); break;
                case 3: handleUpdate();  break;
                case 4: handleRemove();  break;
                case 5:
                    view.showMessage("======= Good bye *=======");
                    running = false;
                    break;
                default:
                    view.showError("Invalid option. Try again.");
            }
        }
        DatabaseHelper.close();
    }

    private void handleInsert() {
        view.printInsertTypeMenu();
        int type = view.readInt("Enter Type Number : ");
        switch (type) {
            case 1: insertVolunteer();        break;
            case 2: insertSalariedEmployee(); break;
            case 3: insertHourlyEmployee();   break;
            case 0: break;
            default: view.showError("Invalid type!");
        }
    }

    private void insertVolunteer() {
        try {
            view.showMessage("Volunteer");
            int id = dao.getNextId();
            view.showMessage("ID : " + id);
            String name    = view.readString("Name : ");
            String address = view.readString("Address : ");
            double salary  = view.readDouble("Salary : ");

            Volunteer v = new Volunteer(id, name, address, salary);
            view.showPayment(v.pay());
            dao.insertStaff(id, v);
            view.showSuccess("Insert Volunteer Employee is successfully.");
        } catch (SQLException e) {
            view.showError("DB Error: " + e.getMessage());
        }
    }

    private void insertSalariedEmployee() {
        try {
            view.showMessage("SalariedEmployee");
            int id = dao.getNextId();
            view.showMessage("ID : " + id);
            String name    = view.readString("Name : ");
            String address = view.readString("Address : ");
            double salary  = view.readDouble("Salary : ");
            double bonus   = view.readDouble("Bonus : ");

            SalariedEmployee se = new SalariedEmployee(id, name, address, salary, bonus);
            view.showPayment(se.pay());
            dao.insertStaff(id, se);
            view.showSuccess("Insert Salaried Employee is successfully.");
        } catch (SQLException e) {
            view.showError("DB Error: " + e.getMessage());
        }
    }

    private void insertHourlyEmployee() {
        try {
            view.showMessage("HourlySalaryEmployee");
            int id = dao.getNextId();
            view.showMessage("ID : " + id);
            String name    = view.readString("Name : ");
            String address = view.readString("Address : ");
            int    hours   = view.readInt   ("Hour : ");
            double rate    = view.readDouble("Rate : ");

            HourlySalaryEmployee he = new HourlySalaryEmployee(id, name, address, hours, rate);
            view.showPayment(he.pay());
            dao.insertStaff(id, he);
            view.showSuccess("Insert Hourly Employee is successfully.");
        } catch (SQLException e) {
            view.showError("DB Error: " + e.getMessage());
        }
    }

    private void handleDisplay() {
        try {
            List<StaffMember> list = dao.getAllStaff();
            view.printDisplayTable(list);
        } catch (SQLException e) {
            view.showError("DB Error: " + e.getMessage());
        }
    }

    private void handleUpdate() {
        view.showMessage("=====*Update staff information *=====");
        int id = view.readInt("input staff id to update : ");

        try {
            StaffMember staff = dao.getById(id);
            if (staff == null) {
                view.showError("Staff with ID " + id + " not found.");
                return;
            }

            view.printUpdateTable(staff);
            view.printUpdateColumnsFor(staff);

            int col = view.readInt("==> Select Column : ");
            processUpdate(id, col, staff);

        } catch (SQLException e) {
            view.showError("DB Error: " + e.getMessage());
        }
    }

    private void processUpdate(int id, int col, StaffMember staff) throws SQLException {
        boolean updated = false;
        if (staff instanceof Volunteer) {
            switch (col) {
                case 1: updated = dao.updateField(id, "name",    view.readString("Name : "));    break;
                case 2: updated = dao.updateField(id, "address", view.readString("Address : ")); break;
                case 3: updated = dao.updateField(id, "salary",  view.readDouble("Salary : "));  break;
                case 0: return;
                default: view.showError("Invalid column."); return;
            }
        } else if (staff instanceof SalariedEmployee) {
            switch (col) {
                case 1: updated = dao.updateField(id, "name",    view.readString("Name : "));    break;
                case 2: updated = dao.updateField(id, "address", view.readString("Address : ")); break;
                case 3: updated = dao.updateField(id, "salary",  view.readDouble("Salary : "));  break;
                case 4: updated = dao.updateField(id, "bonus",   view.readDouble("Bonus : "));   break;
                case 0: return;
                default: view.showError("Invalid column."); return;
            }
        } else {
            switch (col) {
                case 1: updated = dao.updateField(id, "name",        view.readString("Name : "));    break;
                case 2: updated = dao.updateField(id, "address",     view.readString("Address : ")); break;
                case 3: updated = dao.updateField(id, "rate",        view.readDouble("Rate : "));    break;
                case 4: updated = dao.updateField(id, "hour_worked", view.readInt   ("Hour : "));    break;
                case 0: return;
                default: view.showError("Invalid column."); return;
            }
        }

        if (updated) {
            view.showSuccess("Update successfully.");
        } else {
            view.showError("Update failed or no changes made.");
        }
    }

    private void handleRemove() {
        view.showMessage("Remove Staff");
        int id = view.readInt("Enter Staff ID to remove: ");
        try {
            if (dao.removeById(id)) {
                view.showSuccess("Staff with ID " + id + " removed successfully.");
            } else {
                view.showError("Staff with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            view.showError("DB Error: " + e.getMessage());
        }
    }
}
