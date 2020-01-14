package Controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Model.Alumno;



public class ControllerAddFichaEvaluacion {

	@FXML
	private Slider sldReadingAndUse;
	@FXML
	private Slider sldWriting;
	@FXML
	private Slider sldListening;
	@FXML
	private Slider sldSpeaking;
	@FXML
	private TextArea textApuntesProfesor;
	@FXML
	private Button bGuardar;
	@FXML
	private Label labMedia;
	@FXML
	private TitledPane ventanaAddFicha;
	@FXML
	private ListView<Alumno> listVAlumnos;
	@FXML
	private Button bBack;
	
	private Stage primaryStage;
	
	private Connection con = null;
	
    private Statement stmt = null;
    
    private double media=0.00;
    

    private Date fecha;
    private String profersorUser;
    private ResultSet rSet=null;
    private ObservableList<Alumno> listAlumnos =  FXCollections.observableArrayList();
    private int rs;
    Alumno alumno;
    
    
    public <T> void initialize() {
    	//ventanaAddFicha.setText(ventanaAddFicha.getText()+nombreAlumno+" ("+dniAlumno+")");
    	bBack.setOnAction(e->back());
    	bGuardar.setOnAction(e ->{
			try {
				guardarFicha();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
    	//sldListening.setOnMouseDragReleased(e ->modificarMedia());
    	
    	con = ControllerDB.getConnection();
    	Platform.runLater(()->{
	    	String query = "SELECT * FROM usuario INNER JOIN grupos ON usuario.id_grupo=grupos.id_grupo WHERE grupos.dni_profesor='"+profersorUser+"'";
	    	try {
				stmt = con.createStatement();
				rSet = stmt.executeQuery(query);
				Alumno aux;
				while(rSet.next()) {
						aux = new Alumno(rSet.getString("nombre_usuario"),rSet.getString("nombre"),rSet.getString("apellidos"),
								rSet.getString("DNI"),rSet.getString("tipo_usuario"),rSet.getString("sexo"), rSet.getString("telefono"), rSet.getString("id_grupo"), rSet.getString("evaluacion"));
						
						listAlumnos.add(aux);
				}
				updateListAlumnos();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    	
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
	                setText(a.getUsername()+" ("+a.getDNI()+")");
	                
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
    
    public void guardarFicha() throws ParseException {
    	fecha=new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaS=dateFormat.format(fecha);
    	if(textApuntesProfesor.getLength()<200&&alumno!=null) {
    		String query=" INSERT INTO `fichaEvaluacion` (`dni_alumno`,`readingAndUseOfEnglish`,`writing`,`listening`,`speaking`,`media`,`apuntesProfesor`,`fecha`) VALUES ('"+this.alumno.getDNI()+"','"
    	                 +sldReadingAndUse.getValue()+"','"+sldWriting.getValue()+"','"+sldListening.getValue()+"','"+sldSpeaking.getValue()+"','"+this.media+"','"+this.textApuntesProfesor.getText()+"','"
    	                 +fechaS+"')";
    		try {
    			stmt = con.createStatement();
    			rs = stmt.executeUpdate(query);
    			stmt.close();
    			con.close();
    			System.out.print("hecho");
    			back();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	
    	}else {
    		Alert alert=new Alert(AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.initOwner(primaryStage);
    		if(textApuntesProfesor.getLength()>200) {
	    		alert.setTitle("Error: Longitud de apuntes superadad.");
	    		alert.setContentText("La longitud máxima para un apunte de un profesor es de 200 caracteres.");
	    		alert.show();
    		}else {
    			if(alumno==null) {
    	    		alert.setTitle("Error: Alumno no seleccionado.");
    	    		alert.setContentText("Por favor elija un alumno de la lista que se le proporciona.");
    	    		alert.show();
    			}
    		}
    	}
    }
    
    public void  cambio(Parent newRoot, String title, int height, int width) {
    	primaryStage = (Stage) bBack.getScene().getWindow();
    	primaryStage.setTitle(title);
    	primaryStage.setHeight(height);
        primaryStage.setWidth(width);
		primaryStage.getScene().setRoot(newRoot);	
    }
    
    public void back() {
        
    	FXMLLoader newRoot;
		try {
			
			newRoot = new FXMLLoader(getClass().getResource("/View/Profesor.fxml"));
			Parent root = (Parent)newRoot.load();

			ControllerProfesor controller = newRoot.<ControllerProfesor>getController();
			
			controller.setUser(profersorUser);
			cambio(root, "Profesor", 600, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    @FXML
    public void modificarMedia() {
    	media=(sldReadingAndUse.getValue()+sldWriting.getValue()+sldListening.getValue()+sldSpeaking.getValue())/4;
    	labMedia.setText(Double.toString(media));
    }
    
    public void setUser(String idUser) {
    	this.profersorUser=idUser;
    }
	
	
}
