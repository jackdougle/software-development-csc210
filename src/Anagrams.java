// Author: Jack Douglass
// File: Anagrams.java
// Class: CSC 210, Fall 2024
// Purpose: The purpose of this program is to find and return anagrams of a specified word. An anagram is a word or collection of words that is formed from the letters of another word or collection of words. This program finds and outputs anagrams by taking in three inputs from the user. The first is a String representing the filename of a file containing valid words, the second is a String representing the word of which to find anagrams for, and the final input is an int that represents the maximum number of words output anagrams can contain (0 is inputed if the user doesn't desire a word limit). The file that is accessed using the input file name should contain Strings, one per line. The program will output the word to scramble (the input word), the valid words that are contained in the input word, and then the anagrams of the word.

//package com.gradescope.anagrams;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class Anagrams {
	
	public static HashSet<String> getWordList(String wList) throws FileNotFoundException {
		// this program gets the transfers the words in a file into a HashSet, these words represent the valid words that could potentially form anagrams of an inputed words
		
		File myFile = new File(wList);
		Scanner fReader = new Scanner(myFile);
		HashSet<String> valids = new HashSet<String>();
		
		while (fReader.hasNextLine()) valids.add(fReader.nextLine());
	
		fReader.close();
		return valids;
	}
	
	public static ArrayList<Character> getChars(String word) {
		// this program gets the characters of 'word' and organizes them alphabetically using an ArrayList
		
		ArrayList<Character> chars = new ArrayList<Character>();
		
		for (int i = 0; i < word.length(); i++) chars.add(word.charAt(i));
		
		Collections.sort(chars);  // get list of chars in alphabetical order for comparison purposes
		return chars;
	}
	
	public static void getCombinations(ArrayList<Character> allChars, String word, HashSet<String> valids, HashSet<String> sols) {
		// this program finds valid words from the collection 'valids' that are buildable using letters in 'word' and then places these words in 'sols'
		
		if (valids.contains(word)) sols.add(word);  // get valid words that can be made from letters inn 'word'
		
		for (int i = 0; i < allChars.size(); i++) {
			Character letter = allChars.get(i);
			
			ArrayList<Character> remainingChars = new ArrayList<>(allChars);
			remainingChars.remove(i);  // get rid of current character so future recursive calls can progress through the rest of the characters
			
			getCombinations(remainingChars, word + letter, valids, sols);  // use backtracking to get every possible combination of chars
		}
	}
	
	
	public static void getAnagrams(int len, ArrayList<String> sols, String word, ArrayList<String> result, int maxAnas, int count, ArrayList<ArrayList<String>> allResults) {
		// this program find anagrams of 'word' and places them in 'allResults' which are then printed in the main function
		
		if (count <= len) {  // if potential anagram is longer than original word, it cannot be a valid anagram
			
			if (isAnagram(word, result)) {
				if (maxAnas > 0 && result.size() <= maxAnas) allResults.add(new ArrayList<>(result));
				else if (maxAnas < 0) allResults.add(new ArrayList<>(result));
			}
				for (int i = 0; i < sols.size(); i++) {
					ArrayList<String> remainingSols = new ArrayList<>(sols);
					remainingSols.remove(i);
					
					String newWord = sols.get(i);
					result.add(newWord);
					
					getAnagrams(len, remainingSols, word, result, maxAnas, count + newWord.length(), allResults);  // use backtracking again to get find every anagram of 'word'
					
					result.remove(result.size() - 1);
				
			}
		}
	}
	
	public static boolean isAnagram(String word, ArrayList<String> result) {
		ArrayList<Character> resultChars = new ArrayList<Character>();
		ArrayList<Character> wordChars = new ArrayList<Character>();
		System.out.println(result);
 		for (String s : result) {
			for (int i = 0; i < s.length(); i++) {
				resultChars.add(s.charAt(i));
			}
		}
 		
 		for (int i = 0; i < word.length(); i++) wordChars.add(word.charAt(i));
		Collections.sort(resultChars);
		Collections.sort(wordChars);
		if (resultChars.equals(wordChars)) return true;
		else return false;
	}

	public static void main(String[] args) throws FileNotFoundException {
		String wordList = args[0];
        String word = args[1];
        int maxAnas = Integer.valueOf(args[2]);
        if (maxAnas == 0) maxAnas = -1;  // set to -1 for no limit

        System.out.println("Phrase to scramble: " + word);
        
        HashSet<String> validWords = getWordList(wordList);
        HashSet<String> solutions = new HashSet<String>();
        ArrayList<Character> allChars = getChars(word);
        
        getCombinations(allChars, "", validWords, solutions);
        ArrayList<String> orderedSolution = new ArrayList<String>(solutions);
        Collections.sort(orderedSolution);
        System.out.println(orderedSolution);
        
        
        System.out.println("\nAll words found in " + word + ":");
        System.out.println(orderedSolution);
        
        ArrayList<String> result = new ArrayList<String>();
        System.out.println("\nAnagrams for " + word + ":");
        ArrayList<ArrayList<String>> allResults = new ArrayList<ArrayList<String>>();
        getAnagrams(word.length(), orderedSolution, word, result, maxAnas, 0, allResults);
        for (int i = 0; i < allResults.size(); i++) System.out.println(allResults.get(i));

	}

}