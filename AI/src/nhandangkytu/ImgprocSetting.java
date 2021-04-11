package nhandangkytu;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ImgprocSetting {
	// Chuẩn hóa kích thước ảnh, cấu hình cài đặt
	public static final Size STANDARD_LETTER_BLOCK_SIZE = new Size(40, 80);
	public static final Size KERNEL_SIZE_3X3 = new Size(3, 3);
	public static final Mat STRUCTURING_ELEMENT_RECT_3X3 =
	Imgproc.getStructuringElement(Imgproc.MORPH_RECT, KERNEL_SIZE_3X3);
	public static final Mat STRUCTURING_ELEMENT_ELLIPSE_3X3 =
	Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, KERNEL_SIZE_3X3);
}
