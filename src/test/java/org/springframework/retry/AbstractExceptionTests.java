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

package org.springframework.retry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public abstract class AbstractExceptionTests {

	@Test
	public void testExceptionString() throws Exception {
		Exception exception = getException("foo");
		assertEquals("foo", exception.getMessage());
	}

	@Test
	public void testExceptionStringThrowable() throws Exception {
		Exception exception = getException("foo", new IllegalStateException());
		assertEquals("foo", exception.getMessage().substring(0, 3));
	}

	public abstract Exception getException(String msg);

	public abstract Exception getException(String msg, Throwable t);

}
