/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package application;
import java.lang.*;
import com.busreservationsystem.model.Bus;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.busreservationsystem.model.*;
import com.busreservationsystem.model.Route;
import com.busreservationsystem.model.Bus;
import com.busreservationsystem.model.BusDriver;
import com.busreservationsystem.controller.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author HP
 */
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
    
    ResultSet resultSet;
    PreparedStatement pst;

    @FXML
    void search(ActionEvent event) {
        loadData();
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
