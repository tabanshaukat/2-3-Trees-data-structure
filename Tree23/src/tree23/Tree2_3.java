
package tree23;
public class Tree2_3 <S extends Comparable<S>> {
    private class node {
        private S leftKey, rightKey;
        private node leftChild,middleChild,rightChild;
        
        private node() {
            leftChild=middleChild=rightChild=null;
            leftKey=rightKey=null;}
        private node(S leftKey, S rightKey) {
            this.leftKey = leftKey;
            this.rightKey = rightKey;
            leftChild = middleChild= rightChild=null;    }
        private node(S leftKey, S rightKey, node leftChild, node midChild) {
            this.leftKey = leftKey;
            this.rightKey = rightKey;			
            this.leftChild = leftChild;
            this.middleChild = midChild;     }
        
        //Below are the setters and getters to access the private values of the class
            private void setLeftKey(S value) {this.leftKey = value;}
            private void setRightKey(S value) {this.rightKey = value;}

            private void setLeftChild(node child) {this.leftChild = child;}
            private void setMidChild(node child) {this.middleChild = child;}
            private void setRightChild(node child) {this.rightChild = child;}

            private S getLeftKey() {return leftKey;}
            private S getRightKey() {return rightKey;}

            private node getLeftChild() {return leftChild;}
            private node getMidChild() {return middleChild;}
            private node getRightChild() {return rightChild;}
            
        //to check if the node is leaf     
	private boolean isNodeLeaf() {return leftChild == null && middleChild == null && rightChild == null;}
        //to check whether the node has 2 children or not
	private boolean is2Node() {return rightKey == null; //returns true when right key is null
        }
        //to check whether the node has 3 children or not
        private boolean is3Node() {
		return rightKey != null; //returns true when right Key is not null
        }
        
        private S replaceWithMaxKey() { //replace with in order succcessor of the tree
                S maxKey;
                if(!isNodeLeaf()) { // Recursion, not on leaf node 
                        if(getRightKey() != null) maxKey = rightChild.replaceWithMaxKey(); // traversing to reach last element in right child
                        else maxKey = middleChild.replaceWithMaxKey();  // else, we traverse in middle child
                }
                else{	// Trivial case, we are on the leaf level of the tree
                        if(getRightKey()==null){
                                maxKey = getLeftKey();
                                setLeftKey(null);
                        }
                        else{
                                maxKey = getRightKey();
                                setRightKey(null);//no rebalance required
                        }
                }
                if(!isBalanced()) rebalance(); // rebalance if tree not balanced
                return maxKey;
        }//returns the maximum element in the tree
        private S replaceWithMinKey() {
                S minKey; 
                if(!isNodeLeaf()) { minKey = leftChild.replaceWithMinKey();} //traversing to reach leaf left child
                else { // Trivial case, we are on the leaf level of the tree
                        minKey = leftKey; 
                        leftKey = null;
                        if(rightKey != null) { // We traversed to left most child since there was right child
                                leftKey = rightKey;
                                rightKey = null;
                        }
                }
                if(!isBalanced())rebalance(); //rebalance
                return minKey;
        }//returns the minimum element in the tree
        
