package nhandangkytu;

import java.util.HashMap;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.SVM;

public class LetterRecognizer {
	// Chuẩn bị dữ liệu

	public static final HashMap<Character, Integer> LETTER_2_LABEL_MAP = new HashMap<>();
	private static final char[] LETTERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'T', 'U', 'V', 'X', 'Y', 'Z' };
	static final int NUM_OF_CLASS = LETTERS.length;
	static {
		for (int i = 0; i < NUM_OF_CLASS; i++) {
			LETTER_2_LABEL_MAP.put(LETTERS[i], i);
		}
	}

	private SVM svm;

	public LetterRecognizer(String svmDataFilePath) {
		svm = SVM.load(svmDataFilePath);
	}

	public char recognizeLetter(Mat letterImg) {
		int predictedClass = (int) svm.predict(HaarBaseExtractor.extractFeature(letterImg));
		// Nếu không nhận dạng được thì trả về *
		return predictedClass < 0 || 32 < predictedClass ? '*' : LETTERS[predictedClass];
	}

	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		LetterRecognizer l = new LetterRecognizer("svm_chars.txt");
		Mat mat = Imgcodecs.imread("letter_test/1.png");
		TrainData.preProcessSampleImages(mat);
		Core.bitwise_not(mat, mat);
		HighGui.imshow("image", mat);
		HighGui.waitKey(0);
		HighGui.destroyAllWindows();
		
		System.out.println(l.recognizeLetter(mat));
	}
}
