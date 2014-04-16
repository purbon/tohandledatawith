package com.purbon.data.tree.bplus;


public class BPlusTree {

	private Node root;
	
	public BPlusTree(int n) {
		this.root = new Node(n);
	}
	
	public Node getRootNode() {
		return root;
	}
	
	public Node search(int key) {
		return search(key, root);
	}
	
	private Node search(int k, Node n) {
		if (n.isLeaf())
			return n;
		Node m = null;
		for(int i=0; i < n.size() && m == null; i++) {
			if (k < n.k.get(i)) {
				m = (Node)n.p.get(i);
			}	
		}
		if (m == null)
			m = (Node)n.p.lastElement();
		
		return search(k, m);
	}
	
	public void insert(int key, Object value) {
		Node n = search(key);
		if (n.size() < (n.capacity()-1)) {
			n.add(key, value);
			return;
		}
		// split and relocate
		splitAndRelocate(n, key, value);
		
	}
	
	private void splitAndRelocate(Node n, int key, Object value) {
		n.add(key, value);
		if (n.size() < n.capacity())
			return;
		int  s = (int)Math.floor(n.size()/2);
		
		Node r = new Node(n.capacity());
		for(int i=s; i < n.capacity(); i++) {
			r.k.add(n.k.get(i));
			r.p.add(n.p.get(i));		
		}
		for(int i=n.capacity()-1; i >= s; i--) {
			n.k.remove(i);
			n.p.remove(i);
		}
		if (n.parent == null) { // create a new root node.
			
			root = new Node(n.capacity());
			root.leaf = false;
			root.add(r.k.get(0), n);
			root.p.add(r);
			n.parent = root;
			r.parent = root;
			return;
		}
		r.parent = n.parent;
		splitAndRelocate(r.parent, r.k.get(0), r);
	
		
	}

	public void delete(int key) {
		
	}

}

