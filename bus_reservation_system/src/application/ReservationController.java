/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package application;
import java.io.IOException;
import java.lang.*;
import com.busreservationsystem.model.Bus;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.busreservationsystem.model.*;
import com.busreservationsystem.model.Route;
import com.busreservationsystem.user.Customer;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.controller.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;


public class ReservationController implements Initializable {
    
     @FXML
    private TableColumn<Bus, LocalTime> arrive;

    @FXML
    private TableColumn<Bus, Integer> busId;

    @FXML
    private TableColumn<Bus, LocalDate> date;

    @FXML
    private DatePicker dateId;

    @FXML
    private TableColumn<Bus, LocalTime> departure;

    @FXML
    private TableColumn<Bus, String> destination;

    @FXML
    private TableColumn<Bus, String> driver;

    @FXML
    private ComboBox<String> from;

    @FXML
    private TableColumn<String, Integer> price;

    @FXML
    private Button search;

    @FXML
    private TableColumn<Bus, Integer> seat;

    @FXML
    private TableColumn<Bus, String> source;

    @FXML
    private TableView<Bus> tableId;

    @FXML
    private ComboBox<String> to;
    
    private Customer customer;
    
    ResultSet resultSet;
    PreparedStatement pst;

    @FXML
    void search(ActionEvent event) {
        loadData();
        
    }
    
    public void setCustomer(Customer customer) {
    	this.customer = customer;
    }
    
    public Customer getCustomer() {
    	return this.customer;
    }
    
    public int bookSeat(int customerId, int busId) {
        String url = "jdbc:mysql://localhost:3306/busreservation_db";
        String databaseUsername = "customer";
        String password = "Customer123$";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int availableSeat = -1;
        try {
            conn = DriverManager.getConnection(url, databaseUsername, password);
            String query = "SELECT number_of_seats FROM bus WHERE bus_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, busId);
            rs = stmt.executeQuery();
            int numSeats = 0;
            if (rs.next()) {
                numSeats = rs.getInt("number_of_seats");
            }
            
            query = "SELECT seat FROM reservation WHERE bus_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, busId);
            rs = stmt.executeQuery();
            Set<Integer> reservedSeats = new HashSet<>();
            while (rs.next()) {
                reservedSeats.add(rs.getInt("seat"));
            }
            
            for (int i = 1; i <= numSeats; i++) {
                if (!reservedSeats.contains(i)) {
                    availableSeat = i;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return availableSeat;
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	//LoginController loginController = new LoginController();

    	/*Parent root;
    	try {
    	    root = loader.load();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	    return;
    	}
*/
 

    	// show the login window
    
    	// get the customer object
    	//Customer customer = loginController.getCustomer();
    	
    	//System.out.println(this.customer);
    	
    	
    	//persists to open a new page so if the customer click the x button the program will exitd==Lo	
    	/*if (this.customer == null) {
    		Platform.exit();
    		System.exit(0);
    	}*/
    	Customer customer = StaticCustomer.customer;
    	
    	System.out.println(customer);
    	Reservation admin = new Reservation();
        TableColumn<Bus, Void> actionCol = new TableColumn<>("Action");
        Callback<TableColumn<Bus, Void>, TableCell<Bus, Void>> cellFactory = new Callback<TableColumn<Bus, Void>, TableCell<Bus, Void>>() {
          @Override
          public TableCell<Bus, Void> call(final TableColumn<Bus, Void> param) {
            final TableCell<Bus, Void> cell = new TableCell<Bus, Void>() {
              private final Button editButton = new Button("Book");

              {
                editButton.setOnAction((ActionEvent event) -> {
                  Bus data = getTableView().getItems().get(getIndex());
                  int seatNumber = bookSeat(customer.getCustomerId(), data.getBusId());
                  System.out.println(seatNumber);
                  try {
                	  customer.setReservation(seatNumber, data, data.getBusTicketPrice());        
                  } catch(Exception e) {
          			Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        			Alert alert = new Alert(Alert.AlertType.ERROR);
        			alert.setTitle("Error");
        			alert.setHeaderText("An error occurred while booking");
        			alert.setContentText(
        					"could not reserve a seat in the bus");
        			alert.showAndWait();
                  }
          		  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        		  alert.setContentText("Booked  Succesfuly");
        		  alert.showAndWait();
                  
                });
              }

              @Override
              public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setGraphic(null);
                } else {
                  HBox buttons = new HBox();
                  buttons.getChildren().addAll(editButton);
                  setGraphic(buttons);
                }
              }
            };
            return cell;
          }
        };
        

        actionCol.setCellFactory(cellFactory);//cpy
        tableId.getColumns().addAll(actionCol);//cpy
        connect();
        
    busId.setCellValueFactory(new PropertyValueFactory<>("busId"));
//    driver.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getDrivere().getFirstName()));
    price.setCellValueFactory(new PropertyValueFactory<>("busTicketPrice"));
    departure.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
    arrive.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
    source.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getRoute().getSource()));
    destination.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getRoute().getDestination()));
    date.setCellValueFactory(new PropertyValueFactory<>("date"));
    seat.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));

        
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
           
            pst = connection.prepareStatement("select destination, source from route");
            resultSet = pst.executeQuery();
            while (resultSet.next()) {
                
                from.getItems().add(resultSet.getString("source"));
                to.getItems().add(resultSet.getString("destination"));
                
        }

    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

    private void loadData() {
        
        Reservation reserver = new Reservation();
        ObservableList<Bus> data = FXCollections.observableArrayList();
        String sour = from.getSelectionModel().getSelectedItem();
        String dest = to.getSelectionModel().getSelectedItem();
        String dateString = ((LocalDate) this.dateId.getValue()).format(DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate Date = LocalDate.parse(dateString);

        data.addAll(reserver.showAvailableBus(Date, sour,dest));
        tableId.setItems(data);
    }
    

	
    
   
    
}
