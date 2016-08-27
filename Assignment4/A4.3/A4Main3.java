/** Assignment 4 - CS2010 
 * "Fun with Graphs"
 * Part 3 - "Vertex Labeling Based on Distance"
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
import java.util.Collections;
import java.util.Scanner;

public class A4Main3 {
    /**
     * Label houses by nearest hospitals.
     */
    public static void main(String[] args) {
        // Please hardcode your filename here
        String filename = "sample.txt";
        
        // This G stores the information on the graph and may be used by other Classes in your algorithm
        // Start building the graph from text file
        String line;
        String[] parsed;
        HospitalMap city = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            line = br.readLine();
            city = new HospitalMap(Integer.parseInt(line));
            line = br.readLine();
            int inputEdge = Integer.parseInt(line);

            for(int i = 0; i < inputEdge; i++) {
                line = br.readLine();
                parsed = line.split(" ");
                city.addEdge(Integer.parseInt(parsed[0]), Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Now ask the user for input of hospital vertices
        // User are expected to input up to five valid non-repetitive Integers 
        ArrayList<Integer> hospitalID = new ArrayList<Integer>();
        ArrayList<Integer> houseID = new ArrayList<Integer>();
        System.out.printf("User Input: ");
        Scanner scanner = new Scanner(System.in);
        line = scanner.nextLine();
        parsed = line.split(" ");
        for (String temp : parsed) {
            hospitalID.add(Integer.parseInt(temp));
        }
        scanner.close();

        // Sort the hospital ID in ascending order
        // Get the index of all hospital ID and house ID
        Collections.sort(hospitalID);
        int temp = 0, index = 0;
        for (int i = 0; i < city.getVertices(); i++) {
            if (temp < hospitalID.size()) {
                index = hospitalID.get(temp);
            } else {
                index = -1;
            }

            if (i != index) {
                houseID.add(i);
            } else {
                temp++;
            }
        }
        
        // Run your program with User Input as hospitals and rest of vertices as houses
        // Code here for your main algorithm
        ArrayList<Integer>[] rep = city.computeDistance(hospitalID);
        
        // Print out hospital and houses number and indexes
        System.out.printf("There are %d hospital(s) and %d house(s).\n", hospitalID.size(), houseID.size());
        System.out.printf("Hospitals are: ");
        for (int i = 0; i < hospitalID.size(); i++) {
            if(i != 0) System.out.print(", ");
            System.out.print(hospitalID.get(i));
        }
        System.out.printf("\n");
        System.out.printf("Houses are: ");
        for (int i = 0; i < houseID.size(); i++) {
            if(i != 0) System.out.print(", ");
            System.out.print(houseID.get(i));
        }
        System.out.printf("\n\n");
        
        // Printing the result for each hospital
        int curHospital = 0;
        for (int i = 0; i < hospitalID.size(); i++) {
            curHospital = hospitalID.get(i);
            //First print out number of houses that visit that hospital
            System.out.printf("Following %d house(s) visit hospital %d:\n", rep[curHospital].size(), curHospital);

            for (int j = 0; j < rep[curHospital].size(); j++) {
                //Then for each house that visit this hospital, print out its index and shortest distance to the hospital
                System.out.printf("House %d visit with shortest distance %.4f\n", rep[curHospital].get(j), city.getDistance(rep[curHospital].get(j)));
            }
            System.out.printf("\n");
        }
    }
}