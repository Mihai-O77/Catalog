import java.sql.*;

public class DBconnection {

	private static String url = "jdbc:mysql://localhost:3306/catalogbd";
	private static String user = "root";
	private static String psw = "";
	private static Connection con = null;
	private static Statement stm = null;
	protected static PreparedStatement preparedStm = null;
	
	public DBconnection() {
//		this.createConnection();
	}
	
	public Connection getCon() {
		return con;
	}
	
	public void createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user , psw);
		}
		catch(ClassNotFoundException e){
			System.out.println(e);
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	public static void execAction(String query) {
		try {
			preparedStm.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("Eroare!");
			System.out.println(e);
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			createConnection();
			stm = con.createStatement();
			ResultSet result = stm.executeQuery(query);
			return result;
		}
		catch(SQLException e) {
			System.out.println(e);
			return null;
		}
	}
	
}


