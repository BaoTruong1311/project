package nhandienbienso;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class LicensePlate {
	public static Size KERNEL_SIZE_3X3 = new Size(3, 3);
	public static Size KERNEL_SIZE_1x1 = new Size(1, 1);
	public static Size IMG_STANDARD_SIZE = new Size(800,600);
	public static Mat STRUCTURING_ELEMENT_RECT_3X3 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, KERNEL_SIZE_3X3);
	public static Mat STRUCTURING_ELEMENT_ELLIPSE_3x3 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, KERNEL_SIZE_3X3);
	public static Mat STRUCTURING_ELEMENT_RECT_1X1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, KERNEL_SIZE_1x1);
	public static double PERIMETER_APPROX_EPSILON = 0.1;
	public static double DIFF_ANGLE = 10;
	public static double DIFF_EDGE = 10;
	public static Size LONG_PLATE_STANDARD_SIZE = new Size(94,22);
	public static Size SHORT_PLATE_STANDARD_SIZE = new Size(56, 40);
	
	private Mat candidatePlate;
	private Point[] points;
	private Size plateSize;
	private boolean isLongPlate;
	
	public LicensePlate(Mat candidatePlate, Point[] points, Size plateSize, boolean isLongPlate) {
		this.candidatePlate = candidatePlate;
		this.points = points;
		this.plateSize = plateSize;
		this.isLongPlate = isLongPlate;
	}
	public static boolean isLongPlate(Size size) {
		return size.width/size.height < 2;
	}
	public Mat getCandidatePlate() {
		return candidatePlate;
	}
	public void setCandidatePlate(Mat candidatePlate) {
		this.candidatePlate = candidatePlate;
	}
	public Point[] getPoints() {
		return points;
	}
	public void setPoints(Point[] points) {
		this.points = points;
	}
	public Size getPlateSize() {
		return plateSize;
	}
	public void setPlateSize(Size plateSize) {
		this.plateSize = plateSize;
	}
	public boolean isLongPlate() {
		return isLongPlate;
	}
	public void setLongPlate(boolean isLongPlate) {
		this.isLongPlate = isLongPlate;
	}

}
