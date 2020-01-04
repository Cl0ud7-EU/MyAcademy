package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;

public class ControllerAdminUser {

    @FXML
    private Label labErr;
    @FXML
    private TextField textName;
    @FXML
    private TextField textPass;
    @FXML
    private TextField textApellidos;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textDNI;
    @FXML
    private TextField textTel;
    @FXML
    private ComboBox combType;
    @FXML
    private ComboBox combGender;
    @FXML
    private Button bAdd;
    @FXML
    private ListView<Usuario> listUsers;
    
    private String email;
    
    
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private int rAgregar;
    private Usuario usuario;
    
    private ObservableList<Usuario> listUsuario =  FXCollections.observableArrayList();
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bAdd.setOnAction(e -> agregarUsuario());
        combType.getItems().addAll("Profesor", "Alumno");
        combGender.getItems().addAll("Masculino", "Femenino");
        
        
        //Conexion con la DB
        con = ControllerDB.getConnection();
        
        //Creamos una coleccion de objetos Usuario
        String query = "SELECT * FROM usuario WHERE tipo_usuario != 'admin'";
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nombre_usuario"),rs.getString("nombre"),rs.getString("apellidos"),
						rs.getString("DNI"),rs.getString("tipo_usuario"),rs.getString("sexo"));
				usuarios.add(usuario);
				listUsuario.add(usuario);
				
				
			}
			listUsers.setItems(listUsuario);
			
			listUsers.setCellFactory(param -> new ListCell<Usuario>() {
				@Override
		        protected void updateItem(Usuario u, boolean empty){
		        super.updateItem(u, empty);
		            if(empty || u == null || u.getNombre() == null){
		                setText("");
		            }
		            else{
		                setText(u.getNombre()+" "+u.getDNI());
		                
		                //Listener que actualiza los campos al selecciona un usuario
		                listUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>() {
		                    @Override
		                    public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
		                        textName.setText(newValue.getNombre());
		                        textApellidos.setText(newValue.getApellidos());
		                        textDNI.setText(newValue.getDNI());
		                        combType.getSelectionModel().select(newValue.getTipo());
		                        combGender.getSelectionModel().select(newValue.getSexo());
		                    }
		                });
		                //listUsers.getSelectionModel().getSelectedIndex();   
		            }

		        } 
		        
		    });
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
  
        
    }
    public void  cambio(Parent newRoot) {
    	Stage primaryStage = (Stage) bAdd.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);	
    }
   
    public void  agregarUsuario() {
    	if(combType.getValue() == "Alumno") {
    		email = null;
    	}
    	else {
    		email = textEmail.getText();
    	}
    	//Falta cambiar el nombre de estudiante que tenemos que crearlo y el telefono
    	/*String query = "UPDATE `usuario` (`dni`, `tipo_usuario`, `nombre`, `apellidos`, `sexo`, `id_clase`, `nombre_usuario`) VALUES "
    			+ "('"+ textDNI.getText() +"', '"+combType.getValue()+"', '"+textName.getText()+"', '"+ textApellidos.getText() +"', '"+ combGender.getValue() +"'"
    					+ ", NULL, '"+textName.getText()+"@estudiantes"+"') WHERE `dni` = '"+ textDNI.getText() +"'";*/
    	String query = "UPDATE `usuario` SET "
    			+ " `dni` = '"+textDNI.getText() 
    			+ "', `tipo_usuario` = '"+combType.getValue()
    			+ "', `nombre` = '"+textName.getText()
    			+ "', `apellidos` = '"+textApellidos.getText()
    			+ "', `sexo` = '"+combGender.getValue()
    			+ "', `id_clase` = NULL"
    			+ ", `nombre_usuario` = '"+textName.getText()+"@estudiantes"
    			+ "' WHERE `usuario`.`dni` = '"+textDNI.getText()+"';";
    		System.out.println(query);
		try {
			stmt = con.createStatement();
			rAgregar = stmt.executeUpdate(query);
			stmt.close();
			con.close();
			System.out.print("hecho");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    	
    }
    
}