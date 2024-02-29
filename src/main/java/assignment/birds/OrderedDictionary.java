package assignment.birds;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    /**
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/birds/DictionaryException.java
     */
    @Override
    public BirdRecord find(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {         
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current.getData();
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }

    }

    public Node findNode(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current;
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }

    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws birds.DictionaryException
     */
    @Override
    public void insert(BirdRecord r) throws DictionaryException {
        // Write this method
        Node current_node = this.root;
        Node prev_node = current_node;
        int choice = 0;

        if (!root.isEmpty()) {
            while (current_node != null) {
                // left
                if (r.getDataKey().compareTo(current_node.getData().getDataKey()) == -1) {
                    choice = -1;
                    prev_node = current_node;
                    current_node = current_node.getLeftChild();

                    // right
                } else if (r.getDataKey().compareTo(current_node.getData().getDataKey()) == 1) {
                    choice = 1;
                    prev_node = current_node;
                    current_node = current_node.getRightChild();
                } else {
                    throw new DictionaryException("Identical Key detected");
                }

            }
            System.out.println("depth");
            current_node = new Node(r, null, null);

            // adjust parents and children
            if (choice == -1) {
                prev_node.setLeftChild(current_node);
            } else if (choice == 1) {
                prev_node.setRightChild(current_node);
            }
            current_node.setParent(prev_node);
        } else {
          this.root.setData(r);
        }


    }

    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws birds.DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {
        // Write this method
    }

    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException{
        Node current;
        DataKey LastKey;
        current=findNode(k); //find node gets the node at key
        if( current.hasRightChild() ) //if that node has a right child that exists
        {
            current=current.getRightChild(); //go right once
            while(current.hasLeftChild()) { //go left til there is no more left
                current=current.getLeftChild();
            }
            return current.getData(); //this is the successor, return the data

        }
        else if( current.getData().getDataKey().compareTo(k) >= 0) //if the node has no right child determine if itself is a R or L child
        { //if in here, this means that current is a left child, so its successor is its parent
            return current.getParent().getData();
        }
        else if(current.getData().getDataKey().compareTo(k) <= 0)//if not then current is a right child
        {//since its a right child, go up to its parent, go back up the parents, the first node that is a right child, is the successor

            LastKey=current.getData().getDataKey();
            current=current.getParent();//go up one parent
            while(current.getData().getDataKey().compareTo(LastKey) <= 0) //while the next node is a right child
            {
                LastKey=current.getData().getDataKey();
                current=current.getParent();

            } //when we exit the while loop, it means that we have found a node, who is a left child, meaning its parent is its successor
            return current.getData();


        }
        else{throw new DictionaryException("No other successor");} //if we get here, it means there was no successor,
    }

   
    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k; it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord predecessor(DataKey k) throws DictionaryException{
        Node current;
        DataKey LastKey;
        current=findNode(k); //find node gets the node at key
        if( current.hasLeftChild() ) //if that node has a Left child that exists
        {
            current=current.getLeftChild(); //go Left once
            while(current.hasRightChild()) { //go right til there is no more right
                current=current.getRightChild();
            }
            return current.getData(); //this is the predacessor, return the data

        }
        else if( current.getData().getDataKey().compareTo(k) <= 0) //if the node has no left child determine if itself is a R or L child
        { //if in here, this means that current is a right child, so its predacessor is its parent
            return current.getParent().getData();
        }
        else if(current.getData().getDataKey().compareTo(k) >= 0)//if not then current is a left child
        {//since its a left child, go up to its parent, go back up the parents, the first node that is a left child, is the predacessor

            LastKey=current.getData().getDataKey();
            current=current.getParent();//go up one parent
            while(current.getData().getDataKey().compareTo(LastKey) >= 0) //while the next node is a right child
            {
                LastKey=current.getData().getDataKey();
                current=current.getParent();

            } //when we exit the while loop, it means that we have found a node, who is a left child, meaning its parent is its predacessor
            return current.getData();


        }
        else{throw new DictionaryException("No other predacessor");} //if we get here, it means there was no successor,
    }


    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public BirdRecord smallest() throws DictionaryException{
        Node current_node = root;

        while (current_node.getLeftChild() != null) {
            if (current_node.getLeftChild().getData().getDataKey().compareTo(current_node.getData().getDataKey()) == -1) {
                current_node = current_node.getLeftChild();
            } else {
                current_node = current_node.getRightChild();
            }
        }
        return current_node.getData(); // change this statement
    }

    /*
	 * Returns the record with largest key in the ordered dictionary. Returns
	 * null if the dictionary is empty.
     */
    @Override
    public BirdRecord largest() throws DictionaryException{
        Node current_node = root;

        while (current_node.getRightChild() != null) {
            current_node = current_node.getRightChild();
        }

        return current_node.getData();
    }
      
    /* Returns true if the dictionary is empty, and true otherwise. */
    @Override
    public boolean isEmpty (){
        return root.isEmpty();
    }
}
