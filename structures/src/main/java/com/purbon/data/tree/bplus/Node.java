package com.purbon.data.tree.bplus;

import java.util.Vector;

public class Node {

	Vector<Integer> k;
	Vector<Object> p;
	boolean leaf;
	
	Node(int n) {
		k = new Vector<Integer>(n);
		p = new Vector<Object>(n+1);
		leaf = true;
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
		p.insertElementAt(v, pos);
		return pos;
	}

}
