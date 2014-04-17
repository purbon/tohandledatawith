package com.purbon.data.tree.bplus;

/**
 * BPlusTree implementation, might not be production ready, having a focus on educational purpouses.
 * @author purbon
 *
 */
public class BPlusTree {

	private Node root;
	
	/**
	 * Default constructor
	 * @param n The internal bucket size.
	 */
	public BPlusTree(int n) {
		this.root = new Node(n);
	}
	
	/**
	 * Search a key into the tree
	 * @param key The key to be searched
	 * @return the result of this operation.
	 */
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
			m = (Node)n.p.get(n.p.size()-1);
		
		return search(k, m);
	}
	
	/**
	 * Let you insert a new value into the tree
	 * @param key The new key
	 * @param value The new value
	 */
	public void insert(int key, Object value) {
		Node n = search(key);
		if (n.k.size() < (n.capacity()-1)) {
			n.add(key, value);
			return;
		}
		splitAndRelocate(n, key, value);
	}
		
	/**
	 * Remove a key from the tree
	 * @param key
	 */
	public void delete(int key) {
		Node n = search(key);
		n.remove(key);
		if (n.size() < n.capacity()/2) { // data should be redistributed
			
		}
	}

	/**
	 * Get's the root tree node.
	 * @return the root node.
	 */
	public Node getRootNode() {
		return root;
	}
	
	public void setRoot(Node node) {
		this.root = node;
	}

	private void splitAndRelocate(Node n, int key, Object value) {
		n.add(key, value);
		if (n.size() <= n.capacity())
			return;
		int  s = (int)Math.ceil(n.capacity()/2.0);
		Node r = split(n, s);
		if (n.parent == null) {
			root = new Node(n.capacity(), false);
			// move up the remaining key.			
			key = r.k.remove(0);
			root.k.add(key);
			n.p.add(r.p.get(0));
			r.p.remove(0);
			
			root.p.add(n);
			root.p.add(r);
			r.parent = root;
			n.parent = root;
			return;
		} else {
			splitAndRelocate(n.parent, r.k.get(0), r);
		}	
	}

	
	private Node split(Node n, int s) {
		Node r = new Node(n.capacity());
		for(int i=s; i < n.k.size(); i++) {
			r.k.add(n.k.get(i));
		}
		for(int i=s; i < n.p.size(); i++) {
			r.p.add(n.p.get(i));		
		}
		for(int i=n.k.size()-1; i >= s; i--) {
			n.k.remove(i);
		}
		for(int i=n.p.size()-1; i >= s; i--) {
			n.p.remove(i);
		}
		r.parent = n.parent;
		n.sibling = r;
		return r;
	}
}

