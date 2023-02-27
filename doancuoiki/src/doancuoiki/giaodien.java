package doancuoiki;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import java.awt.SystemColor;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import java.awt.Dialog.ModalExclusionType;
import connect.Connect;
public class giaodien extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					giaodien frame = new giaodien();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public giaodien() {
		setResizable(false);
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setBackground(Color.RED);
		setForeground(Color.WHITE);
		setFont(new Font("Dialog", Font.BOLD, 20));
		setTitle("KFOOTBALLMANAGEMENT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 380);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setForeground(Color.GRAY);
		txtUsername.setHorizontalAlignment(SwingConstants.LEFT);
		txtUsername.setBounds(407, 120, 176, 28);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setHorizontalAlignment(SwingConstants.LEFT);
		txtPassword.setToolTipText("Mật Khẩu");
		txtPassword.setEchoChar('*');
		txtPassword.setBounds(407, 173, 176, 28);
		contentPane.add(txtPassword);
		
		JLabel lblNewLabel_1 = new JLabel("Mật Khẩu");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(313, 173, 73, 28);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Tài Khoản");
		lblNewJgoodiesLabel.setForeground(Color.WHITE);
		lblNewJgoodiesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewJgoodiesLabel.setBounds(298, 118, 103, 28);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("ĐĂNG NHẬP");
		lblNewJgoodiesTitle.setBackground(new Color(0, 204, 255));
		lblNewJgoodiesTitle.setForeground(new Color(102, 255, 0));
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewJgoodiesTitle.setBounds(326, 11, 257, 70);
		contentPane.add(lblNewJgoodiesTitle);
		
		JButton Login = new JButton("Đăng Nhập");
		Login.setIcon(null);
		Login.setForeground(Color.BLACK);
		Login.setBackground(UIManager.getColor("Button.background"));
		Login.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) {
				String username= txtUsername.getText();
				String password= new String(txtPassword.getPassword());
				
				StringBuilder sb=new StringBuilder();
				
				
				if (username.equals(""))
				{
					sb.append("Chưa nhập tài khoản!\n");
				}
				if(password.equals(""))
				{
					sb.append("Chưa nhập mật khẩu!\n");
				}
				
				if(sb.length()>0)
					{
					JOptionPane.showMessageDialog(contentPane , sb.toString(),"Lỗi Đăng Nhập!", JOptionPane.ERROR_MESSAGE);
					return;
					}
				
				try {
					Connection conn = Connect.openConnection();
					PreparedStatement prst = conn.prepareStatement("select * from user where name = ? and password =?");
					prst.setString(1, username);
					prst.setString(2, password);
					ResultSet rs = prst.executeQuery();
					if (rs.next())
					{
						dispose();
						Loginalready lga=new Loginalready();
						lga.setVisible(true);
					}
					else
					{
						JOptionPane.showMessageDialog(contentPane , "sai tai khoan hoac mat khau",
								"Lỗi Đăng Nhập!", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		);
	
		
		Login.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Login.setBounds(407, 247, 176, 43);
		contentPane.add(Login);
		
		JLayeredPane JPanel = new JLayeredPane();
		JPanel.setForeground(new Color(255, 255, 255));
		JPanel.setBackground(new Color(255, 255, 255));
		JPanel.setBounds(30, 90, 200, 200);
		contentPane.add(JPanel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("D:\\eclipse-workspace\\doancuoiki\\img\\images (2).jpg"));
		lblNewLabel.setBounds(0, 0, 200, 200);
		JPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBackground(UIManager.getColor("MenuBar.shadow"));
		lblNewLabel_2.setForeground(new Color(102, 255, 0));
		lblNewLabel_2.setIcon(new ImageIcon("D:\\eclipse-workspace\\doancuoiki\\img\\800px_COLOURBOX4666665.jpg"));
		lblNewLabel_2.setBounds(0, 0, 616, 358);
		contentPane.add(lblNewLabel_2);
	}
}
