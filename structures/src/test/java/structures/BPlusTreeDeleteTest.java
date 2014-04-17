package structures;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.purbon.data.tree.bplus.BPlusTree;
import com.purbon.data.tree.bplus.Node;

public class BPlusTreeDeleteTest {

	BPlusTree t;
	@Before
	public void setUp() throws Exception {
		t = new BPlusTree(4);
		t.setRoot(buildTree());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSimpleInsert() {
		t.insert(28, "d28");
		Node r = t.getRootNode();
		int [] da = new int[]{25,28,30};
		Node secondChild = ((Node)r.pointers().get(1));
		for(int i=0; i < secondChild.keys().size(); i++)
		assertEquals((Integer)da[i], secondChild.keys().get(i));
	}

	@Test
	public final void testSplitInsert() {
		t.insert(28, "d28");
		t.insert(70, "d70");
		Node r = t.getRootNode();
		assertEquals((Integer)5,(Integer)r.pointers().size());
		Assert.assertArrayEquals(new Object[]{5,10,15,20},((Node)r.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{25,28,30},  ((Node)r.pointers().get(1)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{50,55},     ((Node)r.pointers().get(2)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{60,65, 70}, ((Node)r.pointers().get(3)).keys().toArray());
	}
	
	@Test
	public final void testSplitMoreInsert() {
		t.insert(28, "d28");
		t.insert(70, "d70");
		t.insert(95, "d95");
		Node r = t.getRootNode();
		assertEquals((Integer)2,(Integer)r.pointers().size());
		Assert.assertArrayEquals(new Object[]{25,50}, ((Node)r.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{75,85}, ((Node)r.pointers().get(1)).keys().toArray());
		Node child = ((Node)r.pointers().get(0));
		assertEquals((Integer)3,(Integer)child.pointers().size());
		Assert.assertArrayEquals(new Object[]{5,10,15,20}, ((Node)child.pointers().get(0)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{25,28,30},   ((Node)child.pointers().get(1)).keys().toArray());
		Assert.assertArrayEquals(new Object[]{50,55},      ((Node)child.pointers().get(2)).keys().toArray());


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
