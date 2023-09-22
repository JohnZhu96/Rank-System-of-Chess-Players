package main;

/**
 * This class is the AVL Tree Node class which includes method for an AVL tree. This will be used to create 
 * AVL Tree Nodes for rankings of IDs and ELOs. 
 * Known Bugs: None 
 * 
 * @author Linfeng Zhu
 * <linfengzhu@brandeis.edu>
 * <11/7/2022>
 * COSI 21A PA2
 */

public class AVLPlayerNode{
    private Player data;
    private double value;
    private AVLPlayerNode parent;
    private AVLPlayerNode leftChild;
    private AVLPlayerNode rightChild;
    private int rightWeight;
    private int balanceFactor;
    
    private int height;
    private AVLPlayerNode root;
    
    /**
     * Constructs an AVLPlayerNode with two parameters Player and double
     * @param data  The Player that is stored in this node	
     * @param value The value that the node is sorted based on 
     */
    public AVLPlayerNode(Player data,double value){
        root = this;
    	this.data = data;
        this.value=value;
    }
    
    /**
     * This method will insert a node in the AVL Tree
     * @param newGuy The New Player that is inserted
     * @param value  The value that the node is sorted based on 
     * @return
     * Running Time: O(logn)
     */
    //This should return the new root of the tree
    //make sure to update the balance factor and right weight
    //and use rotations to maintain AVL condition
    public AVLPlayerNode insert(Player newGuy,double value){
    	AVLPlayerNode newNode = new AVLPlayerNode(newGuy,value);
    	if (root == null) {
    		root=newNode;
    		return root;
    	}else {
    		insert_helper(newNode,root);
    		AVLPlayerNode temp = newNode;
    		while(temp.parent!=null) {
    			if(temp.balanceFactor>1 || temp.balanceFactor<-1) {
    				temp.reBalance();
    			}
    			temp=temp.parent;
    		}
    		if(balanceFactor>1 || balanceFactor<-1) {
    			temp.reBalance();
    		}
    	}
    	return root;
    }
    
    /**
     * This is a helper method that is used in insert method above
     * @param curr  The current Tree Node that is waiting to be inserted
     * @param v 	The Tree Node that curr is inserted into
     * Running Time: O(logn)
     */
    private void insert_helper(AVLPlayerNode newNode, AVLPlayerNode v) {
    	if( newNode.value<v.value && v.leftChild ==null ) {
    		v.leftChild = newNode;
    		newNode.parent = v;
    		newNode.root = v.root;
    		newNode.height = calHeight(newNode);
    		v.height = calHeight(v);
    		root.updateBalanceFactor();
    		newNode.rightWeight = newNode.calRightWeight();
    		v.rightWeight = v.calRightWeight();
    	}else if(newNode.value>v.value && v.rightChild==null  ) {
    		v.rightChild=newNode;
    		newNode.parent = v;
    		newNode.root=v.root;
    		newNode.height = calHeight(newNode);
    		v.height = calHeight(v);
    		root.updateBalanceFactor();
    		newNode.rightWeight = newNode.calRightWeight();
    		v.rightWeight = v.calRightWeight();
    	}else if(newNode.value<v.value) {
    		insert_helper(newNode,v.leftChild);
    	}else if (newNode.value>v.value){
    		insert_helper(newNode,v.rightChild);
    	}
    }
    
    /**
     * This method will balance the tree by using rotations
     * Running Time: O(logn)
     */
    private void reBalance() {
    	if(balanceFactor<-1) {
    		if(rightChild.balanceFactor<=0) {
    			this.rotateLeft();
    		}else {
    			rightChild.rotateRight();
    			this.rotateLeft();
    		}
    	}else if(balanceFactor>1){
    		if(leftChild.balanceFactor>=0) {
    			this.rotateRight();
    		}else {
    			leftChild.rotateLeft();
    			this.rotateRight();
    		}
    	}
    }
    
    /**
     * This method calculates a node's height
     * @param v
     * Running Time: O(logn)
     */
    private int calHeight(AVLPlayerNode v){
    	if(v==null) {
    		return -1;
    	}else {
    		return Math.max(calHeight(v.rightChild), calHeight(v.leftChild))+1;
    	}
    }
    
