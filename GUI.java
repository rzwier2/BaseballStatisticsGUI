import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class GUI extends JFrame {
	
	private JButton addPlayer, removePlayer, goButton;
	private JLabel errorMessage;
	private JTextField tfSchool, tfPlayerNum, tfFirstName, tfLastName,tfAtBats, tfRuns, tfHits, tfDoubles, tfTriples, tfHomeruns,tfRBI, tfBB, tfK, tfSB, tfCS, tfBatAvg, tfOBP, tfSLG;
	private JTable table;
	private Font font;
	private String teamBoxName;
	private String school;
	private String[] teamNames = { "Haverford College", "Swarthmore College", "Franklin & Marshall College", "Dickinson College", "Muhlenburg College", "Johns Hopkins University", "Ursinus College", "Washington College" };
	private JComboBox<String> teamList = new JComboBox<String>(teamNames);
	
	public GUI(Database db) {
		super();
		table = new JTable(db.defaultTableModel);
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("DroidSerif-Regular2.ttf"));
			font = font.deriveFont(Font.PLAIN, 18);
		} catch(IOException e) {
			font = new Font("Georgia", Font.PLAIN, 18);
		} catch (FontFormatException e) {
			font = new Font("Georgia", Font.PLAIN, 18);
		}
		
		table.setFont(font);
		table.setRowHeight(table.getRowHeight() + 8);
		table.setAutoCreateRowSorter(true);
		
		ListenForMouse mouseListener = new ListenForMouse();
		table.addMouseListener(mouseListener);
		
		// JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane, BorderLayout.CENTER);
		
		// Set button: addPlayer, removePlayer and goButton
		// addPlayer = new JButton("Add Player");
		removePlayer = new JButton("Remove Player");
		goButton = new JButton("GO");
		
		// Add action listeners 
		ListenForAction actionListener = new ListenForAction();
//		addPlayer.addActionListener(actionListener);
		removePlayer.addActionListener(actionListener);
		goButton.addActionListener(actionListener);
	
		// Strings to appear in the ComboBox
		
		// ComboBox
		teamList.setSelectedIndex(7);
		ListenForAction handler = new ListenForAction();

		teamList.addActionListener(handler);
		JLabel label1 = new JLabel("Select team:"); // Label for the ComboBox
		
		// Text field: "name" + width
		tfSchool = new JTextField("School", 10);
		tfPlayerNum = new JTextField("#", 2);
		tfFirstName = new JTextField("First Name", 6);
		tfLastName = new JTextField("Last Name", 8);
//		tfAtBats = new JTextField("AB", 3);
//		tfRuns = new JTextField("Runs", 2);
//		tfHits = new JTextField("Hits", 2);
//		tfDoubles = new JTextField("2B", 2);
//		tfTriples = new JTextField("3B", 2);
//		tfHomeruns = new JTextField("HR", 2);
//		tfRBI = new JTextField("RBI", 3);
//		tfBB = new JTextField("BB", 2);
//		tfK = new JTextField("K", 2);
//		tfSB = new JTextField("SB", 2);
//		tfCS = new JTextField("CS", 2);
	
		
		// Create a focus listener
		ListenForFocus focusListener = new ListenForFocus();
		tfSchool.addFocusListener(focusListener);
		tfPlayerNum.addFocusListener(focusListener);
		tfFirstName.addFocusListener(focusListener);
		tfLastName.addFocusListener(focusListener);
//		tfAtBats.addFocusListener(focusListener);
//		tfRuns.addFocusListener(focusListener);
//		tfHits.addFocusListener(focusListener);
//		tfDoubles.addFocusListener(focusListener);
//		tfTriples.addFocusListener(focusListener);
//		tfHomeruns.addFocusListener(focusListener);
//		tfRBI.addFocusListener(focusListener);
//		tfBB.addFocusListener(focusListener);
//		tfK.addFocusListener(focusListener);
//		tfSB.addFocusListener(focusListener);
//		tfCS.addFocusListener(focusListener);
//		tfBatAvg.addFocusListener(focusListener);
//		tfOBP.addFocusListener(focusListener);
//		tfSLG.addFocusListener(focusListener);
		
		JPanel inputPanel = new JPanel();
		inputPanel.add(label1);
		inputPanel.add(teamList);
		inputPanel.add(goButton);
		inputPanel.add(tfSchool);
		inputPanel.add(tfPlayerNum);
		inputPanel.add(tfFirstName);
		inputPanel.add(tfLastName);
		inputPanel.add(removePlayer);
