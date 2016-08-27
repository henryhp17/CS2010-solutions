/** Lab 4 - CS2010 
 * Empirical Complexity Analysis of Minimum Spanning Tree: Kruskal vs Prim!
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Henry Pratama Suryadi
 * YOUR MATRICULATION #: A0105182R
 * 
 * COMMENTS:
 * You are not allowed to change any existing codes for reading and writing. You
 * may add your own reading code or printing code to print your results of each part.
 * 
 * 
 * Assigned: Mar 23 (Wednesday), 2016
 * Due: Apr 4 (Monday by 11.59pm), 2016
 */

import java.util.Scanner;

public class Lab4Main {

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
        
        /*
            Read the file and build the graph here
        */
        In inFile = new In(filename);
        EdgeWeightedGraph graph = new EdgeWeightedGraph(inFile);

        System.out.format("\nNumber of Vertices = %d\n", graph.V());
        System.out.format("Number of Edges = %d\n\n", graph.E());
        
        
        long t1 = System.currentTimeMillis();
        /*
            Use Kruskal's Algorithm to compute MST
        */
        KruskalMST kruskalMST = new KruskalMST(graph);
        long t2 = System.currentTimeMillis();
        /*
            Please get the number of times edges are compared in the priority queue
            and the number of iterations in the find operation of KruskalMST here
        */
        int num_of_comp_edge_Kruskal = kruskalMST.getHeapComp();
        int num_of_iter_find_Kruskal = kruskalMST.getUnionFind();
        int total_Kruskal = num_of_comp_edge_Kruskal + num_of_iter_find_Kruskal;
        System.out.println("Compute MST by Kruskal Algorithm");
        System.out.format("   Heap edge comp + UF find = %d + %d = %d\n", 
                          num_of_comp_edge_Kruskal, num_of_iter_find_Kruskal, total_Kruskal);
        System.out.format("   Time of Computing MST by Kruskal Algorithm = %d ms\n", t2 - t1);
        
        
        t1 = System.currentTimeMillis();
        /*
            Use Lazy Prim's Algorithm to compute MST
        */
        LazyPrimMST lazyPrimMST = new LazyPrimMST(graph);
        t2 = System.currentTimeMillis();
        /*
            Please get the number of times edges are compared in the priority queue
            and the number of iterations in the scan operation of LazyPrimMST here
        */
        int num_of_comp_edge_Prim = lazyPrimMST.getHeapComp();
        int num_of_iter_find_Prim = lazyPrimMST.getScan();
        int total_Prim = num_of_comp_edge_Prim + num_of_iter_find_Prim;
        System.out.println("Compute MST by Lazy Prim Algorithm");
        System.out.format("   Heap edge comp + Scan = %d + %d = %d\n", 
                          num_of_comp_edge_Prim, num_of_iter_find_Prim, total_Prim);
        System.out.format("   Time of Computing MST by Lazy Prim Algorithm = %d ms\n", t2 - t1);
        
        
        t1 = System.currentTimeMillis();
        /*
            Use improved Lazy Prim's Algorithm to compute MST
        */
        LazyPrimMSTImproved lazyPrimMSTImproved = new LazyPrimMSTImproved(graph);
        t2 = System.currentTimeMillis();
        /*
            Please get the number of times edges are compared in the priority queue
            and the number of iterations in the scan operation of LazyPrimMST here
        */
        int num_of_comp_edge_improved_Prim = lazyPrimMSTImproved.getHeapComp();
        int num_of_iter_find_improved_Prim = lazyPrimMSTImproved.getScan();
        int total_improved_Prim = num_of_comp_edge_improved_Prim + num_of_iter_find_improved_Prim;
        System.out.println("Compute MST by improved Lazy Prim Algorithm");
        System.out.format("   Heap edge comp + Scan = %d + %d = %d\n", 
                          num_of_comp_edge_improved_Prim, num_of_iter_find_improved_Prim, total_improved_Prim);
        System.out.format("   Time of Computing MST by improved Lazy Prim Algorithm = %d ms\n", t2 - t1);
        
    } // end main
    
} // end Lab4Main
