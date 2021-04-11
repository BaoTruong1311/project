

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Map implements Draw {
	private int[][] map;

	public Map(int[][] map) {
		this.map = map;
	}

	@Override
	public void draw(Graphics g) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 1 || map[i][j] == 4 || map[i][j] == 6)
					g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), j * 10, i * 10, (j + 1) * 10,
							(i + 1) * 10, 912, 30, 930, 45, null);
				else if (map[i][j] == 2)
					g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), j * 10, i * 10, (j + 1) * 10,
							(i + 1) * 10, 897, 35, 910, 60, null);
				else if (map[i][j] == 7)
					g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), j * 10, i * 10, (j + 1) * 10,
							(i + 1) * 10, 900, 0, 910, 10, null);
				else if (map[i][j] == 8)
					g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), j * 10, i * 10, (j + 1) * 10,
							(i + 1) * 10, 897, 15, 910, 30, null);
			}
		}
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				result += map[i][j];
			}
			result += "\n";
		}
		return result;
	}
}
