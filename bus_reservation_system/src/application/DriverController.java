package application;

import com.busreservationsystem.base.Address;
import com.busreservationsystem.controller.Administrator;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.model.Route;
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

import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class DriverController implements Initializable {

	@FXML
	private Button AddDriverId;

	@FXML
	private TableColumn<BusDriver, Integer> BusId;

	@FXML
	private TextField CityId;

	@FXML
	private DatePicker DateId;

	@FXML
	private TextField EmailId;

	@FXML
	private TextField FirstNameId;

	@FXML
	private TextField LastNameId;

	@FXML
	private TextField PhoneNumberId;

	@FXML
	private TextField RegionId;

	@FXML
	private TextField StreetId;

//        @FXML
//        private TextField genderTextFieldId;

	@FXML
	private ToggleGroup gender;

	@FXML
	private RadioButton male;

	@FXML
	private RadioButton female;

	@FXML
	private TableColumn<BusDriver, String> cityId;

	@FXML
	private TableColumn<BusDriver, LocalDate> dobID;

	@FXML
	private TableColumn<BusDriver, String> firstnameID;

	@FXML
	private TableColumn<BusDriver, String> genderId;

	@FXML
	private TableColumn<BusDriver, Integer> idid;

	@FXML
	private TableColumn<BusDriver, String> lastnameID;

	@FXML
	private TableColumn<BusDriver, String> phoneNumberId;

	@FXML
	private TableColumn<BusDriver, String> emailId;

	@FXML
	private TableColumn<BusDriver, String> regionId;

	@FXML
	private TableColumn<BusDriver, String> streetAddressId;

	@FXML
	private TableView<BusDriver> tableId;

	@FXML
	private void add() {
		BusDriver driver = new BusDriver();
		Address address = new Address();

		address.setStreetAddress(StreetId.getText());
		address.setCity(CityId.getText());
		address.setRegion(RegionId.getText());

		String genderValue = "";
		ToggleButton selected = (ToggleButton) gender.getSelectedToggle();
		if (selected.getText().equals("male")) {
			genderValue = "M";
		} else if (selected.getText().equals("female")) {
			genderValue = "F";
		}

		driver.setGender(genderValue);
		driver.setFirstName(FirstNameId.getText());
		driver.setLastName(LastNameId.getText());
//		driver.setGender(genderTextFieldId.getText());
		driver.setDateOfBirth(DateId.getValue());
		driver.setEmail(EmailId.getText());
		driver.setPhoneNumber(PhoneNumberId.getText());
		driver.setAssignedBus(null);
		driver.setAddress(address);
		Administrator admin = new Administrator();
		admin.addDriver(driver);
		admin.saveDriver();

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Driver added Succesfuly");
		alert.showAndWait();

		refreshTable();
	}

	private void refreshTable() {
		// create an observablelist to store data
		ObservableList<BusDriver> data = FXCollections.observableArrayList();

		// fetch data from database and add it to the observable list
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String databaseUsername = "customer";
			String password = "Customer123$";
			connection = DriverManager.getConnection(url, databaseUsername, password);

			String sql = "SELECT `bus_driver`.`bus_driver_id`, `bus_driver`.`first_name`, `bus_driver`.`last_name`, "
					+ "`bus_driver`.`gender`, `bus_driver`.`date_of_birth`, `bus_driver`.`email`, `bus_driver`.`phone_number`, "
					+ "`bus_driver`.`assigned_bus`, `address`.`street_address`, `address`.`city`, `address`.`region`, "
					+ "`bus`.`bus_id`, `bus`.`date`, `bus`.`departure_time`, `bus`.`arrival_time`, `bus`.`bus_ticket_price`, "
					+ "`bus`.`number_of_seats`, `route`.`source`, `route`.`destination` " + "FROM `bus_driver` "
					+ "LEFT JOIN `address` ON `bus_driver`.`address_id` = `address`.`address_id` "
					+ "LEFT JOIN `bus` ON `bus_driver`.`assigned_bus` = `bus`.`bus_id` "
					+ "LEFT JOIN `route` ON `bus`.`route_id` = `route`.`route_id`";

			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("bus_driver_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String gender = resultSet.getString("gender");
				Date sqlDate = resultSet.getDate("date_of_birth");
				LocalDate date = sqlDate.toLocalDate();
				String email = resultSet.getString("email");
				String phoneNumber = resultSet.getString("phone_number");
				int assigned_bus = resultSet.getInt("assigned_bus");
				String streetAddress = resultSet.getString("street_address");
				String city = resultSet.getString("city");
				String region = resultSet.getString("region");

				Address address = new Address(streetAddress, city, region);
				BusDriver driver = new BusDriver();
				driver.setDriverId(id);
				driver.setFirstName(firstName);
				driver.setLastName(lastName);
				driver.setGender(gender);
				driver.setDateOfBirth(date);
				driver.setEmail(email);
				driver.setPhoneNumber(phoneNumber);
				driver.setAddress(address);

				if (assigned_bus != 0) {
					int busId = resultSet.getInt("bus_id");
					LocalDate busDate = resultSet.getDate("date").toLocalDate();
					LocalTime departureTime = resultSet.getTime("departure_time").toLocalTime();
					LocalTime arrivalTime = resultSet.getTime("arrival_time").toLocalTime();
					double ticketPrice = resultSet.getDouble("bus_ticket_price");
					int numberOfSeats = resultSet.getInt("number_of_seats");
					String source = resultSet.getString("source");
					String destination = resultSet.getString("destination");

					Route route = new Route(source, destination);

					Bus bus = new Bus(driver, route, date, departureTime, arrivalTime, ticketPrice, numberOfSeats);
					bus.setBusId(busId);
					driver.setAssignedBus(bus);
				} else {
					Bus bus = null;
					driver.setAssignedBus(bus);
				}

				data.add(driver);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();

		}

		tableId.setItems(data);

	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// create columns with delete and edit buttons for each row
		Administrator admin = new Administrator();
		TableColumn<BusDriver, Void> actionCol = new TableColumn<>("Action");
		Callback<TableColumn<BusDriver, Void>, TableCell<BusDriver, Void>> cellFactory = new Callback<TableColumn<BusDriver, Void>, TableCell<BusDriver, Void>>() {
			@Override
			public TableCell<BusDriver, Void> call(final TableColumn<BusDriver, Void> param) {
				final TableCell<BusDriver, Void> cell = new TableCell<BusDriver, Void>() {
					private final Button deleteButton = new Button("Delete");
					private final Button editButton = new Button("Edit");

					{
						deleteButton.setOnAction((ActionEvent event) -> {
							BusDriver data = getTableView().getItems().get(getIndex());
							// call the delete method
							admin.removeDriver(data);
							getTableView().getItems().remove(data);
							refreshTable();
						});
						editButton.setOnAction((ActionEvent event) -> {
							BusDriver data = getTableView().getItems().get(getIndex());
							System.out.println(data.getFirstName());
							StaticCustomer.driver = data;
							FXMLLoader loader = new FXMLLoader(getClass().getResource("editDriver.fxml"));

							Parent root;
							try {
								root = loader.load();
							} catch (IOException e) {
								e.printStackTrace();
								return;
							}

							EditDriverController editController = loader.getController();

							Stage stage = new Stage();
							stage.setScene(new Scene(root));
							stage.showAndWait();

							BusDriver updatedDriver = editController.getUpdateDriver();

							updatedDriver.setAssignedBus(data.getAssignedBus());
							updatedDriver.getAddress().setAddressId(data.getAddress().getAddressId());
							// call the edit method
							admin.editDriverInfo(data, updatedDriver);

							/*
							 * int selectedIndex = getTableRow().getIndex();
							 * tableid.getItems().set(selectedIndex, updatedBus);
							 */
							refreshTable();
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							HBox buttons = new HBox();
							buttons.getChildren().addAll(editButton, deleteButton);
							setGraphic(buttons);
						}
					}
				};
				return cell;
			}
		};

		actionCol.setCellFactory(cellFactory);// cpy
		tableId.getColumns().addAll(actionCol);// cpy

		// initialize columns in table view
		idid.setCellValueFactory(new PropertyValueFactory<>("driverId"));
		streetAddressId.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getStreetAddress()));
		cityId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));
		regionId.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getRegion()));
		firstnameID.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastnameID.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		emailId.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneNumberId.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		dobID.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
		genderId.setCellValueFactory(new PropertyValueFactory<>("gender"));
		BusId.setCellValueFactory(cellData -> {
			Bus assignedBus = cellData.getValue().getAssignedBus();
			int busId = assignedBus != null ? assignedBus.getBusId() : 0;
			return new SimpleIntegerProperty(busId).asObject();
		});
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

	private Stage stage;
	private Scene scene;

	@FXML
	public void Toaddorreview(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("addORview.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

}