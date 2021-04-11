package week123;

import java.util.Scanner;

public class ComputeAreaWithConsoleInput {
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a number for radius: ");
		double radius = input.nextDouble();
		
		double area = radius * radius * 3.14;
		System.out.println("the area for the circle of radius " + radius + " is 12" + area);
		
	}

}
