package com.purbon.data.tree.bplus;

/**
 * BPlusTree implementation, might not be production ready, having a focus on educational purpouses.
 * @author purbon
 *
 */
public class BPlusTree {

	private Node root;
	private int  deep;
	/**
	 * Default constructor
	 * @param n The internal bucket size.
	 */
	public BPlusTree(int n) {
		this.root = new Node(n);
		this.deep = 1;
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
			
			int diff = (n.capacity()/2)-n.size();
			int siblingOverflow = n.sibling.size()-(n.sibling.capacity()/2);
			if (siblingOverflow >= diff) {
				for(int i=0; i < diff; i++) {
					n.keys().add(n.sibling.keys().remove(0));
					n.pointers().add(n.sibling.pointers().remove(0));
				}
			} else if (siblingOverflow == 0) {
				for(int i=0; i < diff; i++) {
					n.sibling.keys().add(0, n.keys().remove(0));
					n.sibling.pointers().add(0, n.pointers().remove(0));
				}
				n.parent.pointers().remove(n);
			}
		
		}
		rebalanceNode(n.parent); 		// reorder parent
	}

	private void rebalanceNode(Node node) {
		if (node.keys().size() >= node.pointers().size()) {				
			Node lastPrevNode = (Node)node.previous.pointers().remove(node.previous.pointers().size()-1);
			lastPrevNode.parent = node;
			node.pointers().add(0, lastPrevNode);
			node.keys().add(0, node.previous.keys().remove(node.previous.keys().size()-1));
		}
		if (node.keys().size() >= node.pointers().size())
			node.keys().remove(node.keys().size()-1);

		if (node.parent != null)
			rebalanceKeys(node.parent);
		rebalanceLeaf(node);
	
	}
	
	public void rebalanceKeys(Node node) {
		for(int i=0; i < node.size(); i++) {
			int  parentKey  = node.keys().get(i);
				Node rightChild = (Node)node.pointers().get(i+1);
				if (rightChild.keys().get(0) < parentKey) {
					int key = rightChild.keys().get(0);
					rightChild.keys().set(0, parentKey);
					node.keys().set(i, key);
				}
		}
	}
	
	
	private void rebalanceLeaf(Node node) {
		for(int i=0; i < node.size(); i++) {
			int  parentKey  = node.keys().get(i);
			if ( (i+1) < node.pointers().size() ) {
				Node rightChild = (Node)node.pointers().get(i+1);
				if (rightChild.keys().get(0) > parentKey) {
					node.keys().set(i, rightChild.keys().get(0));
				}
			}
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
			root.k.add(r.k.get(0));
			if (deep > 1) {
				r.k.remove(0);
				n.p.add(r.p.get(0));
				r.p.remove(0);
				r.leaf = false;
				for(int i=0; i < r.p.size(); i++) {
					((Node)r.p.get(i)).parent = r;
				}
			}
			n.sibling  = r;
			r.previous = n;
			root.p.add(n);
			root.p.add(r);
			r.parent = root;
			n.parent = root;
			deep++;
			return;
		} else {
			splitAndRelocate(n.parent, r.k.get(0), r);
		}	
	}

	protected void setDeep(int deep) {
		this.deep = deep;
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
		
		n.sibling = r;
		r.previous = n;
		r.parent = n.parent;
		
		return r;
	}
}

