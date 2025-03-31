package model;

public class Country {
    private int countryID;
    private String countryName;

    /**
     * Constructor to initialize a Country object with a country ID and country name.
     *
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * Gets the country ID of this Country.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Gets the name of this Country.
     */
    public String getCountryName() {
        return countryName;
    }
}
