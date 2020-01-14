package Controller;

import java.io.IOException;
import java.sql.Connection;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerAlumno {
	
	@FXML
    private Button bShowPayments;
	@FXML
    private Button bExit;
	
	private Connection con = null;
    
    private Stage primaryStage;
	private String parametro;
    
    public void initialize() {
    	String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bShowPayments.setOnAction(e -> showPayments());
        bExit.setOnAction(e -> exit());
        
        con = ControllerDB.getConnection();
    }
    
    public void cambio(Parent newRoot, String title, int height, int width) {
    	primaryStage = (Stage) bShowPayments.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }

	private void showPayments() {
		FXMLLoader newRoot;
		try {			
			newRoot = new FXMLLoader(getClass().getResource("/View/ShowPayments.fxml"));
			Parent root = (Parent)newRoot.load();
			//Aqui cogemos el controller al que vamos a llamar
			//ControllerShowPayments controller = newRoot.<ControllerShowPayments>getController();
			//Aqui es donde pasamos el parametro, se llama a una funcion que tenemos que crear en el otro controller(no hace falta crearla ya te dice que falta y
			// le das y te la pone el eclipse, y dentro mandamos lo que queramos, y en el otro controller tenemos que crear una variable, yo le llame parametro,
			//y simplemente dentro de la funcion le damos valor a parametro.
			//controller.setUser(this.parametro);
			
			//Esta linea es importante para cada escena, no hacer un copiar pegar de ella, vale el que tienen que es especifico para cada una
			cambio(root, "Ver pagos", 500, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exit() {
	}

	public void setUser(String dni) {
		this.parametro = dni;		
	}

}
