// Version Java 8
// Lab #1 - CS2010, Sem 2, AY15/16
// See Lab PDF for details on Lab requirements

/* Henry Pratama Suryadi
A0105182R Lab 1 Submission*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinkedList_BST {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List data = new ArrayList();
        List probeData = new ArrayList();
        List timeMiss = new ArrayList();
        List timeHit = new ArrayList();
        double listMiss, listHit, bst1Miss, bst1Hit, bst2Miss, bst2Hit;
        int numMiss, numHit;
        
        // read data from files
        String name = "10000"; // data name, you can change it to read different files
        String dataName = "data/" + name + ".dat";
        String probeDataName = "data/probe_"  + name + ".dat";
        String line;
        try {
            // read data
            System.out.println("Opening Data To Insert To List and BST " + dataName );
            BufferedReader reader = new BufferedReader(new FileReader(dataName));
            while ((line = reader.readLine()) != null) {
                int key = Integer.parseInt(line);
                data.add(key);
            }
            // read probing values
            System.out.println("Opening Probes To Search List and BST" + probeDataName);
            reader = new BufferedReader(new FileReader(probeDataName));
            while ((line = reader.readLine()) != null) {
                probeData.add(Integer.parseInt(line));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("FileNotFOundException: Unable to open file");
        }
        catch (IOException e) {
            System.out.println("IOException: Error reading file");
        }
        System.out.println("Data to enter    = " + data.size());
        System.out.println("Number of probes = " + probeData.size() + "\n");
        
        
        // generate linked list by insert every data point in the list
        System.out.println("Adding data to Sorted Linked List...");
        long tBuild1 = System.currentTimeMillis();
        LinkedList list = new LinkedList();
        for (Object element : data) {
            list.add((Integer)element);
        }
        long tBuild2 = System.currentTimeMillis();
        System.out.println("time of generating linked list = " + (double)(tBuild2 - tBuild1) / 1000.0 + "s");
        
        // build binary search tree by insert every data point without shuffling
        System.out.println("Adding data to Binary Search Tree...");
        tBuild1 = System.currentTimeMillis();
        BST <Integer, String> bst1 = new BST<Integer, String>();
        for (Object element : data) {
            bst1.add((Integer)element, "No Value"); //  key=element, value="No Value"
        }
        tBuild2 = System.currentTimeMillis();
        System.out.println("time of building tree          = " + (double)(tBuild2 - tBuild1) / 1000.0 + "s");
        
        // build binary search tree by insert every data point with shuffling
        /*
            You can do shuffling here for binary search tree
        */
        Collections.shuffle(data);
        System.out.println("Adding data (shuffled) to Binary Search Tree...");
        tBuild1 = System.currentTimeMillis();
        BST <Integer, String> bst2 = new BST<Integer, String>();
        for (Object element : data) {
            bst2.add((Integer)element, "No Value"); //  key=element, value="No Value"
        }
        tBuild2 = System.currentTimeMillis();
        System.out.println("time of building tree          = " + (double)(tBuild2 - tBuild1) / 1000.0 + "s\n");
        
        
        // probe values in linked list
        System.out.println("Probing Linked List...");
        long tProbe1 = System.currentTimeMillis();
        for (Object element : probeData) {
            int idx = list.search((Integer)element);
            if (idx != -1){
                timeHit.add(list.getTime());
            } else {
                timeMiss.add(list.getTime());
            }
        }
        long tProbe2 = System.currentTimeMillis();
        
        numHit = timeHit.size();
        numMiss = timeMiss.size();
        listHit = getAverage(timeHit);
        listMiss = getAverage(timeMiss);
        System.out.println("time of probing data of linked list                  = " 
                           + (double)(tProbe2 - tProbe1) / 1000.0 + "s");
        
        // probe values in BST without shuffling data
        timeHit = new ArrayList();
        timeMiss = new ArrayList();
        System.out.println("Probing BST without shuffling data...");
        tProbe1 = System.currentTimeMillis();
        for (Object element : probeData) {
            String value1 = bst1.find((Integer)element);
            if (value1 != null){
                timeHit.add(bst1.getTime());
            } else {
                timeMiss.add(bst1.getTime());
            }
        }
        tProbe2 = System.currentTimeMillis();
        
        bst1Hit = getAverage(timeHit);
        bst1Miss = getAverage(timeMiss);
        System.out.println("Time of probing data of BST (without shuffling data) = " + (double)(tProbe2 - tProbe1) / 1000.0 + "s");
        
        // probe values in BST
        timeHit = new ArrayList();
        timeMiss = new ArrayList();
        System.out.println("Probing BST with shuffling data...");
        tProbe1 = System.currentTimeMillis();
        for (Object element : probeData) {
            String value2 = bst2.find((Integer)element);
            if (value2 != null){
                timeHit.add(bst2.getTime());
            } else {
                timeMiss.add(bst2.getTime());
            }
        }
        tProbe2 = System.currentTimeMillis();

        bst2Hit = getAverage(timeHit);
        bst2Miss = getAverage(timeMiss);
        System.out.println("Time of probing data of BST (with shuffling data)    = " 
                           + (double)(tProbe2 - tProbe1) / 1000.0 + "s\n");
        
        
        // Statistics
        // To get statistical information, you need to add and change to the codes
        // of BST.java and LinkedList.java
        /*
            Print your result in the following codes
            Your LinkedList and BST should keep track of each time "compared" was called during the *probes*  - not when constructing the List/BST
        */

        System.out.println(numHit + "---" + numMiss);
        System.out.println("-----------------------------------------------");
        System.out.println("Elements Found (HIT) ");
        System.out.println("Linked list                 : Average comparison time = " + listHit);
        System.out.println("BST (without shuffling data): Average comparison time = " + bst1Hit);
        System.out.println("BST (with shuffling data)   : Average comparison time = " + bst2Hit);
        System.out.println("Elements Not Found (MISS) ");
        System.out.println("Linked list                 : Average comparison time = " + listMiss);
        System.out.println("BST (without shuffling data): Average comparison time = " + bst1Miss);
        System.out.println("BST (with shuffling data)   : Average comparison time = " + bst2Miss);
        System.out.println("Overall (HIT + MISS)");
        System.out.println("Linked list                 : Average comparison time = " + (listHit * numHit + listMiss * numMiss) / probeData.size());
        System.out.println("BST (without shuffling data): Average comparison time = " + (bst1Hit * numHit + bst1Miss * numMiss) / probeData.size());
        System.out.println("BST (with shuffling data)   : Average comparison time = " + (bst2Hit * numHit + bst2Miss * numMiss) / probeData.size());
        System.out.println("n      = " + data.size());
        System.out.println("log(n) = " + 
                           Math.ceil(Math.log(data.size()) / Math.log(2)));
        
    }

    public static double getAverage(List timeList)
    {
        int sum = 0;
        if(!timeList.isEmpty()) {
            for (Object element : timeList) {
                sum += (Integer)element;
                //System.out.print((int)element + " - ");
            }
            //System.out.print("\n");
            return sum * 1.0 / timeList.size();
        }
        return sum;
    }
}
