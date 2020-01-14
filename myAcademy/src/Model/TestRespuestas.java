package Model;

public class TestRespuestas {
	
	private String[] respuestas =  new String[5];
	private String id_alumno;
	private int id_test;
	private int id;
	
	public TestRespuestas(int id, int idTest, String idAlumno, String[]Respuestas) {
		
		for (int i = 0; i < respuestas.length; i++) {
			this.respuestas[i] = respuestas[i];
		}
		this.id_alumno = idAlumno;
		this.id_test = idTest;
		this.id = id;
	}

	public String getIdAlumno() {
		return id_alumno;
	}

	public void setIdAlumno(String idAlumno) {
		this.id_alumno = idAlumno;
	}

	public int getIdTest() {
		return id_test;
	}

	public void setIdTest(int idTest) {
		this.id_test = idTest;
	}
	public String getRespuesta(int i) {
		return respuestas[i];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
