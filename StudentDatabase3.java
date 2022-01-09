package SqlDataBase;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
import java.awt.Color;
import java.awt.SystemColor;

public class StudentDatabase3 {

	JFrame frmStudentDatabase;
	private JTable table;
	private JTextField courseTextField;
	private JTextField yearTextField;
	private JTextField genderTextField;
	private JTextField remarkTextField;
	private JTextField clearedTextField;
	private JLabel lblTotalLabel;
	
	private DefaultTableModel model;
	
	Connection conn =  null;
	
	PreparedStatement pst;
	Statement stmt;
	ResultSet rs;
	String SQL;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentDatabase3 window = new StudentDatabase3();
					window.frmStudentDatabase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	public static Connection dbConnect() { // Step 1 - Get a connection to SQLite database	
		try 
		{   // Step 1.1 - load Java's JDBC SQLite Driver
			Class.forName("org.sqlite.JDBC");			
			// Step 1.2 - get a DB Connection
			Connection conn = DriverManager.getConnection("JDBC:sqlite:d://Games//student.sqlite"); // created using SQLite Manager (SQLiteManager_4.6.6_1430708940)
			//System.out.print(conn);
			// prompt user if connection attempt is successful
			JOptionPane.showMessageDialog(null, "Connection Successful.");
			return conn;
		}
		catch (Exception err)
		{
			JOptionPane.showMessageDialog(null, "Connection unsuccessful. Exception -> "+err);
			return null;
		}
	}

