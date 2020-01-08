package Model;

public class FichaEvaluacion {

	private double readingAndUseOfEnglish,writing,listening,speaking,media;
	private String apuntesProfesor;
	
	
	public FichaEvaluacion(double rAndUseOf,double writ, double list, double speak, double media, String apuntes) {
		this.readingAndUseOfEnglish=rAndUseOf;
		this.writing=writ;
		this.listening=list;
		this.speaking=speak;
		this.media=media;
		this.apuntesProfesor=apuntes;
	}


	public double getReadingAndUseOfEnglish() {
		return readingAndUseOfEnglish;
	}


	public double getWriting() {
		return writing;
	}


	public double getListening() {
		return listening;
	}


	public double getSpeaking() {
		return speaking;
	}


	public double getMedia() {
		return media;
	}


	public String getApuntesProfesor() {
		return apuntesProfesor;
	}


	public void setReadingAndUseOfEnglish(double readingAndUseOfEnglish) {
		this.readingAndUseOfEnglish = readingAndUseOfEnglish;
	}


	public void setWriting(double writing) {
		this.writing = writing;
	}


	public void setListening(double listening) {
		this.listening = listening;
	}


	public void setSpeaking(double speaking) {
		this.speaking = speaking;
	}


	public void setMedia(double media) {
		this.media = media;
	}


	public void setApuntesProfesor(String apuntesProfesor) {
		this.apuntesProfesor = apuntesProfesor;
	}
	
}
