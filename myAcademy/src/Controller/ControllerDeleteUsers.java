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

import Model.Alumno;
import Model.Usuario;
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

public class ControllerDeleteUsers {

	@FXML
	private ListView<Usuario> listVUsuarios;
	@FXML
	private Button bDelete;
	@FXML
	private Button bBack;
	
	private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
	private int rsInsert;
	
	private Usuario usuario;
	
	private ObservableList<Usuario> listUsuarios =  FXCollections.observableArrayList();
	
	private String usuarioDNI;
	
	public void initialize() {
		String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
		
        bDelete.setOnAction(e -> deleteUsers());
        bBack.setOnAction(e -> back());
        
        //Se establece la conexion con la base de datos
        con = ControllerDB.getConnection();
        
        //Se crea una coleccion de objetos Alumno
        String query = "SELECT * FROM usuario  WHERE tipo_usuario = 'Alumno' OR tipo_usuario = 'Profesor'";
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				usuario = new Usuario(rs.getString("nombre_usuario"),rs.getString("nombre"),rs.getString("apellidos"),
						rs.getString("DNI"),rs.getString("tipo_usuario"),rs.getString("sexo"));
				
				listUsuarios.add(usuario);
			}
			
			update();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void deleteUsers() {
		int i = 0;
		for (Usuario a :listUsuarios) {
			if (a.getDNI().equalsIgnoreCase(usuarioDNI)) {
				String query = "DELETE FROM usuario WHERE dni = '"+usuarioDNI+"'";
		
				try {
					stmt = con.createStatement();
					rsInsert = stmt.executeUpdate(query);
					stmt.close();
					con.close();
    				back();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			i++;
		}		
	}
	
	public void update() {
		listVUsuarios.setItems(listUsuarios);
		
		listVUsuarios.setCellFactory(param -> new ListCell<Usuario>() {
			protected void updateItem(Usuario a, boolean empty) {
		        super.updateItem(a, empty);
		        if (empty || a == null || a.getNombre() == null) {
		        	setText("");
		        } else {
		        	setText(a.getUsername());
		        	
		        	listVUsuarios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
	                        usuarioDNI = newValue.getDNI();	                       
	                    }
	                });
		        }
			}
		});
	}
	
	private void back() {
		try {
			con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
