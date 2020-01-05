package Model;

public class Grupo {

	private String id_grupo;
	private String dni_profesor;
	private String dia;
	private String hora_inicio;
	private String duracion;
	
	public Grupo(String id_grupo, String dni_profesor, String dia, String hora_inicio, String duracion ) {
		
		this.setId_grupo(id_grupo);
		this.setDni_profesor(dni_profesor);
		this.setDia(dia);
		this.setHora_inicio(hora_inicio);
		this.setDuracion(duracion);
	}

	public String getId_grupo() {
		return id_grupo;
	}

	public void setId_grupo(String id_grupo) {
		this.id_grupo = id_grupo;
	}

	public String getDni_profesor() {
		return dni_profesor;
	}

	public void setDni_profesor(String dni_profesor) {
		this.dni_profesor = dni_profesor;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(String hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	
	
}
