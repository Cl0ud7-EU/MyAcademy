package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControllerAddTest {
	
	
	
	@FXML
    private Button bAdd;
	@FXML
    private Button bBack;
	
	@FXML
    private TextField textPregunta;
	@FXML
    private TextField textResp1;
	@FXML
    private TextField textResp2;
	@FXML
    private TextField textResp3;
	
	@FXML
    private RadioButton rBut1;
	@FXML
    private RadioButton rBut2;
	@FXML
    private RadioButton rBut3;
	
    private Stage primaryStage;
    ToggleGroup toggleGroup = new ToggleGroup();
    
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private int rsInt;
	
	private String idParametro;
	
	/*
	private String[] preguntas = new String[5];
	private String[] respuestas =  new String[15];
	private String[] correctas =  new String[5];
	*/
	String preguntas = "";
	String respuestas = "";
	String correctas = "";
	
	private int contador;
	
	
	
	public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
      //Conexion con la DB
        con = ControllerDB.getConnection();
        

        bAdd.setOnAction(e -> addPregunta());
        bBack.setOnAction(e -> back());
        
        

        rBut1.setToggleGroup(toggleGroup);
        rBut1.setUserData("1");
        rBut2.setToggleGroup(toggleGroup);
        rBut2.setUserData("2");
        rBut3.setToggleGroup(toggleGroup);
        rBut3.setUserData("3");
       
        contador = 0;
        
    }
	
	public void  cambio(Parent newRoot, String title, int height, int width) {
    	primaryStage = (Stage) bBack.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }
	
	public void addPregunta() {
		
		
		
		
		preguntas = preguntas +"," +textPregunta.getText();
		respuestas = respuestas +"," +textResp1.getText();
		respuestas = respuestas +"," +textResp2.getText();
		respuestas = respuestas +"," +textResp3.getText();
		
		correctas = correctas +"," + toggleGroup.getSelectedToggle().getUserData().toString();
		
		/*preguntas[contador] = textPregunta.getText();
		respuestas[contador*3 + 0] = textResp1.getText();
		respuestas[contador*3 + 1] = textResp2.getText();
		respuestas[contador*3 + 2] = textResp3.getText();*/
	
		//correctas[contador] = toggleGroup.getSelectedToggle().getUserData().toString();
		if (contador == 4) {
			bAdd.setOnAction(e -> addTest());
		}
		
		contador++;
		if (contador == 4) {
			bAdd.setText("Crear Test");
		}
		
		
	}
	
	public void addTest() {
	
		//Buscamos el id del grupo al que da este profesor
		String queryId = "SELECT id_grupo FROM grupos WHERE dni_profesor = '12345678P'";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		String query = "INSERT INTO `test2`(`id_Grupo`, `preguntas`, `respuestas`, `correctas`) VALUES ("+rs+","+preguntas+","+respuestas+","+correctas+"))";
		
		
	}
	
	//Funcion para el boton volver
    public void back() {
    
    	FXMLLoader newRoot;
		try {
			
			newRoot = new FXMLLoader(getClass().getResource("/View/Profesor.fxml"));
			Parent root = (Parent)newRoot.load();

			ControllerProfesor controller = newRoot.<ControllerProfesor>getController();
			
			controller.setUser(idParametro);
			cambio(root, "Crear Test", 500, 600);
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
