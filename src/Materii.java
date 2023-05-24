
public class Materii {

	private String numeMaterie;
	private int idMaterie;
	private String profesorul;

	
	Materii(String denumire, int id, String profesor){
		this.numeMaterie = denumire;
		this.idMaterie = id;
		this.profesorul = profesor;
	}


	public String getnumeMaterie() {
		return numeMaterie;
	}


	public void setnumeMaterie(String denumire) {
		this.numeMaterie = denumire;
	}


	public String getProfesor() {
		return profesorul;
	}


	public void setProfesor(String profesor) {
		this.profesorul = profesor;
	}


	public int getIdMaterie() {
		return idMaterie;
	}
	
	
	

}
