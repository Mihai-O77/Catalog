
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MainCatalog {
	
	protected static PreparedStatement preparedStm = null;
	static String next;
	static Scanner scaner;

	static boolean profesor = false;
	static boolean secretar = false;
	static boolean elev = false;	
    static Service service;
    static DBconnection conn;

	public static void main(String[] args) throws SQLException {
		
		conn = new DBconnection();
		conn.createConnection();
		service = new Service();

		System.out.println(" *CATALOG* ");
		
		next = "";
		scaner = new Scanner(System.in);
		user();
		info_edit();
		
		scaner.close();
		conn.getCon().close();

	}
	
	private static void displayAll(ResultSet rs, String tableName) throws SQLException {
		
		while(rs.next()) {
			switch(tableName) {
			case "elev":
				System.out.println("elev : " + rs.getString("numeElev") +  " email: " + 
			                       rs.getString("emailElev") + " id: " + rs.getInt("idElev"));break;
			case "materia":
				System.out.println("materia: " + rs.getString("numeMaterie") + " profesor: " + rs.getString("profesor") + 
						            " id: " + rs.getInt("idMaterie"));break;
			case "profesor":
				System.out.println("profesor: " + rs.getString("numeProfesor") + " materie: " + 
			                        rs.getString("materiiProfesor") + " id: " + rs.getInt("idProfesor"));break;
			}
		}
		conn.getCon().close();
	}
	
	
	private static void user() throws SQLException {
		System.out.println("User: ");
		System.out.println("---secretar \n--- profesor \n--- elev");
		String user = scaner.nextLine();
		switch(user) {
		case "secretar":
			secretar = true; profesor = false; elev = false; info_edit();break;
		case "profesor":
			secretar = false; profesor = true; elev = false; info_edit();break;
		case "elev":
			secretar = false; profesor = false; elev = true; info_edit();break;
		default:
			secretar = false; profesor = false; elev = false;
			user();
			break;
		}
	}
	
	private static void info_edit() throws SQLException {
		if(elev) {
			System.out.println("__user: elev__");
			System.out.println("Tastati numele elevului...");
			String nume = scaner.nextLine();
			displayInfoElev(nume);
			user();
		}
		if(profesor || secretar) {
		System.out.println("_i_ --- informatii / _e_ --- editare / _ex_ ---logout");
		next = scaner.nextLine();
		while(!next.equals("e") && !next.equals("i")) {
			info_edit();
		}
		if(next.equals("e")) {
			edit();
		}
		else if(next.equals("i")) {
			System.out.println("Informatii...");
			System.out.println("Informatii...");
			displayAll(conn.execQuery("SELECT * FROM elev"),"elev");
			displayAll(conn.execQuery("select * FROM materie"),"materie");
			displayAll(conn.execQuery("select * FROM profesor"),"profesor");
			info_edit();
		}
		else if(next.equals("ex")) {
			user();
		}
	}
	}
	
	private static void displayInfoElev(String nume) throws SQLException {
		ResultSet idElev = conn.execQuery("select idElev from elev where `numeElev` = '"+ nume+"'");
		int idelev = 0;
		System.out.println("Elev: " + nume);
		while(idElev.next()) {
			idelev = idElev.getInt("idElev");
		}
		String query = "SELECT el.numeElev, nt.nota, nt.idMaterie FROM note nt INNER JOIN elev el ON nt.idElev=el.idElev WHERE `numeElev`='"+nume+"'";
		ResultSet info = conn.execQuery(query);
		
		while(info.next()) {
			String query2 = "SELECT numeMaterie FROM materie where idMaterie="+info.getInt("idMaterie");
			ResultSet informat =conn.execQuery(query2);
			while(informat.next()) {
				System.out.println(" "+informat.getString("numeMaterie")+" nota: "+info.getInt("nota"));
			}
		}
		user();
	}
	
	
	private static void edit() throws SQLException {
		
		System.out.println("*Editare*");
		if(secretar) {
			System.out.println(" _new_ ---adauga \n _del_ ------ sterge \n _b_ ------ inapoi");
		}
		else if(profesor) {
			System.out.println(" _a_ ------- alege un elev \n _b_ ------- inapoi");
		}
		
		next = scaner.nextLine();
		while(!next.equals("a") && !next.equals("new") && !next.equals("b") ) {
			edit();
		}
		if(next.equals("a")) {
			if(profesor) {
			System.out.println("Introdu numele elevului...");
			String nume = scaner.nextLine();
			System.out.println(" _n_ --- adauga o nota \n _a_ ------absenta \n _b_ ------ inapoi");
			
			
		next = scaner.nextLine();
		switch(next) {
		case "n":
			System.out.println("Introdu materia...");
			String materie1 = scaner.nextLine();
			System.out.println("Introdu nota...");
			int nota = scaner.nextInt();
			service.addNota(nume, materie1, nota);break;
		case "a":
			System.out.println("Introdu materia...");
			String materie2 = scaner.nextLine();
			System.out.println("_m --- motivat");
			System.out.println("_n_ --- nemotivat");
			String absenta = "nemotivat";
			String abs = scaner.nextLine();
			if(abs.equals("m")) {
				absenta = "motivat";
			}
			service.addAbsenta(nume, materie2, absenta);break;
		case "b":
			edit();break;
		}
		}
	}
		else if(next.equals("b")) {
			info_edit();
		}
		else if(next.equals("new")) {
			newChoice();
		}
		else if(next.equals("del")) {
			delEntity();
		}
	}
	
	private static void delEntity() throws SQLException {
		System.out.println(" _p_ --- sterge profesor \\n _e_ ------- sterge elev \\n _a_ ------ sterge materie \\n _exit_ -------- iesire");
		next = scaner.nextLine();
		switch(next) {
		case "e":
			System.out.println(" --- Numele elevului: ");
			String elev = scaner.nextLine();
			service.removeElev(elev);
			break;
		case "p":
			System.out.println(" --- Numele elevului: ");
			String prof = scaner.nextLine();
			service.removeProfesor(prof);
			break;
		case "m":
			System.out.println(" --- Numele elevului: ");
			String mat = scaner.nextLine();
			service.removeMaterie(mat);
			break;
		case "exit":
			edit();break;
		default:
			newChoice();break;
			
		}
	}

	private static void newChoice() throws SQLException {
		System.out.println(" _p_ --- adauga profesor \n _e_ ------- adauga elev \n _m_ ------ adauga materie \n _exit_ -------- iesire");
		String choice = scaner.nextLine();
		switch(choice) {
		case "e":
			addELEV();break;
		case "p":
			addProf();break;
		case "m":
			addMaterie();break;
		case "exit":
			edit();break;
		default:
			newChoice();break;
		}
	}
	
	private static void addMaterie() throws SQLException {
		System.out.println("Introdu materia...");
		String numeMat = scaner.nextLine();
		System.out.println("Introdu numele profesorului...");
		String numeProf = scaner.nextLine();
		service.addMaterii(numeMat, numeProf);
		newChoice();
	}
	
	private static void addELEV() throws SQLException {
		System.out.println("Introdu numele elevului...");
		String numeElev = scaner.nextLine();
		System.out.println("Introdu email-ul elevului...");
		String email = scaner.nextLine();
		service.addElev(numeElev, email);
		newChoice();
		}

	private static void addProf() throws SQLException {
		System.out.println("Introdu numele profesorului...");
		String numeProf = scaner.nextLine();
		System.out.println("Introdu materia/m,materiile separate de virgula...");
		String materie = scaner.nextLine();
		service.addProfesor(numeProf, materie);
		newChoice();
		}
}
