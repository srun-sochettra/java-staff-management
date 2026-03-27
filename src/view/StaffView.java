package view;

import model.HourlySalaryEmployee;
import model.SalariedEmployee;
import model.StaffMember;
import model.Volunteer;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.CellStyle.HorizontalAlign;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;

public class StaffView {

    private final Scanner sc = new Scanner(System.in);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED   = "\u001B[31m";
    public static final String ANSI_CYAN  = "\u001B[36m";

    public void printMainMenu() {
        Table t = new Table(1, BorderStyle.UNICODE_BOX);
        CellStyle centerStyle = new CellStyle(HorizontalAlign.CENTER);
        t.addCell(ANSI_CYAN + " Welcome to Staff Management System " + ANSI_RESET, centerStyle);
        t.addCell(" ");
        t.addCell("        1.Insert Employee");
        t.addCell("        2.Display Employee");
        t.addCell("        3.Update Employee");
        t.addCell("        4.Remove Employee");
        t.addCell("        5.Exit system");
        t.addCell(" ");
        System.out.println(t.render());
    }

    public void printInsertTypeMenu() {
        System.out.println("========* Insert Employee *========");
        System.out.println("\nChoose Type:");
        Table t = new Table(4, BorderStyle.UNICODE_BOX);
        CellStyle centerStyle = new CellStyle(HorizontalAlign.CENTER);
        t.addCell(" 1. Volunteer ", centerStyle);
        t.addCell(" 2. Salaried Employee ", centerStyle);
        t.addCell(" 3. Hourly Employee ", centerStyle);
        t.addCell(" 0. Back ", centerStyle);
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
        CellStyle centerStyle = new CellStyle(HorizontalAlign.CENTER);
        
        t.addCell(" Type ", centerStyle); 
        t.addCell(" ID ", centerStyle); 
        t.addCell(" Name ", centerStyle); 
        t.addCell(" Address ", centerStyle);
        t.addCell(" Salary ", centerStyle); 
        t.addCell(" Bonus ", centerStyle); 
        t.addCell(" Hours ", centerStyle); 
        t.addCell(" Rate ", centerStyle); 
        t.addCell(" Pay ", centerStyle);

        list.forEach(s -> {
            t.addCell(" " + s.getType() + " ", centerStyle);
            t.addCell(" " + s.getId() + " ", centerStyle);
            t.addCell(" " + s.getName() + " ", centerStyle);
            t.addCell(" " + s.getAddress() + " ", centerStyle);

            if (s instanceof Volunteer) {
                Volunteer v = (Volunteer) s;
                t.addCell(" " + String.format("%.1f", v.getSalary()) + " ", centerStyle);
                t.addCell(" --- ", centerStyle); 
                t.addCell(" --- ", centerStyle); 
                t.addCell(" --- ", centerStyle);
            } else if (s instanceof SalariedEmployee) {
                SalariedEmployee se = (SalariedEmployee) s;
                t.addCell(" " + String.format("%.1f", se.getSalary()) + " ", centerStyle);
                t.addCell(" " + String.format("%.1f", se.getBonus()) + " ", centerStyle);
                t.addCell(" --- ", centerStyle); 
                t.addCell(" --- ", centerStyle);
            } else {
                HourlySalaryEmployee he = (HourlySalaryEmployee) s;
                t.addCell(" --- ", centerStyle); 
                t.addCell(" --- ", centerStyle);
                t.addCell(" " + he.getHourWorked() + " ", centerStyle);
                t.addCell(" " + String.format("%.1f", he.getRate()) + " ", centerStyle);
            }
            t.addCell(" " + String.format("%.1f", s.pay()) + " ", centerStyle);
        });

        System.out.println(t.render());
    }

    public void printUpdateTable(StaffMember s) {
        Table t;
        CellStyle centerStyle = new CellStyle(HorizontalAlign.CENTER);
        if (s instanceof Volunteer) {
            Volunteer v = (Volunteer) s;
            t = new Table(5, BorderStyle.UNICODE_BOX);
            t.addCell(" Type ", centerStyle); t.addCell(" ID ", centerStyle); t.addCell(" Name ", centerStyle); t.addCell(" Address ", centerStyle); t.addCell(" Salary ", centerStyle);
            t.addCell(" " + v.getType() + " ", centerStyle); t.addCell(" " + v.getId() + " ", centerStyle); t.addCell(" " + v.getName() + " ", centerStyle);
            t.addCell(" " + v.getAddress() + " ", centerStyle); t.addCell(" " + String.format("%.1f", v.getSalary()) + " ", centerStyle);
        } else if (s instanceof SalariedEmployee) {
            SalariedEmployee se = (SalariedEmployee) s;
            t = new Table(6, BorderStyle.UNICODE_BOX);
            t.addCell(" Type ", centerStyle); t.addCell(" ID ", centerStyle); t.addCell(" Name ", centerStyle); t.addCell(" Address ", centerStyle); t.addCell(" Salary ", centerStyle); t.addCell(" Bonus ", centerStyle);
            t.addCell(" " + se.getType() + " ", centerStyle); t.addCell(" " + se.getId() + " ", centerStyle); t.addCell(" " + se.getName() + " ", centerStyle);
            t.addCell(" " + se.getAddress() + " ", centerStyle); t.addCell(" " + String.format("%.1f", se.getSalary()) + " ", centerStyle);
            t.addCell(" " + String.format("%.1f", se.getBonus()) + " ", centerStyle);
        } else {
            HourlySalaryEmployee he = (HourlySalaryEmployee) s;
            t = new Table(6, BorderStyle.UNICODE_BOX);
            t.addCell(" Type ", centerStyle); t.addCell(" ID ", centerStyle); t.addCell(" Name ", centerStyle); t.addCell(" Address ", centerStyle); t.addCell(" Hours ", centerStyle); t.addCell(" Rate ", centerStyle);
            t.addCell(" " + he.getType() + " ", centerStyle); t.addCell(" " + he.getId() + " ", centerStyle); t.addCell(" " + he.getName() + " ", centerStyle);
            t.addCell(" " + he.getAddress() + " ", centerStyle); t.addCell(" " + he.getHourWorked() + " ", centerStyle);
            t.addCell(" " + String.format("%.1f", he.getRate()) + " ", centerStyle);
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