package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.JOptionPane;

import com.busreservationsystem.model.*;
import com.busreservationsystem.model.Route;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.Exception.InvalidDateFormatException;
import com.busreservationsystem.Exception.InvalidTimeFormatException;
import com.busreservationsystem.controller.Administrator;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import application.EditBusController;
public class adminController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TableColumn<Bus, LocalTime> arrtimeid;

	@FXML
	private TableColumn<Bus, Integer> busnoid;

	@FXML
	private TableColumn<Bus, LocalTime> deptimeid;
	@FXML
	private TableColumn<Bus, String> destinationid;

	@FXML
	private TableColumn<Bus, Integer> driverid;

	@FXML
	private TableColumn<Bus, String> sourceid;

	@FXML
	private TableColumn<Bus, Integer> totalseatsid;

	@FXML
	private TableColumn<Bus, Double> priceid;
	
    @FXML
    private ComboBox<Integer> assignDriver;

	@FXML
	private TableColumn<Bus, LocalDate> datetableId;

	@FXML
	private DatePicker dateId;
	@FXML
	private TextField arrivaltime_TextField;

	@FXML
	private TextField deptime_TextField;

	@FXML
	private Button addbusid;

	@FXML
	private TextField destination_TextField;

	@FXML
	private TextField driver_TextField;

	@FXML
	private TextField source_TextField;

	@FXML
	private TableView<Bus> tableid;

	@FXML
	private TextField totald_TextField;
	@FXML
	private TextField price_TextField;

	@FXML
	private void add() {
		
		if (source_TextField.getText().isBlank() == true || source_TextField.getText().isBlank() == true || price_TextField.getText().isBlank() == true)
		{
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setContentText("Please Enter Full Bus Information");
			alert.showAndWait();
			return;
		}
		
		
		Bus bus = new Bus();
		Route route = new Route();

		// add data
		route.setSource(source_TextField.getText());
		route.setDestination(destination_TextField.getText());

		// add data to bus
		bus.setDriver(null);
		bus.setRoute(route);
		bus.setDate(dateId.getValue());
				
		try 
		{
			  validateTime(deptime_TextField.getText());
			  
			  bus.setDepartureTime(LocalTime.parse(deptime_TextField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")));
			  
			  validateTime(arrivaltime_TextField.getText());
			  
				bus.setArrivalTime(LocalTime.parse(arrivaltime_TextField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")));

			} 
		catch (InvalidTimeFormatException e) 
			{
				System.out.println("Error: " + e.getMessage());
			}

		bus.setBusTicketPrice(Double.parseDouble(price_TextField.getText()));
		bus.setnumberOfSeats(Integer.parseInt(totald_TextField.getText()));
		
		Administrator admin = new Administrator();

		admin.addBus(bus);
		int busId = admin.store();
		
		
		System.out.println(busId);
		int driverId = assignDriver.getSelectionModel().getSelectedItem();
		
		System.out.println(busId);
		
		admin.assignDriverToBus(busId, driverId);
		

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Bus added Succesfuly");
		alert.showAndWait();

		refreshTable();

	}

	 void validateTime(String time) throws InvalidTimeFormatException {
		  // Check if the input time is in the correct format
		  if (!time.matches("\\d{2}:\\d{2}:\\d{2}")) {
		    throw new InvalidTimeFormatException();
		  }
		}
	 
	@FXML
	private void refreshTable() {
		// create an observablelist to store data
		ObservableList<Bus> data = FXCollections.observableArrayList();

		// fetch data from database and add it to the observable list
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			String url = "jdbc:mysql://localhost:3306/busreservation_db";
			String databaseUsername = "customer";
			String password = "Customer123$";

			connection = DriverManager.getConnection(url, databaseUsername, password);
			String sql = "SELECT `bus`.`bus_id`, `bus`.`date`, "
					+ "`bus`.`departure_time`, `bus`.`arrival_time`, `bus`.`bus_ticket_price`, `bus`.`number_of_seats`, "
					+ "`bus_driver`.`bus_driver_id`, `route`.`source`, `route`.`destination` " + "FROM `bus` "
					+ "LEFT JOIN `route` ON `bus`.`route_id` = `route`.`route_id` "
					+ "LEFT JOIN `bus_driver` ON `bus`.`driver_id` = `bus_driver`.`bus_driver_id`";

			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("bus_id");
				Date sqlDate = resultSet.getDate("date");
				LocalDate date = sqlDate.toLocalDate();
				Time sqldep = resultSet.getTime("departure_time");
				LocalTime departureTime = sqldep.toLocalTime();
				Time sqlarr = resultSet.getTime("arrival_time");
				LocalTime arrivalTime = sqlarr.toLocalTime();
				double busTicketPrice = resultSet.getDouble("bus_ticket_price");
				int numberOfSeats = resultSet.getInt("number_of_seats");
				int driverId = resultSet.getInt("bus_driver.bus_driver_id");
				String source = resultSet.getString("route.source");
				String destination = resultSet.getString("route.destination");

				Route route = new Route(source, destination);
				BusDriver driver = new BusDriver();
				driver.setDriverId(driverId);
				Bus bus = new Bus(driver, route, date, departureTime, arrivalTime, busTicketPrice, numberOfSeats);
				bus.setBusId(id);

				data.add(bus);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		tableid.setItems(data);
	}
	
	
    public void connect() {
        ObservableList<Bus> data = FXCollections.observableArrayList();
		
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            String url = "jdbc:mysql://localhost:3306/busreservation_db";
            String databaseUsername = "customer";
            String password = "Customer123$";
            connection = DriverManager.getConnection(url, databaseUsername, password);
           
            statement = connection.prepareStatement("select bus_driver_id from bus_driver");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                
            	assignDriver.getItems().add(resultSet.getInt("bus_driver_id"));
        }

    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		connect();
		// create columns with delete and edit buttons for each row
		Administrator admin = new Administrator();
		TableColumn<Bus, Void> actionCol = new TableColumn<>("Action");
		Callback<TableColumn<Bus, Void>, TableCell<Bus, Void>> cellFactory = new Callback<TableColumn<Bus, Void>, TableCell<Bus, Void>>() {
			@Override
			public TableCell<Bus, Void> call(final TableColumn<Bus, Void> param) {
				final TableCell<Bus, Void> cell = new TableCell<Bus, Void>() {
					private final Button deleteButton = new Button("Delete");
					private final Button editButton = new Button("Edit");

					{
						deleteButton.setOnAction((ActionEvent event) -> {
							Bus data = getTableView().getItems().get(getIndex());
							// call the delete method
							admin.removeBus(data);
							getTableView().getItems().remove(data);
							refreshTable();
						});
						editButton.setOnAction((ActionEvent event) -> {
							Bus data = getTableView().getItems().get(getIndex());
							
							FXMLLoader loader = new FXMLLoader(getClass().getResource("editBus.fxml"));
							
							  Parent root;
							    try {
							        root = loader.load();
							    } catch (IOException e) {
							        e.printStackTrace();
							        return;
							    }
							    
							EditBusController editController = loader.getController();
							
						    Stage stage = new Stage();
						    stage.setScene(new Scene(root));
						    stage.showAndWait();
						    
						    
					        Bus updatedBus = editController.geteUpdateBus();
					        
					        updatedBus.setDriver(data.getDrivere());
					        updatedBus.getRoute().setRouteId(data.getRoute().getRouteId());
							// call the edit method
							admin.editBus(data, updatedBus); 
							

							/*int selectedIndex = getTableRow().getIndex();
							tableid.getItems().set(selectedIndex, updatedBus);*/
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

		actionCol.setCellFactory(cellFactory);
		tableid.getColumns().addAll(actionCol);
		// initialize columns in table view
		busnoid.setCellValueFactory(new PropertyValueFactory<>("busId"));
		driverid.setCellValueFactory(cellData -> {
		    int driverId = cellData.getValue().getDrivere().getDriverId();
		    return new SimpleIntegerProperty(driverId).asObject();
		});
		sourceid.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoute().getSource()));
		destinationid.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getRoute().getDestination()));
		datetableId.setCellValueFactory(new PropertyValueFactory<>("date"));
		deptimeid.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
		arrtimeid.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
		priceid.setCellValueFactory(new PropertyValueFactory<>("busTicketPrice"));
		totalseatsid.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));

		// make the table editable
		tableid.setEditable(true);
		// the try block should be updated
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
	public void toViewCustomer(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("viewCustomer.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void toadmin(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	 public void ToaddORview(ActionEvent event) throws IOException {
		  Parent root = FXMLLoader.load(getClass().getResource("addORview.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}


	@FXML
	 public void ToWelcome(ActionEvent event) throws IOException {
		  Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}

}
