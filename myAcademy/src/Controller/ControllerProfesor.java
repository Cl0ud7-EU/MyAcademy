package Controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerProfesor {
	
	@FXML
    private Button bCrearTest;
    @FXML
    private Button bExit;
    @FXML
    private Button bCrearFicha;
    
    private String idParametro;
    private Stage primaryStage;
    
    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        

        bCrearTest.setOnAction(e -> addTest());
        bCrearFicha.setOnAction(e -> addFicha());
       
        Platform.runLater(() -> {

        	System.out.println(idParametro);

        });
        bExit.setOnAction(e -> exit());
        
    }
    
    public void  cambio(Parent newRoot, String title, int height, int width) {
    	primaryStage = (Stage) bCrearTest.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }
    public void  addTest() {

    	FXMLLoader newRoot;
		try {
			
			newRoot = new FXMLLoader(getClass().getResource("/View/CrearTest.fxml"));
			Parent root = (Parent)newRoot.load();

			ControllerAddTest controller = newRoot.<ControllerAddTest>getController();
			
			controller.setUser(idParametro);
			cambio(root, "Crear Test", 700, 800);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  addFicha() {

    	FXMLLoader newRoot;
		try {
			
			newRoot = new FXMLLoader(getClass().getResource("/View/AddFichaEvaluacion.fxml"));
			Parent root = (Parent)newRoot.load();

			ControllerAddFichaEvaluacion controller = newRoot.<ControllerAddFichaEvaluacion>getController();
			controller.setUser(idParametro);
			cambio(root, "Crear Ficha Evaluacion", 500, 687);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  exit() {
    	FXMLLoader newRoot;
		try {
			
			newRoot = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
			Parent root = (Parent)newRoot.load();

			//controller.setUser(idParamentro);
			cambio(root, "Login", 640, 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	   
    }
    //Se usa para pasar un parametro entre vistas
    public void setUser(String string) {
		idParametro = string;
		
		
	}


}
