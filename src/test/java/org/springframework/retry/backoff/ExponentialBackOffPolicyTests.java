/*
 * Copyright 2006-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.retry.backoff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Rob Harrop
 * @author Dave Syer
 */
public class ExponentialBackOffPolicyTests {

	private final DummySleeper sleeper = new DummySleeper();

	@Test
	public void testSetMaxInterval() {
		ExponentialBackOffPolicy strategy = new ExponentialBackOffPolicy();
		strategy.setMaxInterval(1000);
		assertTrue(strategy.toString().contains("maxInterval=1000"));
		strategy.setMaxInterval(0);
		// The minimum value for the max interval is 1
		assertTrue(strategy.toString().contains("maxInterval=1"));
	}

	@Test
	public void testSetInitialInterval() {
		ExponentialBackOffPolicy strategy = new ExponentialBackOffPolicy();
		strategy.setInitialInterval(10000);
		assertTrue(strategy.toString().contains("initialInterval=10000,"));
		strategy.setInitialInterval(0);
		assertTrue(strategy.toString().contains("initialInterval=1,"));
	}

	@Test
	public void testSetMultiplier() {
		ExponentialBackOffPolicy strategy = new ExponentialBackOffPolicy();
		strategy.setMultiplier(3.);
		assertTrue(strategy.toString().contains("multiplier=3."));
		strategy.setMultiplier(.5);
		assertTrue(strategy.toString().contains("multiplier=1."));
	}

	@Test
	public void testSingleBackOff() {
		ExponentialBackOffPolicy strategy = new ExponentialBackOffPolicy();
		strategy.setSleeper(sleeper);
		BackOffContext context = strategy.start(null);
		strategy.backOff(context);
		assertEquals(ExponentialBackOffPolicy.DEFAULT_INITIAL_INTERVAL, sleeper.getLastBackOff());
	}

	@Test
	public void testMaximumBackOff() {
		ExponentialBackOffPolicy strategy = new ExponentialBackOffPolicy();
		strategy.setMaxInterval(50);
		strategy.setSleeper(sleeper);
		BackOffContext context = strategy.start(null);
		strategy.backOff(context);
		assertEquals(50, sleeper.getLastBackOff());
	}

	@Test
	public void testMultiBackOff() {
		ExponentialBackOffPolicy strategy = new ExponentialBackOffPolicy();
		long seed = 40;
		double multiplier = 1.2;
		strategy.setInitialInterval(seed);
		strategy.setMultiplier(multiplier);
		strategy.setSleeper(sleeper);
		BackOffContext context = strategy.start(null);
		for (int x = 0; x < 5; x++) {
			strategy.backOff(context);
			assertEquals(seed, sleeper.getLastBackOff());
			seed *= multiplier;
		}
	}

}
