package com.purbon.data.tree.bplus;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BPlusTreeDeleteTest {
	
	BPlusTree t;
	@Before
	public void setUp() throws Exception {
		t = new BPlusTree(4);
		t.setRoot(buildTree());
		t.setDeep(2);
		t.insert(28, "d28");
		t.insert(70, "d70");
		t.insert(95, "d95");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testDeleteFromBucket() {
		t.delete(70);
		Node r = t.getRootNode();
		assertEquals((Integer)2,(Integer)r.pointers().size());
		
		Assert.assertArrayEquals(new Object[]{25,50}, ((Node)r.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{75,85}, ((Node)r.pointers().get(1)).keys().toArray());
		Node child = ((Node)r.pointers().get(0));
		assertEquals((Integer)3,(Integer)child.pointers().size());
		assertEquals(false, child.isLeaf());
		Assert.assertArrayEquals(new Object[]{5,10,15,20}, ((Node)child.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{25,28,30},   ((Node)child.pointers().get(1)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{50,55},      ((Node)child.pointers().get(2)).keys().toArray());
		child = ((Node)r.pointers().get(1));
		assertEquals((Integer)3,(Integer)child.pointers().size());
		assertEquals(false, child.isLeaf());
		Assert.assertArrayEquals(new Object[]{60,65}, ((Node)child.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{75,80},   ((Node)child.pointers().get(1)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{85,90,95},      ((Node)child.pointers().get(2)).keys().toArray());
	}

	@Test
	public final void testDeleteWithRelocation() {
		t.delete(25);
		Node r = t.getRootNode();
		assertEquals((Integer)2,(Integer)r.pointers().size());
		Assert.assertArrayEquals(new Object[]{28,50}, ((Node)r.pointers().get(0)).keys().toArray());
		Node child = ((Node)r.pointers().get(0));
		assertEquals((Integer)3,(Integer)child.pointers().size());
		Assert.assertArrayEquals(new Object[]{5,10,15,20},((Node)child.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{28,30},     ((Node)child.pointers().get(1)).keys().toArray());
	}
	
	
	@Test
	public final void testDeleteWithSiblingRelocation() {
		t = new BPlusTree(4);
		t.setRoot(buildAnotherTree());
		t.setDeep(3);
		t.delete(13);
		
		Node r = t.getRootNode();
		
		assertEquals((Integer)1,(Integer)r.keys().size());
		assertEquals((Integer)2,(Integer)r.pointers().size());
		
		Assert.assertArrayEquals(new Object[]{9,11},((Node)r.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{20},  ((Node)r.pointers().get(1)).keys().toArray());
		Node child = ((Node)r.pointers().get(1));
		Assert.assertArrayEquals(new Object[]{15,16},((Node)child.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{20,25},((Node)child.pointers().get(1)).keys().toArray());
	}
	
	@Test
	public final void testDeleteWithPreviousRelocation() {
		t = new BPlusTree(4);
		t.setRoot(buildAnotherTree());
		t.setDeep(3);
		
		t.delete(13);
		t.delete(15);
		
		Node r = t.getRootNode();
		assertEquals((Integer)1,(Integer)r.keys().size());
		assertEquals((Integer)2,(Integer)r.pointers().size());
		
		Assert.assertArrayEquals(new Object[]{11},r.keys().toArray());
		
		Assert.assertArrayEquals(new Object[]{9},((Node)r.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{16},  ((Node)r.pointers().get(1)).keys().toArray());
		Node child = ((Node)r.pointers().get(0));
		Assert.assertArrayEquals(new Object[]{1,4},((Node)child.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{9,10},((Node)child.pointers().get(1)).keys().toArray());
		child = ((Node)r.pointers().get(1));
		Assert.assertArrayEquals(new Object[]{11,12},((Node)child.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{16,20,25},((Node)child.pointers().get(1)).keys().toArray());
	}
	
	private Node buildAnotherTree() {
		Node n = new Node(4, false);
		n.keys().add(13);
		
		Node c1 = new Node(4, false);
		c1.setParent(n);
		c1.keys().add(9);
		c1.keys().add(11);
		
		Node c2 = new Node(4, false);
		c2.setParent(n);
		c2.keys().add(16);
		
		n.pointers().add(c1);
		n.pointers().add(c2);
		
		c1.sibling = c2;
		c2.previous = c1;
		
		Node c3 = new Node(4);
		c3.setParent(c1);
		c3.keys().add(1);
		c3.keys().add(4);
		
		Node c4 = new Node(4);
		c4.setParent(c1);
		c4.keys().add(9);
		c4.keys().add(10);
		
		Node c5 = new Node(4);
		c5.setParent(c1);
		c5.keys().add(11);
		c5.keys().add(12);
	
		c1.pointers().add(c3);
		c1.pointers().add(c4);
		c1.pointers().add(c5);
		
		for(int i=0; i < 2; i++) {
			c3.pointers().add("d"+c3.keys().get(i));
			c4.pointers().add("d"+c4.keys().get(i));
			c5.pointers().add("d"+c5.keys().get(i));
		}
		
		c3.sibling  = c4;
		c4.sibling  = c5;
		c5.previous = c4;
		c4.previous = c3;
		Node c6 = new Node(4);
		c6.setParent(c2);
		c6.keys().add(13);
		c6.keys().add(15);
		
		Node c7 = new Node(4);
		c7.setParent(c2);
		c7.keys().add(16);
		c7.keys().add(20);
		c7.keys().add(25);

		c2.pointers().add(c6);
		c2.pointers().add(c7);
		
		for(int i=0; i < 3; i++) {
			if (i < 2)
				c6.pointers().add("d"+c6.keys().get(i));
			c7.pointers().add("d"+c7.keys().get(i));
		}
		
		c5.sibling  = c6;
		c6.previous = c5;
		c6.sibling = c7;
		c7.previous = c6;
		
		return n;
	}
	
	private Node buildTree() {
		Node n = new Node(4, false);
		n.keys().add(25);
		n.keys().add(50);
		n.keys().add(75);
		
		Node c1 = new Node(4);
		c1.setParent(n);
		c1.keys().add(5);
		c1.keys().add(10);
		c1.keys().add(15);	
		c1.keys().add(20);	

		Node c2 = new Node(4);
		c2.setParent(n);
		c2.keys().add(25);
		c2.keys().add(30);
		
		Node c3 = new Node(4);
		c3.setParent(n);
		c3.keys().add(50);
		c3.keys().add(55);
		c3.keys().add(60);	
		c3.keys().add(65);
		
		Node c4 = new Node(4);
		c4.setParent(n);
		c4.keys().add(75);
		c4.keys().add(80);
		c4.keys().add(85);	
		c4.keys().add(90);

		for(int i=0; i < 4; i++) {
			c1.pointers().add("");
			c2.pointers().add("");
			c3.pointers().add("");
			c4.pointers().add("");
		}
		
		n.pointers().add(c1);
		n.pointers().add(c2);
		n.pointers().add(c3);
		n.pointers().add(c4);
		return n;
	}
}