        private boolean isBalanced() {
            boolean isBalanced = false;
            if(isNodeLeaf())isBalanced = true;  // If we are at the leaf node(last level), it is well-balanced for sure
            // There are two cases: 2 Node or 3 Node
            else if(leftChild.getLeftKey()!= null && middleChild.getLeftKey() != null) {
                    if(rightKey != null) { // 3 Node/has 3 children
                        if(rightChild.getLeftKey() != null) 
                        {
                                isBalanced = true;
                        }
                    }
                    else 
                    {  // 2 Node/has 2 children
                        isBalanced = true;
                    }
            }
            return isBalanced;
    }//to check whether the tree is balanced or not
        private void rebalance() {
            while(!isBalanced()) {
                    if(getLeftChild().getLeftKey() == null) { // The unbalance is in the left child
                            // We put the left element of current node as the left element of the left child
                            getLeftChild().setLeftKey(getLeftKey());
                            // Now we replace the left element of the mid child as the left element of the current node
                            setLeftKey(getMidChild().getLeftKey());
                            // If a right element on the mid child exists, we shift it to the left
                            if(getMidChild().getRightKey() != null) {
                                    getMidChild().setLeftKey(getMidChild().getRightKey());
                                    getMidChild().setRightKey(null);
                            }
                            // Else, we let the mid child EMPTY, so the next iteration may solve this situation
                            // if not, the condition of the critical case
                            else {
                                    getMidChild().setLeftKey(null);
                            }
                    }
                    else if(getMidChild().getLeftKey() == null) { // The unbalance is in the right child
                            //BASE CASE, each node (child) of the deepest level have just one element (the right is empty)
                            // will have to rebalance from a higher level of the tree
                            if(getRightKey() == null) {
                                    if(getLeftChild().getLeftKey() != null && getLeftChild().getRightKey() == null && getMidChild().getLeftKey() == null) {
                                            setRightKey(getLeftKey());
                                            setLeftKey(getLeftChild().getLeftKey());
                                            //remove the current children
                                            setLeftChild(null); 
                                            setMidChild(null);
                                            setRightChild(null);
                                    }
                                    else {
                                            getMidChild().setLeftKey(getLeftKey());
                                            if(getLeftChild().getRightKey() == null) {
                                                    setLeftKey(getLeftChild().getLeftKey());
                                                    getLeftChild().setLeftKey(null);
                                            }
                                            else {
                                                    setLeftKey(getLeftChild().getRightKey());
                                                    getLeftChild().setRightKey(null);
                                            }
                                            if(getLeftChild().getLeftKey() == null && getMidChild().getLeftKey() == null) {
                                                    // The other but same case
                                                    setLeftChild(null); 
                                                    setMidChild(null);
                                                    setRightChild(null);
                                            }
                                    }
                            }
                            else {
                                    // We put the right element of the current node as the left element of the middle child
                                    getMidChild().setLeftKey(getRightKey());
                                    // We put the left element of the right child as the right element of the current node
                                    setRightKey(getRightChild().getLeftKey());
                                    // If the right child, where we have taken the last element has a right element, we move it
                                    // into the left of the same child
                                    if(getRightChild().getRightKey() != null) {
                                            getRightChild().setLeftKey(getRightChild().getRightKey());
                                            getRightChild().setRightKey(null);
                                    }
                                    else { // Else, we let the right child EMPTY
                                            getRightChild().setLeftKey(null);
                                    }
                            }
                            // Unbalance in the right child
                    } else if(getRightKey() != null && getRightChild().getLeftKey() == null) {
                            if(getMidChild().getRightKey() != null) { // (1)case
                                    getRightChild().setLeftKey(getRightKey());
                                    setRightKey(getMidChild().getRightKey());
                                    getMidChild().setRightKey(null);
                            }
                            else { // (2)case
                                    getMidChild().setRightKey(getRightKey());
                                    setRightKey(null);
                            }
                    }
            }			
    }
}
    
    private node root;
    private int elements;//repsents the number of values in the 2-3 tree
    private int isRootGreater = 1, isRootLesser = -1; //for the comparision of keys
    private boolean IsLastElementInsertedCorrectly;// to know if the last element has been added correctly or not
    
    public Tree2_3() {
            root=new node(); //a root node is created
            elements=0; //initially there is zero element in the tree
    }
    public int getSize(){return elements;}//returns the number of values in the tree currently
    public boolean isEmpty(){
            if(root==null) return true; //when tree is emepty
            if(root.getLeftKey()==null) return true; //when root's left key is empty
            return false; //when the tree is not empty
    }//to check if tree is empty or not
    public int getLevel() {
        int level=0;
        node tempNode = root;
        while(tempNode != null) 
        {
            tempNode = root.getLeftChild(); //traversing till the most left node child
            level++;//counting each levell
        }
        return level;
    }//returns the current height of the tree
    
