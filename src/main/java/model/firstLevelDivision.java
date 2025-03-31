package model;

public class firstLevelDivision {
    private int divisionID;
    private String divisionName;
    public int country_ID;

    /**
     *
     * divisionID, divisionName, country_ID parameters for the firstLevelDivision class
     */
    public firstLevelDivision(int divisionID, String divisionName, int country_ID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.country_ID = country_ID;
    }

    public int getDivisionID() {

        return divisionID;
    }

    public String getDivisionName() {

        return divisionName;
    }

    public int getCountry_ID() {

        return country_ID;
    }

}
