import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validare {
	
	private static Pattern numePattern = Pattern.compile("^[A-Za-z ,.'-]+$");
	private static Pattern idPattern = Pattern.compile("[0-9]+");
	private static Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
	private static Pattern materiePattern = Pattern.compile("^[a-z ,.'-]+[a-z]*$");
	
	public Validare() {
		
	}
	
	public void validateNume(String nume) {
		Matcher match =numePattern.matcher(nume);
		if(!match.matches()) {
			System.out.println("Nume incorect !");
		}
	}
	
	public void validateEmail(String email) {
		Matcher match =emailPattern.matcher(email);
		if(!match.matches()) {
			System.out.println("Email incorect !");
		}
	}
	
	public void validateMaterie(String materie) {
		Matcher match =materiePattern.matcher(materie.toLowerCase());
		if(!match.matches()) {
			System.out.println("Materia incorect !");
		}
	}

}
