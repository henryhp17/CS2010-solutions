/* Henry Pratama Suryadi
A0105182R Lab 1 Submission*/
public class BST <K extends Comparable<K>, V> {
    
    private Node<K, V> root;
    private int nodesNum;
    private int compareCount;

    /**
     * Initializes an empty binary tree.
     */
    public BST() {
        root = new Node<K, V>();
        nodesNum = 0;
    }

    
    /**
     * Returns true if this tree is empty.
     * @return true if this tree is empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    
    /**
     * Returns the number of nodes in BST.
     * @return the number of nodes
     */
    public int size() {
        return nodesNum;
    }


    /**
     * Inserts <key, value> item where it belongs in the tree
     * @param key the key
     * @param value the value
     * @return true if item is inserted; false if it is already in the tree
     */
    public boolean add(K key, V value) {
        if (nodesNum == 0) {
            root.key = key;
            root.value = value;
            nodesNum++;
        }
        else {
            Node<K, V> nextNode = root;
            while (true) {
                if (key.compareTo(nextNode.key) < 0) {
                    if (nextNode.left == null) {
                        nextNode.left = new Node<K, V>(key, value);
                        nodesNum++;
                        break;
                    }  
                    else {
                        nextNode = nextNode.left;
                    }
                }
                else if (key.compareTo(nextNode.key) > 0) {
                    if (nextNode.right == null) {
                        nextNode.right = new Node<K, V>(key, value);
                        nodesNum++;
                        break;
                    }
                    else {
                        nextNode = nextNode.right;
                    } 
                }
                else {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    
    /**
     * find whether key is in this tree
     * @param key the target key
     * @return true if target is found in the tree
     */
    public boolean contains(K key) {
        return (find(key) != null);
    }
    
    
    /**
     * search key in this tree
     * @param key the target key
     * @return the value of key 
     */
    public V find(K key) {
        Node<K, V> nextNode = root;
        compareCount = 0;
        while (nextNode != null) {
            compareCount++;
            if (key.compareTo(nextNode.key) == 0)
                return nextNode.value;
            else if (key.compareTo(nextNode.key) < 0)
                nextNode = nextNode.left;
            else
                nextNode = nextNode.right;
        }
        return null;
    }
    
    public int getTime() {
        return compareCount;
    }
    
    private K delReturn;  // return value of function delete
    /**
     * remove the node of key from the tree
     * @param key the target key
     * @return the key which is deleted if found; otherwise null
     */
    public K delete(K key) {
        root = _delete(root, key);
        if (root != null)
            nodesNum--;
        return delReturn;
    }
    
    
    /**
     * remove the node of key from the tree
     * @param key the target key
     * @return true if key is found
     */
    public boolean remove(K key) {
        if (delete(key) != null) {
            return true;
        }
        else {
            return false;
        }
    }
    
    
    @Override
    public String toString() {
        String output = "------------ BST ------------\n";
        output = output + "parent: left child, right child\n";
        Node<K, V>[] queue;
        queue = new Node[10000];
        int head = 0, tail = 0;
        queue[tail] = root;
        tail++;
        while(head < tail) {
            output = output + queue[head].key + ": ";
            if (queue[head].left != null) {
                output = output + queue[head].left.key + ", ";
                queue[tail] = queue[head].left;
                tail++;
            }
            else {
                output = output + "null, ";
            }
            if (queue[head].right != null) {
                output = output + queue[head].right.key + "\n";
                queue[tail] = queue[head].right;
                tail++;
            }
            else {
                output = output + "null\n";
            }
            head++;
            
        }
        output = output + "-----------------------------\n";
        
        return output;
    }
    
    
    /**
     * recursive function called by function delete to remove target
     * @param localRoot the root of the subtree
     * @param key the node with key to be removed
     * @return the modified local root that does not contain the item
     */
    private Node<K, V> _delete(Node<K, V> localRoot, K key) {
        if (localRoot == null) {
            delReturn = null;
            return localRoot;
        }
        
        if (key.compareTo(localRoot.key) < 0) {
            localRoot.left = _delete(localRoot.left, key);
            return localRoot;
        }
        else if (key.compareTo(localRoot.key) > 0) {
            localRoot.right = _delete(localRoot.right, key);
            return localRoot;
        }
        else {
            // the target is at local root
            delReturn = localRoot.key;
            if (localRoot.left == null) {
                // If there is no left child, return right child
                return localRoot.right;
            }
            else if (localRoot.right == null) {
                // If there is no right child, return left child
                return localRoot.left;
            }
            else {
                // If there are two children, replace the target node with 
                // inorder predecessor(largest node smaller then target)
                if (localRoot.left.right == null) {
                    localRoot.left.right = localRoot.right;
                    return localRoot.left;
                }
                else {
                    Node curNode = localRoot.left.right;
                    Node parent = localRoot.left;
                    while (curNode.right != null) {
                        parent = curNode;
                        curNode = curNode.right;
                    }
                    parent.right = curNode.left;
                    curNode.left = localRoot.left;
                    curNode.right = localRoot.right;
                    return curNode;
                }
                
            } // end if localRoot
        } // end if key.compareTo
    }
    
    
    
    private class Node <K extends Comparable<K>, V> {
        public K key;
        public V value;
        public Node left, right;

        public Node(){
            left = null;
            right = null;
        }
        
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }
    
}
