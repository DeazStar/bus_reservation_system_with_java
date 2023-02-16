package application;

import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.model.Route;
import com.busreservationsystem.user.Customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.busreservationsystem.base.Address;
import com.busreservationsystem.user.Customer;

public class ViewCustomerController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TableColumn<Customer, String> cityId;

	@FXML
	private TableColumn<Customer, LocalDate> dobID;

	@FXML
	private TableColumn<Customer, String> emailID;

	@FXML
	private TableColumn<Customer, String> firstnameID;

	@FXML
	private TableColumn<Customer, String> genderId;

	@FXML
	private TableColumn<Customer, Integer> idid;

	@FXML
	private TableColumn<Customer, String> lastnameID;

	@FXML
	private TableColumn<Customer, String> phoneID;

	@FXML
	private TableColumn<Customer, String> regionId;

	@FXML
	private TableColumn<Customer, String> streetAddressId;

	@FXML
	private TableView<Customer> tableid;

//	
//	@FXML
	private void refreshTable() {
		// create an observablelist to store data
		ObservableList<Customer> data = FXCollections.observableArrayList();
//		
		// fetch data from database and add it to the observable list
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String databaseUsername = "customer";
			String password = "Customer123$";

			connection = DriverManager.getConnection(url, databaseUsername, password);
			String sql = "SELECT customer.customer_id, customer.first_name, customer.last_name, "
					+ "customer.gender, customer.date_of_birth, customer.email, "
					+ "customer.phone_number, address.street_address, " + "address.city, address.region "
					+ "FROM customer " + "LEFT JOIN address ON customer.address_id = address.address_id";
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("customer.customer_id");
				String firstName = resultSet.getString("customer.first_name");
				String lastName = resultSet.getString("customer.last_name");
				String gender = resultSet.getString("customer.gender");
				Date sqlDate = resultSet.getDate("customer.date_of_birth");
				LocalDate date = sqlDate.toLocalDate();
				String email = resultSet.getString("customer.email");
				String phoneNumber = resultSet.getString("customer.phone_number");
				String streetAddress = resultSet.getString("address.street_address");
				String city = resultSet.getString("address.city");
				String region = resultSet.getString("address.region");

				Address address = new Address(streetAddress, city, region);
				Customer customer = new Customer();

				customer.setCustomerId(id);
				customer.setFirstName(firstName);
				customer.setLastName(lastName);
				customer.setGender(gender);
				customer.setDateOfBirth(date);
				customer.setEmail(email);
				customer.setPhoneNumber(phoneNumber);
				customer.setAddress(address);

				data.add(customer);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		tableid.setItems(data);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		idid.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		streetAddressId.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getStreetAddress()));
		cityId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));
		regionId.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getRegion()));
		firstnameID.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastnameID.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		emailID.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneID.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		dobID.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
		genderId.setCellValueFactory(new PropertyValueFactory<>("gender"));
		try {
			refreshTable();
		} catch (NullPointerException e) {
			Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("An error occurred while loading the table");
			alert.setContentText(
					"The table could not be loaded due to a null pointer exception. Please check the code for any errors and try again.");
			alert.showAndWait();
		}
	}

	@FXML
	public void toaddORview(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("addORview.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}