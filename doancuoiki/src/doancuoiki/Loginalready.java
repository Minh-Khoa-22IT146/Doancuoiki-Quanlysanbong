package doancuoiki;

import java.awt.EventQueue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connect.Connect;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.xdevapi.PreparableStatement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class Loginalready extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtSdt;
	private JTextField txtGia;
	private JTable table;
	private JTextField Datetxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loginalready frame = new Loginalready();
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
	public Loginalready() {
		setResizable(false);
		setTitle("Kfootbal Management Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 970, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(133, 59, 105, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtSdt = new JTextField();
		txtSdt.setBounds(133, 110, 105, 28);
		contentPane.add(txtSdt);
		txtSdt.setColumns(10);
		
		
		JComboBox TimeCbb = new JComboBox();
		TimeCbb.setBounds(133, 160, 105, 28);
		contentPane.add(TimeCbb);
		
		for (int i = 6 ; i <= 21 ; i++) {
			if(i<11 || i>16)
			{
			TimeCbb.addItem(i + ":30");
			}
		}
		
		
		
		Datetxt = new JTextField();
		Datetxt.setColumns(10);
		Datetxt.setBounds(133, 215, 105, 28);
		contentPane.add(Datetxt);
		
		txtGia = new JTextField();
		txtGia.setColumns(10);
		txtGia.setBounds(133, 260, 105, 28);
		contentPane.add(txtGia);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(510, 0, 444, 378);
		contentPane.add(scrollPane);
		table = new JTable();
		table.setForeground(new Color(51, 0, 51));
		table.setBackground(new Color(255, 250, 250));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					int selected= table.getSelectedRow();
					String tg=table.getValueAt(selected, 4).toString();
					String gia=table.getValueAt(selected, 5).toString();
					String name=table.getValueAt(selected, 1).toString();
					String sdt=table.getValueAt(selected,2 ).toString();
					Object gio=table.getValueAt(selected, 3);			
					
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                java.util.Date date = sdf.parse(tg);
					java.sql.Date sqlDate =  new java.sql.Date(date.getTime());
					
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
					String strDate = dateFormat.format(sqlDate);  
					
					Datetxt.setText(strDate);
					txtGia.setText(gia);
					txtName.setText(name);
					txtSdt.setText(sdt);
					TimeCbb.setSelectedItem(gio);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
                ;
			}
		});
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"S\u00E2n", "T\u00EAn", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "Gi\u1EDD \u0111\u1EB7t", "Ng\u00E0y \u0111\u1EB7t", "Gi\u00E1"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, true, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(2).setPreferredWidth(95);
		table.getColumnModel().getColumn(5).setResizable(false);
		scrollPane.setViewportView(table);
		
		JComboBox SanCbb = new JComboBox();
		SanCbb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String Selected = SanCbb.getSelectedItem().toString();
					Connection conn = Connect.openConnection();					
					PreparedStatement prst = conn.prepareStatement("select * from sanbong where idsanbong = ?");
					prst.setString(1, Selected);
					ResultSet rs = prst.executeQuery();
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					String Ten , lichsan , giatien , tenkhach ,  sdt , thoigian;
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					while(rs.next()) {
						
						Ten = rs.getString(1);
						lichsan = rs.getString(2);
						giatien = rs.getString(3);
						tenkhach = rs.getString(4);
						sdt = rs.getString(5);
						thoigian = rs.getString(6);
						
				
						String[] row = {Ten , tenkhach , sdt , lichsan , thoigian , giatien};
						model.addRow(row);
					}
					Connect.closeAll(conn, prst, rs);;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		SanCbb.setBounds(133, 11, 105, 28);
		contentPane.add(SanCbb);
		Connection conn = Connect.openConnection();
		try {
			PreparedStatement prst = conn.prepareStatement("select tensan from loaisan");
			ResultSet rs = prst.executeQuery();
			while(rs.next()) {
				SanCbb.addItem(rs.getInt(1));
			}
			Connect.closeAll(conn, prst, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lblNewLabel = new JLabel("Sân:");
		lblNewLabel.setForeground(new Color(255, 102, 51));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(26, 11, 80, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblTn = new JLabel("Tên: ");
		lblTn.setForeground(new Color(102, 255, 255));
		lblTn.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTn.setBounds(26, 59, 80, 28);
		contentPane.add(lblTn);
		
		JLabel lblGi = new JLabel("Giờ đặt: ");
		lblGi.setForeground(new Color(102, 255, 255));
		lblGi.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGi.setBounds(26, 160, 80, 28);
		contentPane.add(lblGi);
		
		JLabel lblSTing = new JLabel("Ngày đặt: ");
		lblSTing.setForeground(new Color(255, 102, 51));
		lblSTing.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSTing.setBounds(26, 211, 80, 28);
		contentPane.add(lblSTing);
		
		JButton btnThemsan = new JButton("Thêm sân");
		btnThemsan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			Connection conn =Connect.openConnection();
				try {
					PreparedStatement prst =conn.prepareStatement("select tensan from loaisan "   );
					int last = 0;
					ResultSet rs = prst.executeQuery();
					while(rs.next()) {
						last = rs.getInt(1);
					}
				
				PreparedStatement prst1=conn.prepareStatement("insert into loaisan values (?)"   );
				prst1.setInt(1, last+1);
				prst1.execute();
				SanCbb.addItem(last+1);
					
				Connect.closeAll(conn, prst, rs);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		
		btnThemsan.setBounds(258, 14, 89, 23);
		contentPane.add(btnThemsan);
		
		JButton btnNewButton = new JButton("Đặt");
		btnNewButton.setBackground(UIManager.getColor("Button.focus"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = Connect.openConnection();
				StringBuilder sb=new StringBuilder();
				if (txtName.getText().equals(""))
				{
					sb.append("Chưa nhập tên!\n");
				}
				if(txtSdt.getText().equals(""))
				{
					sb.append("Chưa nhập số điện thoại!\n");
				}
				if(Datetxt.getText().equals(""))
				{
					sb.append("Chưa nhập ngày\n");
				}
				if(txtGia.getText().equals(""))
				{
					sb.append("Chưa Nhập Giá!\n");
				}
				if(sb.length()>0)
					{
					JOptionPane.showMessageDialog(contentPane , sb.toString(),"Lỗi Đặt Sân!", JOptionPane.ERROR_MESSAGE);
					return;
					}
				
				try {
					String strDate = Datetxt.getText();
	                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	                java.util.Date date = sdf.parse(strDate); 
	                java.sql.Date sqlDate =  new java.sql.Date(date.getTime());
	                
	                String reg = "^0\\d{9}$";
	                Pattern pt =Pattern.compile(reg);
	                Matcher mtc=pt.matcher(txtSdt.getText());
	                
	                
					PreparedStatement prst = conn.prepareStatement("select idsanbong, lichsan, Thoigian from sanbong where idsanbong = ? and lichsan = ? and Thoigian=?");
					prst.setString(1, SanCbb.getSelectedItem().toString());
					prst.setString(2, TimeCbb.getSelectedItem().toString());
					prst.setDate(3, sqlDate);
					ResultSet rss = prst.executeQuery();
					if(rss.next()) {
						JOptionPane.showMessageDialog(btnNewButton, "San da duoc dat!");
						
					}
					else if (!mtc.find())
	                {
	                	JOptionPane.showMessageDialog(btnNewButton, "So dien thoai khong hop le");
	                }
					else {	
					 prst = conn.prepareStatement("insert into sanbong values (?,?,?,?,?,?)");
					prst.setString(2, TimeCbb.getSelectedItem().toString());;
					prst.setString(3, txtGia.getText());
					prst.setString(4, txtName.getText());
					prst.setString(5, txtSdt.getText());
					prst.setDate(6, sqlDate);
					prst.setString(1, SanCbb.getSelectedItem().toString());
					Boolean rs = prst.execute();
					if(rs) {
						JOptionPane.showMessageDialog(btnNewButton, " dat san thanh cong!! ");
					}
					}
					
					Connect.closeAll(conn, prst, rss);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}

			private void While(boolean b) {
				// TODO Auto-generated method stub
				
			}
	}
			);
		btnNewButton.setBounds(10, 309, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnXa = new JButton("Xóa");
		btnXa.setBackground(UIManager.getColor("Button.focus"));
		btnXa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = Connect.openConnection();
				try {
					PreparedStatement prst = conn.prepareStatement("delete from sanbong where lichsan=? and idsanbong=?");
					
					int selected =table.getSelectedRow();
					String gioda =table.getValueAt(selected, 3).toString();
					String san =table.getValueAt(selected, 0).toString();
					
					prst.setString(1, gioda);
					prst.setString(2, san);
					int rs = prst.executeUpdate();
					while(rs != 0) {
						JOptionPane.showMessageDialog(btnXa, " xoa thanh cong ");
						rs--;
					}
					
					
					Connect.closeAll(conn, prst, null);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnXa.setBounds(136, 309, 89, 23);
		contentPane.add(btnXa);
		
		JButton btnNewButton_1_1 = new JButton("Sửa");
		btnNewButton_1_1.setBackground(UIManager.getColor("Button.focus"));
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = Connect.openConnection();
				StringBuilder sb=new StringBuilder();
				if (txtName.getText().equals(""))
				{
					sb.append("Chưa nhập tên!\n");
				}
				if(txtSdt.getText().equals(""))
				{
					sb.append("Chưa nhập số điện thoại!\n");
				}
				if(Datetxt.getText().equals(""))
				{
					sb.append("Chưa nhập ngày\n");
				}
				if(txtGia.getText().equals(""))
				{
					sb.append("Chưa Nhập Giá!\n");
				}
				if(sb.length()>0)
					{
					JOptionPane.showMessageDialog(contentPane , sb.toString(),"Lỗi cập nhật!", JOptionPane.ERROR_MESSAGE);
					return;
					}
				try {
					
				
					PreparedStatement prst = conn.prepareStatement("UPDATE sanbong SET giatien=?,tenkhachhang=?,phonenumber=?"
							+ "where idsanbong=? and Thoigian=? and lichsan=?");
			
					String strDate = Datetxt.getText();
	                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	                java.util.Date date = sdf.parse(strDate); 
	                java.sql.Date sqlDate =  new java.sql.Date(date.getTime());
	                
	                String reg = "^0\\d{9}$";
	                Pattern pt =Pattern.compile(reg);
	                Matcher mtc=pt.matcher(txtSdt.getText());
	                if(!mtc.find())
	                {
	                	JOptionPane.showMessageDialog(btnNewButton_1_1, "So dien thoai khong hop le !");
	                }
	                else {
					prst.setString(6, TimeCbb.getSelectedItem().toString());
					prst.setString(1, txtGia.getText());
					prst.setString(2, txtName.getText());
					prst.setString(3,txtSdt.getText());
					prst.setDate(5,sqlDate);
					prst.setString(4, SanCbb.getSelectedItem().toString());
					prst.executeUpdate();
					

					JOptionPane.showMessageDialog(btnNewButton_1_1," cap nhat thanh cong!");
		                }
					Connect.closeAll(conn, prst, null);
	                
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1_1.setBounds(258, 309, 89, 23);
		contentPane.add(btnNewButton_1_1);
	
		
		
		
		
		JLabel lblGi_1 = new JLabel("Giá: ");
		lblGi_1.setForeground(new Color(102, 255, 255));
		lblGi_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGi_1.setBounds(26, 260, 80, 28);
		contentPane.add(lblGi_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 832, 378);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSinThoi = new JLabel("Số điện \nthoại: ");
		lblSinThoi.setBounds(24, 111, 113, 28);
		panel.add(lblSinThoi);
		lblSinThoi.setForeground(new Color(255, 102, 51));
		lblSinThoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JButton btnNewButton_2 = new JButton("Xóa hết");
		btnNewButton_2.setBackground(UIManager.getColor("Button.focus"));
		btnNewButton_2.setBounds(10, 355, 89, 23);
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn =Connect.openConnection();
				try {
					PreparedStatement prst = conn.prepareStatement("truncate sanbong");
					prst.execute();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnNewButton_1_1_1 = new JButton("Làm mới");
		btnNewButton_1_1_1.setBackground(UIManager.getColor("Button.focus"));
		btnNewButton_1_1_1.setBounds(136, 355, 89, 23);
		panel.add(btnNewButton_1_1_1);
		
		JButton btnNewButton_1 = new JButton("Xóa sân");
		btnNewButton_1.setBackground(UIManager.getColor("Button.focus"));
		btnNewButton_1.setBounds(261, 355, 89, 23);
		panel.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("D:\\eclipse-workspace\\doancuoiki\\img\\HD-wallpaper-football-player-is-hitting-a-ball-with-leg-wearing-red-blue-dress-football.jpg"));
		lblNewLabel_1.setBounds(0, 0, 822, 378);
		panel.add(lblNewLabel_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn =Connect.openConnection();
				try {
					PreparedStatement prst =conn.prepareStatement("select tensan from loaisan "   );
					int last = 0;
					ResultSet rs = prst.executeQuery();
					while(rs.next()) {
						last = rs.getInt(1);
					}
				
				PreparedStatement prst1=conn.prepareStatement("delete from loaisan where tensan= ?"   );
				prst1.setInt(1, last);
				prst1.execute();
				
				SanCbb.removeItemAt(last-1);
		
			Connect.closeAll(conn, prst, rs);
				
				
				
					
				Connect.closeAll(conn, prst, rs);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn=Connect.openConnection();
				String Ten , lichsan , giatien , tenkhach ,  sdt , thoigian;
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				while(model.getRowCount() > 0)
				{
				    model.removeRow(0);
				}
				
				try {
					PreparedStatement prst = conn.prepareStatement("select * from sanbong");
					ResultSet rs = prst.executeQuery();
					while(rs.next()) {
						Ten = rs.getString(1);
						lichsan = rs.getString(2);
						giatien = rs.getString(3);
						tenkhach = rs.getString(4);
						sdt = rs.getString(5);
						thoigian = rs.getString(6);
						
						
						String[] row = {Ten , tenkhach , sdt , lichsan , thoigian , giatien};
						model.addRow(row);
					}
					Connect.closeAll(conn, prst, rs);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
	}
}
