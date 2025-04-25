// Author: Jack Douglass
// File: Gradenator.java
// Class: CSC 210, Fall 2024
// Purpose and Use: This program is a grade calculator for a class. It reads input files consisting of lines with information about class assignments, how much those assignments are worth, and the student's grades on those assignments. The program then outputs the grade the student got on each section of classwork, and then their total grade, calculating by using weighted multiplication with the percent value and average grade of each classwork section. A user should create an input file with data containing the classwork section name, the grades achieved on that section's assignments, and how much that contributes to the class's final grade. 

//package com.gradescope.gradenator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Gradenator {
	
	public static double getAverage(String line) {
		String[] nums = line.split(" ");
		double sum = 0;
		for(int i = 0; i < nums.length; i++) {
			sum += Double.valueOf(nums[i]);
		}
		return sum/nums.length;
	}
	
	public static double getPercent(String pStr) {
		return Double.valueOf(pStr.substring(0, pStr.length() - 1)) / 100;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner iReader = new Scanner(System.in);
		System.out.println("File name?");
		String filename = iReader.nextLine();
		File myFile = new File(filename);
		Scanner fReader = new Scanner(myFile);
		
		double actualGrade = 0.0;
		double totalGrade = 0.0;
		
		while (fReader.hasNextLine()) {
			String line = fReader.nextLine();
			String[] arr = line.strip().split("; ");
			double avg = 0;
			if(arr[0].length() <= 3) {
				avg = Double.valueOf(arr[0]);
			} else {
				avg = getAverage(arr[0]);
			}
			Double pct = getPercent(arr[2]);
			actualGrade += avg * pct;
			totalGrade += pct * 100;
			System.out.format("%s; %.1f%%; avg=%.1f\n", arr[1], pct * 100, avg);
		}
		
		System.out.format("TOTAL = %.1f%% out of %.1f%%", actualGrade, totalGrade);
			
		
		iReader.close();
		fReader.close();
	}

}
