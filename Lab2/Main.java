/* Henry Pratama Suryadi
A0105182R Lab 2 Submission*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		String name = "5";
		String dataName = "input" + name + ".txt";

		try {
			// Set the input file and read the input
			BufferedReader reader = new BufferedReader(new FileReader(dataName));
			String line = reader.readLine();
			// Parse the input, separate the numbers and commands
			line = line.substring(1, line.length()-1);
			String[] heapData = line.split(", ");
			
			// Create the k-select heap object
			KSelectHeap heapBuild = new KSelectHeap(Integer.parseInt(heapData[0]));

			int i = 1;
			while (i < heapData.length) {
				if (heapData[i].equals("+")) { // peek the top element in the heap
					System.out.println(heapBuild.peek() + " - peek");
				}
				else if (heapData[i].equals("-")) { // delete the top element in the heap
					System.out.println(heapBuild.delete() + " - delete");
				}
				else { // insert element into the heap
					heapBuild.insert(Integer.parseInt(heapData[i]));
				}
				i++;
			}
		}

		catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Unable to open file");
        }
        catch (IOException e) {
            System.out.println("IOException: Error reading file");
        }
	}
}