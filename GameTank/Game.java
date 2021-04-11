

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Game extends JFrame{
	private GameBoard b;

	public Game() {
		this.init();
		this.addComponent();
		this.addEvent();
	}
	
	public void addComponent() {
		this.add(b,BorderLayout.CENTER);
	}
	
	public void addEvent() {
		this.addKeyListener(b);				
	}
	
	public void init() {
		VarPublic.MAP = new Map(txtFile_to_matrix(new File("C:\\Users\\samhangngoai\\eclipse-workspace\\MasterOOP\\resource\\stage_"+VarPublic.LEVEL+".txt")));
		b = new GameBoard();
		this.setTitle("Game tank");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(VarPublic.WIDTH+15, VarPublic.WIDTH+40);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static int[][] txtFile_to_matrix(File fileDir) {
		int n = 0;
		int m = 0;

		String str = "";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			String line = "";
			while ((line = br.readLine()) != null) {
				str += line + "\n";
			}
		} catch (FileNotFoundException e) {
			System.out.println("Err:" + e);
		} catch (IOException e) {
			System.out.println("Err:" + e);
		}

		str.trim();

		String[] arr = str.split("\n");
		
		m = arr.length;
		n = arr[0].length();

		int[][] result = new int[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				result[i][j] = Integer.parseInt(arr[i].split("")[j]);
			}
		}

		return result;
	}
}
