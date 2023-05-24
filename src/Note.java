
import javax.xml.crypto.Data;

public class Note {
	private int nota;
	private Data data;
	private String materia;
	private String elevul;
	
	
	Note(int nota, Data data, String materia, String elevul){
		this.nota = nota;
		this.data = data;
		this.materia = materia;
		this.elevul = elevul;
	}
	
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getElevul() {
		return elevul;
	}

	public void setElevul(String elevul) {
		this.elevul = elevul;
	}

}
