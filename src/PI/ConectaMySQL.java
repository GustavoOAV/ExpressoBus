package PI;

// Importando classe do SQL 
import java.sql.*;

// Classe para realizar a conexão com o MySQL
public class ConectaMySQL {
	
	// Define os parâmetros de conexão
	private final static String url = "jdbc:mysql://localhost:3306/rodoviaria";
	private final static String username = "root";
	private final static String password = "@Terremoto132";
	private Connection con;
	
	// Método para abrir a conexão
	public Connection openDB() {
		try {
			con = DriverManager.getConnection(url, username, password);
			return con;
		} catch (SQLException e) {
			System.out.println("\nNão foi possível estabelecer conexão. " + e + "\n");
			System.exit(1);
		}
		return null;
	}
	
	// Método para fechar a conexão
	public void closeDB() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("\nNão foi possível fechar a conexão " + e + "\n");
		}
	}
}
