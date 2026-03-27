package view;

import model.HourlySalaryEmployee;
import model.SalariedEmployee;
import model.StaffMember;
import model.Volunteer;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;

public class StaffView {

    private final Scanner sc = new Scanner(System.in);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED   = "\u001B[31m";

    public void printMainMenu() {
        Table t = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);
        t.addCell(ANSI_GREEN + " Welcome to Staff Management System " + ANSI_RESET);
        t.addCell("\n 1.Insert Employee\n\n 2.Display Employee\n\n 3.Update Employee\n\n 4.Remove Employee\n\n 5.Exit system\n");
        System.out.println(t.render());
    }

    public void printInsertTypeMenu() {
        Table t = new Table(4, BorderStyle.UNICODE_BOX);
        t.addCell(" 1. Volunteer ");
        t.addCell(" 2. Salaries Employee ");
        t.addCell(" 3.Hourly Employee ");
        t.addCell(" 0. Back ");
        System.out.println(t.render());
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { showError("Please enter a valid integer."); }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { showError("Please enter a valid number."); }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public void printDisplayTable(List<StaffMember> list) {
        if (list.isEmpty()) {
            showError("No employees found.");
            return;
        }

        Table t = new Table(9, BorderStyle.UNICODE_BOX);
        t.addCell(" Type "); t.addCell(" ID "); t.addCell(" Name "); t.addCell(" Address ");
        t.addCell(" Salary "); t.addCell(" Bonus "); t.addCell(" Hours "); t.addCell(" Rate "); t.addCell(" Pay ");

        list.forEach(s -> {
            t.addCell(" " + s.getType() + " ");
            t.addCell(" " + s.getId() + " ");
            t.addCell(" " + s.getName() + " ");
            t.addCell(" " + s.getAddress() + " ");

            if (s instanceof Volunteer) {
                Volunteer v = (Volunteer) s;
                t.addCell(" " + String.format("%.1f", v.getSalary()) + " ");
                t.addCell(" ---- "); t.addCell(" ---- "); t.addCell(" ---- ");
            } else if (s instanceof SalariedEmployee) {
                SalariedEmployee se = (SalariedEmployee) s;
                t.addCell(" " + String.format("%.1f", se.getSalary()) + " ");
                t.addCell(" " + String.format("%.1f", se.getBonus()) + " ");
                t.addCell(" ---- "); t.addCell(" ---- ");
            } else {
                HourlySalaryEmployee he = (HourlySalaryEmployee) s;
                t.addCell(" ---- "); t.addCell(" ---- ");
                t.addCell(" " + he.getHourWorked() + " ");
                t.addCell(" " + String.format("%.1f", he.getRate()) + " ");
            }
            t.addCell(" " + String.format("%.1f", s.pay()) + " ");
        });

        System.out.println(t.render());
    }

    public void printUpdateTable(StaffMember s) {
        Table t;
        if (s instanceof Volunteer) {
            Volunteer v = (Volunteer) s;
            t = new Table(5, BorderStyle.CLASSIC);
            t.addCell(" Type "); t.addCell(" ID "); t.addCell(" Name "); t.addCell(" Address "); t.addCell(" Salary ");
            t.addCell(" " + v.getType() + " "); t.addCell(" " + v.getId() + " "); t.addCell(" " + v.getName() + " ");
            t.addCell(" " + v.getAddress() + " "); t.addCell(" " + String.format("%.1f", v.getSalary()) + " ");
        } else if (s instanceof SalariedEmployee) {
            SalariedEmployee se = (SalariedEmployee) s;
            t = new Table(6, BorderStyle.CLASSIC);
            t.addCell(" Type "); t.addCell(" ID "); t.addCell(" Name "); t.addCell(" Address "); t.addCell(" Salary "); t.addCell(" Bonus ");
            t.addCell(" " + se.getType() + " "); t.addCell(" " + se.getId() + " "); t.addCell(" " + se.getName() + " ");
            t.addCell(" " + se.getAddress() + " "); t.addCell(" " + String.format("%.1f", se.getSalary()) + " ");
            t.addCell(" " + String.format("%.1f", se.getBonus()) + " ");
        } else {
            HourlySalaryEmployee he = (HourlySalaryEmployee) s;
            t = new Table(6, BorderStyle.CLASSIC);
            t.addCell(" Type "); t.addCell(" ID "); t.addCell(" Name "); t.addCell(" Address "); t.addCell(" Hours "); t.addCell(" Rate ");
            t.addCell(" " + he.getType() + " "); t.addCell(" " + he.getId() + " "); t.addCell(" " + he.getName() + " ");
            t.addCell(" " + he.getAddress() + " "); t.addCell(" " + he.getHourWorked() + " ");
            t.addCell(" " + String.format("%.1f", he.getRate()) + " ");
        }
        System.out.println(t.render());
    }

    public void printUpdateColumnsFor(StaffMember s) {
        if (s instanceof Volunteer) {
            System.out.println("   1.Name   2.Address   3.Salary   0.Cancel");
        } else if (s instanceof SalariedEmployee) {
            System.out.println("   1.Name   2.Address   3.Salary   4.Bonus   0.Cancel");
        } else {
            System.out.println("   1.Name   2.Address   3.Rate   4.Hour   0.Cancel");
        }
    }

    public void showMessage(String msg) { System.out.println(msg); }
    public void showSuccess(String msg) { System.out.println(ANSI_GREEN + msg + ANSI_RESET); }
    public void showError(String msg)   { System.out.println(ANSI_RED + msg + ANSI_RESET); }
    public void showPayment(double pay) { System.out.printf("Payment: %.1f%n", pay); }
}