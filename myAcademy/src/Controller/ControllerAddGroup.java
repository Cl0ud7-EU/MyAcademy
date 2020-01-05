package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import Model.Profesor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerAddGroup {

   
    @FXML
    private Button bAdd;
    @FXML
    private TextField textName;
    @FXML
    private ListView<Profesor> listVProfes;
 
    
    private ObservableList<Profesor> listProfesores =  FXCollections.observableArrayList();
    private Profesor profesor;
    
    
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private int rsInsert;
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bAdd.setOnAction(e -> addGrupo());
        
        
        //Conexion con la DB
        con = ControllerDB.getConnection();
        
      //Creamos una coleccion de objetos Profesor
        String query = "SELECT * FROM usuario  WHERE tipo_usuario = 'Profesor'";
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Profesor profesor = new Profesor(rs.getString("nombre_usuario"),rs.getString("nombre"),rs.getString("apellidos"),
						rs.getString("DNI"),rs.getString("tipo_usuario"),rs.getString("sexo"), rs.getString("email"));
				listProfesores.add(profesor);
				
				
			}
			updateList();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
  
        
    }
    public void  cambio(Parent newRoot) {
    	Stage primaryStage = (Stage) bAdd.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);	
    }
   
    public void  addGrupo() {
    	
    	//Falta cambiar el nombre de estudiante que tenemos que crearlo
    	String query = "INSERT INTO `grupos` (`id_grupo`, `dni_profesor`) VALUES "
    			+ "('"+ textName.getText() +"', '"+listVProfes.getSelectionModel().getSelectedItem().getDNI()+"')";
    	
    	
		try {
			stmt = con.createStatement();
			rsInsert = stmt.executeUpdate(query);
			stmt.close();
			con.close();
			System.out.print("hecho");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void  updateList() {
    	
    	listVProfes.setItems(listProfesores);
		
    	listVProfes.setCellFactory(param -> new ListCell<Profesor>() {
			@Override
	        protected void updateItem(Profesor p, boolean empty){
	        super.updateItem(p, empty);
	            if(empty || p == null || p.getNombre() == null){
	                setText("");
	            }
	            else{
	                setText(p.getUsername());
	                
	                //Listener que actualiza los campos al seleccionar un usuario
	                listVProfes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profesor>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Profesor> observable, Profesor oldValue, Profesor newValue) {
	                      //  textName.setText(newValue.getNombre());
	                      
	                    }
	                });
	                  
	            }
	            
	        } 
	        
	    });
    }
}