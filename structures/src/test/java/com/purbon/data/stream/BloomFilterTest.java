package com.purbon.data.stream;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.purbon.data.stream.sets.BloomFilter;

public class BloomFilterTest {

	BloomFilter<Integer> filter;
	
	@Before
	public void setUp() throws Exception {
		long n = 100;
		double p = 0.0001;
		filter = new BloomFilter<Integer>(n, p);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSingle() {
		filter.add(10);
		Assert.assertTrue(filter.exist(10));
	}
	
	@Test
	public void testPositive() {
		for(int i=0; i < 100; i++) {
			filter.add(i);
			Assert.assertTrue(filter.exist(i));
		}
		Assert.assertFalse(filter.exist(200));
	}
	
	@Test
	public void testFalsePositive() {
		for(int i=0; i < 5000; i++) {
			filter.add(i);
			Assert.assertTrue(filter.exist(i));
		}
		Assert.assertTrue(filter.exist(50001));
	}

}
