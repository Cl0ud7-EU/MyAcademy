package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerAdmin {

    @FXML
    private Label labName;
    @FXML
    private Button bAddUser;
    @FXML
    private Button bAdminUser;
    @FXML
    private Button bAddGrupo;
    @FXML
    private Button bAdminGrupo;
    @FXML
    private Button bBackup;
    @FXML
    private Button bExit;
   
    
    
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        //labName.setText("Adrián"); No borrar
        bAddUser.setOnAction(e -> addUser());
        bAdminUser.setOnAction(e -> adminUser());
        bAddGrupo.setOnAction(e -> addGrupo());
        bAdminGrupo.setOnAction(e -> adminGrupo());
        bBackup.setOnAction(e -> backup());
        bExit.setOnAction(e -> exit());
    }
    public void  cambio(Parent newRoot) {
    	Stage primaryStage = (Stage) bAddUser.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);	
    }
    public void  addUser() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/AddUser.fxml"));
			cambio(newRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  adminUser() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/AdminUser.fxml"));
			cambio(newRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  addGrupo() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/AddGrupo.fxml"));
			cambio(newRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  adminGrupo() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/AdminGrupo.fxml"));
			cambio(newRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  backup() {
    	
   
    }
    public void  exit() {
    	
    	   
    }

    
}