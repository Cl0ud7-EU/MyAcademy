package Model;

public class Profesor extends Usuario {
	
	private String email;
	
	public Profesor(String username, String nombre, String apellidos, String dni, String tipo, String sexo, String email) {
		super(username, nombre, apellidos, dni, tipo, sexo);
		
		this.email = email;
		
	}
	public  void setEmail(String email) {
		this.email = email;
		
	}
	public  String getEmail() {
		return email;
		
	}

}
