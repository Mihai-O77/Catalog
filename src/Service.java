
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Service extends DBconnection{
	
	DBrepository elevRepository = new DBrepository("elev");
	DBrepository profRepository = new DBrepository("profesor");
	DBrepository matRepository = new DBrepository("materie");
	static Validare validare;
	boolean exista = true;
	
	
	public Service() {
		super();
		validare = new Validare();
	}
	
	private ArrayList<Elev> elevRepo() throws SQLException {
		ResultSet result = execQuery(elevRepository.getQuery());      //obti array-ul cu obiectele elev
		ArrayList<Elev> elevRepo = elevRepository.elevObj(result);
		return elevRepo;
	}
	
	private ArrayList<Materii> materiiRepo() throws SQLException {
		ResultSet result = execQuery(matRepository.getQuery());           //obti array-ul cu obiectele materie
		ArrayList<Materii> materiiRepo = matRepository.materiiObj(result);
		return materiiRepo;
	}
	
	public HashMap<String, ArrayList<String>> profRepo() throws SQLException {
		HashMap<String, ArrayList<String>> prof_materieRepo = new HashMap<>();
		
		ResultSet result1 = execQuery(profRepository.getQuery());
		ResultSet result2 = execQuery(matRepository.getQuery());
		ArrayList<Profesor> profesorRepo = profRepository.profesorObj(result1, result2);
		for(Profesor prof: profesorRepo) {
			prof_materieRepo.put(prof.getNume(), prof.getMateriiPredate());
			System.out.println("nume: " + prof.getNume());
			System.out.println("materii: " + prof.getMateriiPredate());
		}
		return prof_materieRepo;
	}
	
	public void removeElev(String nume) throws SQLException { 
		try {
			String query = "delete from elev where numeElev=?";
			preparedStm = getCon().prepareStatement(query);
			preparedStm.setString(1, nume);
			execAction(query);
		}
		catch(SQLException e) {
			System.out.println("Eroare!");
			System.out.println(e);
		}
	}
	
	public void addElev(String nume, String email) throws SQLException {
		
		ArrayList<Elev> elevRepo = elevRepo();
		for(Elev elev: elevRepo) {
		 if(elev.getNume().equals(nume) || elev.getEmail().equals(email)) {
			 exista = false;
			 System.out.println("Elevul " + nume + " este deja inregistrat");
			 System.out.println(" Adaugati alt elev ?");
			 break;
		 }
		}
		 if(exista) {
			 validare.validateNume(nume);
			 validare.validateEmail(email);
			 try {
				 String query = "INSERT INTO elev (numeElev, emailElev) VALUES (?,?)";
				 preparedStm = getCon().prepareStatement(query);
				 preparedStm.setString(1, nume);
				 preparedStm.setString(2, email);
				 execAction(query);
			 }
			 catch(SQLException e) {
				 System.out.println("Eroare!");
				 System.out.println(e);
			 }
			 System.out.println("Elevul " + nume + " a fost inregistrat cu succes !");
		 }
	}
	
	public void removeProfesor(String nume) throws SQLException {
		try {
			String query = "delete from profesor where numeProfesor = ?";
			preparedStm = getCon().prepareStatement(query);
			preparedStm.setString(1, nume);
			execAction(query);
		}
		catch(SQLException e) {
			System.out.println("Eroare!");
			System.out.println(e);
		}
	}
	
	public void addProfesor(String nume, String materia) throws SQLException {
		
		ResultSet result1 = execQuery(profRepository.getQuery());
		ResultSet result2 = execQuery(matRepository.getQuery());
		ArrayList<Profesor> profesorRepo = profRepository.profesorObj(result1, result2);
		for(Profesor profesor: profesorRepo) {
		  if(profesor.getNume().equals(nume)) {
			  exista = false;
			  System.out.println("Profesorul " + nume + " este deja inregistrat");
			  System.out.println(" Adaugati alt profesor? ");
			  break;
		  }
		}
	   if(exista) {
		   validare.validateNume(nume);
		   validare.validateMaterie(materia);
		   try {
			   String query = "INSERT INTO profesor (numeProfesor, materiiProfesor) VALUES (?,?)";
			   preparedStm = getCon().prepareStatement(query);
			   preparedStm.setString( 1, nume);
			   String mat = String.join(",", materia);
			   preparedStm.setString(2, mat);
			   execAction(query);
		   }
		   catch(SQLException e) {
			  System.out.println("Eroare!");
			  System.out.println(e);
		   }
		   System.out.println("Profesorul " + nume + " a fost inregistrat ccu succes !");
	   }
	}
	
	public void removeMaterie(String materie) throws SQLException {
		try {
			String query = "delete from materie where numeMaterie=?";
			preparedStm = getCon().prepareStatement(query);
			preparedStm.setString(1, materie);
			execAction(query);
		}
		catch(SQLException e) {
			System.out.println("Eroare!");
			System.out.println(e);
		}
	}
	
	
	public void addMaterii(String nume, String profesor) throws SQLException {
		ArrayList<Materii> matRepository = materiiRepo();
		for(Materii materia: matRepository) {
		  if(materia.getnumeMaterie().equals(materia)) {
			  exista = false;
			  System.out.println("Materia " + nume + " este deja inregistrata");
			  System.out.println("Adaugati alta materie ?");
			  break;
		  }
		}
		if(exista) {
			validare.validateMaterie(nume);
			try {
			String query = "INSERT INTO materie (numeMaterie, profesor) VALUES (?,?)";
			preparedStm = getCon().prepareStatement(query);
			preparedStm.setString(1, nume);
			preparedStm.setString(2, profesor);
			execAction(query);
			}
			catch(SQLException e) {
				System.out.println("Eroare!");
				System.out.println(e);
			}
			System.out.println("Materia " + nume + " a fost inregistrata cu succes !");
		}
	}
	
	public void addNota(String elev, String materie, int nota) throws SQLException {
		ResultSet idElev = execQuery("select * from elev where `numeElev` ='"+elev+"'" );
		int idELEV = 0;
		int idMAT = 0;
		while(idElev.next()) {
			 idELEV = idElev.getInt("idElev");
		}
		ResultSet idMat = execQuery("select * from materie where `numeMaterie` ='"+materie+"'" );
		while(idMat.next()) {
			 idMAT = idMat.getInt("idMaterie");
		}
		String query = "insert into note (idElev, idMaterie, nota) values (?,?,?)";
		preparedStm = getCon().prepareStatement(query);
		preparedStm.setInt(1, idELEV);
		preparedStm.setInt(2, idMAT);
		preparedStm.setInt(3, nota);
		execAction(query);
	}
	
	
	public void addAbsenta(String elev, String materie, String absenta) throws SQLException  {
		ResultSet idelev = execQuery("select idElev from elev where `numeElev`='"+elev+"'");
		int idElev = 0;
		int idMaterie = 0;
		while(idelev.next()) {
			idElev = idelev.getInt("idElev");
		}
		ResultSet idmat = execQuery("select idMaterie from materie where `numeMaterie`='"+materie+"'");
		while(idmat.next()) {
			idMaterie = idmat.getInt(idMaterie);
		}
		
		String query = "insert into absente (idElev, idMaterie, absenta) values (?,?,?)";
		preparedStm = getCon().prepareStatement(query);
		preparedStm.setInt(1, idElev);
		preparedStm.setInt(2, idMaterie);
		preparedStm.setString(3, absenta);
		execAction(query);
	}

}
