package model;

public class HourlySalaryEmployee extends StaffMember {
    private int    hourWorked;
    private double rate;

    public HourlySalaryEmployee(int id, String name, String address,
                                int hourWorked, double rate) {
        super(id, name, address);
        this.hourWorked = hourWorked;
        this.rate       = rate;
    }

    public int    getHourWorked()       { return hourWorked; }
    public double getRate()             { return rate; }
    public void   setHourWorked(int h)  { this.hourWorked = h; }
    public void   setRate(double r)     { this.rate = r; }

    @Override public double pay()    { return hourWorked * rate; }
    @Override public String getType(){ return "HourlySalaryEmployee"; }
}