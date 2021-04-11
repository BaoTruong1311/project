package test;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import nhandienbienso.LicensePlate;
import nhandienbienso.LicensePlateRecognizer;

public class Main {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat mat = Imgcodecs.imread("license_plate/3.jpg");
				
		LicensePlateRecognizer licensePlateRecognizer = new LicensePlateRecognizer();
		List<LicensePlate> list = licensePlateRecognizer.detectCandidatePlate(mat);
		
		for(LicensePlate l: list) {
			HighGui.imshow("Pre", l.getCandidatePlate());
			HighGui.waitKey();
			HighGui.destroyAllWindows();
		}
	}
}
