package model;

public class Contacts {
    public int contactID;
    public String contactName;
    public String contactEmail;

    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets contactID
     */
    public int getId() {

        return contactID;
    }

    /**
     * Gets contactName
     */
    public String getContactName() {

        return contactName;
    }

    /**
     * Returns contactEmail
     */
    public String getContactEmail() {

        return contactEmail;
    }
}
