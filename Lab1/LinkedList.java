/* Henry Pratama Suryadi
A0105182R Lab 1 Submission*/
public class LinkedList {
    
    private int counter;
    private Node head;
    private int compareCount;
    
    // Default constructor
    public LinkedList() {
    }
    

    /**
     * search data in linked list
     * @param data the data
     * @return the index of data if found; -1 if not found
     */
    public int search(Object data) {
        compareCount = 0;
        if (head == null) {
            return 0;
        }

        Node sampleTemp = new Node(data);
        Node sampleCurrent = head;

        int index = 0;
            
        if (sampleCurrent != null) {
            if (sampleCurrent.getData().equals(sampleTemp.getData())) {            
                return index;
            }
            else {
                while (sampleCurrent.getNext() != null) {
                    compareCount++;
                    sampleCurrent = sampleCurrent.getNext();
                    if (sampleCurrent.getData().equals(sampleTemp.getData())) {
                        return index;
                    }   
                    ++index;
                 }
            }
        }
        
        return -1;
    }

    public int getTime() {
        return compareCount;
    }
    /**
     * appends the specified element to the end of this list.
     * @param data the data
     */
    public void add(Object data) {
	// Initialize Node only incase of 1st element
	if (head == null) {
            head = new Node(data);
	}
 
	Node sampleTemp = new Node(data);
	Node sampleCurrent = head;
 
	// Let's check for NPE before iterate over sampleCurrent
	if (sampleCurrent != null) {
            // starting at the head node, crawl to the end of the list and then add element after last node
            while (sampleCurrent.getNext() != null) {
		sampleCurrent = sampleCurrent.getNext();
            }
            // the last node's "next" reference set to our new node
            sampleCurrent.setNext(sampleTemp);
	}
	// increment the number of elements variable
	incrementCounter();
    }
 
    
    private int getCounter() {
	return counter;
    }
 
    private void incrementCounter() {
	counter++;
    }
 
    private void decrementCounter() {
	counter--;
    }
 
    
    /**
     * inserts the specified element at the specified position in this list
     * @param data the data
     * @param index the position
     */
    public void add(Object data, int index) {
	Node sampleTemp = new Node(data);
	Node sampleCurrent = head;
 
	// Let's check for NPE before iterate over sampleCurrent
	if (sampleCurrent != null) {
            // crawl to the requested index or the last element in the list, whichever comes first
            for (int i = 1; i < index && sampleCurrent.getNext() != null; i++) {
                sampleCurrent = sampleCurrent.getNext();
            }
	}
	// set the new node's next-node reference to this node's next-node reference
	sampleTemp.setNext(sampleCurrent.getNext());
	// now set this node's next-node reference to the new node
	sampleCurrent.setNext(sampleTemp);
	// increment the number of elements variable
	incrementCounter();
    }
 
    
    /**
     * get the element at the specified position in this list
     * @param index the index
     * @return the element
     */
    public Object get(int index)
    {
	// index must be 0 or higher
	if (index < 0)
            return null;
	Node sampleCurrent = null;
	if (head != null) {
            sampleCurrent = head.getNext();
            for (int i = 0; i < index; i++) {
		if (sampleCurrent.getNext() == null)
                    return null;
                sampleCurrent = sampleCurrent.getNext();
            }
            return sampleCurrent.getData();
        }
	return sampleCurrent;
    }
 
    
    /**
     * removes the element at the specified position in this list.
     * @param index the position
     * @return true if found and delete; false if not found
     */
    public boolean remove(int index) {
	// if the index is out of range, exit
	if (index < 1 || index > size())
            return false;
 
	Node sampleCurrent = head;
	if (head != null) {
            for (int i = 0; i < index; i++) {
		if (sampleCurrent.getNext() == null)
                    return false;
                sampleCurrent = sampleCurrent.getNext();
            }
            sampleCurrent.setNext(sampleCurrent.getNext().getNext());
            decrementCounter();
            return true;
	}
        
	return false;
    }
 
    
    /**
     * get the number of elements in this list
     * @return the number of elements in this list
     */
    public int size() {
	return getCounter();
    }
 

    @Override
    public String toString() {
	String output = "------- linked list ---------\n";
	if (head != null) {
            Node sampleCurrent = head.getNext();
            while (sampleCurrent != null) {
		output += "[" + sampleCurrent.getData().toString() + "]";
		sampleCurrent = sampleCurrent.getNext();
            }
	}
        output = output + "\n-----------------------------\n";
	return output;
    }
    
    
    private class Node {
	// reference to the next node in the chain, or null if there isn't one.
	private Node next;
	// data carried by this node. could be of any type you need.
	private Object data;
 
	// Node constructor
	public Node(Object dataValue) {
            next = null;
            data = dataValue;
	}
 
	// another Node constructor if we want to specify the node to point to.
	@SuppressWarnings("unused")
	public Node(Object dataValue, Node nextValue) {
            next = nextValue;
            data = dataValue;
	}
 
	// these methods should be self-explanatory
	public Object getData() {
            return data;
	}
 
	@SuppressWarnings("unused")
	public void setData(Object dataValue) {
            data = dataValue;
	}
 
	public Node getNext() {
            return next;
	}
 
	public void setNext(Node nextValue) {
            next = nextValue;
	}                                                    
    }
    
}
