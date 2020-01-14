package Model;

public class Test {
	
	
	private String id_grupo;
	private int id_test;
	private String[] preguntas =  new String[3];
	private String[] respuestas =  new String[9];
	private String[] correctas =  new String[3];
	
	public Test(int id_test, String id_grupo, String[] preguntas, String[] respuestas, String[] correctas) {
	
		
		
		for (int i = 0; i < preguntas.length; i++) {
			this.preguntas[i] = preguntas[i];
			this.correctas[i] = correctas[i];
		}
		for (int i = 0; i < respuestas.length; i++) {
			this.respuestas[i] = respuestas[i];
		}
	
	}
	public String getPreguntas(int i) {
		return preguntas[i];
	}

	public String getRespuesta(int i) {
		return respuestas[i];
	}

	public String getCorrectas(int i) {
		return correctas[i];
	}
	public String getId_grupo() {
		return id_grupo;
	}
	public int getId_test() {
		return id_test;
	}
	
	


	
	
	
}
