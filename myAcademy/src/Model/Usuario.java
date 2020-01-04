package Model;

public class Usuario {

		private String username;
		private String nombre;
		private String apellidos;
		private String dni;
		private String tipo;
		private String sexo;
		
		public Usuario(String username, String nombre, String apellidos, String dni, String tipo, String sexo) {
			
			this.username = username;
			this.nombre = nombre;
			this.apellidos = apellidos;
			this.dni = dni;
			this.tipo = tipo;
			this.sexo = sexo;
			
		}


		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getApellidos() {
			return apellidos;
		}

		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}

		public String getDNI() {
			return dni;
		}

		public void setDNI(String dNI) {
			dni = dNI;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public String getSexo() {
			return sexo;
		}

		public void setSexo(String sexo) {
			this.sexo = sexo;
		}
		
}
