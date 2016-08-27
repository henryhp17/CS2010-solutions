/** Assignment 4 - CS2010 
 * "Fun with Graphs"
 * Part 1 - "Shortest and Longest Process Chain"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Henry Pratama Suryadi
 * YOUR MATRICULATION #: A0105182R
 * 
 * COMMENTS:
 * You are not allowed to change any existing codes for printing. You
 * may add your own reading code or printing code to print your results of each part.
 * 
 * 
 * Assigned: Mar 25 (Friday), 2016
 * Due: Apr 15 (Friday by 11.59pm), 2016
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class A4Main1{
    /**
     * Label houses by nearest hospitals.
     */
    public static void main(String[] args) {
        String filename;
        if(args.length > 1)
            filename = args[0];
        else {
            System.out.print("Please input filename: ");
            Scanner scanner = new Scanner(System.in);  // for reading from console
            filename = scanner.nextLine();
        }
        
        // Start building the graph from text file
        ProcessChain pc = null;
        int edge = 0;
        try {
        	BufferedReader br = new BufferedReader(new FileReader(filename));
        	String line;
        	String[] parseLine;
        	line = br.readLine();
        	pc = new ProcessChain(Integer.parseInt(line));
        	line = br.readLine();
        	edge = Integer.parseInt(line);

        	for (int i = 0; i < edge; i++) {
        		line = br.readLine();
        		parseLine = line.split(" ");
        		pc.addEdge(Integer.parseInt(parseLine[0]), Integer.parseInt(parseLine[1]));
        	}

        	pc.topSort();
        } catch(IOException e) {
        	e.printStackTrace();
        }

        // Print all the starting points
        ArrayList<Integer> points = pc.getPoints(0);
        System.out.print("Start points are: ");
        for (int i = 0; i < points.size(); i++) {
            if (i != 0) System.out.print(", ");
            System.out.print(points.get(i));
        }
        System.out.println();
        // Print all the ending points
        points = pc.getPoints(1);
        System.out.print("End points are: ");
        for (int i = 0; i < points.size(); i++) {
            if (i != 0) System.out.print(", ");
            System.out.print(points.get(i));
        }
        System.out.print("\n\n");
        
        // Print out length of the shortest path
		// Print out the corresponding path
		int distance = pc.computeShortPath();
		Stack<Integer> pathFromStoV = pc.getPath(0);
        System.out.println("Length of shortest path is: " + distance);
		System.out.format("%d to %d (%d):  ", pc.getStart(0), pc.getEnd(0), distance);
		while (!pathFromStoV.isEmpty()) {
			if (pathFromStoV.size() == 1) System.out.print(pathFromStoV.pop());
			else System.out.print(pathFromStoV.pop() + "->");
		}
		System.out.println();

        // Print out length of the longest path
		// Print out the corresponding path
		distance = pc.computeLongPath();
		pathFromStoV = pc.getPath(1);
        System.out.println("Length of longest path is: " + distance);
		System.out.format("%d to %d (%d):  ", pc.getStart(1), pc.getEnd(1), distance);
		while (!pathFromStoV.isEmpty()) {
			if (pathFromStoV.size() == 1) System.out.print(pathFromStoV.pop());
			else System.out.print(pathFromStoV.pop() + "->");
		}
		System.out.println();
    }

}