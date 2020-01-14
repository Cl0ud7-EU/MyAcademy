package Controller;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Test;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControllerRealizarTest {
	

	@FXML
    private Button bResponder;
	@FXML
    private Button bBack;
	
	@FXML
    private Label lPregunta;
	
	@FXML
    private Label lResp1;
	@FXML
    private Label lResp2;
	@FXML
    private Label lResp3;
	
	@FXML
    private RadioButton rBut1;
	@FXML
    private RadioButton rBut2;
	@FXML
    private RadioButton rBut3;
	
	
	private Stage primaryStage;
	private String idParametro;
    ToggleGroup toggleGroup = new ToggleGroup();
    
    private Connection con = null;
    private Statement stmt = null;
    private int rs;
    
    private Test test;
    private int contador;
    
    private String respuestas;
	
	public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
      //Conexion con la DB
        con = ControllerDB.getConnection();
        

        bResponder.setOnAction(e -> responder());
        bBack.setOnAction(e -> back());
        
        

        rBut1.setToggleGroup(toggleGroup);
        rBut1.setUserData("1");
        rBut2.setToggleGroup(toggleGroup);
        rBut2.setUserData("2");
        rBut3.setToggleGroup(toggleGroup);
        rBut3.setUserData("3");
        
        Platform.runLater(() -> {
        	
        	
        	lPregunta.setText(test.getPreguntas(0));
        	lResp1.setText(test.getRespuesta(0).toString());
        	lResp2.setText(test.getRespuesta(1).toString());
        	lResp3.setText(test.getRespuesta(2).toString());
        	
        	

        });
       
        contador = 0;
        
    }
	public void  cambio(Parent newRoot, String title, int height, int width) {
    	primaryStage = (Stage) bBack.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }
	
	public void responder() {
		
		if(toggleGroup.getSelectedToggle() != null) {
			if(contador ==  0) {
				respuestas = toggleGroup.getSelectedToggle().getUserData().toString();
				
			}
			else {
				respuestas = respuestas +"¬" + toggleGroup.getSelectedToggle().getUserData().toString();
			}
		
			toggleGroup.selectToggle(null);
			contador++;
			if (contador < 5) {
				lPregunta.setText(test.getPreguntas(contador));
		    	lResp1.setText(test.getRespuesta(contador*3));
		    	lResp2.setText(test.getRespuesta(contador*3 + 1));
		    	lResp3.setText(test.getRespuesta(contador*3 + 2));
		    	
	    	}
			else {
				addTest();
				System.out.println("Test realizado");
				back();
			}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Mensaje");
	    	alert.setHeaderText("Selecciona una respuesta");
	    	//alert.setContentText("I have a great message for you!");
	    	alert.showAndWait().ifPresent(rs -> { 
	    		
	    	});	
		}
		
		
    	
		
	}
	
	public void addTest() {
    	
		
		String query;
		try {
			
				query = "INSERT INTO `testRealizado2` (`id`, `id_alumno`, `respuestas`, `id_test`) VALUES (NULL,'"+idParametro+"','"+respuestas+"','"+test.getId_test()+"')";

				stmt = con.createStatement();
				rs = stmt.executeUpdate(query);
				stmt.close();
				con.close();
				back();
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String query = "INSERT INTO `test2` (`id`, `id_Grupo`, `preguntas`, `respuestas`, `correctas`) VALUES (NULL, '1', 'b', 'b', 'b')";
		
		
	
	}
	//Funcion para el boton volver
    public void back() {
    
    	FXMLLoader newRoot;
		try {
			
			newRoot = new FXMLLoader(getClass().getResource("/View/Alumno.fxml"));
			Parent root = (Parent)newRoot.load();

			ControllerAlumno controller = newRoot.<ControllerAlumno>getController();
			
			controller.setUser(idParametro);
			cambio(root, "Alumno", 500, 650);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
  //Se usa para pasar un parametro entre vistas
    public void setUser(String string) {
		idParametro = string;
		
	}
  //Se usa para pasar el test seleccionado entre vistas
    public void setTest(Test test) {
		this.test = test;
		
	}
}
