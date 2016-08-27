/** Assignment 3 - CS2010 
 * "Undirected Graphs"
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
 * Assigned: Feb 29 (Monday), 2016
 * Due: Mar 18 (Friday by 11.59pm), 2016
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class A3Main {
    
    public static void main(String[] args) {
    
        Scanner scanner = new Scanner(System.in);  // for reading from console
        
        /*
         * Your code: read and parse the file "countries_borders.dat"
        */
        // Initialise all required variables
        WorldGraph curWorld = new WorldGraph();
        ArrayList<String> inBorder = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("countries_borders.dat"));
            String line = br.readLine();

            // Add countries from input
            while (line != null) {
                String[] parseData = line.split(" >> ");
                curWorld.insertCountry(parseData[0]);
                inBorder.add(parseData[1]);
                line = br.readLine();
            }
            br.close();
            curWorld.initiateBlocked(); // Initialise blocks
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Unable to open file");
        } catch (IOException e) {
            System.out.println("IOException: Error reading file");
        }
        // Add the borders after all countries has been inserted
        for (int i = 0; i < inBorder.size(); i++) {
            curWorld.addBorder(i, inBorder.get(i));
        }

        boolean exit = false;
        String menuInfo = 
                "================ Assignment 3 ==================\n"
              + "1. List all countries and bordering countries \n"
              + "2. Find shortest path \n"
              + "3. List all countries with N neighbors \n"
              + "4. Block a country \n"
              + "5. Find countries with borders larger than X km \n"
              + "6. Show all connected components (largest to smallest) \n"
              + "7. Exit \n> ";
        
        while (!exit) {
            System.out.print(menuInfo);
            int menu = scanner.nextInt();
            System.out.println("================================================");
            
            switch (menu) {
                case 1:
                    // List all countries and bordering countries
                    System.out.println(curWorld.listCountries());
                    break;
                    
                case 2: // find shortest path
                    System.out.println(">>> 3.1(b) Find shortest path");
                    while (true) {
                        System.out.format(">>> Enter source and destination pair (or -1 to escape): ");
                        int source = scanner.nextInt();
                        if (source == -1) // -1 to escape
                            break;
                        int destination = scanner.nextInt();  // destination id
                        
                        /*
                         * Your code: find shortest path and print the result
                         * ** For requirement 3.6, you can compute and print 
                         * in this part
                        */
                        System.out.println(curWorld.shortestPath(source, destination));
                        
                    } // end while
                    System.out.println();
                    break;

                case 3:
                    System.out.println("\n>>> 3.2 List all countries with N neighbors");
                    while (true) {
                        System.out.print(">>> Enter N (or -1 to escape): ");
                        int n = scanner.nextInt();
                        if (n == -1) // -1 to escape
                            break;
                        
                        /*
                         * Your code: find countries with n neighbors and print
                         * the result
                        */
                        System.out.println(curWorld.listNumNeighbour(n));

                    } // end while
                    System.out.println();
                    break;

                case 4:
                    System.out.println("\n>>> 3.3 Block countries");
                    while (true) {
                        System.out.format(">>> Enter ID of the country you want to block (or -1 to escape): ");
                        int id = scanner.nextInt();
                        if (id == -1) // -1 to escape
                            break;
                        
                        /*
                         * Your code: 
                         * (1) you can find shortest path here OR
                         * (2) 
                         * list and finish this part by modifying your code in case 2
                         * You are allowed to jump to other part of switch branch
                         * to help you finish this part
                        */
                        curWorld.blockPath(id);
                        
                    } // end while
                    System.out.println();
                    break;

                case 5:
                    System.out.println("\n>>> 3.4 Find country with borders larger than X");
                    while (true) {
                        System.out.print(">>> Enter border length X (or -1 to escape): ");
                        double x = scanner.nextDouble();
                        if (x < 0) // -1 to escape
                            break;

                        /*
                         * Your code: find country with borders larger than X
                         * and print the result
                        */
                        System.out.println(curWorld.listLenBorder(x));
                        
                    } // end while
                    break;

                case 6:
                    System.out.println("\n>>> 3.5 Show all connected components");
                    
                    /*
                     * Your code: do connected components analysis and print the result
                    */
                    System.out.println(curWorld.connectedComponent());
                    
                    break;
                
                case 7:
                    // exit
                    exit = true;
                    System.out.println(">>> Exit");
                    break;
                
                default:
                    System.out.println("** Wrong command **");
                    break;

            } // end switch (menu)
            
        } // end while
        
    } // public static void main(String[] args)
    
} // class A3Main
