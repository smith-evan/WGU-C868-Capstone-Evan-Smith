package model;

public class ReportMonth {
    public String appointmentMonth;
    public int appointmentTotal;
    public ReportMonth(String appointmentMonth, int appointmentTotal) {
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotal = appointmentTotal;
    }

    /**
     * returns appointmentMonth
     */
    public String getAppointmentMonth() {

        return appointmentMonth;
    }

    /**
     * returns appointmentTotal
     */
    public int getAppointmentTotal() {

        return appointmentTotal;
    }
}
