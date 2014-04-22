package com.purbon.data.stream.count;

/**
 * Count min sketch
 * @author purbon
 *
 * @param <K>
 */
public class CountMinSketch<K>  extends AbstractCountSketch<K> {

	public CountMinSketch(double eps, double confidence, long seed) {
		super(eps, confidence, seed);
	}

	public CountMinSketch(double eps, double confidence) {
		super(eps, confidence);
	}

	public CountMinSketch(int w, int d, long seed) {
		super(w, d, seed);
	}

	public CountMinSketch(int w, int d) {
		super(w, d);
	}

	public long get(K a) {
		long min = Long.MAX_VALUE;
		for(int j=0; j < d; ++j) {
			min = Math.min(min, count[j][hash(j, a)]);
		}
		return min;
	}
	
	
	
}
