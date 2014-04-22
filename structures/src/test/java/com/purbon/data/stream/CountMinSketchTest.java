package com.purbon.data.stream;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CountMinSketchTest {

	CountMinSketch<Integer> sketch;
	int [] set;
	int nTotal;
	int nScale;
	double confidence;
	double eps;
	
	@Before
	public void setUp() throws Exception {
		nTotal     =  80000;
		nScale     =  8500;
		
		confidence = 0.9999;
		eps        = 0.0001;
		
		long seed = 7364181;
		Random rand = new Random(seed);
		sketch = new CountMinSketch<Integer>(eps, confidence, seed);
		set    = new int[nScale];
		
		for(int i=0; i < nTotal; i++) {
			int v = rand.nextInt(nScale);
			sketch.put(v);
			set[v]++;
		}

	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testSketchConstruction() {
		int nErr = 0;
		for(int i=0; i < set.length; i++) {
			Integer v  = set[i];
			if (v == null)
				v = 0;
			double err = 1.0*(sketch.get(i) - v)/Math.max(sketch.get(i), v);
			if (sketch.get(i) != v) {
				System.out.println(sketch.get(i)+" "+v+" "+err);
				nErr++;
			}
		}
		double pOk = 1 - (1.0*nErr)/nScale;
		assertTrue("Confidence not reached: required " + sketch.getConfidence() + ", reached " + pOk, pOk > sketch.getConfidence());
	}

}
