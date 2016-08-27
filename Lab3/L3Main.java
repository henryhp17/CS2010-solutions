/** Lab 3 - CS2010 
 * "Space analysis of graphs"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Henry Pratama Suryadi
 * YOUR MATRICULATION #: A0105182R
 * 
 * COMMENTS:
 * You are not allowed to change any existing codes for reading and writing. You
 * may add your own printing code to print your results of each part.
 * 
 * 
 * Assigned: Mar 2 (Wednesday), 2016
 * Due: Mar 18 (Friday by 11.59pm), 2016
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class L3Main {
    
    public static void main(String[] args) throws IOException {
    
        /* 
         * Step 1: Your code here
         *
         * read text file and build adjacency list graph (AdjListGraph.java) 
         * and adjacency matrix graph (AdjMatrixGraph.java)
        */

        // Initialise graph for all versions
        AdjListGraph adjListGraph = null;
        AdjMatrixGraph adjMatGraph = null;
        AdjMatrixMoreEfficientGraph adjEffGraph = null;
        
        // Create object for input sequence according to the file name
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter input filename: ");
        String filename = br.readLine();
        
        // Inserting data to each graph, pay attention to different path representation for ubuntu and windows
        In inputFile = new In("data/" + filename);
        adjListGraph = new AdjListGraph(inputFile);
        inputFile = new In("data/" + filename);
        adjMatGraph = new AdjMatrixGraph(inputFile);
        inputFile = new In("data/" + filename);
        adjEffGraph = new AdjMatrixMoreEfficientGraph(inputFile);
        
        /*
         * Step 2
         * get the size of graph of each representation
         *    AdjListGraph.java: adj
         *    AdjMatrixGraph.java: adj
		 * Please use adjListGraph.sizeOfGraph() to get the memory usage of the graph with list implementation. 
		 * Please use adjMatGraph.sizeOfGraph() to get the memory usage of the graph with matrix implementation. 
        */
                
        // Output every required data from all versions
        System.out.format("1. Number of vertices = %d \n", adjListGraph.V());
        System.out.format("2. Number of edges = %d \n", adjListGraph.E());
        System.out.format("3. Output of the graph using adjacency list:\n");
        //adjListGraph.printGraph();
        System.out.format("4. Adjacency list\n (a) Memory needed to record edges = %d\n", adjListGraph.E() * Size.INTEGER);
        System.out.format(" (b) Total amount of memory used  = %d\n", adjListGraph.sizeOfGraph());
        System.out.format(" (c) Efficiency  = %f\n", 100.0 * adjListGraph.E() * Size.INTEGER / adjListGraph.sizeOfGraph());
        System.out.format("5. Output of the graph using matrix:\n");
        //adjMatGraph.printGraph();
        System.out.format("6. Adjacency matrix\n (a) Memory needed to record edges = %d\n", adjMatGraph.E() * Size.BOOLEAN);
        System.out.format(" (b) Total amount of memory used  = %d\n", adjMatGraph.sizeOfGraph());
        System.out.format(" (c) Efficiency  = %f\n", 100.0 * adjMatGraph.E() * Size.BOOLEAN / adjMatGraph.sizeOfGraph());
        //adjEffGraph.printGraph();
        System.out.format("Additional task: Efficient Adjacency matrix\n (a) Memory needed to record edges = %d\n", adjEffGraph.E() * Size.BOOLEAN);
        System.out.format(" (b) Total amount of memory used  = %d\n", adjEffGraph.sizeOfGraph());
        System.out.format(" (c) Efficiency  = %f\n", 100.0 * adjEffGraph.E() * Size.BOOLEAN / adjEffGraph.sizeOfGraph());
    }
    
}
