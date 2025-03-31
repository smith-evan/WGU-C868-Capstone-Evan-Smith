package model;


/**
 * Customers class contains customerName, customerAddress, customerPostalCode, customerPhoneNumber, customerState, divisionID.
 * These values are necessary for the customer information and this class also contains setters and getters for functionality.
 */

public class Customers {

    private String divisionName;
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    //private int customerState;
    private int divisionID;

    public Customers(int customerID, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhoneNumber, int divisionID, String divisionName) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }



    /**
     * gets and returns customerID
     */
    public Integer getCustomerID() {

        return customerID;
    }

    /**
     * customerID setter
     */
    public void setCustomerID(Integer customerID) {

        this.customerID = customerID;
    }

    /**
     *
     */
    public String getCustomerName() {

        return customerName;
    }

    /**
     * part of customerName setter
     */
    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }

    /**
     * customerName setter
     */
    public String getCustomerAddress() {

        return customerAddress;
    }


    public void setCustomerAddress(String address) {

        this.customerAddress = address;
    }

    /**
     * Get customerPostalCode
     */
    public String getCustomerPostalCode() {

        return customerPostalCode;
    }

    /**
     * part of postalCode setter
     */
    public void setCustomerPostalCode(String postalCode) {

        this.customerPostalCode = postalCode;
    }

    /**
     * returns customerPhoneNumber
     */
    public String getCustomerPhone() {

        return customerPhoneNumber;
    }

    /**
     * customerPhone setter
     */
    public void setCustomerPhone(String phone) {

        this.customerPhoneNumber = phone;
    }

    /**
     * returns divisionID
     */
    public Integer getCustomerDivisionID() {

        return divisionID;
    }

    /**
     * returns divisionName
     */
    public String getDivisionName() {

        return divisionName;
    }

    /**
     * divisionID setter
     */
    public void setCustomerDivisionID(Integer divisionID) {

        this.divisionID = divisionID;
    }

}
