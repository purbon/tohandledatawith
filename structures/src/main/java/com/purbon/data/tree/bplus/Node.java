package com.purbon.data.tree.bplus;

import java.util.Vector;

public class Node {

	Vector<Integer> k;
	Vector<Object> p;
	Node parent;
	
	boolean leaf;
	
	Node(int n) {
		k = new Vector<Integer>(n);
		p = new Vector<Object>(n);
		leaf = true;
		parent = null;
	}
	
	public boolean isLeaf() {
		return leaf;
	}
	
	public int capacity() {
		return k.capacity();
	}
	
	public int size() {
		return k.size();
	}

	public Vector<Integer> keys() {
		return k;
	}
	
	public Vector<Object> pointers() {
		return p;
	}
	
	public int add(int key, Object v) {
		int pos = -1;
		for(int i=0; i < size() && pos == -1; i++) {
			if (key < k.get(i)) {
				pos = i;
			}
		}
		if (k.size() < k.capacity() && pos == -1)
			pos = k.size();
		k.insertElementAt(key, pos);
		if (!isLeaf() && p.size() > pos) {
			Node fn = ((Node)p.get(pos));
			if (fn.k.size() > 0 && key > fn.k.get(0))
				pos++;
		}
		p.insertElementAt(v, pos);
		return pos;
	}

}
