package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



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
	private TextField textApuntesProfesor;
	@FXML
	private Button bGuardar;
	@FXML
	private Label labMedia;
	@FXML
	private TitledPane ventanaAddFicha;
	
	private Connection con = null;
	
    private Statement stmt = null;
    
    private double media=0.00;
    private String dniAlumno="";
    private String nombreAlumno="";
    private Date fecha;
    private int rs;
    
    
    public void initialize() {
    	ventanaAddFicha.setText(ventanaAddFicha.getText()+nombreAlumno+" ("+dniAlumno+")");
    	bGuardar.setOnAction(e ->{
			try {
				guardarFicha();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
    	sldListening.setOnDragDropped(e ->modificarMedia());
    	con = ControllerDB.getConnection();
    }
    
    public void guardarFicha() throws ParseException {
    	fecha=new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fecha=dateFormat.parse(fecha.toString());
    	if(textApuntesProfesor.getLength()<200) {
    		String query=" INSERT INTO `fichaEvaluacion` (`dni_alumno`,`readingAndUseOfEnglish`,`writing`,`listening`,`speaking`,`media`,`apuntesProfesor`,`fecha`) VALUES ("+this.dniAlumno+","
    	                 +sldReadingAndUse.getValue()+","+sldWriting.getValue()+","+sldListening.getValue()+","+sldSpeaking.getValue()+","+this.media+","+this.textApuntesProfesor.getText()+","
    	                 +fecha;
    		try {
    			stmt = con.createStatement();
    			rs = stmt.executeUpdate(query);
    			stmt.close();
    			con.close();
    			System.out.print("hecho");
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}else {
    		Alert alert=new Alert(AlertType.ERROR);
    		alert.setTitle("Error: Longitud de apuntes superadad");
    		alert.setHeaderText(null);
    		alert.setContentText("La longitud máxima para un apunte de un profesor es de 200 caracteres");
    	}
    }
    
    public void modificarMedia() {
    	media=(sldReadingAndUse.getValue()+sldWriting.getValue()+sldListening.getValue()+sldSpeaking.getValue())/4;
    	labMedia.setText(Double.toString(media));
    }
    
    public void setDNIAlumno(String dniAlumno) {
    	this.dniAlumno=dniAlumno;
    }
    
    public void setNombreAlumno(String nombreAlumno) {
    	this.nombreAlumno=nombreAlumno;
    }
	
	
}
