package view;

import model.HourlySalaryEmployee;
import model.SalariedEmployee;
import model.StaffMember;
import model.Volunteer;

import java.util.List;
import java.util.Scanner;

public class StaffView {

    private final Scanner sc = new Scanner(System.in);

    // ─────────────────────────── MENUS ───────────────────────────────────────

    public void printMainMenu() {
        System.out.println("+-------------------------------------+");
        System.out.println("| Welcome to Staff Management System  |");
        System.out.println("+-------------------------------------+");
        System.out.println("|     1. Insert Employee              |");
        System.out.println("|     2. Display Employee             |");
        System.out.println("|     3. Update Employee              |");
        System.out.println("|     4. Remove Employee              |");
        System.out.println("|     5. Exit system                  |");
        System.out.println("+-------------------------------------+");
    }

    public void printInsertTypeMenu() {
        System.out.println("=========* Insert Employee *=========");
        System.out.println("Choose Type:");
        System.out.println("| 1. Volunteer | 2. Salaries Employee | 3. Hourly Employee | 0. Back |");
    }

    // ─────────────────────────── INPUT READERS ───────────────────────────────

    public int    readInt(String prompt)    {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Please enter a valid integer."); }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Please enter a valid number."); }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // ─────────────────────────── DISPLAY TABLE ───────────────────────────────

    public void printDisplayTable(List<StaffMember> list) {
        if (list.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        String sep = "+----------------------+----+------------+-------------+---------+---------+-------+-------+---------+";
        String hdr = "| %-20s | %2s | %-10s | %-11s | %-7s | %-7s | %-5s | %-5s | %-7s |";

        System.out.println(sep);
        System.out.printf(hdr + "%n",
                "Type", "ID", "Name", "Address", "Salary", "Bonus", "Hours", "Rate", "Pay");
        System.out.println(sep);

        // Java 8 Stream API
        list.stream().forEach(s -> {
            String salary, bonus, hours, rate;

            if (s instanceof Volunteer) {
                Volunteer v = (Volunteer) s;
                salary = String.format("%.1f", v.getSalary());
                bonus  = "---"; hours = "---"; rate = "---";

            } else if (s instanceof SalariedEmployee) {
                SalariedEmployee se = (SalariedEmployee) s;
                salary = String.format("%.1f", se.getSalary());
                bonus  = String.format("%.1f", se.getBonus());
                hours  = "---"; rate = "---";

            } else {
                HourlySalaryEmployee he = (HourlySalaryEmployee) s;
                salary = "---"; bonus = "---";
                hours  = String.valueOf(he.getHourWorked());
                rate   = String.format("%.1f", he.getRate());
            }

            System.out.printf(hdr + "%n",
                    s.getType(), s.getId(), s.getName(), s.getAddress(),
                    salary, bonus, hours, rate,
                    String.format("%.1f", s.pay()));
            System.out.println(sep);
        });
    }

    // ─────────────────────────── UPDATE TABLE ────────────────────────────────

    public void printUpdateTable(StaffMember s) {
        if (s instanceof Volunteer) {
            Volunteer v = (Volunteer) s;
            String sep = "+-----------------+----+------------+-------------+---------+";
            String fmt = "| %-15s | %2s | %-10s | %-11s | %-7s |";
            System.out.println(sep);
            System.out.printf(fmt + "%n", "Type", "ID", "Name", "Address", "Salary");
            System.out.println(sep);
            System.out.printf(fmt + "%n",
                    v.getType(), v.getId(), v.getName(), v.getAddress(),
                    String.format("%.1f", v.getSalary()));
            System.out.println(sep);

        } else if (s instanceof SalariedEmployee) {
            SalariedEmployee se = (SalariedEmployee) s;
            String sep = "+-----------------+----+------------+-------------+---------+---------+";
            String fmt = "| %-15s | %2s | %-10s | %-11s | %-7s | %-7s |";
            System.out.println(sep);
            System.out.printf(fmt + "%n", "Type", "ID", "Name", "Address", "Salary", "Bonus");
            System.out.println(sep);
            System.out.printf(fmt + "%n",
                    se.getType(), se.getId(), se.getName(), se.getAddress(),
                    String.format("%.1f", se.getSalary()),
                    String.format("%.1f", se.getBonus()));
            System.out.println(sep);

        } else {
            HourlySalaryEmployee he = (HourlySalaryEmployee) s;
            String sep = "+-----------------+----+------------+-------------+-------+-------+";
            String fmt = "| %-15s | %2s | %-10s | %-11s | %-5s | %-5s |";
            System.out.println(sep);
            System.out.printf(fmt + "%n", "Type", "ID", "Name", "Address", "Hours", "Rate");
            System.out.println(sep);
            System.out.printf(fmt + "%n",
                    he.getType(), he.getId(), he.getName(), he.getAddress(),
                    he.getHourWorked(),
                    String.format("%.1f", he.getRate()));
            System.out.println(sep);
        }
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

    // ─────────────────────────── MESSAGES ────────────────────────────────────

    public void showMessage(String msg)  { System.out.println(msg); }
    public void showPayment(double pay)  { System.out.printf("Payment: %.1f%n", pay); }
}