    /**
     * This method will update a Three Node's balance factor and return it
     * @return Balance Factor of a Tree Node
     * Running Time: O(logn)
     */
    private int updateBalanceFactor(){
    	if(leftChild==null && rightChild ==null) {
    		balanceFactor =0;
    		return 0;
    	}else if(leftChild==null) {
    		balanceFactor = -1-calHeight(rightChild);
    		return balanceFactor;
    	}else if(rightChild==null) {
    		balanceFactor = calHeight(leftChild)+1;
    		return balanceFactor;
    	}else {
    		balanceFactor=calHeight(leftChild) - calHeight(rightChild);
    		leftChild.updateBalanceFactor();
    		rightChild.updateBalanceFactor();
    		return balanceFactor;
    	}
    }
    
    /**
     * This method will calculate the right weight of a Tree Node
     * @return
     * Running Time: O(logn)
     */
    private int calRightWeight(){
    	if(rightChild==null) {
    		rightWeight=0;
    		return 0;
    	}else {
    		rightWeight = rightChild.calRightWeight()+1;
    		return rightWeight;
    	}
    }

    
    /**
     * This method returns the new root of tree after deletion
     * @param value  The value stored in the Tree Node
     * @return  New root after deletion
     * Running Time: O(logn)
     */ 
    //This should return the new root of the tree
    //remember to update the right weight
    public AVLPlayerNode delete(double value){
    	//Extra Credit: use rotations to maintain the AVL condition
    	AVLPlayerNode deleteNode=root.findNode(value);
    	if(deleteNode==null) {
    		return root;
    	}
    	AVLPlayerNode x;
    	AVLPlayerNode y;

    	if(deleteNode.leftChild==null||deleteNode.rightChild==null) {
    		y=deleteNode;
    	}else {
    		y=successor(deleteNode);
    	}
    	
    	if(y.leftChild!=null) {
    		x=y.leftChild;
    	}else {
    		x=y.rightChild;
    	}
    	
    	if(x!=null) {
    		x.parent=y.parent;
    	}
    	
    	if(y.parent==null) {
    		root=x;
    		if(root!=null) {
    			root.updateRoot(root);
    		}	
    	}else {
    		if(y==y.parent.leftChild) {
    			y.parent.leftChild=x;
    		}else {
    			y.parent.rightChild=x;
    		}
    	}
    	
    	if(y!=deleteNode) {
    		deleteNode.data=y.data;
    		deleteNode.value=y.value;
    	}
    	
    	if(root==null) {
    		this.value=0;
    		this.data=null;
    	}
    	
    	if(root!=null) {
    		root.updateBalanceFactor();
        	root.updateRightWeight();
    	}
    	balanceDelete();
    	
    	return root;
    }
    
    /**
     * This is a helper method of getRank that finds the Tree Node with certain value
     * @param value The target value wanted to be found in a Tree Node
     * @return The Tree Node that stores the target value
     * Running Time: O(logn)
     */
    private AVLPlayerNode findNode(double value) {
    	if(data==null) {
    		return null;
    	}
    	if(this.value==value) {
    		return this;
    	}else {
    		if(leftChild!=null&&this.value>value) {
    			return leftChild.findNode(value);
    		}else if(rightChild!=null&&this.value<value){
    			return rightChild.findNode(value);
    		}else {
    			return null;
    		}
    	}
    }
    
    /**
     * This is a helper method that balances the Tree after deletion was made
     * Running Time: O(logn)
     */
    private void balanceDelete() {
    	if(this.root==null) {
    		return;
    	}
    	AVLPlayerNode maxNode=getMax(root);
    	AVLPlayerNode minNode=getMin(root);
    	while(minNode.parent!=null) {
			if(minNode.balanceFactor>1 ||minNode.balanceFactor<-1) {
				minNode.reBalance();
			}
			minNode=minNode.parent;
		}
    	while(maxNode.parent!=null) {
			if(maxNode.balanceFactor>1 ||maxNode.balanceFactor<-1) {
				maxNode.reBalance();
			}
			maxNode=maxNode.parent;
		}
    	if(maxNode.balanceFactor>1 || maxNode.balanceFactor<-1) {
			maxNode.reBalance();
		}
    	
    }
    
    /**
     * This method gets the Tree Node with minimum value
     * @param   The root of the Tree
     * @return The node in the Tree with minimum value
     * Running Time: O(logn)
     */
    public AVLPlayerNode getMin(AVLPlayerNode node) {
    	while(node.leftChild!=null) {
    		node=node.leftChild;
    	}
    	return node;
    }
    
