package com.purbon.data.stream.count;

/**
 * Count mean sketch
 * @author purbon
 *
 * @param <K>
 */
public class CountMeanSketch<K> extends AbstractCountSketch<K> {
	
	public CountMeanSketch(double eps, double confidence) {
		super(eps, confidence);
	}


	public CountMeanSketch(double eps, double confidence, long seed) {
		super(eps, confidence, seed);
	}


	public CountMeanSketch(int w, int d, long seed) {
		super(w, d, seed);
	}

	public CountMeanSketch(int w, int d) {
		super(w, d);
	}


	public long get(K a) {
		long [] e = new long[d];
		for(int j=0; j < d; ++j) {
			long counter = count[j][hash(j, a)];
			long noise   = (n-counter)/(w-1);
			e[j] = counter - noise;
		}
		return median(e);
	}
	
	
	private long median(long e[]) {
		long sum = 0;
		for(int i=0; i < e.length; i++) {
			sum += e[i];
		}
		return (long)Math.ceil(sum/e.length);
	}
	
	
}
