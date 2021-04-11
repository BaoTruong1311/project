package tachso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import nhandienbienso.LicensePlate;

public class NumberRecognizer {
	private static int LETTER_MIN_AREA = 40;

	// Bước 5: Tiền xử lí ảnh trước khi tách ảnh
	private static Mat preprocessCandidateImg(Mat src) {
		Mat processingImg = new Mat();
		Imgproc.cvtColor(src, processingImg, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(processingImg, processingImg, LicensePlate.KERNEL_SIZE_3X3, 1);
		Imgproc.adaptiveThreshold(processingImg, processingImg, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
				Imgproc.THRESH_BINARY, 27, 1);
		if ((double) Core.countNonZero(processingImg) / processingImg.total() > 0.55) {
			Core.bitwise_not(processingImg, processingImg);
		}
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_OPEN,
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				LicensePlate.STRUCTURING_ELEMENT_RECT_3X3);
		Imgproc.morphologyEx(processingImg, processingImg, Imgproc.MORPH_CLOSE,
				LicensePlate.STRUCTURING_ELEMENT_ELLIPSE_3x3);
		return processingImg;
	}

	// Cắt thành các kí tự riêng lẻ

	public static List<LetterBlock> splitLetterImg(LicensePlate plate) {
		Mat preprocessedCandidateImg = preprocessCandidateImg(plate.getCandidatePlate());
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(preprocessedCandidateImg, contours, hierarchy, Imgproc.RETR_TREE,
				Imgproc.CHAIN_APPROX_SIMPLE);

		List<LetterBlock> letterImages = new LinkedList<>();
		HashSet<Integer> ignoredContoursIdx = new HashSet<>();
		int contoursSize = contours.size();
		for (int contourIdx = 0; contourIdx < contoursSize; contourIdx++) {
			// If a contour is child of another contour which was recognized as a letter
			// block, We will ignore it. Do it also for all its child by adding its
			// contourIdx into the ignored list
			if (ignoredContoursIdx.contains((int) hierarchy.get(0, contourIdx)[3])) {
				ignoredContoursIdx.add(contourIdx);
				continue;
			}
			MatOfPoint contour = contours.get(contourIdx);
			Rect boundingRect = Imgproc.boundingRect(contour);
			int boundingRectArea = boundingRect.width * boundingRect.height;
			// check area of contour
			// check solidity
			// check aspect ratio
			if (LETTER_MIN_AREA < boundingRectArea && Imgproc.contourArea(contour) / boundingRectArea > 0.15
					&& (double) boundingRect.width / boundingRect.height < 1.0) {
				Mat letterMat = preprocessedCandidateImg.submat(boundingRect);
				double pixelRatio = (double) Core.countNonZero(letterMat) / letterMat.total();
				// Ratio of white pixel should be in range [0.3, 0.8] of all pixels
				if (0.3 < pixelRatio && pixelRatio < 0.8) {
					ignoredContoursIdx.add(contourIdx);
					letterImages.add(new LetterBlock(boundingRect.x, boundingRect.y, letterMat));
				}
			}
		}
		sortLetters(letterImages, plate.isLongPlate());
		return letterImages;
	}

	// Sắp xếp các kí tự từ trái sang phải
	private static void sortLetters(List<LetterBlock> letterImageInfos, boolean isLongPlate) {
		if (!isLongPlate) {
			letterImageInfos.sort(LetterBlock.Y_AXIS_COMPARATOR);
		}
		letterImageInfos.sort(LetterBlock.X_AXIS_COMPARATOR);
	}

}
