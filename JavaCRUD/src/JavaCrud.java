import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frmNadetdevJavaCrud;
	private JTextField txtBookName;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTextField txtSearchBook;
	private JTable Table_Book_List;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frmNadetdevJavaCrud.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		DbConnect();
		Book_List_load();
	}

	/**
	 * Db connection
	 */
	Connection con;
	PreparedStatement pStatement;
	ResultSet rs;

	public void DbConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_book_crud", "root", "P@ssword01");

		} catch (ClassNotFoundException ec) {
			ec.printStackTrace();
		} catch (SQLException es) {
			es.printStackTrace();
		}
	}

	/**
	 * Reload table list
	 */
	public void Book_List_load() {
		try {
			pStatement = con.prepareStatement("select * from books");
			rs = pStatement.executeQuery();
			Table_Book_List.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNadetdevJavaCrud = new JFrame();
		frmNadetdevJavaCrud.setTitle("NadetDev Java CRUD");
		frmNadetdevJavaCrud.setBounds(100, 100, 972, 533);
		frmNadetdevJavaCrud.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNadetdevJavaCrud.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("BOOK SHOP");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(22, 0, 178, 49);
		frmNadetdevJavaCrud.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(22, 69, 439, 183);
		frmNadetdevJavaCrud.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblBookName = new JLabel("Book name");
		lblBookName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBookName.setBounds(10, 32, 105, 19);
		panel.add(lblBookName);

		JLabel lblEdition = new JLabel("Edition");
		lblEdition.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEdition.setBounds(10, 71, 105, 19);
		panel.add(lblEdition);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPrice.setBounds(10, 114, 105, 19);
		panel.add(lblPrice);

		txtBookName = new JTextField();
		txtBookName.setBounds(118, 31, 277, 25);
		panel.add(txtBookName);
		txtBookName.setColumns(10);

		txtEdition = new JTextField();
		txtEdition.setColumns(10);
		txtEdition.setBounds(118, 73, 277, 25);
		panel.add(txtEdition);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(118, 116, 277, 25);
		panel.add(txtPrice);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bName, bEdition, bPrice;

				// Get widget text value
				bName = txtBookName.getText();
				bEdition = txtEdition.getText();
				bPrice = txtPrice.getText();

				try {
					// Insert statement
					pStatement = con.prepareStatement("insert into books (title, edition, price) values (?, ?, ?)");
					pStatement.setString(1, bName);
					pStatement.setString(2, bEdition);
					pStatement.setString(3, bPrice);
					pStatement.executeUpdate();

					// Show successfull message
					JOptionPane.showMessageDialog(null, "Record added !");

					// Reload book list
					Book_List_load();

					// Clear widgets
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSave.setBounds(22, 289, 133, 40);
		frmNadetdevJavaCrud.getContentPane().add(btnSave);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//Exit from program
				System.exit(0);
				
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnExit.setBounds(175, 289, 133, 40);
		frmNadetdevJavaCrud.getContentPane().add(btnExit);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				txtBookName.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
				txtSearchBook.setText("");
				txtBookName.requestFocus();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnClear.setBounds(328, 289, 133, 40);
		frmNadetdevJavaCrud.getContentPane().add(btnClear);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Search and Edition", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(22, 381, 926, 82);
		frmNadetdevJavaCrud.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblBookId = new JLabel("Book Id");
		lblBookId.setBounds(10, 34, 88, 20);
		lblBookId.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(lblBookId);

		txtSearchBook = new JTextField();
		txtSearchBook.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {
					String id = txtSearchBook.getText();

					pStatement = con.prepareStatement("select title, edition, price from books where id = ?");
					pStatement.setString(1, id);
					ResultSet rSet = pStatement.executeQuery();

					if (rSet.next() == true) {
						String bName = rSet.getString(1);
						String bEdition = rSet.getString(2);
						String bPrice = rSet.getString(3);

						txtBookName.setText(bName);
						txtEdition.setText(bEdition);
						txtPrice.setText(bPrice);
					} else {
						txtBookName.setText("");
						txtEdition.setText("");
						txtPrice.setText("");
					}

				} catch (SQLException exs) {
					exs.printStackTrace();
				}
			}
		});
		txtSearchBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearchBook.setColumns(10);
		txtSearchBook.setBounds(93, 34, 296, 25);
		panel_1.add(txtSearchBook);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bName, bEdition, bPrice, bId;

				// Get widget text value
				bName = txtBookName.getText();
				bEdition = txtEdition.getText();
				bPrice = txtPrice.getText();
				bId = txtSearchBook.getText();

				try {
					// Insert statement
					pStatement = con.prepareStatement("update books set title=?, edition=?, price=? where id=?");
					pStatement.setString(1, bName);
					pStatement.setString(2, bEdition);
					pStatement.setString(3, bPrice);
					pStatement.setString(4, bId);
					pStatement.executeUpdate();

					// Show successfull message
					JOptionPane.showMessageDialog(null, "Record updated !");

					// Reload book list
					Book_List_load();

					// Clear widgets
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtSearchBook.setText("");
					txtBookName.requestFocus();

				} catch (Exception e3) {
					e3.printStackTrace();
				}

			}
		});
		btnUpdate.setBounds(540, 19, 133, 40);
		panel_1.add(btnUpdate);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String bId;

				// Get widget text value
				
				bId = txtSearchBook.getText();

				try {
					// Insert statement
					pStatement = con.prepareStatement("delete from books where id=?");
					pStatement.setString(1, bId);
					pStatement.executeUpdate();

					// Show successfull message
					JOptionPane.showMessageDialog(null, "Record deleted !");

					// Reload book list
					Book_List_load();

					// Clear widgets
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtSearchBook.setText("");
					txtBookName.requestFocus();
					

				} catch (Exception e3) {
					e3.printStackTrace();
				}
				
				
			}
		});
		btnDelete.setBounds(683, 19, 133, 40);
		panel_1.add(btnDelete);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Book List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(471, 10, 477, 364);
		frmNadetdevJavaCrud.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JScrollPane TableScrollPane = new JScrollPane();
		TableScrollPane.setBounds(10, 21, 457, 333);
		panel_2.add(TableScrollPane);

		Table_Book_List = new JTable();
		TableScrollPane.setViewportView(Table_Book_List);
	}
}
