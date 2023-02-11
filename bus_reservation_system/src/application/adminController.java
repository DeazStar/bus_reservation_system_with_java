package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import com.busreservationsystem.model.*;
import com.busreservationsystem.model.Route;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.controller.Administrator;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;

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
	private TableColumn<Bus, String> driverid;

	@FXML
	private TableColumn<Bus, String> sourceid;

	@FXML
	private TableColumn<Bus, Integer> totalseatsid;

	@FXML
	private TableColumn<Bus, Double> priceid;
	
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
		Bus bus = new Bus();
		Route route = new Route();

		// add data
		route.setSource(source_TextField.getText());
		route.setDestination(destination_TextField.getText());

		// add data to bus
		bus.setDriver(null);
		bus.setRoute(route);
		bus.setDate(dateId.getValue());
		bus.setDepartureTime(LocalTime.parse(deptime_TextField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")));
		bus.setArrivalTime(LocalTime.parse(arrivaltime_TextField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")));
		bus.setBusTicketPrice(Double.parseDouble(price_TextField.getText()));
		bus.setnumberOfSeats(Integer.parseInt(totald_TextField.getText()));

		Administrator admin = new Administrator();

		admin.addBus(bus);
		admin.store();
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Bus added Succesfuly");
		alert.showAndWait();
		
		refreshTable();
		

	}
	
	

	@FXML
	private void refreshTable() {
		//create an observablelist to store data
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
                    + "`bus_driver`.`first_name`, `route`.`source`, `route`.`destination` "
                    + "FROM `bus` "
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
            	String name = resultSet.getString("bus_driver.first_name");
            	String source = resultSet.getString("route.source");
            	String destination = resultSet.getString("route.destination");
            	
            	Route route = new Route(source, destination);
            	BusDriver driver = new BusDriver();
            	driver.setFirstName(name);
            	Bus bus = new Bus(driver, route, date, departureTime, arrivalTime, busTicketPrice, 
            			numberOfSeats);
            	bus.setBusId(id);
            	
            	data.add(bus);
            	
            }
            
        } catch(SQLException ex) {
        	ex.printStackTrace();
        }
        
        tableid.setItems(data);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//initialize columns in table view
	    busnoid.setCellValueFactory(new PropertyValueFactory<>("busId"));
	    sourceid.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getRoute().getSource()));
	    destinationid.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getRoute().getDestination()));
	    datetableId.setCellValueFactory(new PropertyValueFactory<>("date"));
	    deptimeid.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
	    arrtimeid.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
	    priceid.setCellValueFactory(new PropertyValueFactory<>("busTicketPrice"));
	    totalseatsid.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));
	    
		try {
			refreshTable();
		}
		catch (NullPointerException e) {
			  Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
			  Alert alert = new Alert(Alert.AlertType.ERROR);
			  alert.setTitle("Error");
			  alert.setHeaderText("An error occurred while loading the table");
			  alert.setContentText("The table could not be loaded due to a null pointer exception. Please check the code for any errors and try again.");
			  alert.showAndWait();
		}
	}
	@FXML
	 public void toViewCustomer(ActionEvent event) throws IOException {
	  Parent root = FXMLLoader.load(getClass().getResource("ViewCustomer.fxml"));
	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	  scene = new Scene(root);
	  stage.setScene(scene);
	  stage.show();
	 }
	 public void toadmin(ActionEvent event) throws IOException {
		  Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
		 }
}

