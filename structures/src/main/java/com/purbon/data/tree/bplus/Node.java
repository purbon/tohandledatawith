package com.purbon.data.tree.bplus;

import java.util.ArrayList;
import java.util.List;

public class Node {

	List<Integer> k;
	List<Object> p;
	
	Node parent;
	Node sibling;
	
	int n;
	boolean leaf;
	
	public Node(int n) {
		this(n, true);
	}
	
	public Node(int n, boolean leaf) {
		k = new ArrayList<Integer>();
		p = new ArrayList<Object>();
		this.leaf = leaf;
		parent = null;
		this.n = n;
	}
	public boolean isLeaf() {
		return leaf;
	}
	
	public int capacity() {
		return n;
	}
	
	public int size() {
		return k.size();
	}

	public List<Integer> keys() {
		return k;
	}
	
	public List<Object> pointers() {
		return p;
	}
	
	public int add(int key, Object v) {
		int pos = -1;
		for(int i=0; i < size() && pos == -1; i++) {
			if (key < k.get(i)) {
				pos = i;
			}
		}
		if (pos == -1) {
			k.add(key);
			p.add(v);
			pos = p.size()-1;
		} else {
			k.add(pos, key);
			p.add(pos+1, v);
		}
		
		return pos;

		
	}

	public boolean isFull() {
		return p.size() == n;
	}

	public void remove(int key) {
		int pos = -1;
		for(int i=0; i < k.size() && pos == -1; i++) {
			if (k.get(i).equals(key))
				pos = i;
		}
		if (pos > -1) {
			k.remove(pos);
			p.remove(pos);
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0; i < k.size(); i++) {
			if (i>0)
				sb.append(",");
			sb.append(k.get(i));
		}
		sb.append("] "+leaf);
		return sb.toString();
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	
}
