package nhandangkytu;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;

public class TrainData {	
	// Bước 3: Xây dựng tập dữ liệu huấn luyện trên file

	// Liệt kê các thư mục có chứa dữ liệu huấn luyện

	public List<String> listDirPath(String path) {
		List<String> listOfDir = new LinkedList<>();
		for (File dir : new File(path).listFiles()) {
			if (dir.isDirectory()) {
				listOfDir.add(dir.getAbsolutePath());
			}
		}
		return listOfDir;
	}

	// Liệt kê tắc cả các ảnh mẫu

	public List<String> listFilePath(String path) {
		List<String> listOfFile = new LinkedList<>();
		for (File file : new File(path).listFiles()) {
			if (file.isFile()) {
				listOfFile.add(file.getAbsolutePath());
			}
		}
		return listOfFile;
	}

	// Tiền xử lí ảnh mẫu

	public static void preProcessSampleImages(Mat src) {
		// Chuyển thành ảnh grayscale

		if (src.channels() == 3) {
			Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
		}

		// Chuyển thành ảnh đen trắng

		Imgproc.threshold(src, src, 0, 255, Imgproc.THRESH_OTSU);

		// Nghịch đảo các điểm ảnh : đen -> trắng , trắng -> đen

		Core.bitwise_not(src, src);
	}

	// Xây dựng ma trận và ma trận nhãn

	public void buildTrainingData(String path, Mat samples, Mat labels) {
		File dataSetDir = new File(path);

		List<String> dirPaths = listDirPath(dataSetDir.getAbsolutePath());

		if (dirPaths.size() <= 0 ) {
			return;
		}

		// Một ma trận lưu nhẫn và giá trị triết xuất

		Mat label = new Mat(1, 1, CvType.CV_32SC1);

		// Duyệt quan tất cả các thư mục con các kí tự

		for (String dirPath : dirPaths) {

			// Lấy file của các mẫu

			List<String> filePaths = listFilePath(dirPath);

			if (filePaths.size() <= 0) {
				return;
			}
			
			int labelData = LetterRecognizer.LETTER_2_LABEL_MAP.get(dirPath.charAt(dirPath.length() - 1));
			label.put(0, 0, labelData);
			for (String filePath : filePaths) {
				Mat src = Imgcodecs.imread(filePath);
				preProcessSampleImages(src);
				
				samples.push_back(HaarBaseExtractor.extractFeature(src));
				labels.push_back(label);
			}
		}
		samples.convertTo(samples, CvType.CV_32FC1);
		labels.convertTo(labels, CvType.CV_32SC1);
	}

	// Train data và lưu kết quả

	public boolean trainSVM(Mat samples, Mat responses) {
		SVM letterRecognizeSVM = SVM.create();
				
		if (!letterRecognizeSVM.trainAuto(samples, Ml.ROW_SAMPLE, responses)) {
			return false;
		}
		letterRecognizeSVM.save("svm_chars.txt");
		return true;
	}
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat samples = new Mat();
		Mat labels = new Mat();
		
		TrainData train = new TrainData();
		train.buildTrainingData("letter", samples, labels);
		train.trainSVM(samples, labels);
	}
}
