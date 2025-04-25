// Author: Jack Douglass
// File: AirportInfo.java
// Class: CSC 210, Fall 2024
// Purpose and Use: The purpose of this program is extract data concerning commercial flights to and from airports. The program reads in a file containing flight data. It then outputs multiple data points depending on user input. It can output which airport(s) are involved with the most flights, where every airport's flights are headed to, and a list of airports that are involved in more flights than a number specified by the program user. To use the program, the user should provide a file in comma separated value format (.csv) containing flight data. The user should then input either "MAX", "DEPARTURES" or "LIMIT" to use the different program features. If the user inputs, "LIMIT" they must then input a positive integer to run the program.  

//package com.gradescope.airportinfo;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;

public class AirportInfo {
	
	public static HashMap<String, String> getDestinations(String fileName) throws FileNotFoundException {
		
		// make a temporary HashMap to store origin airports and collect corresponding destination airports as data is traversed through
		HashMap<String, String> tempDestinations = new HashMap<String, String>();
		File myFile = new File(fileName);
		Scanner fReader = new Scanner(myFile);
		
		String line = fReader.nextLine();  // skip first line
		while (fReader.hasNextLine()) {
			line = fReader.nextLine();
			String[] list = line.strip().split(",");
			
			// get starting airports and ending airports from file containing flight information
			String origin = list[2];
			String destination = list[4];
			if (tempDestinations.get(origin) == null) tempDestinations.put(origin, "");
			tempDestinations.put(origin, tempDestinations.get(origin) + "  " + destination);
		}
		
		// make a final HashMap to hold String of starting airport and String containing all ending airports
		HashMap<String, String> destinations = new HashMap<String, String>();
		for (String key : tempDestinations.keySet()) {
			String[] lst = tempDestinations.get(key).split(" ");
			ArrayList<String> tempArrList = new ArrayList<String>();
			for (String k : lst) tempArrList.add(k);
			tempArrList.sort(null);
			String stringDestinations = String.join(" ", tempArrList);
			destinations.put(key, stringDestinations);
		}
		fReader.close();
		return destinations;
	}
	
	public static HashMap<String, Integer> getAirportCount(String fileName) throws FileNotFoundException {
		HashMap<String, Integer> airportCount = new HashMap<String, Integer>();
		File myFile = new File(fileName);
		Scanner fReader = new Scanner(myFile);
		
		String line = fReader.nextLine();
		while (fReader.hasNextLine()) {
			line = fReader.nextLine();
			String[] list = line.strip().split(",");
			String origin = list[2];
			String destination = list[4];
			
			// initialize a pair in the HashMap if airport hasn't been encountered yet
			if (airportCount.get(origin) == null) airportCount.put(origin, 0);
			airportCount.put(origin, airportCount.get(origin) + 1);
			
			// both arriving and leaving flights count for each airport 
			if (airportCount.get(destination) == null) airportCount.put(destination, 0);
			airportCount.put(destination, airportCount.get(destination) + 1);
		}
		
		fReader.close();
		return airportCount;
	}
	
	public static String getMax(HashMap<String, Integer> airportCount) {
		// use a sorted ArrayList to get airports in alphabetical order when constructing the function output
		ArrayList<String> sortedAirports = new ArrayList<String>(airportCount.keySet());
		sortedAirports.sort(null);
		ArrayList<String> maxAirports = new ArrayList<String>();
		
		int max = 0;
		
		// first for loop to get maximum flight number
		for (String key : sortedAirports) {
			if (airportCount.get(key) > max) max = airportCount.get(key);
		}
		
		// second for loop to collect airports with max flight value
		for (String key : airportCount.keySet()) {
			if (airportCount.get(key) == max) maxAirports.add(key);
		}
		
		String maxAirportsString = String.join(", ", maxAirports);
		String maxFlights = "MAX FLIGHTS " + max + " : " + maxAirportsString;
		return maxFlights;
	}
	
	public static String getDepartures(HashMap<String, String> destinations) {
		ArrayList<String> departures = new ArrayList<String>(destinations.keySet());
		departures.sort(null);
		
		String departuresString = "";
		
		// for loop is necessary because output is one large, multi-line string
		for (String key : departures) {
			departuresString += key + " flies to " + destinations.get(key) + "\n";  // start new line after each airport 
		}
		return departuresString;
	}
	
	public static String getLimits(int limit, HashMap<String, Integer> airportCount) {
		ArrayList<String> sortedAirports = new ArrayList<String>(airportCount.keySet());
		sortedAirports.sort(null);
		ArrayList<String> validAirports = new ArrayList<String>();
		for (String key : sortedAirports) {
			if (airportCount.get(key) > limit) validAirports.add(key);
		}
		
		String validAirportsString = "";
		
		for (String key : validAirports) {
			validAirportsString += key + " - " + airportCount.get(key) + "\n";  // only get data for airports with a greater number of flights than limit
		}
		return validAirportsString;
	}

	public static void main(String[] args) throws FileNotFoundException {
        
        HashMap<String, String> destinations = getDestinations(args[0]);
        HashMap<String, Integer> airportCount = getAirportCount(args[0]);
       
        
        if (args[1].equals("MAX")) {
            System.out.println(getMax(airportCount));
        }
        
        if (args[1].equals("DEPARTURES")) {
            System.out.println(getDepartures(destinations));
        }
        
        if (args[1].equals("LIMIT")) {
            System.out.println(getLimits(Integer.valueOf(args[2]), airportCount));
        }
    }

}
