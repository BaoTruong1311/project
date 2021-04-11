package tachso;

import java.util.Comparator;

import org.opencv.core.Mat;

public class LetterBlock {
	private int x;
	private int y;
	private Mat mat;

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Mat getMat() {
		return mat;
	}
	public void setMat(Mat mat) {
		this.mat = mat;
	}
	public LetterBlock(int x, int y, Mat mat) {
		super();
		this.x = x;
		this.y = y;
		this.mat = mat;
	}

	public static final Comparator<LetterBlock> X_AXIS_COMPARATOR = ((o1, o2) -> {
		// Check the differ of y coordinate between 2 point is less than 5 pixels, It
		// means that 2 point is in the same row
		if (Math.abs(o1.y - o2.y) < 7) {
			return o1.x - o2.x;
		}
		return 0;
	});
	public static final Comparator<LetterBlock> Y_AXIS_COMPARATOR = Comparator.comparingInt(o -> o.y);
}
