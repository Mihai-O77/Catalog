
public class Elev {

	private String nume;
	private String email;
	private int idElev;
	private String clasa = "VIII";
	 
	
	
	Elev(String nume, String email, int id){
	this.nume = nume;
	this.email = email;
	this.idElev = id;
	}


	public String getNume() {
		return nume;
	}



	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


	public String getClasa() {
		return clasa;
	}


	public int getIdElev() {
		return idElev;
	}


	
	
}
