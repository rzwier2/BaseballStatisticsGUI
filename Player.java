import javax.swing.JFrame;
import java.util.*;


public class Player {

	static GUI gui;
	static Database db;
	String teamName;
	
	public static void main (String[] args) {
		// System.out.println("Please enter a school name: ");
		// Scanner in = new Scanner (System.in);
		// String school = in.nextLine();
		db = new Database("Washington College");
		gui = new GUI(db);
		
		
		gui.setColumnWidths(db.columns, 40, 350, 40, 130, 100, 120, 80, 80, 80, 80, 100, 100, 10, 10, 100, 100, 100, 100);
		gui.setSize(1500,1000);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		
	}
	public void reup(String teamName) {
		this.teamName = teamName;
		db = new Database(teamName);
		gui = new GUI(db);
		
		
		gui.setColumnWidths(db.columns, 40, 350, 40, 130, 100, 120, 80, 80, 80, 80, 100, 100, 10, 10, 100, 100, 100, 100);
		gui.setSize(1500,1000);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
	}
}