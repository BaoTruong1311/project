package nhandangkytu;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;


public class HaarBaseExtractor {

	private static int NUM_OF_FEATURES = 32;

	// Bước 1: Tiền xử lí ảnh gốc

	private static Mat preprocessLetterImg(Mat letterImg) {
		// Chuẩn hóa kích thước ảnh
		Mat matOfFeatureVector = new Mat(1, NUM_OF_FEATURES, CvType.CV_32FC1);
		Imgproc.resize(letterImg, letterImg, ImgprocSetting.STANDARD_LETTER_BLOCK_SIZE);			

		// Tiền xử lí ảnh
		Mat res = new Mat();
		Point anchor = new Point(-1, -1);
		Imgproc.morphologyEx(letterImg, res, Imgproc.MORPH_OPEN, ImgprocSetting.STRUCTURING_ELEMENT_RECT_3X3, anchor,
				2);
		Imgproc.morphologyEx(res, res, Imgproc.MORPH_OPEN, ImgprocSetting.STRUCTURING_ELEMENT_ELLIPSE_3X3, anchor, 2);
		Imgproc.morphologyEx(res, res, Imgproc.MORPH_CLOSE, ImgprocSetting.STRUCTURING_ELEMENT_RECT_3X3, anchor, 2);
		Imgproc.morphologyEx(res, res, Imgproc.MORPH_CLOSE, ImgprocSetting.STRUCTURING_ELEMENT_ELLIPSE_3X3, anchor, 2);
		return res;
	}
	
	// Bước 2: Xây dựng trích rút 32 đặc trưng dể dựa vào đó mà nhận diện

	public static Mat extractFeature(Mat letterImg) {
		Mat preprocessedImg = preprocessLetterImg(letterImg);

		int zoneCols = preprocessedImg.cols() / 4;
		int zoneRows = preprocessedImg.rows() / 4;
		int totalWhitePixels = Core.countNonZero(preprocessedImg);
		double[] featureVector = new double[NUM_OF_FEATURES];

		int index = 0;

		// Tổng số trên mỗi ô

		for (int rowStart = 0; rowStart < preprocessedImg.rows(); rowStart += zoneRows) {
			for (int colStart = 0; colStart < preprocessedImg.cols(); colStart += zoneCols) {

				featureVector[index++] = (double) Core.countNonZero(
						preprocessedImg.submat(rowStart, rowStart + zoneRows, colStart, colStart + zoneCols))
						/ totalWhitePixels;
			}
		}

		// Tổng số trên 4 hàng

		for (int i = 0; i < 16; i += 4) {
			featureVector[index++] = featureVector[i] + featureVector[i + 1] + featureVector[i + 2]
					+ featureVector[i + 3];
		}

		// Tổng số trên 4 cột

		for (

				int i = 0; i < 4; i++) {
			featureVector[index++] = featureVector[i] + featureVector[i + 4] + featureVector[i + 8]
					+ featureVector[i + 12];
		}

		// Tổng số trên 2 đường chéo

		featureVector[index++] = featureVector[0] + featureVector[5] + featureVector[10] + featureVector[15];
		featureVector[index++] = featureVector[3] + featureVector[6] + featureVector[9] + featureVector[12];

		// Tổng số trên 4 góc

		featureVector[index++] = featureVector[0] + featureVector[1] + featureVector[4] + featureVector[5];
		featureVector[index++] = featureVector[2] + featureVector[3] + featureVector[6] + featureVector[7];
		featureVector[index++] = featureVector[8] + featureVector[9] + featureVector[12] + featureVector[13];
		featureVector[index++] = featureVector[10] + featureVector[11] + featureVector[14] + featureVector[15];

		// Tổng số trong tâm

		featureVector[index++] = featureVector[5] + featureVector[6] + featureVector[9] + featureVector[10];

		// Tổng các số trên vùng biên

		featureVector[index] = featureVector[0] + featureVector[1] + featureVector[2] + featureVector[3]
				+ featureVector[7] + featureVector[11] + featureVector[15] + featureVector[14] + featureVector[13]
				+ featureVector[12] + featureVector[8] + featureVector[4];

		Mat matOfFeatureVector = new Mat(1, NUM_OF_FEATURES, CvType.CV_32FC1);
		matOfFeatureVector.put(0, 0, featureVector);
		return matOfFeatureVector;
	}


}