    /**
     * This method gets the node with maximum value
     * @param b the root of a tree 
     * @return the node in the tree with maximum value
     * Running Time: O(logn)
     */
    public AVLPlayerNode getMax(AVLPlayerNode node) {
    	while(node.rightChild!=null) {
    		node=node.rightChild;
    	}
    	return node;
    }
    
    /**
     * This method finds the successor of a Tree Node
     * @param v A Tree Node
     * @return The successor of the Tree Node
     * Running Time: O(logn)
     */
    private AVLPlayerNode successor(AVLPlayerNode node) {
    	if(node.rightChild!=null) {
    		return getMin(node.rightChild);
    	}else {
    		AVLPlayerNode temp=node.parent;
    		while(temp!=null&&temp==node.rightChild) {
    			node=temp;
    			temp=temp.parent;
    		}
    		return temp;
    	}
    }
    
    /**
     * This method will rotate a Tree Node to the right
     * Running Time: O(logn)
     */
    //remember to maintain rightWeight
    private void rotateRight(){
    	AVLPlayerNode y = leftChild;
    	leftChild = y.rightChild;
    	if(y.rightChild!=null) {
    		y.rightChild.parent=this;
    	}
    	y.parent = this.parent;
    	if(this.parent==null) {
    		root=y;
    		if(root!=null) {
    			root.updateRoot(root);
    		}
    	}else if(this==this.parent.rightChild){
    		this.parent.rightChild=y;
    	}else {
    		this.parent.leftChild=y;
    	}
    	y.rightChild=this;
    	this.parent=y;
    	//updating the balanceFactor and right Weight of every Tree Node
    	root.updateBalanceFactor();
    	root.updateRightWeight();
    }

    /**
     * This method will update the root field for all nodes in the Tree
     * Running Time: O(logn)
     */
    private void updateRoot(AVLPlayerNode root) {
    	if(this!=null) {
    		this.root = root;
    	}
    	if(this.leftChild!=null) {
    		leftChild.updateRoot(root);
    	}
    	if(this.rightChild!=null) {
    		rightChild.updateRoot(root);
    	}
    }
    
    /**
     * This method will update rightWeight for all Nodes in the Tree
     * Running Time: O(logn)
     */
    private void updateRightWeight() {
    	rightWeight = this.calRightWeight();
    	if(leftChild!=null) {
    		leftChild.updateRightWeight();
    	}
    	if(rightChild!=null) {
    		rightChild.updateRightWeight();
    	}
    }
    
    /**
     * This method rotates a Tree Node to Left 
     * Running Time: O(logn)
     */
    //remember to maintain rightWeight
    private void rotateLeft(){
    	AVLPlayerNode y=rightChild;
    	rightChild=y.leftChild;
    	if(y.leftChild!=null) {
    		y.leftChild.parent=this;
    	}
    	y.parent=parent;
    	if(parent==null) {
    		root=y;
    		if(root!=null) {
    			root.updateRoot(root);
    		}
    	}else if(this==parent.leftChild) {
    		parent.leftChild=y;
    	}else {
    		parent.rightChild=y;
    	}
    	y.leftChild=this;
    	parent=y;
    	root.updateBalanceFactor();
    	root.updateRightWeight();
    }
	
    /**
     * This method returns the Player object stored in the node
     * @param value  The value that need to be found in Player objects
     * @return  The Player which stores the target value
     * Running Time: O(logn)
     */
    //this should return the Player object stored in the node with this.value == value
    public Player getPlayer(double value){
    	return root.getPlayer_helper(value);
    }
    
    /**
     * This is a helper method of getPlayer
     * @param value The value of the node want to be found
     * @return The player which stores the target value in its field
     * Running Time: O(logn)
     */
    private Player getPlayer_helper(double value) {
    	if(data==null) {
    		return null;
    	}
    	if(this.value==value) {
    		return data;
    	}else {
    		if(leftChild!=null&&this.value>value) {
    			return leftChild.getPlayer_helper(value);
    		}else if(rightChild!=null&&this.value<value){
    			return rightChild.getPlayer_helper(value);
    		}else {
    			return null;
    		}
    	}
    }
    
