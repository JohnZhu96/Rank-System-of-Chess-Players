/**
* This is a JUnit Test class that tests AVLPlayerNode class
* Known Bugs: "Node"
*
* @author Linfeng Zhu
* <linfengzhu@brandeis.edu>
* <11/13/2002>
* COSI 21A PA2
*/

package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import main.AVLPlayerNode;
import main.Player;

class AVLPlayerNode_Test {
	
	Player Tom = new Player("Tom", 1, 9);
	Player Mary = new Player("Mary", 2, 8);
	Player Judy = new Player("Judy", 3, 7);
	Player Joe = new Player("Joe", 4, 6);
	Player Jane = new Player("Jane", 5, 5);
	Player Fred = new Player("Fred", 6, 4);
	Player Dave = new Player("Dave", 7, 3);
	Player Bill = new Player("Bill", 8, 2);
	Player Alice = new Player("Alice", 9, 1);
	
	@Test
	//Tests when 2 more nodes are added
	void testInsert1() {	
		AVLPlayerNode root =new AVLPlayerNode(Tom,1);
		
		root.insert(Joe,4);
		root.insert(Jane,5);
		assertEquals("((Tom)Joe(Jane))",root.treeString());
		
	}
	
	@Test
	//Tests when 2 more nodes are added
	void testInsert2() {	
		AVLPlayerNode root =new AVLPlayerNode(Tom,1);
		
		root.insert(Joe,4);
		root.insert(Jane,5);

		
		root.insert(Mary,2);
		root.insert(Alice,9);
		assertEquals("((Tom(Mary))Joe(Jane(Alice)))",root.treeString());
		
	}

	
	@Test
	//Tests when 2 more nodes are added
	void testInsert3() {	
		AVLPlayerNode root =new AVLPlayerNode(Tom,1);
		
		root.insert(Jane,5);
		root.insert(Joe,4);
		root.insert(Mary,2);
		root.insert(Alice,9);
		
		root.insert(Judy,3);
		root.insert(Dave,7);
		assertEquals("(((Tom)Mary(Judy))Joe((Jane)Dave(Alice)))",root.treeString());
		
	}
	
	@Test
	//Tests when 2 more nodes are added
	void testInsert4() {	
		AVLPlayerNode root =new AVLPlayerNode(Tom,1);
		
		root.insert(Jane,5);
		root.insert(Joe,4);	
		root.insert(Mary,2);
		root.insert(Alice,9);
		root.insert(Judy,3);
		root.insert(Dave,7);
		
		root.insert(Bill,8);
		root.insert(Fred,6);
		assertEquals("(((Tom)Mary(Judy))Joe((Jane(Fred))Dave((Bill)Alice)))",root.treeString());
	}
	
	@Test
	//Tests the case when nodes are deleted 
	void testDelete1() {	
		AVLPlayerNode root =new AVLPlayerNode(Tom,1);
		
		root.insert(Jane,5);
		root.insert(Joe,4);	
		root.insert(Mary,2);
		root.insert(Alice,9);
		root.insert(Judy,3);
		root.insert(Dave,7);
		root.insert(Bill,8);
		root.insert(Fred,6);
		
		root = root.delete(7);
		assertEquals("(((Tom)Mary(Judy))Joe((Jane(Fred))Bill(Alice)))",root.treeString());
		
		root = root.delete(4);
		assertEquals("(((Tom)Mary(Judy))Jane((Fred)Bill(Alice)))",root.treeString());
		
		root = root.delete(2);
		root=root.delete(9);
		assertEquals("(((Tom)Judy)Jane((Fred)Bill))",root.treeString());
	}
	
	@Test
	//Tests the case when all nodes are deleted
	void testDelete2() {	
		AVLPlayerNode root =new AVLPlayerNode(Tom,1);
		
		root.insert(Jane,5);
		root.insert(Joe,4);	

		
		root = root.delete(1);
		root = root.delete(4);	
		root = root.delete(5);
		assertEquals(null,root);
	}
	
	@Test
	//Tests getRank method
	void testGetRank() {	
		AVLPlayerNode root =new AVLPlayerNode(Tom,1);
		root.insert(Jane,5);
		root.insert(Joe,4);	
		root.insert(Mary,2);
		root.insert(Alice,9);
		root.insert(Judy,3);
		root.insert(Dave,7);
		root.insert(Bill,8);
		root.insert(Fred,6);
		

		assertEquals(4,root.getRank(5));
		
		root.delete(6);
		assertEquals(3,root.getRank(5));
	}
	
	@Test
	//Tests the scoreBoard
	void testScoreBoard1() {	
		AVLPlayerNode root =new AVLPlayerNode(Tom,1);
		
		root.insert(Jane,5);
		root.insert(Joe,4);	
		root.insert(Mary,2);
		root.insert(Alice,9);

		assertEquals("NAME      ID  SCORE     \n"  
				+ "Alice      9 1.00\n"  
				 + "Jane       5 5.00\n"  
				+ "Joe        4 6.00\n"  
				+ "Mary       2 8.00\n"
				+ "Tom        1 9.00\n", root.scoreboard());
	}
}
