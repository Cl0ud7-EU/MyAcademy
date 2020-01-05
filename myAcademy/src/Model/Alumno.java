package Model;

public class Alumno extends Usuario{
	
	private String telefono;
	private String idGrupo;
	private String evaluacion;
	
	public Alumno(String username, String nombre, String apellidos, String DNI, String tipo, String sexo, String telefono, String idGrupo, String evaluacion) {
		super(username, nombre, apellidos, DNI, tipo, sexo);
		
		this.telefono = telefono;
		this.idGrupo = idGrupo;
		this.evaluacion = evaluacion;
	}

	public  void setTelefono(String telefono) {
		this.telefono = telefono;
		
	}
	public  String getTelefono() {
		return telefono;
		
	}
	public  void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
		
	}
	public  String getIdGrupo() {
		return idGrupo;
		
	}
	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
		
	}
	public  String getEvaluacion() {
		return evaluacion;
		
	}
	

}
