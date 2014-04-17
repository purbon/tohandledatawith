package com.purbon.data.tree.bplus;

/**
 * BPlusTree dummy implementation.
 * @author purbon
 *
 */
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
		if (n.k.size() < (n.k.capacity()-1)) {
			n.add(key, value);
			return;
		}
		splitAndRelocate(n, key, value);
	}
		
	private void splitAndRelocate(Node n, int key, Object value) {
		n.add(key, value);
		if (n.size() < n.capacity())
			return;
		int  s = (int)Math.ceil(n.capacity()/2.0);
		Node r = split(n, s);
		if (n.parent == null) {
			root = new Node(n.capacity());
			root.leaf = false;
			root.k.add(r.k.get(0));
			root.p.insertElementAt(n, 0);
			root.p.insertElementAt(r, 1);
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
		return r;
	}

	public void delete(int key) {
		
	}

	@Override
	public String toString() {
		return toString(0, root);
	}
	
	private String toString(int i, Node node) {
		StringBuilder sb = new StringBuilder();
		sb.append("("+i+")");
		sb.append(node);
		sb.append("[");
		for(Object p : node.pointers()) {
			if (p instanceof Node) {
				sb.append(toString(i+1,(Node)p));
			}
		}
		sb.append("]\n");
		return sb.toString();
	}
}

