package doancuoiki;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

import connect.Connect;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;

public class Registerform extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField pwfPassword;
	private JPasswordField pwfRPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registerform frame = new Registerform();
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
	public Registerform() {
		setResizable(false);
		setFont(new Font("Dialog", Font.BOLD, 20));
		setTitle("KFootball Management Tool");
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\eclipse-workspace\\doancuoiki\\img\\lovepik-silhouette-particles-of-creative-football-match-background-image_400182238.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(217, 69, 141, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Tên Đăng Nhập");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(87, 69, 120, 25);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(217, 127, 141, 25);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblE = new JLabel("Email");
		lblE.setForeground(Color.ORANGE);
		lblE.setHorizontalAlignment(SwingConstants.CENTER);
		lblE.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblE.setBounds(87, 127, 120, 25);
		contentPane.add(lblE);
		
		pwfPassword = new JPasswordField();
		pwfPassword.setBounds(217, 186, 141, 25);
		contentPane.add(pwfPassword);
		
		JLabel lblMtKhu = new JLabel("Mật Khẩu");
		lblMtKhu.setForeground(Color.ORANGE);
		lblMtKhu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMtKhu.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMtKhu.setBounds(87, 186, 120, 25);
		contentPane.add(lblMtKhu);
		
		JLabel lblNhpLiMt = new JLabel("Nhập Lại Mật Khẩu");
		lblNhpLiMt.setForeground(Color.ORANGE);
		lblNhpLiMt.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhpLiMt.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNhpLiMt.setBounds(66, 244, 141, 25);
		contentPane.add(lblNhpLiMt);
		
		pwfRPassword = new JPasswordField();
		pwfRPassword.setBounds(217, 244, 141, 25);
		contentPane.add(pwfRPassword);
		
		JButton btnNewButton = new JButton("Đăng Kí");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username=textField.getText();
				String email=textField_1.getText();
				String password=pwfPassword.getText();
				String rpassword=pwfRPassword.getText();
				StringBuilder sb=new StringBuilder();
				if (username.equals(""))
				{
					sb.append("Chưa nhập tài khoản!\n");
				}
				if(password.equals(""))
				{
					sb.append("Chưa nhập mật khẩu!\n");
				}
				if(email.equals(""))
				{
					sb.append("Chưa nhập email!\n");
				}
				if(rpassword.equals(""))
				{
					sb.append("Vui lòng nhập lại mật khẩu!\n");
				}
				if(sb.length()>0)
					{
					JOptionPane.showMessageDialog(contentPane , sb.toString(),"Lỗi Đăng Ký!", JOptionPane.ERROR_MESSAGE);
					return;
					}
				
				try {
					Connection conn= Connect.openConnection();
					PreparedStatement prst = conn.prepareStatement("select name from user where name = ?");
					prst.setString(1, username);
					ResultSet rs = prst.executeQuery();
					
					
					String reg = "^\\w+\\@gmail.com$";
		                Pattern pt =Pattern.compile(reg);
		                Matcher mtc=pt.matcher(email); 
		                
		                if(!mtc.find())
		                {
		                	JOptionPane.showMessageDialog(btnNewButton, "email không hợp lệ!");
		                }
		                
		                else if (rs.next())
					{
						JOptionPane.showMessageDialog(contentPane , "Tài khoản đã tồn tại!",
								"Lỗi Đăng Kí!", JOptionPane.ERROR_MESSAGE);
					}
					else {
						if(password.equals(rpassword)) {
							
							prst = conn.prepareStatement("insert into user(name,password) values (?,?)");
							prst.setString(1,username);
							prst.setString(2,password);
							int rs1 = prst.executeUpdate();
							if(rs1>0) {
								JOptionPane.showMessageDialog(btnNewButton, "Dang ki thanh cong");
								dispose();
								giaodien gd=new giaodien();
								gd.setVisible(true);
							}
							
							
						}
						else {
							
							JOptionPane.showMessageDialog(btnNewButton, "mk khong trung");
						}
					}
					
					
					
				Connect.closeAll(conn, prst, rs);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(217, 292, 141, 39);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 403, 371);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Đăng Kí Tài Khoản");
		lblNewLabel_1.setBounds(105, 11, 288, 47);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(0, 191, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		JLabel lblNewLabel_2 = new JLabel("Đăng  Kí Tài Khoản");
		lblNewLabel_2.setBounds(0, 0, 403, 371);
		lblNewLabel_2.setIcon(new ImageIcon("D:\\eclipse-workspace\\doancuoiki\\img\\lovepik-silhouette-particles-of-creative-football-match-background-image_400182238.jpg"));
		panel.add(lblNewLabel_2);
	}
}
