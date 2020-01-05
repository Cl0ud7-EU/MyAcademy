package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Alumno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;

public class ControllerModAlumno {

    @FXML
    private Label labErr;
    @FXML
    private TextField textName;
    @FXML
    private TextField textPass;
    @FXML
    private TextField textApellidos;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textDNI;
    @FXML
    private TextField textTel;
    @FXML
    private ComboBox combEva;
    @FXML
    private ComboBox combGender;
    @FXML
    private Button bAdd;
    @FXML
    private ListView<Alumno> listVAlumnos;
    
    private String email;
    private String usuarioDNI;
    
    
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private int rAgregar;
    private Alumno alumno;
    
    private ObservableList<Alumno> listAlumnos =  FXCollections.observableArrayList();

    
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bAdd.setOnAction(e -> agregarUsuario());
        combEva.getItems().addAll("Sin nota","Suspenso", "Aprobado", "Notable", "Sobresaliente");
        combGender.getItems().addAll("Masculino", "Femenino");
        
        
        //Conexion con la DB
        con = ControllerDB.getConnection();
        
        //Creamos una coleccion de objetos Usuario
        String query = "SELECT * FROM usuario  WHERE tipo_usuario = 'Alumno'";
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				alumno = new Alumno(rs.getString("nombre_usuario"),rs.getString("nombre"),rs.getString("apellidos"),
						rs.getString("DNI"),rs.getString("tipo_usuario"),rs.getString("sexo"), rs.getString("telefono"), rs.getInt("id_grupo"), rs.getString("evaluacion"));
				listAlumnos.add(alumno);
				
				
			}
			updateList();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
  
        
    }
    public void  cambio(Parent newRoot) {
    	Stage primaryStage = (Stage) bAdd.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);	
    }
   
    public void  agregarUsuario() {
    	
    	
    	
    	//Creamos Usuario
    	alumno = new Alumno(listVAlumnos.getSelectionModel().getSelectedItem().getUsername(),
    			textName.getText(), textApellidos.getText(), textDNI.getText(),
    			listVAlumnos.getSelectionModel().getSelectedItem().getTipo(), combGender.getValue().toString(), textTel.getText(), 
    			listVAlumnos.getSelectionModel().getSelectedItem().getIdGrupo(), combEva.getValue().toString());
    	
    	//Comprobamos que el dni que intentamos modificar no esta ya en la base de datos para otro alum
    	int i = 0;
    	for(Alumno a :listAlumnos){
    		
    		if((a.getDNI().equalsIgnoreCase(textDNI.getText()) && (!textDNI.getText().equalsIgnoreCase(usuarioDNI))))
    		{
    			labErr.setText("Error ese DNI ya pertenece a otro usuario");
    			labErr.setVisible(true);
    			break;
    		}
    		else if(a.getDNI().equalsIgnoreCase(usuarioDNI)) { //Todo correcto
    			
    			String query = "UPDATE `usuario` SET "
    	    			+ " `dni` = '"+textDNI.getText() 
    	    			+ "', `evaluacion` = '"+combEva.getValue().toString()
    	    			+ "', `nombre` = '"+textName.getText()
    	    			+ "', `apellidos` = '"+textApellidos.getText()
    	    			+ "', `telefono` = '"+textTel.getText()
    	    			+ "', `sexo` = '"+combGender.getValue().toString()
    	    			+ "' WHERE `usuario`.`dni` = '"+usuarioDNI+"';";
    	    		System.out.println(query);
    	    		
    	    		
    			try {
    				stmt = con.createStatement();
    				rAgregar = stmt.executeUpdate(query);
    				stmt.close();
    				//con.close();
    				listAlumnos.set(i, alumno);
    				System.out.print("hecho");
    				updateList();
    				//listVAlumnos.getSelectionModel().clearSelection();
    				
    				break;
    				
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    			for(Alumno b :listAlumnos){
    				System.out.print(b.getDNI());
    			}
				
			}
    		i++;
			
		}
    	
    }
    
    public void  updateList() {
    	//listVAlumnos.getSourceItems().clear();
    	listVAlumnos.setItems(listAlumnos);
		
		listVAlumnos.setCellFactory(param -> new ListCell<Alumno>() {
			@Override
	        protected void updateItem(Alumno a, boolean empty){
	        super.updateItem(a, empty);
	            if(empty || a == null || a.getNombre() == null){
	                setText("");
	            }
	            else{
	                setText(a.getUsername());
	                
	                //Listener que actualiza los campos al seleccionar un usuario
	                listVAlumnos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Alumno>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Alumno> observable, Alumno oldValue, Alumno newValue) {
	                        textName.setText(newValue.getNombre());
	                        textApellidos.setText(newValue.getApellidos());
	                        textDNI.setText(newValue.getDNI());
	                        textTel.setText(newValue.getTelefono());
	                        combEva.getSelectionModel().select(newValue.getEvaluacion());
	                        combGender.getSelectionModel().select(newValue.getSexo());
	                        usuarioDNI = newValue.getDNI();
	                        labErr.setVisible(false);
	                    }
	                });
	                  
	            }

	        } 
	        
	    });
    }
    
}