    public boolean insert(S value) {
            IsLastElementInsertedCorrectly = false;
            this.elements++;
            //BASE CASE:when there is no root or no left Key in root
            if(root == null || root.getLeftKey() == null) { 
                    if(root == null) root = new node();
                    root.setLeftKey(value);
                    IsLastElementInsertedCorrectly = true; //Insertion Done Correctly
            }
            else //When root is not null neither the Left Key is null
            {
                    node newRoot = insertR(root, value); // calling of insertR for newRoot 
                    //Root assigned to NewRoot if not null
                    if(newRoot != null) root = newRoot;
            }
            //If insertion is not done correctly,size is decremented to readjust size
            if(!IsLastElementInsertedCorrectly) this.elements--; 
            return IsLastElementInsertedCorrectly; //and False is returned for incorrect insertion otherwise true
    }//helper function of the insertf
    private node insertR(node current, S element) {
            node newParent = null;
            // We aren't in the deepest level yet
            if(!current.isNodeLeaf()) {
                node childPushedUp = null;
                    if (current.leftKey.compareTo(element) == 0 || (current.is3Node() && current.rightKey.compareTo(element) == 0)) {
                            // Already exists
                    }
                    // The new element is smaller than the left element of the current node
                    else if (current.leftKey.compareTo(element) == isRootGreater) {
                            childPushedUp = insertR(current.leftChild, element);
                            // Case childPushedUp != null --> the element has been added on a 3-node (there were 2 values)
                            if (childPushedUp != null) { // A new node comes from the left branch
                                    // The new element, in this case, is always less than the current.leftKey
                                    if (current.is2Node()) {
                                            current.rightKey = current.leftKey;  // shift the current left element to the right
                                            current.leftKey = childPushedUp.leftKey;
                                            current.rightChild = current.middleChild;
                                            current.middleChild = childPushedUp.middleChild;
                                            current.leftChild = childPushedUp.leftChild;
                                    }
                                    else { // In this case we have a new split, so the current element in the left will go up
                                            // copy the right part of the subtree
                                            node rightSubtreeCopy = new node(current.rightKey, null, current.middleChild, current.rightChild);
                                            // Now we create the new "structure", pasting the right part
                                            newParent = new node(current.leftKey, null, childPushedUp, rightSubtreeCopy);
                                    }
                            }
                            // Case: the pushed up element is bigger than the left element and less than the right element
                    } else if (current.is2Node() || (current.is3Node() && current.rightKey.compareTo(element) == isRootGreater)) {
                            childPushedUp = insertR(current.middleChild, element);
                            if (childPushedUp != null) { // A new split
                                    // The right element is empty, so we can set the pushed up element in the left and the existing left element into the right
                                    if (current.is2Node()) {
                                            current.rightKey = childPushedUp.leftKey;
                                            current.rightChild = childPushedUp.middleChild;
                                            current.middleChild = childPushedUp.leftChild;
                                    }
                                    else { // Another case where we have to split again
                                            node left = new node(current.leftKey, null, current.leftChild, childPushedUp.leftChild);
                                            node mid = new node(current.rightKey, null, childPushedUp.middleChild, current.rightChild);
                                            newParent = new node(childPushedUp.leftKey, null, left, mid);
                                    }
                            }
                            // The new element is greater than the right element
                    } else if (current.is3Node() && current.rightKey.compareTo(element) == isRootLesser) {
                            childPushedUp = insertR(current.rightChild, element);
                            if (childPushedUp != null) { // Split and the right element is pushed up
                                    node leftCopy   = new node(current.leftKey, null, current.leftChild, current.middleChild);
                                    newParent       = new node(current.rightKey, null, leftCopy, childPushedUp);
                            }
                    }
            }
            else { // in the deepest level
                    IsLastElementInsertedCorrectly = true;
                    // when the element already exists
                    if (current.leftKey.compareTo(element) == 0 || (current.is3Node() && current.rightKey.compareTo(element) == 0)) {
                            IsLastElementInsertedCorrectly = false;
                    }
                    else if (current.is2Node()) { // a simple case: there is no right element
                            // if the current left element is bigger than the new one --> we shift the left element to the right
                            if (current.leftKey.compareTo(element) == isRootGreater) {
                                    current.rightKey    = current.leftKey;
                                    current.leftKey     = element;
                            }
                            // if the new element is bigger, we add it in the right key directly
                            else if (current.leftKey.compareTo(element) == isRootLesser) current.rightKey = element;
                    }
                    // Case 3-node: there are 2 values in the node and we want to add another one. We have to split the node
                    else newParent = split(current, element);
            }
            return newParent;
    }//Recursive function of the insert
    private node split(node current, S value){
        System.out.println("");
        System.out.println("splitting called for the element "+value);
    node newParent=null;
    //when the left key of the current node is greater than the values
    if (current.leftKey.compareTo(value) == isRootGreater){
        node left   = new node(value, null);
        node right  = new node(current.rightKey, null);
        newParent   = new node(current.leftKey, null, left, right);
    } 
    //when the left key of the current node is lesser than the values
    else if (current.leftKey.compareTo(value) == isRootLesser) {
        // The new element is greater than the current's left only
        if (current.rightKey.compareTo(value) == isRootGreater) {

            node left   = new node(current.leftKey, null);
            node right  = new node(current.rightKey, null);
            newParent   = new node(value, null, left, right);

        } else 
        { //when the values is the largest from both the right and left keys
            node left   = new node(current.leftKey, null);
            node right  = new node(value, null);
            newParent   = new node(current.rightKey, null, left, right);
        }
    }
    return newParent;
}
    
