/** Assignment 4 - CS2010 
 * "Fun with Graphs"
 * Part 2 - "Communication Network Construction and Cost"
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
import java.util.Scanner;

public class A4Main2 {
    /**
     * @param args the command line arguments
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
        CommNet network = null;
        try {
        	BufferedReader br = new BufferedReader(new FileReader(filename));
        	String line;
        	String[] parsed;
        	
        	line = br.readLine();
        	network = new CommNet(Integer.parseInt(line));
        	line = br.readLine();
        	int inputEdge = Integer.parseInt(line);

        	for (int i = 0; i < inputEdge; i++) {
        		line = br.readLine();
        		parsed = line.split(" ");
        		network.addEdge(Integer.parseInt(parsed[0]), Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2]));
        	}
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        // Get the required output from the network class
        int cableCost = network.findMST();
        int numOfEdges = network.getNumEdge();
        
        int numOfTerminators = network.getTerminators();
        int numOfSwitches = network.getSwitches();
        int numOfRouters = network.getRouters();
        int terminatorCost = numOfTerminators * 5;
        int switchCost = numOfSwitches * 500;
        int routerCost = numOfRouters * 1000;

        int overallCost = cableCost + terminatorCost + switchCost + routerCost;
        
        // Print the result to the console
        System.out.format("Number of edges = %d; total cost = $%d\n", numOfEdges, cableCost);
        System.out.format("Number of terminal nodes = %d; total cost = $%d\n", numOfTerminators, terminatorCost);
        System.out.format("Number of switches = %d; total cost = $%d\n", numOfSwitches, switchCost);
        System.out.format("Number of routers = %d; total cost = $%d\n", numOfRouters, routerCost);
        System.out.format("Overall cost = $%d\n", overallCost);
    }
    
}
