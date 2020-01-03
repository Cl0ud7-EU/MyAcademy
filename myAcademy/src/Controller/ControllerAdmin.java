package Controller;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
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
   
    private Connection con = null;
    
  

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
        
        //Conexion con la DB
        con = ControllerDB.getConnection();
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
    	
    	/*NOTE: Getting path to the Jar file being executed*/
        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
        CodeSource codeSource = ControllerAdmin.class.getProtectionDomain().getCodeSource();
        File jarFile;
		try {
			jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
			//*** pruebas ****///
			FileChooser fileChooser = new FileChooser();
		    File selectedFile = fileChooser.showSaveDialog(null);
			//////////////
	        /*NOTE: Creating Database Constraints*/
	        String dbName = "JTw8pRhybP";
	        String dbUser = "JTw8pRhybP";
	        String dbPass = "FeYHzXYXxk";
	        String url = "remotemysql.com:3306";

	        /*NOTE: Creating Path Constraints for folder saving*/
	        /*NOTE: Here the backup folder is created for saving inside it*/
	        String folderPath = jarDir + "\\backup";

	        /*NOTE: Creating Folder if it does not exist*/
	        File f1 = new File(folderPath);
	        f1.mkdir();

	        /*NOTE: Creating Path Constraints for backup saving*/
	        /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
	         String savePath = "\"" + jarDir + "\\backup\\" + "backup.sql\"";

	        /*NOTE: Used to create a cmd command*/
	        String executeCmd = url+"/mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + selectedFile;
	      

	        /*NOTE: Executing the command here*/
	        Process runtimeProcess;
			try {
				runtimeProcess = Runtime.getRuntime().exec(executeCmd);
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
	        

	        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
	        
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
   
    }
    public void  exit() {
    	
    	   
    }

    
}