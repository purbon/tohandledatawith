package structures;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.purbon.data.tree.bplus.BPlusTree;
import com.purbon.data.tree.bplus.Node;

public class BPlusTreeTest {

	BPlusTree t;
	
	@Before
	public void setUp() throws Exception {
		t = new BPlusTree(4);
		t.insert(3,  "d1");
		t.insert(5,  "d2");
		t.insert(10, "d3");
		t.insert(7,  "d4");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInsertOneBucket() {
		assertEquals(1, t.getRootNode().size());
	}
	
	@Test
	public void testRoot() {
		assertEquals(false, t.getRootNode().isLeaf());
	}

	@Test
	public void testSearch() {
		Node n  = t.search(7);
		assertEquals(true, n.isLeaf());
		assertEquals((Integer)7, n.keys().get(0));
		assertEquals((Integer)10, n.keys().get(1));

	}

}
