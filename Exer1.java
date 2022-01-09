package SqlDataBase;



import java.awt.EventQueue;



import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import java.awt.Font;

public class Exer1  {

	static JFrame frmStudentInformation;
	private JTextField idTextField;
	private JTextField nameTextField;
	private JTextField courseTextField;
	private JTextField scoreTextField;
	private JTextField totalTextField;
	private JTextField gradeTextField;
	private JTextField remTextField;
	private ButtonGroup bg = new ButtonGroup();
	 //File ff = new File("d:\\Games\\StudRec.csv");
	 public static String  ff = "d:\\Games\\StudRec.csv";
	 File fle = new File(ff);
 static FileWriter f ;
 static FileReader fr;
 static BufferedReader Br;
	static int x =0, cnt=0;
	static int numHS[]= new int[50];
	
	static String clerance;
	static DefaultTableModel model;
	static String ListStud[][] = new String[20][10];
	static ArrayList arr = new ArrayList();
	static JTextPane Notif;
	String id, name, course, gender, remarks;
	int year,grade;
	double score, total;
	private JTable table;

	
	Connection conn=null;
	PreparedStatement pst;
	java.sql.Statement stmt;
	ResultSet rs;
	String sql;
	
	
	//static DefaultTableModel model ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					f = new FileWriter (ff,true);
					Exer1 window = new Exer1();
					window.frmStudentInformation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public static Connection dbConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn= DriverManager.getConnection("JDBC:sqlite:d://Games//Student.sqlite");
			JOptionPane.showMessageDialog(null, "Connection Successful");
			return conn;
			
		}catch(Exception e) {return null;}
	}
	
	
	
	
	public Exer1() throws IOException{
		initialize();
		conn= dbConnection();
	}

	
	// determines exam grade remark
	private String getRemarks(int grade)
	{  String remarks;
		if (grade>=96) remarks="Excellent";
		else if (grade>=90 && grade<96) remarks="Good";
		else if (grade>=85 && grade<90) remarks="Average";
		else if (grade>=80 && grade<85) remarks="Fair";
		else if (grade>=75 && grade<80) remarks="Poor";
		else remarks="Fail";
		return remarks;
	}	
	
	/**
	 * Initialize the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException {
		frmStudentInformation = new JFrame();
		frmStudentInformation.getContentPane().setBackground(new Color(255, 228, 196));
		frmStudentInformation.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\2ndSemSY2018-2019\\Windowbuilder-SWT-WIndow App codes (Feb-1-2019)\\images\\images2.jpg"));
		frmStudentInformation.setTitle("Student Information");
		frmStudentInformation.setBounds(100, 100, 798, 536);
		frmStudentInformation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStudentInformation.getContentPane().setLayout(null);
		
		JPanel personalPanel = new JPanel();
		personalPanel.setBackground(SystemColor.info);
		personalPanel.setBorder(new TitledBorder(null, "Personal Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		personalPanel.setBounds(15, 16, 291, 279);
		frmStudentInformation.getContentPane().add(personalPanel);
		personalPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setBounds(15, 47, 69, 20);
		personalPanel.add(lblNewLabel);
		
		idTextField = new JTextField();
		idTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Notif.setText("Remider: Hit clear button to erase all textfield");
			}
		});
		idTextField.setBounds(120, 44, 146, 26);
		personalPanel.add(idTextField);
		idTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setBounds(15, 88, 69, 20);
		personalPanel.add(lblNewLabel_1);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(120, 86, 146, 26);
		personalPanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Course");
		lblNewLabel_2.setBounds(15, 130, 69, 20);
		personalPanel.add(lblNewLabel_2);
		
		courseTextField = new JTextField();
		courseTextField.setBounds(120, 128, 146, 26);
		personalPanel.add(courseTextField);
		courseTextField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Year");
		lblNewLabel_3.setBounds(15, 175, 69, 20);
		personalPanel.add(lblNewLabel_3);
		
		JComboBox yearComboBox = new JComboBox();
		yearComboBox.setBounds(120, 172, 146, 26);
		personalPanel.add(yearComboBox);
				
		for (int i=1;i<=5;i++) 		
			yearComboBox.addItem(i);			               
		
		JLabel lblNewLabel_8 = new JLabel("Gender");
		lblNewLabel_8.setBounds(14, 225, 69, 20);
		personalPanel.add(lblNewLabel_8);		
		
		JRadioButton rdbtnFemaleRadioButton = new JRadioButton("Female");
		rdbtnFemaleRadioButton.setBackground(SystemColor.info);
		rdbtnFemaleRadioButton.setSelected(true);
		rdbtnFemaleRadioButton.setBounds(116, 219, 83, 29);
		personalPanel.add(rdbtnFemaleRadioButton);
		
		JRadioButton rdbtnMaleRadioButton = new JRadioButton("Male");
		rdbtnMaleRadioButton.setBackground(SystemColor.info);
		rdbtnMaleRadioButton.setBounds(207, 219, 73, 29);
		personalPanel.add(rdbtnMaleRadioButton);
		
		bg.add(rdbtnFemaleRadioButton);
		bg.add(rdbtnMaleRadioButton);
		
		JPanel examResultPanel = new JPanel();
		examResultPanel.setBackground(SystemColor.info);
		examResultPanel.setBorder(new TitledBorder(null, "Exam Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		examResultPanel.setBounds(319, 16, 301, 279);
		frmStudentInformation.getContentPane().add(examResultPanel);
		examResultPanel.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Score");
		lblNewLabel_4.setBounds(15, 50, 39, 20);
		examResultPanel.add(lblNewLabel_4);
		
		scoreTextField = new JTextField();
		scoreTextField.setBounds(140, 43, 146, 26);
		examResultPanel.add(scoreTextField);
		scoreTextField.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Total Points");
		lblNewLabel_5.setBounds(15, 89, 104, 20);
		examResultPanel.add(lblNewLabel_5);
		
		totalTextField = new JTextField();
		totalTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Notif.setText("Remider: Hit submit before sending the data!");
			}
			
		
		});
		totalTextField.setBounds(140, 85, 146, 26);
		examResultPanel.add(totalTextField);
		totalTextField.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Grade");
		lblNewLabel_6.setBounds(15, 134, 69, 20);
		examResultPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Remarks");
		lblNewLabel_7.setBounds(15, 178, 69, 20);
		examResultPanel.add(lblNewLabel_7);
		
		gradeTextField = new JTextField();
		gradeTextField.setEditable(false);
		gradeTextField.setBounds(140, 128, 146, 26);
		examResultPanel.add(gradeTextField);
		gradeTextField.setColumns(10);
		
		remTextField = new JTextField();
		remTextField.setEditable(false);
		remTextField.setBounds(140, 171, 146, 26);
		examResultPanel.add(remTextField);
		remTextField.setColumns(10);
		
		JCheckBox chckbxClearanceCheckBox = new JCheckBox("Clearance");
		chckbxClearanceCheckBox.setBackground(SystemColor.info);
		chckbxClearanceCheckBox.setBounds(136, 223, 139, 29);
		examResultPanel.add(chckbxClearanceCheckBox);
		
		JLabel lblNewLabel_9 = new JLabel("Requirements");
		lblNewLabel_9.setBounds(15, 227, 104, 20);
		examResultPanel.add(lblNewLabel_9);
		
		JButton btnExitButton = new JButton("Exit");
		
		btnExitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {	
				try {
					
					f.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		btnExitButton.setBounds(488, 457, 132, 29);
		frmStudentInformation.getContentPane().add(btnExitButton);		
		
		JButton btnSubmitButton = new JButton("Submit");
		btnSubmitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id, name, course, gender, remarks;
				int year,grade;
				double score, total;
				try {
					score=total=0;
					id=idTextField.getText();
					name=nameTextField.getText();
					course=courseTextField.getText();
					year=(int)yearComboBox.getSelectedItem();				
					gender=(rdbtnFemaleRadioButton.isSelected()) ? rdbtnFemaleRadioButton.getLabel() : rdbtnMaleRadioButton.getLabel();
					score=Double.parseDouble(scoreTextField.getText());
					total=Double.parseDouble(totalTextField.getText());
					grade=(int)((score/total)*100);
						remarks=getRemarks(grade);
						gradeTextField.setText(""+grade);
						remTextField.setText(remarks);	
						
					if (!id.isEmpty() && !name.isEmpty() && !course.isEmpty() && score>0 && total>0) // if no blank entries
					{
					
						// backup display 
						System.out.println("Id:"+id);
						System.out.println("Name:"+name);
						System.out.println("Course:"+course);
						System.out.println("Year:"+year);
						System.out.println("Gender:"+gender);
						System.out.println("Score:"+score);
						System.out.println("Total:"+total);

						System.out.println("Grade:"+grade);
						System.out.println("Remarks:"+remarks);
						System.out.println("Cleared:"+chckbxClearanceCheckBox.isSelected());	
						System.out.println();
						/*if(chckbxClearanceCheckBox.isSelected()==true) {
							 clerance="Cleared";
						}
						else {
							 clerance="Uncleared";
						}
						
						f.write(id+","+name+","+course+","+year+","+gender+","+score+","+total+","+grade+","+ remarks+","+clerance);
						f.append('\n');
						f.flush();*/
						
						stmt = conn.createStatement();

						
						
						// insert the data
						 stmt.executeUpdate("insert into student (Id,Name,Course,Year,Gender,Score,Total,Grade,Remark,Cleared) "+ "VALUES('"+id+"','"+name+"','"+course+"','"+year+"','"+gender+"','"+score+"','"+total+"','"+grade+"','"+remarks+"','"+chckbxClearanceCheckBox.isSelected()+"')");
						
					JOptionPane.showMessageDialog(null, "Successfully Added!");
						
						Object[] row = {id,name,course,year,gender,score,total,grade, remarks,clerance};
						model.addRow(row);
						//f.close();
						
					}//if
					else
					{
						JOptionPane.showMessageDialog(null,"Lacking data entry.","Error",JOptionPane.ERROR_MESSAGE);						
					}	//ep[x++]= new Exer2Parent(id,name,course,year,gender,score,total,grade,remarks);
					//ep[x++]= new Exer2Parent(id,name,course,year,gender,score,total,grade,remarks);
				}//try
				catch (Exception err)
				{				
					JOptionPane.showMessageDialog(null,"Lacking data entry.","Error",JOptionPane.ERROR_MESSAGE);	
				}
				
			}
		});
		btnSubmitButton.setBounds(15, 457, 115, 29);
		frmStudentInformation.getContentPane().add(btnSubmitButton);
		
		JButton ClearBot = new JButton("Clear All");
		ClearBot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				idTextField.setText("");
				nameTextField.setText("");
				courseTextField.setText("");
				scoreTextField.setText("");
				totalTextField.setText("");
				gradeTextField.setText("");
				 remTextField.setText("");
				 bg.clearSelection();
				 Notif.setText("");
				 chckbxClearanceCheckBox.setSelected(false);
				 
				
			}
		});
		ClearBot.setBounds(650, 155, 89, 29);
		frmStudentInformation.getContentPane().add(ClearBot);
		
		 Notif = new JTextPane();
		 Notif.setEditable(false);
		 Notif.setFont(new Font("Tahoma", Font.ITALIC, 11));
		Notif.setBackground(SystemColor.info);
		Notif.setBounds(630, 16, 132, 128);
		frmStudentInformation.getContentPane().add(Notif);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 329, 757, 117);
		frmStudentInformation.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBackground(SystemColor.info);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Course", "Year", "Gender", "Score", "Total", "Grade", "Renarks", "Clearance"
			}
		));
		
		JLabel lblNewLabel_10 = new JLabel("New Student Record:");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_10.setBounds(15, 304, 191, 14);
		frmStudentInformation.getContentPane().add(lblNewLabel_10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				StudentDatabase3 show = new StudentDatabase3();
				show.frmStudentDatabase.setVisible(true);
				 frmStudentInformation.dispose();
			}
		});
		btnSend.setBounds(174, 460, 89, 26);
		frmStudentInformation.getContentPane().add(btnSend);
		model = (DefaultTableModel) table.getModel();
	
	}
}
