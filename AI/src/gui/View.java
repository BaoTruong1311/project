package gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import nhandangkytu.LetterRecognizer;
import nhandienbienso.LicensePlate;
import nhandienbienso.LicensePlateRecognizer;
import tachso.LetterBlock;
import tachso.NumberRecognizer;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class View extends JFrame {
//	static JLabel l;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
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
	public View() {
		setTitle("Nh\u1EADn Di\u1EC7n Bi\u1EC3n S\u1ED1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 551);
		getContentPane().setLayout(new CardLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		getContentPane().add(panel, "name_94188194707200");

		JButton btnNewButton_1 = new JButton("Add Picture");
		
		
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1.setForeground(Color.ORANGE);
		btnNewButton_1.setBackground(new Color(0, 0, 0));
		
	
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("Add Picture");
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(26)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 749, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
								.addGap(61)
								.addGap(18).addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 150,
										GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(72, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(26)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 51,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup().addGap(18).addComponent(panel_2,
								GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)))
				.addGap(23)));
		
		JLabel lblHinhAnh = new JLabel("");
		panel_1.add(lblHinhAnh);
		panel.setLayout(gl_panel);

		JLabel lblResult = new JLabel();
		panel_2.add(lblResult);
		panel.setLayout(gl_panel);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("C:\\Users\\samhangngoai\\eclipse-workspace\\LicensePlate"));
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("hinh anh", "jpg","png");
				fileChooser.setFileFilter(imageFilter);
				fileChooser.setMultiSelectionEnabled(false);
				int x = fileChooser.showDialog(lblHinhAnh, "chon file");
				if (x== fileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					lblHinhAnh.setIcon(new ImageIcon(f.getAbsolutePath()));
					lblResult.setText("");
					
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					Mat image = Imgcodecs.imread(f.getAbsolutePath());

					List<LicensePlate> plates = new LicensePlateRecognizer().detectCandidatePlate(image);
					if(plates.size()>0) {
						List<LetterBlock> letters = new NumberRecognizer().splitLetterImg(plates.get(0));						
						for(LetterBlock l: letters) {
							LetterRecognizer re = new LetterRecognizer("svm_chars.txt");
							lblResult.setText(lblResult.getText()+"\t"+re.recognizeLetter(l.getMat()));
						}											
					}
				}
			}
		});
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Brower...");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}
}