//		inputPanel.add(tfAtBats);
//		inputPanel.add(tfRuns);
//		inputPanel.add(tfHits);
//		inputPanel.add(tfDoubles);
//		inputPanel.add(tfTriples);
//		inputPanel.add(tfHomeruns);
//		inputPanel.add(tfRBI);
//		inputPanel.add(tfBB);
//		inputPanel.add(tfK);
//		inputPanel.add(tfSB);
//		inputPanel.add(tfCS);
//		inputPanel.add(tfBatAvg);
//		inputPanel.add(tfOBP);
//		inputPanel.add(tfSLG);	
//		inputPanel.add(addPlayer);
		
		
		errorMessage = new JLabel("");
		errorMessage.setForeground(Color.red);
		JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		errorPanel.add(errorMessage);
		
		this.add(inputPanel, BorderLayout.SOUTH);
		this.add(errorPanel, BorderLayout.NORTH);
		
		DefaultTableCellRenderer centerColumns = new DefaultTableCellRenderer();
		centerColumns.setHorizontalAlignment(JLabel.CENTER);
		TableColumn tc = table.getColumn("ID");
		tc.setCellRenderer(centerColumns);
		

	}
	

	private class ListenForAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == goButton) 
			{
				if (teamList.getSelectedItem().equals("Johns Hopkins University")) {
					String a = (String) teamList.getSelectedItem();
					Player data = new Player();
					data.reup(a);
				}
				else if (teamList.getSelectedItem().equals("Washington College")) {
					String a = (String) teamList.getSelectedItem();
					Player data = new Player();
					data.reup(a);
				}
				else if (teamList.getSelectedItem().equals("McDaniel College")) {
					String a = (String) teamList.getSelectedItem();
					Player data = new Player();
					data.reup(a);
				}
				else if (teamList.getSelectedItem().equals("Swarthmore College")) {
					String a = (String) teamList.getSelectedItem();
					Player data = new Player();
					data.reup(a);
				}
				else if (teamList.getSelectedItem().equals("Franklin & Marshall College")) {
					String a = (String) teamList.getSelectedItem();
					Player data = new Player();
					data.reup(a);
				}
				else if (teamList.getSelectedItem().equals("Ursinus College")) {
					String a = (String) teamList.getSelectedItem();
					Player data = new Player();
					data.reup(a);
				}
				else if (teamList.getSelectedItem().equals("Muhlenburg College")) {
					String a = (String) teamList.getSelectedItem();
					Player data = new Player();
					data.reup(a);
				}
				else if (teamList.getSelectedItem().equals("Dickinson College")) {
					String a = (String) teamList.getSelectedItem();
					Player data = new Player();
					data.reup(a);
				}
				else if (teamList.getSelectedItem().equals("Haverford College")) {
					String a = (String) teamList.getSelectedItem();
					Player data = new Player();
					data.reup(a);
				}
			}
			else 
				if (e.getSource() == removePlayer) {
				System.out.println( "Within removePlayer clause");
				try { // delete from database and remove from table
					Player.db.defaultTableModel.removeRow(table.getSelectedRow());
					Player.db.rows.absolute(table.getSelectedRow());
					Player.db.rows.deleteRow();
				} catch(SQLException e1) { // Catch any exceptions
					System.out.println(e1.getMessage());
					errorMessage.setText(e1.getMessage());
				} catch(ArrayIndexOutOfBoundsException e1) {
					System.out.println(e1.getMessage());
					errorMessage.setText("To delete a player, you must first select a row.");
				}
			  } 
			else 
				if(e.getSource() == addPlayer) { 
					System.out.println( "Within addPlayer clause");
					String school, playerNum, firstName, lastName;
					String atBats, runs, hits, doubleB, triple, HR, RBI, BB, K, SB, CS;
					String batAVG, OBP, SLG;
					
					school = tfSchool.getText();
					playerNum = tfPlayerNum.getText();
					firstName = tfFirstName.getText();
					lastName = tfLastName.getText();
					atBats = tfAtBats.getText();
//					runs = tfRuns.getText();
//					hits = tfHits.getText();
//					doubleB = tfDoubles.getText();
//					triple = tfTriples.getText();
//					HR = tfHomeruns.getText();
//					RBI = tfRBI.getText();
//					BB = tfBB.getText();
//					K = tfK.getText();
//					SB = tfSB.getText();
//					CS = tfCS.getText();
//					batAVG = tfBatAvg.getText();
//					OBP = tfOBP.getText();
//					SLG = tfSLG.getText();
					
					int playerID = 0;
					
					try { // insert the information into the database
						Player.db.rows.moveToInsertRow();
						Player.db.rows.updateString("School", school);
						Player.db.rows.updateString("#", playerNum);
						Player.db.rows.updateString("First_Name", firstName);
						Player.db.rows.updateString("Last_Name", lastName);
//						Player.db.rows.updateString("R", runs);
//						Player.db.rows.updateString("H", hits);
//						Player.db.rows.updateString("2B", doubleB);
//						Player.db.rows.updateString("3B", triple);
//						Player.db.rows.updateString("HR", HR);
//						Player.db.rows.updateString("RBI", RBI);
//						Player.db.rows.updateString("BB", BB);
//						Player.db.rows.updateString("K", K);
//						Player.db.rows.updateString("SB", SB);					
//						Player.db.rows.updateString("CS", CS);
//						Player.db.rows.updateString("AVG", batAVG);
//						Player.db.rows.updateString("OBP", OBP);
//						Player.db.rows.updateString("SLG", SLG);
						
						Player.db.rows.insertRow();
						Player.db.rows.updateRow();
						
						Player.db.rows.last();
						playerID = Player.db.rows.getInt(1);
						Object[] player = {school, playerNum, firstName, lastName}; // , runs, hits, doubleB, triple, HR, RBI, BB, K, SB, CS, batAVG, OBP, SLG
						Player.db.defaultTableModel.addRow(player); // Add the row 
						errorMessage.setText(""); 
					
					} catch (SQLException e2) { 
						System.out.println(e2.getMessage());
						if (e2.getMessage().toString().startsWith("Data")) {
							errorMessage.setText(""); 
						}
					}
				} 
			//else System.out.println("Why is this happening?");
		}
	}
	
	

	private class ListenForFocus implements FocusListener {
		public void focusGained(FocusEvent e) { 
			if(tfSchool.getText().equals("School") && e.getSource() == tfSchool) {
				tfSchool.setText("");
			} else if(tfPlayerNum.getText().equals("Num") && e.getSource() == tfPlayerNum) {
				tfPlayerNum.setText("");
			} else if(tfFirstName.getText().equals("First Name") && e.getSource() == tfFirstName) {
				tfFirstName.setText("");
			} else if(tfLastName.getText().equals("Last Name") && e.getSource() == tfLastName) {
				tfLastName.setText("");
			} else if(tfAtBats.getText().equals("AB") && e.getSource() == tfAtBats) {
				tfAtBats.setText("");
			} else if(tfHits.getText().equals("Hits") && e.getSource() == tfHits) {
				tfHits.setText("");
			} else if(tfRuns.getText().equals("Runs") && e.getSource() == tfRuns) {
				tfRuns.setText("");
			} else if(tfDoubles.getText().equals("Doubles") && e.getSource() == tfDoubles) {
				tfDoubles.setText("");
			} else if(tfTriples.getText().equals("Triples") && e.getSource() == tfTriples) {
				tfTriples.setText("");		
			} else if(tfHomeruns.getText().equals("HR") && e.getSource() == tfHomeruns) {
				tfHomeruns.setText("");
				
			} else if(tfRBI.getText().equals("RBI") && e.getSource() == tfRBI) {
				tfRBI.setText("");
			} else if(tfBB.getText().equals("BB") && e.getSource() == tfBB) {
				tfBB.setText("");
			} else if(tfK.getText().equals("K") && e.getSource() == tfBB) {
				tfBB.setText("");
			} else if(tfSB.getText().equals("Stolen Bases") && e.getSource() == tfSB) {
				tfSB.setText("");
			} else if(tfCS.getText().equals("Caught Stealing") && e.getSource() == tfSB) {
				tfSB.setText("");
			} else if(tfBatAvg.getText().equals("AVG") && e.getSource() == tfBatAvg) {
				tfBatAvg.setText("");
			} else if(tfOBP.getText().equals("OBP") && e.getSource() == tfOBP) {
				tfOBP.setText("");
			} else if(tfSLG.getText().equals("SLG") && e.getSource() == tfSLG) {
				tfSLG.setText("");
			}
		}

		public void focusLost(FocusEvent e) { 
			if(tfSchool.getText().equals("") && e.getSource() == tfSchool) {
				tfSchool.setText("School");
			} else if(tfPlayerNum.getText().equals("") && e.getSource() == tfPlayerNum) {
				tfPlayerNum.setText("Num");
			} else if(tfFirstName.getText().equals("") && e.getSource() == tfFirstName) {
				tfFirstName.setText("First Name");
			} else if(tfLastName.getText().equals("") && e.getSource() == tfLastName) {
				tfLastName.setText("Last Name");
			} else if(tfAtBats.getText().equals("") && e.getSource() == tfAtBats) {
				tfAtBats.setText("AB");
			} else if(tfHits.getText().equals("") && e.getSource() == tfHits) {
				tfHits.setText("Hits");
			} else if(tfRuns.getText().equals("") && e.getSource() == tfRuns) {
				tfRuns.setText("Runs");
			} else if(tfDoubles.getText().equals("") && e.getSource() == tfDoubles) {
				tfDoubles.setText("Doubles");
			} else if(tfTriples.getText().equals("") && e.getSource() == tfTriples) {
				tfTriples.setText("Triples");		
			} else if(tfHomeruns.getText().equals("") && e.getSource() == tfHomeruns) {
				tfHomeruns.setText("HR");
				
			} else if(tfRBI.getText().equals("") && e.getSource() == tfRBI) {
				tfRBI.setText("RBI");
			} else if(tfBB.getText().equals("") && e.getSource() == tfBB) {
				tfBB.setText("BB");
			} else if(tfK.getText().equals("") && e.getSource() == tfBB) {
				tfBB.setText("K");
			} else if(tfSB.getText().equals("") && e.getSource() == tfSB) {
				tfSB.setText("Stolen Bases");
			} else if(tfCS.getText().equals("") && e.getSource() == tfSB) {
				tfSB.setText("Caught Stealing");
			} else if(tfBatAvg.getText().equals("") && e.getSource() == tfBatAvg) {
				tfBatAvg.setText("AVG");
			} else if(tfOBP.getText().equals("") && e.getSource() == tfOBP) {
				tfOBP.setText("OBP");
			} else if(tfSLG.getText().equals("") && e.getSource() == tfSLG) {
				tfSLG.setText("SLG");
			}
		}
		
	}
	
	private class ListenForMouse extends MouseAdapter {
		public void mouseReleased(MouseEvent mouseEvent) {			if (SwingUtilities.isRightMouseButton(mouseEvent)) {
				String value = JOptionPane.showInputDialog(null, "Enter Cell Value:");
				if(value != null) { // update the database
					table.setValueAt(value, table.getSelectedRow(), table.getSelectedColumn());
					String updateColumn;
					
					try { 
						Player.db.rows.absolute(table.getSelectedRow()+1);
						updateColumn = Player.db.defaultTableModel.getColumnName(table.getSelectedColumn());												 
					} catch (SQLException e1) { 
						errorMessage.setText("An error has occurred.");
						System.out.println(e1.getMessage());
					}
				}
			}
		}
	}

	public void setColumnWidths(Object[] columns, int...widths) {
		TableColumn column;
		for(int i = 0; i < 8; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(widths[i]);
		}
	}
	
	public void setErrorMessage(String message) {
		errorMessage.setText(message);
	}
}