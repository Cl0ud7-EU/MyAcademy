package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerAddUser {

    @FXML
    private Label labErr;
    @FXML
    private Label labEmail;
    @FXML
    private Label labTel;
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
    private Button bBack;
    
    private String email;
    
    private String parametro;
    private Connection con = null;
    private Statement stmt = null;
    private int rs;
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bAdd.setOnAction(e -> modificarUsuario());
        //Boton volver
        bBack.setOnAction(e -> back());
        
        
        combType.setOnAction(e -> cambioTipo());
        combType.getItems().addAll("Profesor", "Alumno");
        combType.getSelectionModel().select("Alumno");
        combGender.getItems().addAll("Masculino", "Femenino");
        combGender.getSelectionModel().select("Femenino");
        textEmail.setVisible(false);
        labEmail.setVisible(false);
        
    
        //Conexion con la DB
        con = ControllerDB.getConnection();
        
        Platform.runLater(() -> {

        	System.out.println(parametro);

        });
        
  
        
    }
    public void  cambio(Parent newRoot) {
    	Stage primaryStage = (Stage) bAdd.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);	
    }
   
    public void  modificarUsuario() {
    	if(combType.getValue() == "Alumno") {
    		email = "NULL";
    	}
    	else {
    		email = textEmail.getText();
    	}
    	//Falta cambiar el nombre de estudiante que tenemos que crearlo
    	String query = "INSERT INTO `usuario` (`dni`, `tipo_usuario`, `nombre`, `apellidos`, `sexo`, `id_grupo`, `nombre_usuario`, `contraseña`,  `email`, `evaluacion`) VALUES "
    			+ "('"+ textDNI.getText() +"', '"+combType.getValue()+"', '"+ textName.getText() +"', '"+ textApellidos.getText() +"', '"+ combGender.getValue() +"'"
    					+ ", NULL, '"+textName.getText()+textDNI.getText()+"', '"+textPass.getText()+"','"+ email +"','Sin nota'  )";
    	
    	
		try {
			stmt = con.createStatement();
			rs = stmt.executeUpdate(query);
			stmt.close();
			con.close();
			System.out.print("hecho");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void cambioTipo() {

        if (combType.getValue() == "Alumno") {
        	textEmail.setVisible(false);
        	textTel.setVisible(true);
        	labEmail.setVisible(false);
        	labTel.setVisible(true);
        	
        }else {
        	textEmail.setVisible(true);
        	textTel.setVisible(false);
        	labEmail.setVisible(true);
        	labTel.setVisible(false);
        	
        }

    }
    //Funcion para el boton volver
    public void back() {
    	
    }
	public void setUser(String string) {
		parametro = string;
		
	}
    
}