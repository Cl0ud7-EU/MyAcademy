package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Alumno;
import Model.Grupo;
import Model.Profesor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerAdministrarGroupStudents {

   
    @FXML
    private Button bAdd;
    @FXML
    private Button bDelete;
    @FXML
    private Button bBack;
   
    
  
    @FXML
    private ListView<Grupo> listVGroups;
    @FXML
    private ListView<Alumno> listVAlumnos;
    @FXML
    private ListView<Alumno> listVAlumnosN;
 
    private ObservableList<Grupo> listGroups =  FXCollections.observableArrayList();
    private ObservableList<Alumno> listAlumnos =  FXCollections.observableArrayList();
    private ObservableList<Alumno> listAlumnosN =  FXCollections.observableArrayList();

    
   
    private Grupo grupo;
    private Alumno alumno;
    private Alumno alumnoN;
    private String idGrupo;
    private Boolean rep = false;
    
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private int rsInsert;
    
    private Stage primaryStage;
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bAdd.setOnAction(e -> addAlumno());
        bDelete.setOnAction(e-> delAlumno());
        bBack.setOnAction(e -> back());
        listVGroups.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
            public void handle(MouseEvent event) {
        		listVAlumnos.getItems().clear();
        		listVAlumnosN.getItems().clear();
        		idGrupo =  listVGroups.getSelectionModel().getSelectedItem().getId_grupo();
	        	updateAlumnos();
	        	updateAlumnosN();
    	
            }
        });
        
        
        //Conexion con la DB
        con = ControllerDB.getConnection();
        
        //Creamos una coleccion de objetos Grupos
        String queryG = "SELECT * FROM grupos";
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryG);
			
			while(rs.next()) {
				grupo = new Grupo(rs.getString("id_grupo"),rs.getString("dni_profesor"),rs.getString("dia"),
						rs.getString("hora_inicio"),rs.getString("duracion"));
				listGroups.add(grupo);
				
				
			}
			updateListGroups();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
  
        
    }
    public void  cambio(Parent newRoot, String title, int height, int width) {
    	primaryStage = (Stage) bAdd.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }
    public void adminUsuarioGrupo() {
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/AdministrarGroupStudents.fxml"));
			cambio(newRoot, "Administrar estudiantes del grupo", 600, 850);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
    public void  addAlumno() {
    	
    	String query = "UPDATE `usuario` SET "
    			+ " `id_grupo` = '"+idGrupo
    			+ "' WHERE `usuario`.`dni` = '"+alumno.getDNI()+"';";
    		System.out.println(query);
    		
    		
		try {
			stmt = con.createStatement();
			rsInsert = stmt.executeUpdate(query);
			stmt.close();
			//con.close();
			System.out.print("hecho");
			//updateListGroups();
			//listVAlumnos.getSelectionModel().clearSelection();
			listVAlumnos.getItems().clear();
    		listVAlumnosN.getItems().clear();
    		updateAlumnos();
        	updateAlumnosN();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public void  delAlumno() {
    	
    	String query = "UPDATE `usuario` SET "
    			+ " `id_grupo` = '"
    			+ "' WHERE `usuario`.`dni` = '"+alumnoN.getDNI()+"';";
    		System.out.println(query);
    		
    		
		try {
			stmt = con.createStatement();
			rsInsert = stmt.executeUpdate(query);
			stmt.close();
			//con.close();
			System.out.print("hecho");
			//updateListGroups();
			//listVAlumnos.getSelectionModel().clearSelection();
			listVAlumnos.getItems().clear();
    		listVAlumnosN.getItems().clear();
    		updateAlumnos();
        	updateAlumnosN();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public void  updateListAlumnos() {

    	listVAlumnos.setItems(listAlumnos);
		
		listVAlumnos.setCellFactory(param -> new ListCell<Alumno>() {
			@Override
	        protected void updateItem(Alumno a, boolean empty){
	        super.updateItem(a, empty);
	            if(empty || a == null || a.getNombre() == null){
	                setText("");
	            }
	            else{
	                setText(a.getUsername()+" grupo: "+a.getIdGrupo());
	                
	                //Listener que actualiza los campos al seleccionar un usuario
	                listVAlumnos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alumno>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Alumno> observable, Alumno oldValue, Alumno newValue) {
	                     alumno = newValue;
	                    }
	                });
	                  
	            }
	        } 
	        
	    });
    }
    public void  updateListAlumnosN() {
 
    	listVAlumnosN.setItems(listAlumnosN);
		
		listVAlumnosN.setCellFactory(param -> new ListCell<Alumno>() {
			@Override
	        protected void updateItem(Alumno a, boolean empty){
	        super.updateItem(a, empty);
	            if(empty || a == null || a.getNombre() == null){
	                setText("");
	            }
	            else{
	                setText(a.getUsername());
	                
	                //Listener que actualiza los campos al seleccionar un usuario
	                listVAlumnosN.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alumno>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Alumno> observable, Alumno oldValue, Alumno newValue) {
	                     alumnoN = newValue;
	                    }
	                });
	                  
	            }
	        } 
	        
	    });
    }
    public void  updateListGroups() {
    	
    	listVGroups.setItems(listGroups);
		
    	listVGroups.setCellFactory(param -> new ListCell<Grupo>() {
			@Override
	        protected void updateItem(Grupo g, boolean empty){
	        super.updateItem(g, empty);
	            if(empty || g == null || g.getId_grupo() == null){
	                setText("");
	            }
	            else{
	                setText(g.getId_grupo());   
	            }
	            
	            
	        } 
	        
	    });
    }
    public void updateAlumnos() {
    	
    	//Creamos una coleccion de objetos Alumno dentro del grupo seleccionado
        String query = "SELECT * FROM usuario  WHERE tipo_usuario = 'Alumno' AND (id_grupo != '"+idGrupo+"' OR id_grupo IS NULL)";
       
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				alumno = new Alumno(rs.getString("nombre_usuario"),rs.getString("nombre"),rs.getString("apellidos"),
						rs.getString("DNI"),rs.getString("tipo_usuario"),rs.getString("sexo"), rs.getString("telefono"), rs.getString("id_grupo"), rs.getString("evaluacion"));
				listAlumnos.add(alumno);
	
			}
			updateListAlumnos();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    public void updateAlumnosN() {
    	
    	//Creamos una coleccion de objetos Alumno dentro del grupo seleccionado
        String query = "SELECT * FROM usuario  WHERE tipo_usuario = 'Alumno' AND id_grupo = '"+idGrupo+"'";
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				alumno = new Alumno(rs.getString("nombre_usuario"),rs.getString("nombre"),rs.getString("apellidos"),
						rs.getString("DNI"),rs.getString("tipo_usuario"),rs.getString("sexo"), rs.getString("telefono"), rs.getString("id_grupo"), rs.getString("evaluacion"));
				listAlumnosN.add(alumno);
				System.out.print(alumno.getNombre());
				
			}
			updateListAlumnosN();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    private void back() {
    	try {
			con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/AdministrarGroup.fxml"));
			Stage primaryStage = (Stage) bBack.getScene().getWindow();
			primaryStage.setTitle("Administrar Grupo");
			primaryStage.getScene().setRoot(newRoot);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}