	/**
	 * Create the application.
	 */
	public StudentDatabase3() {
		initialize();		
		conn= dbConnect();	// Step1: Get a connection to SQLite database	
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStudentDatabase = new JFrame();
		frmStudentDatabase.getContentPane().setBackground(new Color(255, 228, 196));
		frmStudentDatabase.setTitle("Student Database");
		frmStudentDatabase.setBounds(100, 100, 765, 543);
		frmStudentDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStudentDatabase.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 281, 713, 217);
		frmStudentDatabase.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBackground(SystemColor.info);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Name", "Course", "Year", "Gender", "Score", "Total", "Grade", "Remark", "Cleared"
			}
		));
		
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(36, 67, 69, 20);
		frmStudentDatabase.getContentPane().add(lblCourse);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setBounds(36, 103, 69, 20);
		frmStudentDatabase.getContentPane().add(lblYear);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(36, 139, 69, 20);
		frmStudentDatabase.getContentPane().add(lblGender);
		
		JLabel lblRemark = new JLabel("Remark");
		lblRemark.setBounds(36, 177, 69, 20);
		frmStudentDatabase.getContentPane().add(lblRemark);
		
		JLabel lblCleared = new JLabel("Cleared");
		lblCleared.setBounds(36, 213, 69, 20);
		frmStudentDatabase.getContentPane().add(lblCleared);
		
		courseTextField = new JTextField();
		courseTextField.setBounds(116, 64, 146, 26);
		frmStudentDatabase.getContentPane().add(courseTextField);
		courseTextField.setColumns(10);
		
		yearTextField = new JTextField();
		yearTextField.setBounds(116, 100, 146, 26);
		frmStudentDatabase.getContentPane().add(yearTextField);
		yearTextField.setColumns(10);
		
		genderTextField = new JTextField();
		genderTextField.setBounds(116, 136, 146, 26);
		frmStudentDatabase.getContentPane().add(genderTextField);
		genderTextField.setColumns(10);
		
		remarkTextField = new JTextField();
		remarkTextField.setBounds(115, 174, 146, 26);
		frmStudentDatabase.getContentPane().add(remarkTextField);
		remarkTextField.setColumns(10);
		
		clearedTextField = new JTextField();
		clearedTextField.setBounds(115, 210, 146, 26);
		frmStudentDatabase.getContentPane().add(clearedTextField);
		clearedTextField.setColumns(10);
						
		JButton btnExitButton = new JButton("Exit");
		btnExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExitButton.setBounds(510, 208, 209, 30);
		frmStudentDatabase.getContentPane().add(btnExitButton);
		
		JButton btnLoadButton = new JButton("Load Database to Table");
		btnLoadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int count = 0;
				try
				{
					// Step 2 - Prepare the SQL command
					String query = "select * from student"; // select all columns/data fields from the student (SQLite DB)
					pst = conn.prepareStatement(query);	
					
					// Step 3 - execute SQL command or the query and store in ResultSet
					rs = pst.executeQuery();
					
					//Step 4 - Convert ResultSet to TableModel - using an external library(jar file) rs2xml
					
					table.setModel(DbUtils.resultSetToTableModel(rs)); // populates JTable with ResultSet data using an EXTERNAL LIBRARY rs2xml.jar				
			
					/* OR Display ResultSet on output console
					while (rs.next()) {
						System.out.println(rs.getString("ID")+" , "+rs.getString("Name")+" , "+rs.getString("Course")+" , "+rs.getString("Year")+
								" , "+rs.getString("Gender")+" , "+rs.getString("Score")+" , "+rs.getString("Total")+
								" , "+rs.getString("Grade")+" , "+rs.getString("Remark")+" , "+rs.getString("Cleared"));
						count++;
					}
					System.out.println("Count: "+count);
					*/
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
		});
		btnLoadButton.setBounds(511, 61, 209, 30);
		frmStudentDatabase.getContentPane().add(btnLoadButton);
		
		JButton btnCourseButton = new JButton(">>");
		btnCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = 0;
				String course=courseTextField.getText();
				try
				{
					
					String query = "select * from student where Course='"+course+"'"; // select all columns from the student (SQLite DB) where Course is as specified
					pst = conn.prepareStatement(query);					
					rs = pst.executeQuery();
					//table.setModel(DbUtils.resultSetToTableModel(rs)); // populates JTable with ResultSet data using an EXTERNAL LIBRARY rs2xml.jar

					/* Display on output console
					while (rs.next()) {
						System.out.println(rs.getString("ID")+" , "+rs.getString("Name")+" , "+rs.getString("Course")+" , "+rs.getString("Year")+
								" , "+rs.getString("Gender")+" , "+rs.getString("Score")+" , "+rs.getString("Total")+
								" , "+rs.getString("Grade")+" , "+rs.getString("Remark")+" , "+rs.getString("Cleared"));
						count++;
					}
					System.out.println("Count: "+count);
					*/
				}
				catch(Exception err)
				{
					err.printStackTrace();
				}
			}
		});
		btnCourseButton.setBounds(266, 63, 57, 29);
		frmStudentDatabase.getContentPane().add(btnCourseButton);
		
		JButton btnGenderButton = new JButton(">>");
		btnGenderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = 0;
				String gender = genderTextField.getText();
				try
				{
					String query = "select * from student where Gender='"+gender+"'"; // select all columns from the student (SQLite DB) where Gender is as specified. 
					pst = conn.prepareStatement(query);					
					rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); // populates JTable with ResultSet data using an EXTERNAL LIBRARY rs2xml.jar

					/* Display on output console
					while (rs.next()) {
						System.out.println(rs.getString("ID")+" , "+rs.getString("Name")+" , "+rs.getString("Course")+" , "+rs.getString("Year")+
								" , "+rs.getString("Gender")+" , "+rs.getString("Score")+" , "+rs.getString("Total")+
								" , "+rs.getString("Grade")+" , "+rs.getString("Remark")+" , "+rs.getString("Cleared"));
						count++;
					}
					System.out.println("Count: "+count);
					*/
				}
				catch(Exception err)
				{
					err.printStackTrace();
				}
			}
		});
		btnGenderButton.setBounds(266, 135, 57, 29);
		frmStudentDatabase.getContentPane().add(btnGenderButton);
		
		JButton btnYearButton = new JButton(">>");
		btnYearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = 0;
				int year = Integer.parseInt(yearTextField.getText());
				try
				{
					String query = "select * from student where Year="+year; // select all columns from the student (SQLite DB) where Year is as specified. 
					pst = conn.prepareStatement(query);					
					rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); // populates JTable with ResultSet data using an EXTERNAL LIBRARY rs2xml.jar

					/* Display on output console
					while (rs.next()) {
						System.out.println(rs.getString("ID")+" , "+rs.getString("Name")+" , "+rs.getString("Course")+" , "+rs.getString("Year")+
								" , "+rs.getString("Gender")+" , "+rs.getString("Score")+" , "+rs.getString("Total")+
								" , "+rs.getString("Grade")+" , "+rs.getString("Remark")+" , "+rs.getString("Cleared"));
						count++;
					}
					System.out.println("Count: "+count);
					*/
				}
				catch(Exception err)
				{
					err.printStackTrace();
				}
			}
		});
		btnYearButton.setBounds(266, 99, 57, 29);
		frmStudentDatabase.getContentPane().add(btnYearButton);
		
		JLabel lblNewLabel = new JLabel("FILTERS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(15, 28, 98, 20);
		frmStudentDatabase.getContentPane().add(lblNewLabel);
		
		JButton btnClearFiltersButton = new JButton("Clear");
		btnClearFiltersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				courseTextField.setText("");				
				yearTextField.setText("");
				genderTextField.setText("");
				remarkTextField.setText("");
				clearedTextField.setText("");
			}
		});
		btnClearFiltersButton.setBounds(510, 171, 209, 31);
		frmStudentDatabase.getContentPane().add(btnClearFiltersButton);
		
		JButton btnRemarkButton = new JButton(">>");
		btnRemarkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = 0;
				String remark = remarkTextField.getText();
				try
				{
					String query = "select * from student where Remark='"+remark+"'"; // select all columns from the student (SQLite DB) where Remark is as specified. 
					pst = conn.prepareStatement(query);					
					rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); // populates JTable with ResultSet data using an EXTERNAL LIBRARY rs2xml.jar

					/* Display on output console
					while (rs.next()) {
						System.out.println(rs.getString("ID")+" , "+rs.getString("Name")+" , "+rs.getString("Course")+" , "+rs.getString("Year")+
								" , "+rs.getString("Gender")+" , "+rs.getString("Score")+" , "+rs.getString("Total")+
								" , "+rs.getString("Grade")+" , "+rs.getString("Remark")+" , "+rs.getString("Cleared"));
						count++;
					}
					System.out.println("Count: "+count);
					*/
				}
				catch(Exception err)
				{
					err.printStackTrace();
				}
			}
		});
		btnRemarkButton.setBounds(266, 173, 57, 29);
		frmStudentDatabase.getContentPane().add(btnRemarkButton);
		
		JButton btnClearedButton = new JButton(">>");
		btnClearedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = 0;
				String cleared = clearedTextField.getText();
				try
				{
					String query = "select * from student where Cleared='"+cleared+"'"; // select all columns from the student (SQLite DB) where Cleared is as specified. 
					pst = conn.prepareStatement(query);					
					rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); // populates JTable with ResultSet data using an EXTERNAL LIBRARY rs2xml.jar

					/* Display on output console
					while (rs.next()) {
						System.out.println(rs.getString("ID")+" , "+rs.getString("Name")+" , "+rs.getString("Course")+" , "+rs.getString("Year")+
								" , "+rs.getString("Gender")+" , "+rs.getString("Score")+" , "+rs.getString("Total")+
								" , "+rs.getString("Grade")+" , "+rs.getString("Remark")+" , "+rs.getString("Cleared"));
						count++;
					}
					System.out.println("Count: "+count);
					*/
				}
				catch(Exception err)
				{
					err.printStackTrace();
				}
			}
		});
		btnClearedButton.setBounds(266, 211, 57, 25);
		frmStudentDatabase.getContentPane().add(btnClearedButton);
		
		JButton btnNewButton = new JButton("Course AND Year");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int count = 0;
				String course = courseTextField.getText();
				int year = Integer.parseInt(yearTextField.getText()); 
				try
				{
					String query = "select * from student where Course='"+course+"' AND Year="+year; // select all columns from the student (SQLite DB) where Course is as specified AND Year is as specified. 
					pst = conn.prepareStatement(query);					
					rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); // populates JTable with ResultSet data using an EXTERNAL LIBRARY rs2xml.jar

					/* Display on output console
					while (rs.next()) {
						System.out.println(rs.getString("ID")+" , "+rs.getString("Name")+" , "+rs.getString("Course")+" , "+rs.getString("Year")+
								" , "+rs.getString("Gender")+" , "+rs.getString("Score")+" , "+rs.getString("Total")+
								" , "+rs.getString("Grade")+" , "+rs.getString("Remark")+" , "+rs.getString("Cleared"));
						count++;
					}
					System.out.println("Count: "+count);
					*/
				}
				catch(Exception err)
				{
					err.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(510, 97, 209, 32);
		frmStudentDatabase.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Year OR Remark");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int count = 0;				
				int year = Integer.parseInt(yearTextField.getText()); 
				String remark = remarkTextField.getText();
				try
				{
					String query = "select * from student where Year="+year+" OR Remark='"+remark+"'"; // select all columns from the student (SQLite DB) where Year is as specified OR Remark is as specified. 
					pst = conn.prepareStatement(query);					
					rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs)); // populates JTable with ResultSet data using an EXTERNAL LIBRARY rs2xml.jar

					/* Display on output console
					while (rs.next()) {
						System.out.println(rs.getString("ID")+" , "+rs.getString("Name")+" , "+rs.getString("Course")+" , "+rs.getString("Year")+
								" , "+rs.getString("Gender")+" , "+rs.getString("Score")+" , "+rs.getString("Total")+
								" , "+rs.getString("Grade")+" , "+rs.getString("Remark")+" , "+rs.getString("Cleared"));
						count++;
					}
					System.out.println("Count: "+count);
					*/
				}
				catch(Exception err)
				{
					err.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(510, 135, 209, 30);
		frmStudentDatabase.getContentPane().add(btnNewButton_1);
		
		lblTotalLabel = new JLabel("");
		lblTotalLabel.setBounds(15, 259, 108, 20);
		frmStudentDatabase.getContentPane().add(lblTotalLabel);
		
		JButton btnNewButton_2 = new JButton("+ Insert");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/*insertStud win = new insertStud();
				win.insertStud.setVisible(true);
				frmStudentDatabase.dispose();
				*/
				try {
					Exer1 show = new Exer1();
					show.frmStudentInformation.setVisible(true);
					frmStudentDatabase.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		btnNewButton_2.setBounds(335, 63, 115, 29);
		frmStudentDatabase.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Delete");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				

				try {
					System.out.println(table.getSelectedRow());
					String id=	JOptionPane.showInputDialog("Enter the ID Number that you want to delete? ");
					String query = "select * from student where Id='"+id+"'";
					pst= conn.prepareStatement(query);					
					rs = pst.executeQuery();
					String data= rs.getString("Id");
					if(id.equals(data)) {
					//JOptionPane.showMessageDialog(null, "User Found! \n ID:"+data);
						String SQL="DELETE FROM * student ";
						pst = conn.prepareStatement(SQL);					
						pst.setString(1, id);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Successfully Deleted! Load again to refresh!");
						
				}
					else 
					JOptionPane.showMessageDialog(null, "Invalid Choice");
						
					
					
					
				}catch(Exception ex) {
				ex.printStackTrace();	
				}
				
				
			}
		});
		btnNewButton_3.setBounds(333, 100, 117, 26);
		frmStudentDatabase.getContentPane().add(btnNewButton_3);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			String id,name,course,gender,remark,clearance,data;
			int year;	
			
			double score,total,grade;
				try {
					
					id=	JOptionPane.showInputDialog("Enter the ID Number that you want to Update ");
				String query = "select * from student where Id='"+id+"'";
				pst= conn.prepareStatement(query);	
				rs = pst.executeQuery();
				data= rs.getString("Id");
				name=rs.getString("Name");
				course=rs.getString("Course");
				year=rs.getInt("Year");
				gender=rs.getString("Gender");
				score=rs.getDouble("Score");
				total=rs.getDouble("Total");
				grade=rs.getDouble("Grade");
				remark=rs.getString("Remark");
				clearance=rs.getString("Cleared");
				if(id.equals(data)) {
					JTextField ch = new JTextField();
					Object[] obj= { "User Found! \n1. ID:"+data+"\n2. Name:"+name
							+"\n3. Course:"+course+"\n4. Year:"+year+"\n5. Gender:"+gender+"\n6. Score:"+score
							+"\n7. Total:"+total+"\n8. Grade:"+grade+"\n9. Remark:"+remark+"\n10 Clearance:"+clearance+"\n",ch};
				JOptionPane.showMessageDialog(null, obj);
				
				int choice =Integer.parseInt(ch.getText());
				switch (choice) {
				
		
					
				case 1:
				String ID=	JOptionPane.showInputDialog("Enter the new ID");
				SQL="UPDATE student set Id=? where Name='"+name+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setString(1, ID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh !");
					break;
				case 2:
					String NAME=	JOptionPane.showInputDialog("Enter the new Name");
					SQL="UPDATE student set Name=? where Id='"+data+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setString(1, NAME);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					break;
				case 3:
					String COURSE=	JOptionPane.showInputDialog("Enter the new Course");
					SQL="UPDATE student set Course=? where Name='"+name+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setString(1, COURSE);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					break;
				case 4:
				int YEAR= Integer.parseInt(JOptionPane.showInputDialog("Enter the new Year Level"));
				SQL="UPDATE student set Year=? where Name='"+name+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setInt(1, YEAR);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					break;
				case 5:
					String GENDER=	JOptionPane.showInputDialog("Enter the student new Gender");
					SQL="UPDATE student set Gender=? where Name='"+name+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setString(1,GENDER);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					break;
				case 6:
					double SCORE=Double.parseDouble(JOptionPane.showInputDialog("Enter the new Score"));
					SQL="UPDATE student set Score=? where Name='"+name+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setDouble(1, SCORE);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					break;
				case 7:
					double TOTSCORE=Double.parseDouble(JOptionPane.showInputDialog("Enter the new Total Score"));
					SQL="UPDATE student set Total=? where Name='"+name+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setDouble(1, TOTSCORE);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					break;
				case 8:
					double GRADE=Double.parseDouble(JOptionPane.showInputDialog("Enter the new Grade"));
					SQL="UPDATE student set Grade=? where Name='"+name+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setDouble(1, GRADE);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					break;
				case 9:
					String REMARK=	JOptionPane.showInputDialog("Enter the new students remark");
					SQL="UPDATE student set Remark=? where Name='"+name+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setString(1, REMARK);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					break;
				case 10:
					String Clearance=	JOptionPane.showInputDialog("Enter the new update for clearance");
					SQL="UPDATE student set Cleared=? where Name='"+name+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setString(1, Clearance);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
				
				}
				}
				else 
					JOptionPane.showMessageDialog(null, "Invalid Choice");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
				}
			}
		});
		btnUpdate.setBounds(333, 136, 117, 30);
		frmStudentDatabase.getContentPane().add(btnUpdate);
	}
}
