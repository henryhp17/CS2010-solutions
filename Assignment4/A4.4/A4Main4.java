/** Assignment 4 - CS2010 
 * "Fun with Graphs"
 * Part 4 - "Minimum-Maximum Height for Tunnels"
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
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class A4Main4 {
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
            scanner.close();
        }
        
        // Start building the graph from text file
        RoadTunnels tunnels = null;
        try {
        	BufferedReader br = new BufferedReader(new FileReader(filename));
        	String line;
        	String[] parsed;
        	
        	line = br.readLine();
        	tunnels = new RoadTunnels(Integer.parseInt(line));
        	line = br.readLine();
        	int inputEdge = Integer.parseInt(line);

        	for (int i = 0; i < inputEdge; i++) {
        		line = br.readLine();
        		parsed = line.split(" ");
        		tunnels.addEdge(Integer.parseInt(parsed[0]), Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2]));
        	}
        } catch (IOException e) {
        	e.printStackTrace();
        }

        // Get the MST of the graph by selecting edges with greates height
        tunnels.findMST();
		// Input the two nodes of graph.
        int s = Integer.parseInt("5");
        int v = Integer.parseInt("6");
		Stack<Integer> pathFromStoV = tunnels.findRoute(s, v);

		// Print out the corresponding path
		System.out.format("Path from %d to %d :  ", s, v);
		while (!pathFromStoV.isEmpty()) {
			if (pathFromStoV.size() != 1) System.out.print(pathFromStoV.pop() + "-");
			else        System.out.print(pathFromStoV.pop());
		}
		System.out.println();
		// Print the minimum passable height
		int minimumHeight = tunnels.getHeight();
		System.out.format("Maximum Height = %d\n", minimumHeight);
    }
}