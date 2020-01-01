package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerLogin {

    @FXML
    private Label labErr;
    @FXML
    private TextField textName;
    @FXML
    private TextField textPass;
    @FXML
    private Button bEntrar;
    
    
    
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bEntrar.setOnAction(e -> entrar());
      
  
        
    }
    public void  cambio(Parent newRoot) {
    	Stage primaryStage = (Stage) bEntrar.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);	
    }
   
    public void  entrar() {

    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/Administrador.fxml"));
			//Aqui hay que llamar antes a un metodo que compruebe en la base de datos, el nombre que se recoge del textName y las pass del textPass
			cambio(newRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    
}