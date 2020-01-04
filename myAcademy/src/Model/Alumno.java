package Model;

public class Alumno extends Usuario{
	
	private String telefono;
	private int idGrupo;
	private String evaluacion;
	
	public Alumno(String username, String nombre, String apellidos, String DNI, String tipo, String sexo, String telefono, int idGrupo, String evaluacion) {
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
	public  void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
		
	}
	public  int getIdGrupo() {
		return idGrupo;
		
	}
	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
		
	}
	public  String getEvaluacion() {
		return evaluacion;
		
	}
	

}
