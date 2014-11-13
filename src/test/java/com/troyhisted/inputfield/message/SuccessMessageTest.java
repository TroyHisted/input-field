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
 * Tests the SuccessMessage implementation of the {@link Message} interface.
 *
 * @author Troy Histed
 */
public class SuccessMessageTest extends AbstractMessageTest {

	/**
	 * Constructs an instance of a Message.
	 *
	 * @param message
	 *            the message text
	 * @param args
	 *            any message variables
	 * @return a new Message instance
	 */
	@Override
	public Message construct(String message, String... args) {
		return new SuccessMessage(message, args);
	}

	/**
	 * Ensure the message level for a success message is 25.
	 */
	@Test
	public void testMessageLevel() {
		final Message message = this.construct("");
		Assert.assertEquals(25, message.getLevel());
	}

}
