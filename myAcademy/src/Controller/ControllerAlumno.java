package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Alumno;
import Model.Test;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControllerAlumno {
	
	@FXML
    private Button bShowPayments;
	@FXML
    private Button bRealizar;
	@FXML
    private Button bExit;
	
	@FXML
    private ListView<Test> listVRealizados;
	@FXML
    private ListView<Test> listVTest;
	
    private ObservableList<Test> listRealizados =  FXCollections.observableArrayList();
    private ObservableList<Test> listTest =  FXCollections.observableArrayList();
    private Test test;
	
	private String[] preguntas;
	private String[] respuestas;
	private String[] correctas;
	
	private Connection con = null;
	private Statement stmt = null;
    private ResultSet rs = null;
    
    private Stage primaryStage;
	private String idParametro;
    
    public void initialize() {
    	String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bShowPayments.setOnAction(e -> showPayments());
        bExit.setOnAction(e -> exit());
        bRealizar.setOnAction(e -> realizar());
        
        con = ControllerDB.getConnection();
        
        Platform.runLater(() -> {
        String query1 = "SELECT * FROM usuario  WHERE dni = '"+idParametro+"'";
       
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query1);
			if (rs.next()) {
			String query2 = "SELECT * FROM grupos  WHERE id_grupo = '"+rs.getString("id_grupo")+"'";
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(query2);
			
			}
			
			if (rs.next()) {
			//Para sacar los no realizados
			String query3 = "SELECT * FROM test2 AS T1 WHERE id_Grupo = '"+rs.getString("id_grupo")+"' AND NOT EXISTS( SELECT * FROM testRealizado2 AS T2 WHERE T1.id = T2.id_test AND T2.id_alumno = '"+idParametro+"')";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query3);
			}
			
			
			while(rs.next()) {
				
				
				preguntas = rs.getString(3).split("¬");
				respuestas = rs.getString(4).split("¬");
				correctas = rs.getString(5).split("¬");
				
				test = new Test(rs.getInt(1),rs.getString(2),preguntas,respuestas,correctas);
				listTest.add(test);
				
			}
			updateList();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        });
        
        
        
        
    }
    
    public void cambio(Parent newRoot, String title, int height, int width) {
    	primaryStage = (Stage) bExit.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }
    
    public void realizar() {
    	FXMLLoader newRoot;
		try {
			
			newRoot = new FXMLLoader(getClass().getResource("/View/RealizarTest.fxml"));
			Parent root = (Parent)newRoot.load();

			ControllerRealizarTest controller = newRoot.<ControllerRealizarTest>getController();
			
			controller.setUser(idParametro);
			controller.setTest(listVTest.getSelectionModel().getSelectedItem());
			cambio(root, "Test", 700, 800);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	
    }
	public void showPayments() {
		System.out.println("hola");
		FXMLLoader newRoot;
		try {			
			newRoot = new FXMLLoader(getClass().getResource("/View/ShowPayments.fxml"));
			Parent root = (Parent)newRoot.load();
			//Aqui cogemos el controller al que vamos a llamar
			ControllerShowPayments controller = newRoot.<ControllerShowPayments>getController();
			//Aqui es donde pasamos el parametro, se llama a una funcion que tenemos que crear en el otro controller(no hace falta crearla ya te dice que falta y
			// le das y te la pone el eclipse, y dentro mandamos lo que queramos, y en el otro controller tenemos que crear una variable, yo le llame parametro,
			//y simplemente dentro de la funcion le damos valor a parametro.
			controller.setUser(this.idParametro);
			
			//Esta linea es importante para cada escena, no hacer un copiar pegar de ella, vale el que tienen que es especifico para cada una
			cambio(root, "Ver pagos", 500, 800);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void  updateList() {
    	//listVAlumnos.getSourceItems().clear();
    	listVTest.setItems(listTest);
		
		listVTest.setCellFactory(param -> new ListCell<Test>() {
			@Override
	        protected void updateItem(Test t, boolean empty){
	        super.updateItem(t, empty);
	            if(empty || t == null){
	                setText("");
	            }
	            else{
	                setText("Test: "+t.getId_test());
	                  
	            }

	        } 
	        
	    });
    }

	private void exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Mensaje");
    	alert.setHeaderText("Estas seguro de que quieres salir?");
    	//alert.setContentText("I have a great message for you!");
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
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
    	});	
	}

	public void setUser(String dni) {
		this.idParametro = dni;		
	}

}
