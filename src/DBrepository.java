import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DBrepository {
	
	private String tableName;
	private String query;
	
	public DBrepository(String tableName) {
		this.tableName = tableName;
		this.query = "SELECT * FROM " + this.tableName;
	}
	
	
	public ArrayList<Elev> elevObj(ResultSet result) throws SQLException{
		ArrayList<Elev> elevRepository = new ArrayList<>();
		 while(result.next()) {
			int idElev = result.getInt("idElev");
			String numeElev = result.getString("numeElev");
			String emailElev = result.getString("emailElev");
			elevRepository.add(new Elev(numeElev, emailElev, idElev));
		}
		
		return elevRepository;
	}

	public ArrayList<Profesor> profesorObj(ResultSet result1, ResultSet result2) throws SQLException{
		ArrayList<Profesor> profesorRepository = new ArrayList<>();
		ArrayList<String> materiiRepository = new ArrayList<>();
		 while(result1.next()) {
			 int idProfesor = result1.getInt("idProfesor");
			 String numeProfesor = result1.getString("numeProfesor");
			 while(result2.next()) {
				 String numeMaterie = result2.getString("numeMaterie");
				 String profMaterie = result2.getString("profesor");
				 if(profMaterie == numeProfesor) {
					 materiiRepository.add(numeMaterie);
				 }
				 profesorRepository.add(new Profesor(numeProfesor , idProfesor, materiiRepository));
			 	 materiiRepository.clear();
			 }
		 }
		
		return profesorRepository;
	}
	
	
	public ArrayList<Materii> materiiObj(ResultSet result) throws SQLException{
		ArrayList<Materii> materiiRepository = new ArrayList<>();
		while(result.next()) {
			int idMaterie = result.getInt("idMaterie");
			String numeMaterie = result.getString("numeMaterie");
			String numeProfesor = result.getString("profesor");
			materiiRepository.add(new Materii(numeMaterie, idMaterie, numeProfesor));
		}
		return materiiRepository;
	}

	
	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getQuery() {
		return query;
	}

}
