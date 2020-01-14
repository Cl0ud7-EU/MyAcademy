package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    
    private Stage primaryStage;
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        bEntrar.setOnAction(e -> entrar());
        
        //Conexion con la DB
        con = ControllerDB.getConnection();
       
      
  
        
    }
    public void  cambio(Parent newRoot, String title, int height, int width) {
    	primaryStage = (Stage) bEntrar.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }
   
    public void  entrar() {

    	FXMLLoader newRoot;
    	String usuario = textName.getText();
		String query = "SELECT * FROM usuario WHERE nombre_usuario = '"+usuario+"'";
	
		
		try {
			
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				
				//nUsuario = rs.getString("nombre_usuario");
				if (rs.next()) {

					if(rs.getString(7).contentEquals(textName.getText()) && rs.getString(8).contentEquals(textPass.getText())) {
						if(rs.getString("tipo_usuario").contentEquals("admin")) {
							newRoot = new FXMLLoader(getClass().getResource("/View/Administrador.fxml"));
							Parent root = (Parent)newRoot.load();
							ControllerAdmin controller = newRoot.<ControllerAdmin>getController();
							
							controller.setUser(rs.getString(1));
							cambio(root, "Administrador", 450, 600);
						
						}
						else if(rs.getString(2).contentEquals("Alumno")) {
							newRoot = new FXMLLoader(getClass().getResource("/View/Alumno.fxml"));
							Parent root = (Parent)newRoot.load();
							ControllerAlumno controller = newRoot.<ControllerAlumno>getController();
							
							controller.setUser(rs.getString(1));
							cambio(root, "Alumno", 500, 700);
						
						}
						else {
							newRoot = new FXMLLoader(getClass().getResource("/View/Profesor.fxml"));
							Parent root = (Parent)newRoot.load();
							ControllerProfesor controller = newRoot.<ControllerProfesor>getController();
							
							controller.setUser(rs.getString(1));
							cambio(root, "Profesor", 600, 600);
						}
						stmt.close();
						con.close();
						
					}else {
						labErr.setVisible(true);
					}
				}
				labErr.setVisible(true);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    
}