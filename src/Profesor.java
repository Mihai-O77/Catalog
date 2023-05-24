
import java.util.ArrayList;

public class Profesor {
   private String nume;
   private int idProfesor;
   private ArrayList<String> materiiPredate;
   
   
 Profesor(String nume, int id, ArrayList<String> materiiPredate){
	 this.nume = nume;
	 this.idProfesor = id;
	 this.materiiPredate = materiiPredate;
 } 
   

public String getNume() {
	return nume;
}

public void setNume(String nume) {
	this.nume = nume;
}


public ArrayList<String> getMateriiPredate() {
	return materiiPredate;
}


public void setMateriiPredate(ArrayList<String> materiiPredate) {
	this.materiiPredate = materiiPredate;
}


public int getIdProfesor() {
	return idProfesor;
}


}
