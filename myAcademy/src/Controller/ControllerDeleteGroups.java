package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Grupo;
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

public class ControllerDeleteGroups {

	@FXML
	private ListView<Grupo> listVGrupos;
	@FXML
	private Button bDelete;
	@FXML
	private Button bBack;
	
	private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
	private int rsInsert;
	
	private Grupo grupo;
	
	private ObservableList<Grupo> listGrupos =  FXCollections.observableArrayList();
	
	private String grupoID;
	
	public void initialize() {
		String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
		
        bDelete.setOnAction(e -> deleteGroups());
        bBack.setOnAction(e -> back());
        
        //Se establece la conexion con la base de datos
        con = ControllerDB.getConnection();
        
        //Se crea una coleccion de objetos Grupo
        String query = "SELECT * FROM grupos";
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				grupo = new Grupo(rs.getString("id_grupo"),rs.getString("dni_profesor"),rs.getString("dia"),
						rs.getString("hora_inicio"),rs.getString("duracion"));
				
				listGrupos.add(grupo);
			}
			
			update();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void deleteGroups() {
		int i = 0;
		for (Grupo a :listGrupos) {
			if (a.getId_grupo().equalsIgnoreCase(grupoID)) {
				String query = "DELETE FROM grupos WHERE id_grupo = '"+grupoID+"'";
		
				try {
					stmt = con.createStatement();
					rsInsert = stmt.executeUpdate(query);
					stmt.close();
					//con.close();
    				back();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			i++;
		}		
	}
	
	public void update() {
		listVGrupos.setItems(listGrupos);
		
		listVGrupos.setCellFactory(param -> new ListCell<Grupo>() {
			protected void updateItem(Grupo a, boolean empty) {
		        super.updateItem(a, empty);
		        if (empty || a == null || a.getId_grupo() == null) {
		        	setText("");
		        } else {
		        	setText(a.getId_grupo());
		        	
		        	listVGrupos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Grupo>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Grupo> observable, Grupo oldValue, Grupo newValue) {
	                        grupoID = newValue.getId_grupo();	                       
	                    }
	                });
		        }
			}
		});
	}
	
	private void back() {
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/Administrador.fxml"));
			Stage primaryStage = (Stage) bBack.getScene().getWindow();
			primaryStage.setTitle("Administrador");
			primaryStage.getScene().setRoot(newRoot);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
