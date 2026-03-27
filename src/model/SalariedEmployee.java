package model;

public class SalariedEmployee extends StaffMember {
    private double salary;
    private double bonus;

    public SalariedEmployee(int id, String name, String address,
                            double salary, double bonus) {
        super(id, name, address);
        this.salary = salary;
        this.bonus  = bonus;
    }

    public double getSalary()            { return salary; }
    public double getBonus()             { return bonus; }
    public void   setSalary(double s)    { this.salary = s; }
    public void   setBonus(double b)     { this.bonus  = b; }

    @Override public double pay()    { return salary + bonus; }
    @Override public String getType(){ return "SalariedEmployee"; }
}