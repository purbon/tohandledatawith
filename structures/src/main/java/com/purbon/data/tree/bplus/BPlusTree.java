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
		
		root = new Node(n.capacity());
		root.leaf = false;
		root.add(key, n);
		root.p.add(r);
		
	}

	public void delete(int key) {
		
	}

}

