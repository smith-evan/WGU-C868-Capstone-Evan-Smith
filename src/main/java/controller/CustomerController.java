package controller;

import DAO.appointmentAccess;
import DAO.countryAccess;
import DAO.customerAccess;
import DAO.firstLevelDivisionAccess;
import main.DBConnection;
import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import model.Appointments;
import model.Country;
import model.Customers;
import model.firstLevelDivision;

import java.sql.Connection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class and method to manipulate customer data.
 */

public class CustomerController implements Initializable {

    @FXML
    private TableColumn<?, ?> customerRecordsTableName;
    @FXML
    private Button customerRecordsAddCustomer;
    @FXML
    private Button customerRecordsCancel;
    @FXML
    private TableView<Customers> customerRecordsTable;
    @FXML
    private TableColumn<?, ?> customerRecordsTableAddress;
    @FXML
    private TableColumn<?, ?> customerRecordsTableID;
    @FXML
    private TableColumn<?, ?> customerRecordsTablePhone;
    @FXML
    private TableColumn<?, ?> customerRecordsTablePostalCode;
    @FXML
    private TableColumn<?, ?> customerRecordsTableState;
    @FXML
    private TableColumn<?, ?> customerRecordsTableCountry;
    @FXML
    private TextField customerIDEdit;
    @FXML
    private TextField customerNameEdit;
    @FXML
    private TextField customerEditPhone;
    @FXML
    private TextField customerEditPostal;
    @FXML
    private ComboBox<String> customerEditState;
    @FXML
    private ComboBox<String> customerEditCountry;
    @FXML
    private TextField customerAddressEdit;

    /**
     * method for returning to main menu
     */

