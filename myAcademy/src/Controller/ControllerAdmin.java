package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerAdmin {

    @FXML
    private Label labName;
    @FXML
    private Button bAddUser;
    @FXML
    private Button bDelUser;
    @FXML
    private Button bModAlumnos;
    @FXML
    private Button bModProfes;
    @FXML
    private Button bAddGrupo;
    @FXML
    private Button bDelGrupo;
    @FXML
    private Button bAdminGrupo;
    @FXML
    private Button bAddPayment;
    @FXML
    private Button bBackup;
    @FXML
    private Button bExit;
   
    private Connection con = null;
    
    private Stage primaryStage;

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        //labName.setText("Adrián"); No borrar
        bAddUser.setOnAction(e -> addUser());
        bModAlumnos.setOnAction(e -> modAlumnos());
        bModProfes.setOnAction(e -> modProfes());
        bAddPayment.setOnAction(e -> addPayment());
        bDelUser.setOnAction(e -> delUser());
        bAddGrupo.setOnAction(e -> addGrupo());
        bAdminGrupo.setOnAction(e -> adminGrupo());
        bBackup.setOnAction(e -> backup());
        bExit.setOnAction(e -> exit());
        
        //Conexion con la DB
        con = ControllerDB.getConnection();
        
    }
    public void  cambio(Parent newRoot, String title, int height, int width) {
    	primaryStage = (Stage) bAddUser.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }
    public void  addUser() {
    	//Forma de pasar parametros, tendriamos que modificar todos los sitios donde cargamos una nueva escena y hacerlas igual que esta
    	FXMLLoader newRoot;
		try {
			
			newRoot = new FXMLLoader(getClass().getResource("/View/AddUser.fxml"));
			Parent root = (Parent)newRoot.load();
			//Aqui cogemos el controller al que vamos a llamar
			ControllerAddUser controller = newRoot.<ControllerAddUser>getController();
			//Aqui es donde pasamos el parametro, se llama a una funcion que tenemos que crear en el otro controller(no hace falta crearla ya te dice que falta y
			// le das y te la pone el eclipse, y dentro mandamos lo que queramos, y en el otro controller tenemos que crear una variable, yo le llame parametro,
			//y simplemente dentro de la funcion le damos valor a parametro.
			controller.setUser("Adrian");
			
			//Esta linea es importante para cada escena, no hacer un copiar pegar de ella, vale el que tienen que es especifico para cada una
			cambio(root, "Añadir Usuario", 500, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  modAlumnos() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/ModificarAlumno.fxml"));
			cambio(newRoot, "Modificar Alumno", 500, 836);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  modProfes() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/ModificarProfesor.fxml"));
			cambio(newRoot, "Modificar Profesor", 500, 836);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  addPayment() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/AddPayment.fxml"));
			cambio(newRoot, "A�adir Pago", 500, 836);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  delUser() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/DeleteUsers.fxml"));
			cambio(newRoot, "Eliminar usuarios", 500, 836);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  addGrupo() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/AddGroup.fxml"));
			cambio(newRoot, "A�adir Grupo", 500, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  adminGrupo() {
    	
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/AdministrarGroup.fxml"));
			cambio(newRoot, "Administrar Grupo", 600, 850);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void  backup() {
    	
    	/*NOTE: Getting path to the Jar file being executed*/
        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
        CodeSource codeSource = ControllerAdmin.class.getProtectionDomain().getCodeSource();
        File jarFile;
		try {
			jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
		
	        /*NOTE: Creating Database Constraints*/
	        String dbName = "JTw8pRhybP";
	        String dbUser = "JTw8pRhybP";
	        String dbPass = "FeYHzXYXxk";
	        String url = "remotemysql.com";

	        /*NOTE: Creating Path Constraints for folder saving*/
	        /*NOTE: Here the backup folder is created for saving inside it*/
	        String folderPath = jarDir + "/backup";

	        /*NOTE: Creating Folder if it does not exist*/
	        File f1 = new File(folderPath);
	        f1.mkdir();

	        /*NOTE: Creating Path Constraints for backup saving*/
	        /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
	         String savePath = "/" + jarDir + "/backup/" + "backup.sql";

	        /*NOTE: Used to create a cmd command*/
	        String executeCmd ="mysqldump --single-transaction --host "+url+" -u " + dbUser + " -p" + dbPass +" "+ dbName + " -r "+ savePath;

	        /*NOTE: Executing the command here*/
	        Process runtimeProcess;
			try {
				runtimeProcess = Runtime.getRuntime().exec(executeCmd);
				
				/* de prueba para borrar ////////////////////////////////////////////////////////////////////////////////////
				BufferedReader stdError = new BufferedReader(new InputStreamReader(runtimeProcess.getErrorStream()));
	
				String s = null;
				
				// Read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				System.out.println(stdError.readLine());
				System.out.println(stdError.readLine()); */
				
				try {
					int processComplete = runtimeProcess.waitFor();
					if (processComplete == 0) {
			            System.out.println("Backup Complete");
			        } else {
			            System.out.println("Backup Failure");
			        }
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
   
    }
    public void  exit() {
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
    				cambio(root, "Login", 400, 640);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	    }
    	});	
    	   
    }
	public void setUser(String string) {
		// TODO Auto-generated method stub
		
	}

    
}