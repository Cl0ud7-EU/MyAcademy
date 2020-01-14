package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Alumno;
import Model.Pago;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerShowPayments {

	@FXML
	private TextField textAmount;
	@FXML
	private TextField textDate;
	@FXML
	private ListView<Pago> listVPagos;
	@FXML
	private Button bBack;
	
	private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
	
	private Pago pago;
	
	private ObservableList<Pago> listPagos =  FXCollections.observableArrayList();
	private String parametro;
	
	public void initialize() {
		String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");        
		
        bBack.setOnAction(e -> back());
        
        //Se establece la conexion con la base de datos
        con = ControllerDB.getConnection();
        
        Platform.runLater(() -> {
        	//Se crea una coleccion de objetos Pago
            String query = "SELECT * FROM pago WHERE dni_alumno = '"+this.parametro+"'";
            try {
    			stmt = con.createStatement();
    			rs = stmt.executeQuery(query);
    			
    			while(rs.next()) {
    				pago = new Pago(rs.getString("id_pago"),rs.getString("cantidad"),rs.getString("fecha"), rs.getString("dni_alumno"));
    				
    				listPagos.add(pago);
    			}
    			
    			showPayments();
    		} catch (SQLException e1) {
    			e1.printStackTrace();
    		}
        });
        
        
	}
	
	public void showPayments() {
		listVPagos.setItems(listPagos);
		
		listVPagos.setCellFactory(param -> new ListCell<Pago>() {
			protected void updateItem(Pago a, boolean empty) {
		        super.updateItem(a, empty);
		        if (empty || a == null || a.getId_pago() == null) {
		        	setText("");
		        } else {
		        	setText(a.getId_pago());
		        	
		        	listVPagos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pago>() {
	                    public void changed(ObservableValue<? extends Pago> observable, Pago oldValue, Pago newValue) {
	                        textAmount.setText(newValue.getCantidad());
	                        textDate.setText(newValue.getFecha());
	                       
	                    }
	                });
		        }
			}
		});
	}
	
	public void  cambio(Parent newRoot, String title, int height, int width) {
    	Stage primaryStage = (Stage) bBack.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }
	
	private void back() {
		FXMLLoader newRoot;
		try {
			newRoot = new FXMLLoader(getClass().getResource("/View/Alumno.fxml"));
			Parent root = (Parent)newRoot.load();
			ControllerAlumno controller = newRoot.<ControllerAlumno>getController();
			
			controller.setUser(this.parametro);
			cambio(root, "Alumno", 500, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUser(String dni) {
		this.parametro = dni;		
	}
	
}
