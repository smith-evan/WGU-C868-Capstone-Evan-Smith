package model;

public class Reports {

    private int countryCount;
    private String countryName;
    public String appointmentMonth;
    public int appointmentTotal;

    /**
     *
     */
    public Reports(String countryName, int countryCount) {
        this.countryCount = countryCount;
        this.countryName = countryName;

    }


    /**
     * Returns countryName
     */
    public String getCountryName() {

        return countryName;
    }

    /**
     * Returns total of countries
     */
    public int getCountryCount() {

        return countryCount;
    }

}
