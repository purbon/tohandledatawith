package com.purbon.data.tree.bplus;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.purbon.data.tree.bplus.BPlusTree;
import com.purbon.data.tree.bplus.Node;

public class BPlusTreeInsertAndSearchTest {

	BPlusTree t;
	
	@Before
	public void setUp() throws Exception {
		t = new BPlusTree(4);
		t.insert(1,  "d1");
		t.insert(7,  "d3");
		t.insert(4,  "d2");
		t.insert(10, "d4");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInsertOneBucket() {
		assertEquals(4, t.getRootNode().size());
	}
	
	@Test
	public void testRoot() {
		assertEquals(true, t.getRootNode().isLeaf());
	}

	@Test
	public void testSearch() {
		Node n  = t.search(7);
		assertEquals((Integer)7,  n.keys().get(2));
	}
	
	@Test
	public void testInsertSecondBucket() {
		t.insert(17, "d5");
		t.insert(21, "d6");
		Node root = t.getRootNode();
		assertEquals(1, root.size());
	}

	@Test
	public void testSecondBucketSearch() {
		t.insert(17, "d5");
		t.insert(21, "d6");
		Node node = t.search(10);
		assertEquals((Integer)10, node.keys().get(1));
	}
	
	@Test
	public void testThirdBucketSearch() {
		t.insert(17, "d5");
		t.insert(21, "d6");
		t.insert(31, "d7");
		t.insert(25, "d8");
		Node root = t.getRootNode();
		assertEquals(2, root.size());
		int[]  pk   = new int[]{7,17};
		for(int i=0; i < pk.length; i++) {
			assertEquals((Integer)pk[i], root.keys().get(i));
		}
	}
	
	@Test
	public void testFourBucketSearch() {
		t.insert(17, "d5");
		t.insert(21, "d6");
		t.insert(31, "d7");
		t.insert(25, "d8");
		t.insert(19, "d9");
		t.insert(20, "d10");
		assertEquals((Integer)7, t.getRootNode().keys().get(0));
	}

	@Test
	public void testFourBucketSecondLevelSearch() {
		t.insert(17, "d5");
		t.insert(21, "d6");
		t.insert(31, "d7");
		t.insert(25, "d8");
		t.insert(19, "d9");
		t.insert(20, "d10");
		int[]  pk   = new int[]{2,2};
		for(int i=0; i < pk.length; i++) {
			assertEquals((Integer)pk[i], (Integer)((Node)t.getRootNode().pointers().get(i)).size());
		}

	}
}
