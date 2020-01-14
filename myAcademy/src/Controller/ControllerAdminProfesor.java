package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Profesor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ControllerAdminProfesor {
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
    private ComboBox combGender;
    @FXML
    private Button bAdd;
    @FXML
    private Button bBack;
    @FXML
    private ListView<Profesor> listVProfesores;
    
    private String email;
    private String usuarioDNI;
    
    
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private int rAgregar;
    private Profesor profesor;
    
    private ObservableList<Profesor> listProfesores =  FXCollections.observableArrayList();

    
  

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        bAdd.setOnAction(e -> agregarUsuario());
        bBack.setOnAction(e -> back());
        combGender.getItems().addAll("Masculino", "Femenino");
        
        
        //Conexion con la DB
        con = ControllerDB.getConnection();
        
        //Creamos una coleccion de objetos Profesor
        String query = "SELECT * FROM usuario  WHERE tipo_usuario = 'Profesor'";
        try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				profesor = new Profesor(rs.getString("nombre_usuario"),rs.getString("nombre"),rs.getString("apellidos"),
						rs.getString("DNI"),rs.getString("tipo_usuario"),rs.getString("sexo"), rs.getString("email"));
				listProfesores.add(profesor);				
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
    	profesor = new Profesor(listVProfesores.getSelectionModel().getSelectedItem().getUsername(),
    			textName.getText(), textApellidos.getText(), textDNI.getText(), 
    			listVProfesores.getSelectionModel().getSelectedItem().getTipo(), combGender.getValue().toString(), 
    			textEmail.getText());
    	
    	//Comprobamos que el dni que intentamos modificar no esta ya en la base de datos para otro alum
    	int i = 0;
    	for(Profesor a :listProfesores){
    		
    		if((a.getDNI().equalsIgnoreCase(textDNI.getText()) && (!textDNI.getText().equalsIgnoreCase(usuarioDNI))))
    		{
    			labErr.setText("Error ese DNI ya pertenece a otro usuario");
    			labErr.setVisible(true);
    			break;
    		}
    		else if(a.getDNI().equalsIgnoreCase(usuarioDNI)) { //Todo correcto
    			
    			String query = "UPDATE `usuario` SET "
    	    			+ " `dni` = '"+textDNI.getText()
    	    			+ "', `nombre` = '"+textName.getText()
    	    			+ "', `apellidos` = '"+textApellidos.getText()
    	    			+ "', `email` = '"+textEmail.getText()
    	    			+ "', `sexo` = '"+combGender.getValue().toString()
    	    			+ "' WHERE `usuario`.`dni` = '"+usuarioDNI+"';";
    	    		System.out.println(query);
    	    		
    	    		
    			try {
    				stmt = con.createStatement();
    				rAgregar = stmt.executeUpdate(query);
    				stmt.close();
    				//con.close();
    				listProfesores.set(i, profesor);
    				System.out.print("hecho");
    				updateList();
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
    	//listVAlumnos.getSourceItems().clear();
    	listVProfesores.setItems(listProfesores);
		
    	listVProfesores.setCellFactory(param -> new ListCell<Profesor>() {
			@Override
	        protected void updateItem(Profesor a, boolean empty){
	        super.updateItem(a, empty);
	            if(empty || a == null || a.getNombre() == null){
	                setText("");
	            }
	            else{
	                setText(a.getUsername());
	                
	                //Listener que actualiza los campos al seleccionar un usuario
	                listVProfesores.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profesor>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Profesor> observable, Profesor oldValue, Profesor newValue) {
	                        textName.setText(newValue.getNombre());
	                        textApellidos.setText(newValue.getApellidos());
	                        textDNI.setText(newValue.getDNI());
	                        combGender.getSelectionModel().select(newValue.getSexo());
	                        textEmail.setText(newValue.getEmail());
	                        usuarioDNI = newValue.getDNI();
	                        labErr.setVisible(false);
	                       
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
