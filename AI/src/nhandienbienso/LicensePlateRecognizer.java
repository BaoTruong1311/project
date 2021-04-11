package nhandienbienso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class LicensePlateRecognizer {
	
	//chuẩn hóa kích thước ảnh
	//chuyển ảnh xám xóa nhiễu bằng gaussian
	//dò biên canny với ngưỡng dưới 50 trên 150 để lấy đg biên của vật thể trong ảnh
	public static Mat preProcessSrcImg(Mat src) {
		Imgproc.resize(src, src, LicensePlate.IMG_STANDARD_SIZE);
		Imgproc.getStructuringElement(Imgproc.MORPH_RECT, LicensePlate.KERNEL_SIZE_3X3);
		Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, LicensePlate.KERNEL_SIZE_3X3);
		Mat processingImg = new Mat();
		Point anchor = new Point(-1, -1);
		Imgproc.cvtColor(src, processingImg, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(processingImg, processingImg, LicensePlate.KERNEL_SIZE_3X3, 1);
		
		Imgproc.Sobel(processingImg, processingImg, -1, 1, 0);
		Imgproc.threshold(processingImg, processingImg, 0, 255, Imgproc.THRESH_OTSU);
		
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3, anchor, 2);
		Imgproc.morphologyEx(processingImg ,processingImg, Imgproc.MORPH_OPEN, 
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3, anchor, 2);
		
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3, anchor, 2);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE, 
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3, anchor, 2);
		
		Imgproc.Canny(src, processingImg, 150, 200, 3, true);

		HighGui.imshow("Pre", processingImg);
		HighGui.waitKey();
		HighGui.destroyAllWindows();
		
		return processingImg;
	}

	// Tìm vị trí vùng tứ giác

	private static Point[] getInRangeQuadrilateral(MatOfPoint contour, MatOfPoint2f contour2f) {
		// If input contour is convex, it can be a contour of a quadrilateral
		if (Imgproc.isContourConvex(contour)) {
			return null;
		}
		
		double contourPerimeter = Imgproc.arcLength(contour2f, true);

		return getQuadrilateral(contour2f, contourPerimeter, LicensePlate.PERIMETER_APPROX_EPSILON);
	}

	// Trả về tọa độ của 4 đỉnh tứ giác

	private static Point[] getQuadrilateral(MatOfPoint2f contour2f, double contourPerimeter, double perimeterEpsilon) {
		double approxPerimeter = perimeterEpsilon * contourPerimeter;
		MatOfPoint2f approxOfPoint2f = new MatOfPoint2f();
		Imgproc.approxPolyDP(contour2f, approxOfPoint2f, approxPerimeter, true);
		Point[] res = approxOfPoint2f.toArray();
		// return the point array if the number of vertices is equal 4, null otherwise
		return res.length == 4 ? res : null;
	}

	// tính góc của một tam giác khi đã biết chiều dài 3 cạnh sử dụng định lí cosin

	private static double calculateTriangleAngle(double aa, double bb, double cc, double a, double b) {
		return Math.toDegrees(Math.acos((aa + bb - cc) / (2 * a * b)));
	}

	// kiểm tra xem một góc có phải là góc vuông hay gần vuông hay không

	private static boolean isNearlyPerpendicular(double angle) {
		return Math.abs(90 - angle) <= LicensePlate.DIFF_ANGLE;
	}

	private static boolean isNearlyPerpendicular(double aa, double bb, double cc, double a, double b) {
		return isNearlyPerpendicular(calculateTriangleAngle(aa, bb, cc, a, b));
	}

	// Tính độ dài bình phương 1 đoạn thẳng

	private static double computeSideSquare(Point p1, Point p2) {
		double deltaX = p1.x - p2.x;
		double deltaY = p1.y - p2.y;
		return deltaX * deltaX + deltaY * deltaY;
	}

	// Kiểm tra 2 đoạn thẳng có bằng nhau không

	private static boolean isNearlyEqualLength(double l1, double l2) {
		return Math.abs(l1 - l2) <= LicensePlate.DIFF_EDGE;
	}

	// Kiểm tra xem tứ giác có phải là biển số hay không

	private static boolean isRectangle(Point[] quadrilateralVertices) {
		if(quadrilateralVertices == null) return false;
		// Calculate square of four side's length
		double square01 = computeSideSquare(quadrilateralVertices[0], quadrilateralVertices[1]);
		double square12 = computeSideSquare(quadrilateralVertices[1], quadrilateralVertices[2]);
		double square23 = computeSideSquare(quadrilateralVertices[2], quadrilateralVertices[3]);
		double square30 = computeSideSquare(quadrilateralVertices[3], quadrilateralVertices[0]);
		// Calculate square of two diagonal side's length
		double square02 = computeSideSquare(quadrilateralVertices[0], quadrilateralVertices[2]);
		double square13 = computeSideSquare(quadrilateralVertices[1], quadrilateralVertices[3]);
		// Length of 4 sides
		double length01 = Math.sqrt(square01);
		double length12 = Math.sqrt(square12);
		double length23 = Math.sqrt(square23);
		double length30 = Math.sqrt(square30);
		// Check for two opposite side's length is nearly equal
		boolean isRect = isNearlyEqualLength(length01, length23) && isNearlyEqualLength(length12, length30);
		// Check for 4 angle is nearly perpendicular
		isRect = isRect && isNearlyPerpendicular(square01, square30, square13, length01, length30);
		isRect = isRect && isNearlyPerpendicular(square01, square12, square02, length01, length12);
		isRect = isRect && isNearlyPerpendicular(square12, square23, square13, length12, length23);
		isRect = isRect && isNearlyPerpendicular(square23, square30, square02, length23, length30);
		return isRect;
	}

	// Tách vật thể ra ngoài
	private static Mat doPerspectiveTransformation(Mat src, RotatedRect rotatedRect, Size dSize) {
		Point[] points = new Point[4];
		rotatedRect.points(points);
		MatOfPoint2f srcPerspectivePoints = new MatOfPoint2f(points);
		MatOfPoint2f dstPerspectivePoints;
		Point p0 = new Point(0, dSize.height);
		Point p1 = new Point(0, 0);
		Point p2 = new Point(dSize.width, 0);
		Point p3 = new Point(dSize.width, dSize.height);
		// Build a dst map
		if (rotatedRect.size.width > rotatedRect.size.height) {
			dstPerspectivePoints = new MatOfPoint2f(p0, p1, p2, p3);
		} else {
			dstPerspectivePoints = new MatOfPoint2f(p3, p0, p1, p2);
		}
		Mat dst = new Mat();
		Mat M = Imgproc.getPerspectiveTransform(srcPerspectivePoints, dstPerspectivePoints);
		Imgproc.warpPerspective(src, dst, M, dSize);
		return dst;
	}
	//tìm hình bao có trong ảnh sau khi đã xử lý, sau đó ss về dk kích thước xem có phải biển số ko
	public static List<LicensePlate> detectCandidatePlate(Mat src) {
		List<LicensePlate> candidatePlates = new LinkedList<>();
		
		Mat preprocessedImg = preProcessSrcImg(src);

		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(preprocessedImg, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		HashSet<Integer> ignoredContoursIdx = new HashSet<>();
		Point[] points = new Point[4];
		int contoursSize = contours.size();
		for (int contourIdx = 0; contourIdx < contoursSize; contourIdx++) {
			// If a contour is child of another contour which was recognized as a plate, We
			// will ignore it. Do it also for all its child by adding its contourIdx into
			// the ignoredlist
			if (ignoredContoursIdx.contains((int) hierarchy.get(0, contourIdx)[3])) {
				ignoredContoursIdx.add(contourIdx);
				continue;
			}
			MatOfPoint contour = contours.get(contourIdx);
			MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
			if (isRectangle(getInRangeQuadrilateral(contour, contour2f))) {
				ignoredContoursIdx.add(contourIdx);
				RotatedRect rotatedRect = Imgproc.minAreaRect(contour2f);
				boolean isLongPlate = LicensePlate.isLongPlate(rotatedRect.size);
				Size plateSize = isLongPlate ? LicensePlate.LONG_PLATE_STANDARD_SIZE
						: LicensePlate.SHORT_PLATE_STANDARD_SIZE;
				Mat candidatePlate = doPerspectiveTransformation(src, rotatedRect, plateSize);
				rotatedRect.points(points);
				candidatePlates.add(new LicensePlate(candidatePlate, points, plateSize, isLongPlate));
			}
		}
		return candidatePlates;
	}
	
	//
	private static Mat preprocessCandidateImg(Mat src) {
		 Mat processingImg = new Mat();
		 Imgproc.cvtColor(src, processingImg, Imgproc.COLOR_BGR2GRAY);
		 Imgproc.GaussianBlur(processingImg, processingImg, LicensePlate.KERNEL_SIZE_3X3, 1);
		 Imgproc.adaptiveThreshold(processingImg, processingImg, 255,
		Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 27, 1);
		 if ((double) Core.countNonZero(processingImg) / processingImg.total() > 0.55) {
		 Core.bitwise_not(processingImg, processingImg);
		 }
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				 LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		 return processingImg;
		}
	


	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread("license_plate/3.jpg");

		List<LicensePlate> plates = detectCandidatePlate(image);
		System.out.println(plates.size());
		for(LicensePlate plate : plates) {
			HighGui.imshow("Image", plate.getCandidatePlate());
			HighGui.waitKey();
			HighGui.destroyAllWindows();
		}
	}
}