    /**
     * This method returns the rank of the Tree Node
     * @param value The value stored in the Node
     * @return The rank of the Tree Node
     * Running Time: O(logn)
     */
    //this should return the rank of the node with this.value == value
    public int getRank(double value){
    	AVLPlayerNode temp=root.findNode(value);
    	if(temp==null) {
    		return 0;
    	}
        if(value>root.value) {
        	return temp.getRightRank(temp.value);
        }else if(value<root.value){
        	return root.getRightRank(temp.value)+temp.getLeftRank(temp.value);
        }else {
        	return root.getRightRank(temp.value);
        }
    }


    
    /**
     * This method finds the rank of a Tree Node in left subtree
     * @param value The value stored in the Tree Node
     * @return The rank of the Tree Node in the left subtree of root which stores the target value
     * Running Time: O(logn)
     */
    private int getLeftRank(double value) {
    	if(this==root) {
    		return 0;
    	}
    	if(rightChild==null&&leftChild==null) {
    		return 0;
    	}else {
    		if(rightChild!=null&&rightChild.value>this.value) {
    			return rightChild.getRightRank(value)+this.parent.getLeftRank(value) +1;
    		}else{
    			return parent.getLeftRank(value) +1;
    		}
    	}
    }
    
    /**
     * This method finds the rank of a Tree Node in right subtree
     * @param value The value stored in the Tree Node
     * @return The rank of the Tree Node in the right subtree of root which stores the target value
     * Running Time: O(log n)
     */
    private int getRightRank(double value) {
    	if(this.value==value&&parent!=null&&value>root.value&&value>parent.value&&rightChild==null&&leftChild==null) {
    		return 1;
    	}
    	if(this.value>value&&leftChild==null&&rightChild==null) {
    		return 1;
    	}else {
    		if(this.value<parent.value &&rightChild!=null&&this!=root&&parent!=root) {
    			return 1+rightChild.getRightRank(value)+this.parent.getRightRank(value);
    		}else {
    			int temp=0;
    			if(leftChild!=null&&leftChild.value>value) {
    				temp+=leftChild.getLeftRank(value)+1;
    			}
    			if(rightChild!=null) {
    				temp+=rightChild.getRightRank(value)+1;
    			}
    			if(parent!=null&&parent.value>this.value) {
    				temp+=parent.getRightRank(value)+1;
    			}
    			return temp;
    		}

    	}
    }
    
    /**
     * This method will return a Tree of names with parentheses separating subtrees
     * @return A Tree of names with parentheses separating subtrees
     * Running Time： O（n）
     */
    //this should return the tree of names with parentheses separating subtrees
    //eg "((bob)alice(bill))"
    public String treeString(){
    	if(root!=null) {
    		return root.treeStringHelper();
    	}else {
    		return null;
    	}
    }

    /**
     * This is a helper method of treeString() that uses recursion
     * Running Time: O(n)
     * @return
     */
    private String treeStringHelper() {
    	if(leftChild==null && rightChild==null) {
    		return "(" + data.getName() +")";
    	}else if(rightChild==null) {
    		return "(" +leftChild.treeStringHelper() + data.getName()+")";
    	}else if (leftChild==null) {
    		return "(" + data.getName()+rightChild.treeStringHelper() + ")";
    	}else {
    		return "(" + leftChild.treeStringHelper() + data.getName() +rightChild.treeStringHelper()+")";
    	}
    } 
    
    /**
     * This method returns a formatted scoreboard in descending order of value
     * @return A formatted scoreboard in descending order of value
     * Running Time O(n)
     */
    //this should return a formatted scoreboard in descending order of value
    //see example printout in the pdf for the command L
    public String scoreboard(){
    	String result="";
    	if(root==null) {
    		result=String.format("%-10s", "NAME")+String.format("%-4s", "ID")+String.format("%-10s", "SCORE")+"\n";
    		return result;
    	}else {
    		result=root.visit(result);
    		result=String.format("%-10s", "NAME")+String.format("%-4s", "ID")+String.format("%-10s", "SCORE")+"\n"+result;
    		return result;
    	}
    }
    
    /**
     * This is a helper method of scoreBoard to format a score board
     * @param result a string which already contains some information
     * @return a string with the current node information
     * Running Time: O(n)
     */
    private String visit(String result) {
    	if(rightChild==null&&leftChild==null) {
    		result=result+String.format("%-11s", this.data.getName())+String.format("%-2s", this.data.getID())+String.format("%.2f", this.data.getELO())+"\n";
    		return result;
    	}
    	else {
    		if(rightChild!=null) {
        		result=rightChild.visit(result);
        	}
        	result+=String.format("%-11s", this.data.getName())+String.format("%-2s", this.data.getID())+String.format("%.2f", this.data.getELO())+"\n";
        	if(leftChild!=null) {
        		result=leftChild.visit(result);
        	}
        	return result;
    	}
    }
}
    
	
