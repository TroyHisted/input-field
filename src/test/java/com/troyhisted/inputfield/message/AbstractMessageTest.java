/**
 * Copyright 2014 Troy Histed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.troyhisted.inputfield.message;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests an implementation of the {@link Message} interface to ensure it conforms to the requirements of the
 * interface.
 *
 * @author Troy Histed
 */
public abstract class AbstractMessageTest {

	/**
	 * Constructs an instance of a Message.
	 *
	 * @param message
	 *            the message text
	 * @param args
	 *            any message variables
	 * @return a new Message instance
	 */
	public abstract Message construct(String message, String... args);

	/**
	 * Ensure any subclass property implements the construct method for this test class.
	 */
	@Test
	public void testConstruct() {
		final Message message = this.construct("test");
		Assert.assertNotNull(message);
		Assert.assertEquals("test", message.getText());
	}

	/**
	 * Ensure variable replacement works for multiple args.
	 */
	@Test
	public void testVariableReplacementForMultipleArgs() {
		final Message message = this.construct("{0} {1} {2}", "zero", "one", "two");
		Assert.assertEquals("zero one two", message.getText());
	}

	/**
	 * Ensure a null message returns null.
	 */
	@Test
	public void testNullMessage() {
		final Message message = this.construct(null);
		Assert.assertNull(message.getText());
	}

}
