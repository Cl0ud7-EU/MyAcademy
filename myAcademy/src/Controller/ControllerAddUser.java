package Controller;

import java.io.IOException;

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
    private TextField textName;
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
    
    
    
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bAdd.setOnAction(e -> agregarUsuario());
        combType.getItems().addAll("Profesor", "Alumno");
        combType.getSelectionModel().select("Alumno");
        combGender.getItems().addAll("Masculino", "Femenino");
        combGender.getSelectionModel().select("Femenino");
      
  
        
    }
    public void  cambio(Parent newRoot) {
    	Stage primaryStage = (Stage) bAdd.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);	
    }
   
    public void  agregarUsuario() {

    	
    }


    
}