    public S search(S value) {
            return searchRec(root, value);
    }//helper function of the search
    private S searchRec(node current, S value) {
            S found = null;
            if(current != null){
                // Base case:When element is found
                if(current.leftKey != null && current.leftKey.equals(value)) found = current.leftKey;
                else {
                    // Second base case:the element to search equals the right key of the current node
                    if(current.rightKey != null && current.rightKey.equals(value)) 
                        found = current.rightKey;
                    else {
                            //Recursive cases
                            if(current.leftKey.compareTo(value) == isRootGreater) {
                                    found = searchRec(current.leftChild, value);
                            }
                            else if(current.rightChild == null || current.rightKey.compareTo(value) == isRootGreater) {
                                    found = searchRec(current.middleChild, value);
                            }
                            else if(current.rightKey.compareTo(value) == isRootLesser) {
                                    found = searchRec(current.rightChild, value);
                            }
                            else return null; //when the element not found returns null
                    }
                }
            }
        return found;
    }//Recursive function of the search
    
    public boolean delete(S value) {
        boolean deleted;
        // We decrease the number of values at the start, if the element is not deleted, we increase the values at the end
        this.elements--;
            deleted = deleteRec(root, value); // Immersion
            root.rebalance();
            if(root.getLeftKey() == null) root = null; // We have deleted the last element of the tree
    if(!deleted) this.elements++;
            return deleted;
    }//helper function of the delete
    private boolean deleteRec(node current, S value) {
            boolean deleted = true;
            // Trivial case, we are in the deepest level of the tree but we have not found the element (it does not exist)
            if(current == null) 
                deleted = false;
            else {
                    // Recursive case, we are still finding the element to delete
                    if(!current.getLeftKey().equals(value)) {
                            // If there is no element in the right (2 Node) or the element to delete is less than the right element
                            if(current.getRightKey() == null || current.getRightKey().compareTo(value) == isRootGreater) {
                                    // The left element is bigger than the element to delete, so we go through the left child
                                    if(current.getLeftKey().compareTo(value) == isRootGreater) {
                                            deleted = deleteRec(current.leftChild, value);
                                    }
                                    else { // If not, we go through the mid child
                                            deleted = deleteRec(current.middleChild, value);
                                    }
                            }
                            else {
                                    // If the element to delete does not equals the right element, we go through the right child
                                    if(!current.getRightKey().equals(value)) {
                                            deleted = deleteRec(current.rightChild, value);
                                    }
                                    else { // If not, we have found the element
                                            // Situation A, the element equals the right element of a leaf so we just have to delete it
                                            if(current.isNodeLeaf()) current.setRightKey(null);
                                            else { // We found the element but it is not in the leaf, this is the situation B
                                                    // We get the min element of the right branch, remove it from its current position and put it
                                                    // where we found the element to delete
                                                    S replacement = current.getRightChild().replaceWithMinKey();
                                                    current.setRightKey(replacement);
                                            }
                                    }
                            }
                    }
                    // The left element equals the element to delete
                    else {
                            if(current.isNodeLeaf()) { // Situation A
                                    // The left element, the element to remove, is replaced by the right element
                                    if(current.getRightKey() != null) { 
                                            current.setLeftKey(current.getRightKey());
                                            current.setRightKey(null);
                                    }
                                    else { // If there is no element in the right, a rebalance will be necessary
                                            current.setLeftKey(null); // We let the node empty
                                            // We warn on the bottom up that a node has been deleted (is empty) and a rebalance is necessary
                                            // in THAT level of the tree
                                            return true;
                                    }
                            }
                            else { // Situation B
                                    // We move the max element of the left branch where we have found the element
                                    S replacement = current.getLeftChild().replaceWithMaxKey();
                                    current.setLeftKey(replacement);
                            }
                    }
            }
            if(current != null && !current.isBalanced()) {
                    current.rebalance();  // The bottom level have to be rebalanced
            }
            else if(current != null && !current.isNodeLeaf()) {
                    boolean balanced = false;
                    while(!balanced) {
                            if(current.getRightChild() == null) {
                                    // Critical case of the situation B at the left child
                                    if(current.getLeftChild().isNodeLeaf() && !current.getMidChild().isNodeLeaf()) {
                                            S replacement = current.getMidChild().replaceWithMinKey();
                                            S readdition = current.getLeftKey();
                                            current.setLeftKey(replacement);
                                            insert(readdition);
                                            // Critical case of hte situation B at the right child
                                    } else if(!current.getLeftChild().isNodeLeaf() && current.getMidChild().isNodeLeaf()) {
                                            if(current.getRightKey() == null) {
                                                    S replacement = current.getLeftChild().replaceWithMaxKey();
                                                    S readdition = current.getLeftKey();
                                                    current.setLeftKey(replacement);
                                                    insert(readdition);
                                            }
                                    }
                            }
                            // It is important to note that we can't use the 'else' sentence because the situation could have changed in the if above
                            if(current.getRightChild() != null) {
                                    if(current.getMidChild().isNodeLeaf() && !current.getRightChild().isNodeLeaf()) {

                                            current.getRightChild().rebalance();
                                    }
                                    if(current.getMidChild().isNodeLeaf() && !current.getRightChild().isNodeLeaf()) {
                                            S replacement = current.getRightChild().replaceWithMinKey();
                                            S readdition = current.getRightKey();
                                            current.setRightKey(replacement);
                                            insert(readdition);
                                    }
                                    else balanced = true;
                            }
                            if(current.isBalanced()) balanced = true;
                    }
            }
            return deleted;
    }//Recursive function of the delete
    
