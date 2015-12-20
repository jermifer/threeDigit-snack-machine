package controller;

import java.util.Scanner;

public class VendingMachineFrontController {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String selectedProductCode;
		
		System.out.println("111 - M&Ms - $1.00 - 2 in stock");
		System.out.println("112 - Starburst - $0.60 - 1 in stock");
		System.out.println("113 - Ruffles - $0.55 - 0 in stock \n");
		
		System.out.println("Make Selection");
		
		selectedProductCode = scan.nextLine();
		System.out.println("You entered " + selectedProductCode);
		
		scan.close();
	}
}
