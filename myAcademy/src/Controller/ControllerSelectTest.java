package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ControllerSelectTest {

	@FXML
    private Button bRealizar;
	@FXML
    private Button bBack;
	
	@FXML
    private ListView listTest;
	
	
	private String idParametro;
	
	private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
	
	
	
	public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
      //Conexion con la DB
        con = ControllerDB.getConnection();
        

        bRealizar.setOnAction(e -> realizar());
        bBack.setOnAction(e -> back());
   
    }
	
	public void realizar() {
		
	}
	//Funcion para el boton volver
    public void back() {
    
    	/*FXMLLoader newRoot;
		try {
			
			newRoot = new FXMLLoader(getClass().getResource("/View/Alumno.fxml"));
			Parent root = (Parent)newRoot.load();

			ControllerAlumno controller = newRoot.<ControllerAlumno>getController();
			
			controller.setUser(idParametro);
			cambio(root, "Crear Test", 500, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
    }
}