    public void inOrderDisplay(){
            if(!isEmpty()){
                System.out.println("In Order Display of the 2-3 Tree:");
                inOrderRec(root);    
            }
            else System.out.println("The tree is empty.");
    }//helper funtion of the in-Order Traversal
    private void inOrderRec(node current) {
            if(current != null) {
                    //when the current node is at the leaf
                    if(current.isNodeLeaf()){
                            //Displays the left key
                            System.out.print(current.getLeftKey().toString()+" ");
                            //if right key exists then displays it too
                            if(current.getRightKey() != null) System.out.print(current.getRightKey().toString()+" ");
                    }
                    else {//if the current node is not at leaf
                            //traverse to the left child
                            inOrderRec(current.getLeftChild());
                            //Displays the left Key of current after recursive callings
                            System.out.print(current.getLeftKey().toString()+" ");
                            //recursive call for the Middle Child
                            inOrderRec(current.getMidChild());
                            //if right key exists
                            if(current.getRightKey() != null) {
                                   //when  node is not at leaf,Displays the right key
                                    if(!current.isNodeLeaf()) 
                                        System.out.print(current.getRightKey().toString()+" ");
                                    inOrderRec(current.getRightChild());
                            }
                    }
            }
    }//Recursive function of the in-Order Traversal
   
    public void preOrderDisplay() {
    if (!isEmpty()){
        System.out.println("Pre Order Display of the 2-3 Tree:");
        preOrderRec(root);
    }
    else System.out.println("The tree is empty.");
}//helper funtion of the Pre-Order Traversal
    private void preOrderRec(node current) {
        if(current != null) {
            //Displays the Left Key of the current node
            System.out.print(current.leftKey.toString()+" ");
            preOrderRec(current.leftChild);
            preOrderRec(current.middleChild);
            //When right key also exists
            if (current.rightKey != null) {
                //Displays the right Key of the current node
                System.out.print(current.rightKey.toString()+" ");
                preOrderRec(current.rightChild);
            }
        }
}//Recursive function of the Pre-Order Traversal
}
