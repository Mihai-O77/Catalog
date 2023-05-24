import javax.xml.crypto.Data;

public class Absente {

	private int idElev;
	private int idMaterie;
	private Data dataAbsenta;
	private String motivat_nemotivat;
	
	public Absente() {
		
	}

	public String getMotivat_nemotivat() {
		return motivat_nemotivat;
	}

	public void setMotivat_nemotivat(String motivat_nemotivat) {
		this.motivat_nemotivat = motivat_nemotivat;
	}

	public Data getDataAbsenta() {
		return dataAbsenta;
	}

	public int getIdMaterie() {
		return idMaterie;
	}

	public int getIdElev() {
		return idElev;
	}
	
}
