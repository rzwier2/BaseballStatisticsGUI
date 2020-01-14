import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class Database {

	private Object[][] databaseResults;

	public ResultSet rows;
	public Object[] columns;
	public DefaultTableModel defaultTableModel;
	private Connection conn = null;
	private String teamName;

	@SuppressWarnings("serial")
	public Database(String teamName) {
		this.teamName = teamName;
		System.out.println("School Selected: " + teamName);
		columns = new Object[]{"ID", "School", "Player_Num", "First_Name", "Last_Name", "Hits", "Runs", "2B", "3B", "HR", "RBI", "BB", "K", "SB", "CS", "AVG", "OBP", "SLG"};
		defaultTableModel = new DefaultTableModel(databaseResults, columns);
		try {	
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/baseballstats?user=root&password=baseball");
			Statement sqlStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String select = "SELECT HitterID, School, PlayerNum, FirstName, LastName, R, H, 2B, 3B, HR, RBI, BB, K, SB, CS, BatAvg, OBP, SLG\n" + 
					"FROM team INNER JOIN player ON team.TeamID = player.TeamID\n" + 
					"INNER JOIN hitter ON player.PlayerID = hitter.PlayerID\n" + 
					"WHERE School LIKE '" + teamName +  "'ORDER BY H desc;";
			rows = sqlStatement.executeQuery(select); // Execute the query
			
			
			Object[] tempRow;
			
			while(rows.next()) { // Add the information to the JTable
				tempRow = new Object[]{rows.getString(1), rows.getString(2), rows.getString(3), rows.getString(4), 
						rows.getString(5), rows.getString(6), rows.getString(7),rows.getInt(8), rows.getString(9), rows.getString(10), rows.getString(11), 
						rows.getString(12), rows.getString(13), rows.getString(14), rows.getString(15), rows.getString(16), rows.getString(17), rows.getString(18)};
				defaultTableModel.addRow(tempRow);
			}
		} catch (SQLException e) { // Print errors if exceptions occur
			System.out.println(e.getMessage()); 
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage()); 
		}
	}
}