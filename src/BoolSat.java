// Author: Jack Douglass
// File: BoolSat.java
// Class: CSC 210, Fall 2024
// Purpose: The purpose of this program is to read in an expression representing an SAT problem and determine whether or not this problem is satisfiable or not. The problem is satisfiable when there is a possible permutation of variables within the problem that make the final result equal true. Thus, this program works by testing all possible permutations of any number of Boolean values to see if any equate to true. The problem does this by exhaustively backtracking through a ASTNode tree representing the SAT problem. To use this program, the use should enter an argument that is a filename of a file containing the SAT problem expression. The expression should only contain parentheses, ID node names, and NAND operators. The program will then output whether the program is satisfiable and all permutations of variables that satisfy the problem. The use can also input "DEBUG" as a second argument to enter debug mode, where all possible permutations will be printed out along with whether they equate to true or false when plugged into the expression.

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class BoolSat {
	
	public static String getOuput(ASTNode rootNode, Boolean debugOption) throws FileNotFoundException {
		// this function creates the output of the NP-problem
		// the output is made up the SAT/UNSAT message, and every permutation of every variable in the code
		
		// create a HashMap to store ID nodes and what Boolean they will be treated as during tree traversal
		HashMap<String, Boolean> idNodeVals = new HashMap<String, Boolean>();
		
		// second list containing ID nodes is important for backtracking to get every permutation of variables
		ArrayList<String> idNodes = new ArrayList<String>();
		getIds(rootNode, idNodeVals);
		
		for (String s : idNodeVals.keySet()) idNodes.add(s);
		
		// create a HashSet to store the output and get rid of duplicates
		HashSet<String> outputStrings = new HashSet<String>();
		assignBools(rootNode, idNodeVals, outputStrings, idNodes, debugOption);
		
		// move output strings to an ArrayList to enable sorting to get 
		ArrayList<String> tempOutputStrings = new ArrayList<String>(outputStrings);
		Collections.sort(tempOutputStrings);
		
		Boolean saturated = false;
		if (!debugOption && tempOutputStrings.size() > 0) saturated = true;
		String output = "SAT\n";
		
		for (String s : tempOutputStrings) {
			if (debugOption && s.substring(s.length() - 4, s.length()).equals("true")) saturated = true;
			output += s + "\n";
		}
		
		if (!saturated) output = "UN" + output;
		
		return output;
	}
	
	public static Boolean checkPermutation(ASTNode curNode, HashMap<String, Boolean> idNodeVals) {
		// this function checks the result of the expression with values of each variable deciding by a back tracking algorithm (traverseTree)
		
		if (curNode.isId()) return idNodeVals.get(curNode.getId());
		else {
			// two recursive calls for each NAND node for each child node
			Boolean child1Recursion = checkPermutation(curNode.child1, idNodeVals);
			Boolean child2Recursion = checkPermutation(curNode.child2, idNodeVals);
			
			return nand(child1Recursion, child2Recursion);  // implement the NAND operator in the ASTNode tree
		}
	}
	
	public static Boolean nand(Boolean x, Boolean y) {
		// this function implements the NAND operator in service of finding the result of each permutation
		
		return !(x && y);
	}
	
	public static void assignBools(ASTNode rootNode, HashMap<String, Boolean> idNodeVals, HashSet<String> outputStrings, ArrayList<String> idNodes, Boolean debugOption) {
		// this function builds a HashSet containing strings that make up the output
		// it does so by recursively creating different variable permutations and testing them for problem satisfaction with helper methods
		
		if (idNodes.isEmpty()) {  // empty idNodes ArrayList signifies a new complete permutation of ID node variables
			Boolean permutationResult = checkPermutation(rootNode, idNodeVals);
			String tempLine = generateLine(idNodeVals, debugOption, permutationResult);
			
			if (debugOption) outputStrings.add(tempLine);
			else if (!debugOption && permutationResult) outputStrings.add(tempLine);
			
		} else {
			// remove last node to decrease ArrayList size for next recursive call, necessary for progression through all ID nodes
			String curNodeId = idNodes.remove(idNodes.size() - 1);  
			
			idNodeVals.put(curNodeId, false);
			assignBools(rootNode, idNodeVals, outputStrings, idNodes, debugOption);
			
			idNodeVals.put(curNodeId, true);
			assignBools(rootNode, idNodeVals, outputStrings, idNodes, debugOption);
			
			idNodes.add(curNodeId); // re-add removed ID node so future permutations can take place
		}
	}
	
	public static String generateLine(HashMap<String, Boolean> idNodeVals, Boolean debugOption, Boolean permutationResult) {
		// this function creates a line of the output String using ID node names and Boolean values
		
		ArrayList<String> tempArr = new ArrayList<String>(idNodeVals.keySet());
		Collections.sort(tempArr);  // ID node variables must be in alphabetical order
		
		String line = "";
		for (String id : tempArr) {
			Boolean val = idNodeVals.get(id);
			line += id + ": " + val + ", ";
		}
		
		if (debugOption) {  // edit line if in debug mode
			line += permutationResult;
			return line;
		} else {
			return line.substring(0, line.length() - 2);  // get rid of redundant ", "
		}
	}
	
	public static void getIds(ASTNode curNode, HashMap<String, Boolean> idNodes) {
		// this function records the string IDs of all ID nodes into a HashMap 'idNodes'
		
		if (curNode.isId()) {
			idNodes.put(curNode.getId(), false);
			
		} else {
			// recursive call to progress through NAND nodes
			getIds(curNode.child1, idNodes);
			getIds(curNode.child2, idNodes);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException{
        if(args.length < 1 || args.length > 2) {
            System.err.println(
                    "USAGE: java BoolSat <inputFile.txt> <DEBUG*>");
            System.exit(1);
        }
        
        // Get the expression from the file
        String expression = null;
        Scanner s = new Scanner(new File(args[0]));
        expression = s.nextLine();

        System.out.println("input: " + expression);
        
        s.close();

        // call the parser to generate the AST for the expression
        ASTNode root = BoolSatParser.parse(expression);
        
        // get string output to print out
        String output;
        if (args.length == 2 && args[1].equals("DEBUG")) {
            output = getOuput(root, true);
        } else {
            output = getOuput(root, false);
        }
        
        // print output string
        System.out.println(output);
        
    }

}