    @FXML
    public void customerRecordsCancel(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));
        Scene scene = new Scene(root);
        Stage MainViewReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainViewReturn.setScene(scene);
        MainViewReturn.show();
    }

    /**
     * Lambda fills observablelist with getDivisionName(). The code gets the country names from the allCountries list and adds them to the
     * countryNames list. It then sets this list as the options for the customerEditCountry control, like a ComboBox. The stream and lambda make this process more concise.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DBConnection.startConnection();

            ObservableList<countryAccess> allCountries = countryAccess.getCountries();
            ObservableList<String> countryNames = FXCollections.observableArrayList();
            ObservableList<firstLevelDivisionAccess> allFirstLevelDivisions = firstLevelDivisionAccess.getAllFirstLevelDivisions();
            ObservableList<String> firstLevelDivisionAllNames = FXCollections.observableArrayList();
            ObservableList<Customers> allCustomersList = customerAccess.getAllCustomers(connection);

            customerRecordsTableID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerRecordsTableName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerRecordsTableAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerRecordsTablePostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            customerRecordsTablePhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            customerRecordsTableState.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

            //IDE converted to forEach
            allCountries.stream().map(Country::getCountryName).forEach(countryNames::add);
            customerEditCountry.setItems(countryNames);

            /**
             * The lambda expression is going through each item in the allFirstLevelDivisions collection, getting the division name from each item,
             * and adding it to the firstLevelDivisionAllNames list. After that, the list of division names is used to fill the customerEditState control,
             * while the allCustomersList is used to populate the customerRecordsTable.
             */
            allFirstLevelDivisions.forEach(firstLevelDivision -> firstLevelDivisionAllNames.add(firstLevelDivision.getDivisionName()));

            customerEditState.setItems(firstLevelDivisionAllNames);
            customerRecordsTable.setItems(allCustomersList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method for delete all customer records
     */
    @FXML
    void customerRecordsDelete(ActionEvent event) throws Exception {

        Connection connection = DBConnection.startConnection();
        ObservableList<Appointments> getAllAppointmentsList = appointmentAccess.getAllAppointments();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Really delete this customer and all appointments? ");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            int deleteCustomerID = customerRecordsTable.getSelectionModel().getSelectedItem().getCustomerID();
            appointmentAccess.deleteAppointment(deleteCustomerID, connection);

            String sqlDelete = "DELETE FROM customers WHERE Customer_ID = ?";
            DBConnection.setPreparedStatement(DBConnection.getConnection(), sqlDelete);

            PreparedStatement psDelete = DBConnection.getPreparedStatement();
            int customerFromTable = customerRecordsTable.getSelectionModel().getSelectedItem().getCustomerID();

            // deletes everything
            for (Appointments appointment : getAllAppointmentsList) {
                int customerFromAppointments = appointment.getCustomerID();
                if (customerFromTable == customerFromAppointments) {
                    String deleteStatementAppointments = "DELETE FROM appointments WHERE Appointment_ID = ?";
                    DBConnection.setPreparedStatement(DBConnection.getConnection(), deleteStatementAppointments);
                }
            }
            psDelete.setInt(1, customerFromTable);
            psDelete.execute();
            ObservableList<Customers> refreshCustomersList = customerAccess.getAllCustomers(connection);
            customerRecordsTable.setItems(refreshCustomersList);
        }
    }

    /**
     * Fills in boxes when customer is selected or edit button is pressed
     */

    @FXML
    void customerRecordsEditCustomerButton(ActionEvent event) throws SQLException {
        try {
            DBConnection.startConnection();
            Customers selectedCustomer = (Customers) customerRecordsTable.getSelectionModel().getSelectedItem();

            String divisionName = "", countryName = "";

            if (selectedCustomer != null) {
                ObservableList<countryAccess> getAllCountries = countryAccess.getCountries();
                ObservableList<firstLevelDivisionAccess> getFLDivisionNames = firstLevelDivisionAccess.getAllFirstLevelDivisions();
                ObservableList<String> allFLDivision = FXCollections.observableArrayList();

                customerEditState.setItems(allFLDivision);

                customerIDEdit.setText(String.valueOf((selectedCustomer.getCustomerID())));
                customerNameEdit.setText(selectedCustomer.getCustomerName());
                customerAddressEdit.setText(selectedCustomer.getCustomerAddress());
                customerEditPostal.setText(selectedCustomer.getCustomerPostalCode());
                customerEditPhone.setText(selectedCustomer.getCustomerPhone());

                for (firstLevelDivision flDivision : getFLDivisionNames) {
                    allFLDivision.add(flDivision.getDivisionName());
                    int countryIDToSet = flDivision.getCountry_ID();

                    if (flDivision.getDivisionID() == selectedCustomer.getCustomerDivisionID()) {
                        divisionName = flDivision.getDivisionName();

                        for (Country country : getAllCountries) {
                            if (country.getCountryID() == countryIDToSet) {
                                countryName = country.getCountryName();
                            }
                        }
                    }
                }
                customerEditState.setValue(divisionName);
                customerEditCountry.setValue(countryName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * adds customer when button is pressed
     */
    @FXML
    void customerRecordsAddCustomer(ActionEvent event) {
        try {
            Connection connection = DBConnection.startConnection();
            if (!customerNameEdit.getText().isEmpty() ||
                    !customerAddressEdit.getText().isEmpty() ||
                    !customerEditPostal.getText().isEmpty() ||
                    !customerEditPhone.getText().isEmpty() ||
                    customerEditCountry.getValue() != null && !customerEditCountry.getValue().isEmpty() ||
                    customerEditState.getValue() != null && !customerEditState.getValue().isEmpty()) {
                //create random ID for new customer id
                Integer newCustomerID = (int) (Math.random() * 100);
                int firstLevelDivisionName = 0;
                for (firstLevelDivisionAccess firstLevelDivision : firstLevelDivisionAccess.getAllFirstLevelDivisions()) {
                    if (customerEditState.getSelectionModel().getSelectedItem().equals(firstLevelDivision.getDivisionName())) {
                        firstLevelDivisionName = firstLevelDivision.getDivisionID();
                    }
                }
                String insertStatement = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
                DBConnection.setPreparedStatement(DBConnection.getConnection(), insertStatement);
                PreparedStatement ps = DBConnection.getPreparedStatement();
                ps.setInt(1, newCustomerID);
                ps.setString(2, customerNameEdit.getText());
                ps.setString(3, customerAddressEdit.getText());
                ps.setString(4, customerEditPostal.getText());
                ps.setString(5, customerEditPhone.getText());
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(7, "admin");
                ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(9, "admin");
                ps.setInt(10, firstLevelDivisionName);
                ps.execute();

                customerIDEdit.clear();
                customerNameEdit.clear();
                customerAddressEdit.clear();
                customerEditPostal.clear();
                customerEditPhone.clear();

                ObservableList<Customers> refreshCustomersList = customerAccess.getAllCustomers(connection);
                customerRecordsTable.setItems(refreshCustomersList);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * method adds customer when save button is pressed
     */
    @FXML
    void customerRecordsSaveCustomer(ActionEvent event) {
        try {
            Connection connection = DBConnection.startConnection();
            if (!customerNameEdit.getText().isEmpty() || !customerAddressEdit.getText().isEmpty() || !customerAddressEdit.getText().isEmpty() || !customerEditPostal.getText().isEmpty() || !customerEditPhone.getText().isEmpty() || !customerEditCountry.getValue().isEmpty() || !customerEditState.getValue().isEmpty()) {
                int firstLevelDivisionName = 0;
                for (firstLevelDivisionAccess firstLevelDivision : firstLevelDivisionAccess.getAllFirstLevelDivisions()) {
                    if (customerEditState.getSelectionModel().getSelectedItem().equals(firstLevelDivision.getDivisionName())) {
                        firstLevelDivisionName = firstLevelDivision.getDivisionID();
                    }
                }

                String updateStatement = "UPDATE customers SET " +
                        "Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                        "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                        "WHERE Customer_ID = ?";

                DBConnection.setPreparedStatement(DBConnection.getConnection(), updateStatement);
                PreparedStatement ps = DBConnection.getPreparedStatement();

                ps.setInt(1, Integer.parseInt(customerIDEdit.getText())); // Customer_ID
                ps.setString(2, customerNameEdit.getText()); // Customer_Name
                ps.setString(3, customerAddressEdit.getText()); // Address
                ps.setString(4, customerEditPostal.getText()); // Postal_Code
                ps.setString(5, customerEditPhone.getText()); // Phone
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now())); // Create_Date
                ps.setString(7, "admin"); // Created_By
                ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now())); // Last_Update
                ps.setString(9, "admin"); // Last_Updated_By
                ps.setInt(10, firstLevelDivisionName); // Division_ID
                ps.setInt(11, Integer.parseInt(customerIDEdit.getText())); // WHERE Customer_ID

                ps.execute();
                customerIDEdit.clear();
                customerNameEdit.clear();
                customerAddressEdit.clear();
                customerEditPostal.clear();
                customerEditPhone.clear();

                ObservableList<Customers> refreshCustomersList = customerAccess.getAllCustomers(connection);
                customerRecordsTable.setItems(refreshCustomersList);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * fill customer edit fields with selected customer
     */
    public void customerEditCountryDropDown(ActionEvent event) throws SQLException {
        try {
            DBConnection.startConnection();

            String selectedCountry = customerEditCountry.getSelectionModel().getSelectedItem();

            ObservableList<firstLevelDivisionAccess> getAllFirstLevelDivisions = firstLevelDivisionAccess.getAllFirstLevelDivisions();

            ObservableList<String> flDivisionUS = FXCollections.observableArrayList();
            ObservableList<String> flDivisionCanada = FXCollections.observableArrayList();
            ObservableList<String> flDivisionUK = FXCollections.observableArrayList();

            getAllFirstLevelDivisions.forEach(firstLevelDivision -> {
                if (firstLevelDivision.getCountry_ID() == 1) {
                    flDivisionUS.add(firstLevelDivision.getDivisionName());
                } else if (firstLevelDivision.getCountry_ID() == 2) {
                    flDivisionCanada.add(firstLevelDivision.getDivisionName());
                } else if (firstLevelDivision.getCountry_ID() == 3) {
                    flDivisionUK.add(firstLevelDivision.getDivisionName());
                }
            });

            if (selectedCountry.equals("U.S")) {
                customerEditState.setItems(flDivisionUS);
            } else if (selectedCountry.equals("UK")) {
                customerEditState.setItems(flDivisionUK);
            } else if (selectedCountry.equals("Canada")) {
                customerEditState.setItems(flDivisionCanada);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Search entry field
     */
    @FXML
    private TextField customerSearchText;

    /**
     * Searches using provided value and refreshes table view showing matching values.
     */
    @FXML
    void customerSearchBtnAction(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.startConnection();
        ObservableList<Customers> allCustomers = customerAccess.getAllCustomers(connection);
        ObservableList<Customers> customersFound = FXCollections.observableArrayList();
        String searchString = customerSearchText.getText().trim().toLowerCase();

        if (searchString.isEmpty()) {
            customerRecordsTable.setItems(allCustomers);
            return;
        }

        for (Customers customer : allCustomers) {
            if (String.valueOf(customer.getCustomerID()).contains(searchString) ||
                    customer.getCustomerName().toLowerCase().contains(searchString)) {
                customersFound.add(customer);
            }
        }

        if (customersFound.isEmpty()) {
            displayAlert(1);
            customerRecordsTable.setItems(allCustomers); // Show all customers if no match
        } else {
            customerRecordsTable.setItems(customersFound);
        }
    }


    private void displayAlert(int type) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Result");
        alert.setHeaderText(null);
        alert.setContentText("No matching customers found.");
        alert.showAndWait();
    }


    /**
     * Shows all parts if search field is empty.
     */
    @FXML
    void customerSearchTextKeyPressed(KeyEvent event) throws SQLException {
        if (customerSearchText.getText().trim().isEmpty()) {
            Connection connection = DBConnection.startConnection();
            ObservableList<Customers> allCustomersList = customerAccess.getAllCustomers(connection);
            customerRecordsTable.setItems(allCustomersList);
        }
    }

}

