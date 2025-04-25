/*
 * Author: Jack Douglass
 * File: WordList.java
 * Class: CSC 210, Fall 2024
 * Purpose: The purpose of this program is serve as a database of Strings that each 
 * represent a word in a word search game. This program keeps track of which words are 
 * in the word search grid through the user's game. When a user finds a word, that word 
 * is removed from this program. Once all words are removed from this program, the game 
 * is over. To use this program, a user should run WordSearch.java with a file containing 
 * dimesions for the word search grid and words to be put in the grid as an argument for 
 * WordSearch.java. Words in the argument file will be put into a WordList class. This 
 * class is then used for removing found words and checking if the game is over. 
 */

 package com.files;

 import java.util.ArrayList;

 public class WordList {
      private ArrayList<String> words = new ArrayList<>();
      
      public void addWord(String word) {
          // Adds a word to the words ArrayList

          words.add(word);
      }

      public void removeWord(String word) {
        // This methods removes a word from the words ArrayList, this 
        // is done when a word is found in the word search game

            for (int i = 0; i < words.size(); i += 1) {
                if (words.get(i).equals(word)) words.remove(i);
            }
        }

        public int getWordsLeft() {
            return words.size();
        }
      
      public boolean isEmpty() {
          // This method checks if the words ArrayList is empty, this 
          // is used as the end condition for the word search game
          
          return (words.size() <= 0);
      }

      public String toString() {
        String output = "";
        for(String s : words) {
            output += s + ", ";
        }

        return output;
      }
     
 }
 