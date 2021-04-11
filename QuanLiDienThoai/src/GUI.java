
import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;



import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.awt.GridLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.ImageIcon;

public class GUI extends JFrame {
	private JTextField txtGia;
	private JTextField txtRom;
	private JTextField txtRam;
	private JTextField txtHang;
	private JTextField txtTen;
	private JTextField txtMa;
	private JTable table;
	String result;
	private String header[] = { "STT","Mã sản phẩm", "Tên sản phẩm", "Hãng", "Ram",
			"Rom", "Giá" };
	DefaultTableModel dtm;
	static MyArrayList<SmartPhone> list;
	String row[] = new String[7];
	private JMenuItem itemRemoveName;

	public GUI() {

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		JPanel pnTo = new JPanel();
		pnTo.setBackground(new Color(224, 255, 255));
		pnTo.setBorder(new MatteBorder(10, 10, 10, 10, (Color) new Color(0,
				250, 154)));
		getContentPane().add(pnTo);
		pnTo.setLayout(new BorderLayout());

		JPanel pnTitle = new JPanel();
		pnTitle.setBorder(new MatteBorder(0, 0, 10, 0, (Color) new Color(0,
				250, 154)));
		pnTo.add(pnTitle, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("PHẦN MỀM QUẢN LÍ ĐIỆN THOẠI");
		lblTitle.setForeground(new Color(0, 128, 0));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		pnTitle.add(lblTitle, BorderLayout.SOUTH);

		JPanel pn1 = new JPanel();
		pnTo.add(pn1, BorderLayout.CENTER);
		pn1.setLayout(new BorderLayout(0, 0));

		JPanel pnTimKiem = new JPanel();
		pn1.add(pnTimKiem, BorderLayout.NORTH);

		JButton btnNewButton_2 = new JButton("New button");
		pnTimKiem.add(btnNewButton_2);

		JButton btnNewButton_1 = new JButton("New button");
		pnTimKiem.add(btnNewButton_1);

		JButton btnNewButton = new JButton("New button");
		pnTimKiem.add(btnNewButton);

		JPanel pnEdit = new JPanel();
		pn1.add(pnEdit, BorderLayout.SOUTH);
		pnEdit.setLayout(new GridLayout(0, 6, 0, 0));

		txtMa = new JTextField();
		txtMa.setColumns(10);
		pnEdit.add(txtMa);

		txtTen = new JTextField();
		pnEdit.add(txtTen);
		txtTen.setColumns(10);

		txtHang = new JTextField();
		pnEdit.add(txtHang);
		txtHang.setColumns(10);

		txtRam = new JTextField();
		pnEdit.add(txtRam);
		txtRam.setColumns(10);

		txtRom = new JTextField();
		pnEdit.add(txtRom);
		txtRom.setColumns(10);

		txtGia = new JTextField();
		pnEdit.add(txtGia);
		txtGia.setColumns(10);

		JPanel pnTable = new JPanel();
		pn1.add(pnTable, BorderLayout.CENTER);
		pnTable.setLayout(new BorderLayout(0, 0));

		dtm = new DefaultTableModel();
		for (int i = 0; i < header.length; i++) {
			dtm.addColumn(header[i]);
		}

		table = new JTable(dtm);
		table.setForeground(Color.BLUE);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setRowHeight(25);
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(600);
		table.getColumnModel().getColumn(3).setPreferredWidth(260);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(200);
		table.getColumnModel().getColumn(6).setPreferredWidth(400);
		
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);
		SmartPhone sm1 = new SmartPhone("SMGA1", "Samsung Galaxy a50",
				"Samsung", 64, 4, 4990000);
		SmartPhone sm2 = new SmartPhone("SMGA2", "Samsung Galaxy a30s",
				"Samsung", 64, 4, 6290000);
		SmartPhone sm3 = new SmartPhone("SMGA3", "Samsung Galaxy a50",
				"Samsung", 128, 8, 6490000);
		SmartPhone sm4 = new SmartPhone("SMGA4", "Samsung Galaxy a9",
				"Samsung", 128, 6, 6490000);
		SmartPhone sm5 = new SmartPhone("SMGJ1", "Samsung Galaxy j7",
				"Samsung", 32, 4, 5990000);
		SmartPhone sm6 = new SmartPhone("SMOA1", "Oppo A5 2020", "Oppo", 128,
				4, 5290000);
		SmartPhone sm7 = new SmartPhone("SMOR1", "Oppo Reno2", "Oppo", 256, 8,
				14990000);
		SmartPhone sm8 = new SmartPhone("SMOR2", "Oppo Reno", "Oppo", 256, 6,
				9990000);
		SmartPhone sm9 = new SmartPhone("SMOFP1", "Oppo F11 Pro", "Oppo", 128,
				6, 6690000);
		SmartPhone sm10 = new SmartPhone("SMOA2", "Oppo A3s", "Oppo", 16, 2,
				2990000);
		SmartPhone sm11 = new SmartPhone("SMHPP1", "Huawei P30 Pro", "Huawei",
				256, 8, 20690000);
		SmartPhone sm12 = new SmartPhone("SMHPL1", "Huawei P30 Lite", "Huawei",
				128, 6, 6020000);
		SmartPhone sm13 = new SmartPhone("SMHYP1", "Huawei Y9 Prime", "Huawei",
				128, 4, 5490000);
		SmartPhone sm14 = new SmartPhone("SMHN1", "Huawei Nova 3i", "Huawei",
				128, 4, 3900000);
		SmartPhone sm15 = new SmartPhone("SMHYP2", "Huawei Y7 Pro", "Huawei",
				32, 3, 3290000);
		SmartPhone sm16 = new SmartPhone("SMVVP1", "Vivo V17 Pro", "Vivo", 128,
				8, 4990000);
		SmartPhone sm17 = new SmartPhone("SMVV2", "Vivo V7+", "Vivo", 64, 4,
				6990000);
		SmartPhone sm18 = new SmartPhone("SMVS1", "Vivo S1", "Vivo", 128, 6,
				5990000);
		SmartPhone sm19 = new SmartPhone("SMVV3", "Vivo V15", "Vivo", 128, 6,
				5490000);
		SmartPhone sm20 = new SmartPhone("SMVY1", "Vivo Y19", "Vivo", 128, 6,
				4990000);
		SmartPhone sm21 = new SmartPhone("SMRX1", "Realme XT", "Realme", 128,
				8, 7990000);
		SmartPhone sm22 = new SmartPhone("SMRP1", "Realme 5 Pro", "Realme",
				128, 8, 6990000);
		SmartPhone sm23 = new SmartPhone("SMRP2", "Realme 5 Pro", "Realme",
				128, 4, 5990000);
		SmartPhone sm24 = new SmartPhone("SMR1", "Realme 5", "Realme", 64, 3,
				3990000);
		SmartPhone sm25 = new SmartPhone("SMR2", "Realme 3", "Realme", 32, 3,
				3290000);
		SmartPhone sm26 = new SmartPhone("SMXM1", "Xiaomi Mi 9T", "Xiaomi",
				128, 6, 8490000);
		SmartPhone sm27 = new SmartPhone("SMXM2", "Xiaomi Mi 9T", "Xiaomi", 64,
				4, 6990000);
		SmartPhone sm28 = new SmartPhone("SMXRN8P1", "Xiaomi Redmi Note 8 Pro",
				"Xiaomi", 64, 6, 5990000);
		SmartPhone sm29 = new SmartPhone("SMXRN81", "Xiaomi Redmi Note 8",
				"Xiaomi", 128, 4, 5490000);
		SmartPhone sm30 = new SmartPhone("SMXRN82", "Xiaomi Redmi Note 8",
				"Xiaomi", 64, 4, 4990000);
		SmartPhone sm31 = new SmartPhone("SMN1", "Nokia 8.1", "Nokia", 64, 4,
				5930000);
		SmartPhone sm32 = new SmartPhone("SMN2", "Nokia 7.2", "Nokia", 64, 4,
				5900000);
		SmartPhone sm33 = new SmartPhone("SMN3", "Nokia 6.1 Plus", "Nokia", 64,
				4, 3390000);
		SmartPhone sm34 = new SmartPhone("SMN4", "Nokia 3.2", "Nokia", 32, 3,
				3390000);
		SmartPhone sm35 = new SmartPhone("SMN5", "Nokia 5.1 Plus", "Nokia", 32,
				3, 2960000);
		SmartPhone sm36 = new SmartPhone("SMIP11PM", "Iphone 11 Pro Max",
				"Apple", 512, 4, 43990000);
		SmartPhone sm37 = new SmartPhone("SMIP11P1", "Iphone 11 Pro", "Apple",
				512, 4, 40990000);
		SmartPhone sm38 = new SmartPhone("SMIP11P2", "Iphone 11 Pro", "Apple",
				256, 4, 26990000);
		SmartPhone sm39 = new SmartPhone("SMIPXM", "Iphone Xs Max", "Apple",
				256, 4, 33990000);
		SmartPhone sm40 = new SmartPhone("SMIPXS", "Iphone Xs", "Apple", 64, 4,
				24990000);
		SmartPhone sm41 = new SmartPhone("SMSGA5", "Samsung Galaxy A10",
				"Samsung", 32, 2, 1820000);
		SmartPhone sm42 = new SmartPhone("SMSGM1", "Samsung Galaxy M20",
				"Samsung", 32, 2, 1920000);
		SmartPhone sm43 = new SmartPhone("SMSGA6", "Samsung Galaxy A20",
				"Samsung", 32, 3, 1850000);
		SmartPhone sm44 = new SmartPhone("SMOPR", "Oppo Joy Plus R1011",
				"Oppo", 32, 1, 1490000);
		SmartPhone sm45 = new SmartPhone("SMOA2", "Oppo A71k", "Oppo", 16, 2,
				2490000);
		SmartPhone sm46 = new SmartPhone("SMHY1", "Huawei Y3II", "Huawei", 8,
				1, 1190000);
		SmartPhone sm47 = new SmartPhone("SMHY2", "Huawei Y3 2017", "Huawei",
				8, 1, 1790000);
		SmartPhone sm48 = new SmartPhone("SMHMT", "Huawei MediaPad T3 7.0",
				"Huewei", 8, 1, 2090000);
		SmartPhone sm49 = new SmartPhone("SMVY2", "Vivo Y91C", "Vivo", 32, 2,
				2590000);
		SmartPhone sm50 = new SmartPhone("SMVY3", "Vivo Y71", "Vivo", 16, 2,
				2590000);
		SmartPhone sm51 = new SmartPhone("SMRC1", "Realme C2", "Realme", 16, 2,
				2290000);
		SmartPhone sm52 = new SmartPhone("SMRC2", "Realme C1", "Realme", 16, 1,
				1590000);
		SmartPhone sm53 = new SmartPhone("SMXRG", "Xiaomi Redmi Go", "Xiaomi",
				8, 1, 1490000);
		SmartPhone sm54 = new SmartPhone("iSMXR1", "Xiaomi Redmi 7A", "Xiaomi",
				16, 2, 1990000);
		SmartPhone sm55 = new SmartPhone("SMXR2", "Xiaomi Redmi 6A", "Xiaomi",
				16, 2, 1990000);
		SmartPhone sm56 = new SmartPhone("SMN6", "Nokia 2.1", "Nokia", 8, 1,
				1790000);
		SmartPhone sm57 = new SmartPhone("SMN7", "Nokia 2", "Nokia", 8, 1,
				1890000);
		SmartPhone sm58 = new SmartPhone("SMN8", "Nokia 3.1 Plus", "Nokia", 16,
				2, 2290000);
		SmartPhone sm59 = new SmartPhone("SMIP1", "Iphone 6", "Apple", 32, 1,
				4890000);
		SmartPhone sm60 = new SmartPhone("SMIP2", "Iphone 5S", "Apple", 16, 2,
				5990000);

		list = new MyArrayList<SmartPhone>();
		list.add(sm1);
		list.add(sm2);
		list.add(sm3);
		list.add(sm4);
		list.add(sm5);
		list.add(sm6);
		list.add(sm7);
		list.add(sm8);
		list.add(sm9);
		list.add(sm10);
		list.add(sm11);
		list.add(sm12);
		list.add(sm13);
		list.add(sm14);
		list.add(sm15);
		list.add(sm16);
		list.add(sm17);
		list.add(sm18);
		list.add(sm19);
		list.add(sm20);
		list.add(sm21);
		list.add(sm22);
		list.add(sm23);
		list.add(sm24);
		list.add(sm25);
		list.add(sm26);
		list.add(sm27);
		list.add(sm28);
		list.add(sm29);
		list.add(sm30);
		list.add(sm31);
		list.add(sm32);
		list.add(sm33);
		list.add(sm34);
		list.add(sm35);
		list.add(sm36);
		list.add(sm37);
		list.add(sm38);
		list.add(sm39);
		list.add(sm40);
		list.add(sm41);
		list.add(sm42);
		list.add(sm43);
		list.add(sm44);
		list.add(sm45);
		list.add(sm46);
		list.add(sm47);
		list.add(sm48);
		list.add(sm49);
		list.add(sm50);
		list.add(sm51);
		list.add(sm52);
		list.add(sm53);
		list.add(sm54);
		list.add(sm55);
		list.add(sm56);
		list.add(sm57);
		list.add(sm58);
		list.add(sm59);
		list.add(sm60);

		pnTable.add(scrollPane, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		pnTo.add(menuBar, BorderLayout.SOUTH);

		JMenu jmnAdd = new JMenu("Thêm");
		jmnAdd.setIcon(new ImageIcon(GUI.class
				.getResource("/iconAdd.png")));
		jmnAdd.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		menuBar.add(jmnAdd);

		JMenuItem itemAdd = new JMenuItem("Thêm");
		jmnAdd.add(itemAdd);

		JMenuItem itemAddAt = new JMenuItem("Thêm tại vị trí");
		itemAddAt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				result = JOptionPane.showInputDialog(null,
						"Mời bạn nhập vị trí:");
				try {
					if(txtMa.getText()=="d"||txtTen.getText()==""||txtHang.getText()==""||txtRom.getText()==""||txtRam.getText()==""||txtGia.getText()==""){
						
						addIndex();
					}else{
						JOptionPane.showMessageDialog(null, "Vui lòng nhập sản phẩm");
						
					}
				} catch (Exception e1) {
					
					JOptionPane.showMessageDialog(null, "Vui lòng nhập số!");
				}

			}
		});
		jmnAdd.add(itemAddAt);
		JMenu jmnRemove = new JMenu("Xóa");
		jmnRemove.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		jmnRemove.setIcon(new ImageIcon(GUI.class
				.getResource("/iconRemove.png")));
		menuBar.add(jmnRemove);

		itemRemoveName = new JMenuItem("Xóa theo tên");
		jmnRemove.add(itemRemoveName);

		JMenuItem itemRemoveID = new JMenuItem("Xóa theo mã");
		jmnRemove.add(itemRemoveID);
		
		JMenu jmnSearch = new JMenu("Tìm kiếm");
		jmnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		jmnSearch.setIcon(new ImageIcon(GUI.class.getResource("/iconSearch.png")));
		menuBar.add(jmnSearch);
		
		JMenuItem itemSearchName = new JMenuItem("Tìm kiếm theo tên");
		jmnSearch.add(itemSearchName);
		//Action tìm kiếm theo tên
		itemSearchName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				result = JOptionPane.showInputDialog(null,
						"Nhập tên cần tìm:");
				try {
					searchName(result);
					
				} catch (Exception e2) {
					
				}
			}
		});
		itemRemoveID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				result = JOptionPane.showInputDialog(null,
						"Mời bạn nhập mã cần xóa:");
				try {
					removeID(result);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập mã!");
				}
			}
		});
		// action xóa theo tên
		itemRemoveName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				result = JOptionPane.showInputDialog(null,
						"Mời bạn nhập tên cần xóa:");
				try {

					removeName(result);

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập tên!");
				}
			}
		});
		// action thêm
		itemAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addRow();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		setTitle("Quản lí nhân viên");
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1080,720);
		
		showAllItem();
	}

	// phương thức thêm 1 sản phẩm tại vị trí bất kì
	public void addRow() {
		SmartPhone a = new SmartPhone(txtMa.getText(), txtTen.getText(),
				txtHang.getText(), Double.parseDouble(txtRom.getText()
						.toString()), Double.parseDouble(txtRam.getText()
						.toString()), Double.parseDouble(txtGia.getText()
						.toString()));
		list.add(a);
		showAllItem();
	}

	// phươn thức thêm một sản phẩm tại vị trí cho trước
	public void addIndex() {
		SmartPhone a = new SmartPhone(txtMa.getText(), txtTen.getText(),
				txtHang.getText(), Double.parseDouble(txtRom.getText()
						.toString()), Double.parseDouble(txtRam.getText()
						.toString()), Double.parseDouble(txtGia.getText()
						.toString()));

		list.add(Integer.parseInt(result), a);
		showAllItem();
	}

	// hiển thị các sản phẩm
	public void showAllItem() {
		dtm.setRowCount(0);
		for (int i = 0; i < list.size(); i++) {
			row[0] = i+1+"";
			row[1] = list.get(i).getMaSp();
			row[2] = list.get(i).getTenSP();
			row[3] = list.get(i).getHangSP();
			row[4] = list.get(i).getVram() + "";
			row[5] = list.get(i).getVrom() + "";
			DecimalFormat df = new DecimalFormat("");
			row[6] = df.format(list.get(i).getGia());
			dtm.addRow(row);
		}
	}

	// phương thức xóa một sản phẩm theo tên
	public void removeName(String name) {
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTenSP().equals(name)) {
				list.remove(i);
				
			}

		}
		showAllItem();
	}

	public void removeID(String id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMaSp().equals(id)) {
				list.remove(i);
			}
		}
		showAllItem();
	}
public void searchName(String name){
	for (int i = 0; i < list.size(); i++) {
		if (list.get(i).getTenSP().equals(name)) {
			dtm.setRowCount(0);
			row[1] = list.get(i).getMaSp();
			row[2] = list.get(i).getTenSP();
			row[3] = list.get(i).getHangSP();
			row[4] = list.get(i).getVram() + "";
			row[5] = list.get(i).getVrom() + "";
			DecimalFormat df = new DecimalFormat("");
			row[6] = df.format(list.get(i).getGia());
			dtm.addRow(row);
		}
	}
	
}
	public static void main(String[] args) {
		new GUI();
	}
}
