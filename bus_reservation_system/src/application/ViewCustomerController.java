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

public class ViewCustomerController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	

	@FXML
    private TableColumn<Customer, String> adressID;

    @FXML
    private TableColumn<Customer, LocalDate> dobID;

    @FXML
    private TableColumn<Customer, String> emailID;

    @FXML
    private TableColumn<Customer, String> firstnameID;

    @FXML
    private TableColumn<Customer, String> idid;

    @FXML
    private TableColumn<Customer, String> lastnameID;

    @FXML
    private TableColumn<Customer, String> phoneID;

	@FXML
	private TableView<Customer> tableid;

//	
//	@FXML
//	private void refreshTable() {
//		//create an observablelist to store data
//		ObservableList<Customer> data = FXCollections.observableArrayList();
//		
//		// fetch data from database and add it to the observable list
//        Connection connection = null;
//        PreparedStatement statement = null;
//        
//        try {
//            String url = "jdbc:mysql://localhost:3306/busreservation_db";
//            String databaseUsername = "customer";
//            String password = "Customer123$";
//            
//            connection = DriverManager.getConnection(url, databaseUsername, password);
//            String sql = "SELECT `bus`.`bus_id`, `bus`.`date`, "
//                    + "`bus`.`departure_time`, `bus`.`arrival_time`, `bus`.`bus_ticket_price`, `bus`.`number_of_seats`, "
//                    + "`bus_driver`.`first_name`, `route`.`source`, `route`.`destination` "
//                    + "FROM `bus` "
//                    + "LEFT JOIN `route` ON `bus`.`route_id` = `route`.`route_id` "
//                    + "LEFT JOIN `bus_driver` ON `bus`.`driver_id` = `bus_driver`.`bus_driver_id`";
//            
//            statement = connection.prepareStatement(sql);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//            	int id = resultSet.getInt("bus_id");
//            	Date sqlDate = resultSet.getDate("date");
//            	LocalDate date = sqlDate.toLocalDate();
//            	Time sqldep = resultSet.getTime("departure_time");
//            	LocalTime departureTime = sqldep.toLocalTime();
//            	Time sqlarr = resultSet.getTime("arrival_time");
//            	LocalTime arrivalTime = sqlarr.toLocalTime();
//            	double busTicketPrice = resultSet.getDouble("bus_ticket_price");
//            	int numberOfSeats = resultSet.getInt("number_of_seats");
//            	String name = resultSet.getString("bus_driver.first_name");
//            	String source = resultSet.getString("route.source");
//            	String destination = resultSet.getString("route.destination");
//            	
//            	Route route = new Route(source, destination);
//            	BusDriver driver = new BusDriver();
//            	driver.setFirstName(name);
//            	Bus bus = new Bus(driver, route, date, departureTime, arrivalTime, busTicketPrice, 
//            			numberOfSeats);
//            	bus.setBusId(id);
//            	
//            	data.add(bus);
//            	
//            }
//            
//        } catch(SQLException ex) {
//        	ex.printStackTrace();
//        }
//        
//        tableid.setItems(data);
//	}
//
//	@Override
	public void initialize(URL url, ResourceBundle rb) 
	{
		
	}
	
	
//		//initialize columns in table view
//		idid.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
//		firstnameID.setCellValueFactory(new PropertyValueFactory<>("first_name"));
//		lastnameID.setCellValueFactory(new PropertyValueFactory<>("last_name"));
//			
//		dobID.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));
//		emailID.setCellValueFactory(new PropertyValueFactory<>("email"));
//   
//		phoneID.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
//		adressID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
//		   
//		try {
//			refreshTable();
//		}
//		catch (NullPointerException e) {
//			  Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
//			  Alert alert = new Alert(Alert.AlertType.ERROR);
//			  alert.setTitle("Error");
//			  alert.setHeaderText("An error occurred while loading the table");
//			  alert.setContentText("The table could not be loaded due to a null pointer exception. Please check the code for any errors and try again.");
//			  alert.showAndWait();
//		}
//	}
	@FXML
	 public void toadmin(ActionEvent event) throws IOException {
	  Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	  scene = new Scene(root);
	  stage.setScene(scene);
	  stage.show();
	 }

}
