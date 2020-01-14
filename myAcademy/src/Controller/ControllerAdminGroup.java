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
import javafx.stage.Stage;

public class ControllerAdminGroup {

   
    @FXML
    private Button bAdd;
    @FXML
    private Button bBack;
    @FXML
    private Button bAdministrarGroupStudents;
    @FXML
    private ComboBox combDay;
    @FXML
    private ComboBox combHour;
    @FXML
    private ComboBox combDuration;
    @FXML
    private TextField textName;
   
    @FXML
    private ListView<Profesor> listVProfes;
    @FXML
    private ListView<Grupo> listVGroups;
 
    
    private ObservableList<Profesor> listProfesores =  FXCollections.observableArrayList();
    private ObservableList<Grupo> listGroups =  FXCollections.observableArrayList();
    
    private Profesor profesor;
    private Grupo grupo;
    private String idGrupo;
    private String dniProfe;
    
    
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private int rsInsert;
    
    private Stage primaryStage;
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bAdd.setOnAction(e -> modificarGrupo());
        bAdministrarGroupStudents.setOnAction(e-> adminUsuarioGrupo());
        bBack.setOnAction(e -> back());
        combDay.getItems().addAll("Lunes","Martes", "Miercoles", "Jueves", "Viernes");
        combHour.getItems().addAll("15:00","16:00", "17:00","18:00","19:00","20:00");
        combDuration.getItems().addAll("30m","60m", "90m","120m");
        
        
        //Conexion con la DB
        con = ControllerDB.getConnection();
        
      //Creamos una coleccion de objetos Profesor
        String query = "SELECT * FROM usuario  WHERE tipo_usuario = 'Profesor'";
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Profesor profesor = new Profesor(rs.getString("nombre_usuario"),rs.getString("nombre"),rs.getString("apellidos"),
						rs.getString("DNI"),rs.getString("tipo_usuario"),rs.getString("sexo"), rs.getString("email"));
				listProfesores.add(profesor);
				
				
			}
			updateList();
			/*
			listVGroups.requestFocus();
			listVGroups.getSelectionModel().select(0);
			listVGroups.getFocusModel().focus(0);*/
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
   
    public void  modificarGrupo() {
    	
    	if(listVProfes.getSelectionModel().isEmpty()) {
    		dniProfe = listVGroups.getSelectionModel().getSelectedItem().getDni_profesor();
    	}
    	else {
    		dniProfe = listVProfes.getSelectionModel().getSelectedItem().getDNI();
    		//listVProfes.getSelectionModel().select();
    	}
    
    	//Creamos Usuario
    	grupo = new Grupo(textName.getText(),dniProfe, combDay.getValue().toString(),
    			combHour.getValue().toString(), combDuration.getValue().toString());
    	
    	
    	//Comprobamos que el dni que intentamos modificar no esta ya en la base de datos para otro alum
    	int i = 0;
    	for(Grupo g :listGroups){
    		
    		if((g.getId_grupo().equalsIgnoreCase(textName.getText()) && (!textName.getText().equalsIgnoreCase(idGrupo))))
    		{
    			//labErr.setText("Error ese nombre de grupo ya pertenece a otro grupo");
    			//labErr.setVisible(true);
    			break;
    		}
    		else if(g.getId_grupo().equalsIgnoreCase(idGrupo)) { //Todo correcto
    			
    			String query = "UPDATE `grupos` SET "
    	    			+ " `id_grupo` = '"+textName.getText()
    	    			+ "', `dni_profesor` = '"+grupo.getDni_profesor()
    	    			+ "', `hora_inicio` = '"+grupo.getHora_inicio()
    	    			+ "', `dia` = '"+grupo.getDia()
    	    			+ "', `duracion` = '"+grupo.getDuracion()
    	    			+ "' WHERE `grupos`.`id_grupo` = '"+idGrupo+"';";
    	    		System.out.println(query);
    	    		
    	    		
    			try {
    				stmt = con.createStatement();
    				rsInsert = stmt.executeUpdate(query);
    				stmt.close();
    				//con.close();
    				listGroups.set(i, grupo);
    				System.out.print("hecho");
    				updateListGroups();
    				//listVAlumnos.getSelectionModel().clearSelection();
    				
    				break;
    				
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
				
			}
    		i++;
		}
    }
    
    public void  updateList() {
    	
    	listVProfes.setItems(listProfesores);
		
    	listVProfes.setCellFactory(param -> new ListCell<Profesor>() {
			@Override
	        protected void updateItem(Profesor p, boolean empty){
	        super.updateItem(p, empty);
	            if(empty || p == null || p.getNombre() == null){
	                setText("");
	            }
	            else{
	                setText(p.getUsername());
	                
	                //Listener que actualiza los campos al seleccionar un usuario
	                listVProfes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profesor>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Profesor> observable, Profesor oldValue, Profesor newValue) {
	                      //  textName.setText(newValue.getNombre());
	                      
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
	                
	                //Listener que actualiza los campos al seleccionar un usuario
	                listVGroups.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Grupo>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Grupo> observable, Grupo oldValue, Grupo newValue) {
	                        textName.setText(newValue.getId_grupo());
	                        combDay.getSelectionModel().select(newValue.getDia());
	                        combHour.getSelectionModel().select(newValue.getHora_inicio());
	                        combDuration.getSelectionModel().select(newValue.getDuracion());
	                    	idGrupo = newValue.getId_grupo();
	                    	
	                    	//Prueba para que focusee al profesor que es.
	                    	int i = 0;
	                    	for(Profesor p :listProfesores){
	                    	
	                    		if(newValue.getDni_profesor().equalsIgnoreCase(p.getDNI())) {
	                    			listVProfes.requestFocus();
	                    			listVProfes.getSelectionModel().select(i);
	                    			listVProfes.getFocusModel().focus(i);
	                    		}
	                    		i++;
	                    	}
	                    	
	                      
	                    }
	                });  
	            }
	            
	            
	        } 
	        
	    });
    }
    
    private void back() {
    	Parent newRoot;
		try {
			newRoot = FXMLLoader.load(getClass().getResource("/View/Administrador.fxml"));
			Stage primaryStage = (Stage) bBack.getScene().getWindow();
			primaryStage.setTitle("Administrador");
			primaryStage.getScene().setRoot(newRoot);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}