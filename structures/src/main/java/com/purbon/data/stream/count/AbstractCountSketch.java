package com.purbon.data.stream.count;

import java.util.Random;

/**
 * Abstract class related to count sketches
 * @author purbon
 *
 * @param <K>
 */
public abstract class AbstractCountSketch<K> {


    public static final long PRIME_MODULUS = (1L << 31) - 1;

	int [][] count = null;
	int d = 0;
	int w = 0;
	long n;
	long a[];
	double eps;
	double confidence;
	
	public AbstractCountSketch(double eps, double confidence) {
		this(eps, confidence, System.currentTimeMillis());
	}
	
	public AbstractCountSketch(double eps, double confidence, long seed) {
		this.w = (int)Math.ceil(2 / eps);
        this.d = (int) Math.ceil(-Math.log(1 - confidence) / Math.log(2));
        this.confidence = confidence;
        this.eps = eps;
        init(w,d, seed);
	}
	
	public AbstractCountSketch(int w, int d) {
		this(w, d, System.currentTimeMillis());
	}
	
	public AbstractCountSketch(int w, int d, long seed) {
		this.d = d; // rows
		this.w = w; // columns
		this.eps = 2.0 / w;
        this.confidence = 1 - 1 / Math.pow(2, d);
		init(w,d, seed);
	}

	private void init(int w, int d, long seed) {
		count = new int [d][w];
		a = new long[d];
		n = 0;
		Random rand = new Random(seed);
		for(int i=0; i < d; ++i) {
			a[i] = rand.nextInt(Integer.MAX_VALUE);
		}
	}
	
	public void put(K a) {
		n++;
		for(int j = 0; j < d; ++j) {
			count[j][hash(j, a)] = count[j][hash(j, a)] + 1;
		}
	}
	
	public abstract long get(K a);
		
	public int hash(int i, K v) {
	  long hash = a[i] * v.hashCode();
	       hash += hash >> 32;
	       hash &= PRIME_MODULUS;
	   return ((int) hash) % w;
	}
	
	public double getConfidence() {
		return confidence;
	}
	
	public double getEps() {
		return eps;
	}

	@Override
	public String toString() {
		return "CountMinSketch [d=" + d + ", w=" + w + ", eps=" + eps
				+ ", confidence=" + confidence + "]";
	}
}
