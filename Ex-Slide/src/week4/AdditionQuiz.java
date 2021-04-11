package week4;

import java.util.Scanner;

public class AdditionQuiz {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int number1 = (int) (System.currentTimeMillis() * 8 % 10);
		int number2 = (int) (System.currentTimeMillis() % 10);
		System.out.println("What is " + number1 + " + " + number2 + " = ");
		int answer = input.nextInt();
		System.out.println(number1 + " + " + number2 + " = " + (number1 + number2) + " ,Your answer is " + answer);

	}

}
