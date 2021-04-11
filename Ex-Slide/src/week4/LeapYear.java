package week4;

import java.util.Scanner;

public class LeapYear {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Nhap vao nam ban muon kiem tra: ");
	    int numberYear = input.nextInt();
	    boolean isYear = (numberYear % 4 == 0 && numberYear % 100 != 0) || ( numberYear % 400 == 0 );
	    if( isYear == true) {
	    	System.out.println("Nam nhuan");
	    }else {
	    	System.out.println("Nam khong nhuan");
	    }
	}

}
