package com.purbon.data.stream.sets;

import java.util.BitSet;

import com.purbon.data.hash.MurmurHash;
import com.purbon.data.utils.DataUtils;

/**
 * Straight forward bloom filter implementation.
 * @author purbon
 *
 * @param <K>
 */
public class BloomFilter<K> {

	BitSet set;
	double m;
	int k;
	
	public BloomFilter(long n, double p) {
		double log10_2 = Math.log10(2);
		m = - (n * Math.log10(p))/(log10_2 * log10_2);
		k = (int)((m/n) * log10_2);
		set = new BitSet((int)m);
	}
	
	public void add(K v) {
		for(int i=0; i < k; i++) {
			set.set(hash(i, v));
		}
	}
	
	public boolean exist(K v) {
		boolean exist = true;
		for(int i=0; i < k & exist; i++) {
			boolean bit = set.get(hash(i, v));
			if (!bit)
				exist = false;
		}
		return exist;
	}

	private int hash(int i, K v) {
		byte[] data = DataUtils.toByteArray(v);
		int hash1   = MurmurHash.hash32(data, data.length);
		int hash2    = MurmurHash.hash32(data, data.length, hash1);
		return Math.abs( (hash1 * i + hash2) % (int)m);
	}

	@Override
	public String toString() {
		return "BloomFilter [m=" + m + ", k=" + k + "]";
	}
	
}
