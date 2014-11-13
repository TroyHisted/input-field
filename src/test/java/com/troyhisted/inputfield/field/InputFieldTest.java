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
package com.troyhisted.inputfield.field;

import org.junit.Assert;
import org.junit.Test;

import com.troyhisted.inputfield.message.ErrorMessage;

/**
 * Tests the {@link InputField} implementation of the {@link Field} interface.
 *
 * @author Troy Histed
 */
public class InputFieldTest extends FieldTest {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> Field<T> construct(Class<T> fieldType) {
		return InputField.create();
	}

	/**
	 * Ensure that a null message results in null message text.
	 */
	@Test
	public void testGetMessageFromNull() {
		final Field<Object> field = this.construct(Object.class);
		Assert.assertNull(field.getMessageText());
	}

	/**
	 * Ensure that the placeholder replacement works through the getMessageText method of the field.
	 */
	@Test
	public void testGetMessageTextWithPlaceholder() {
		final Field<Object> field = this.construct(Object.class);
		field.message(new ErrorMessage("{0} world", "hello"));
		Assert.assertEquals("hello world", field.getMessageText());
	}

	/**
	 * Ensure that the label can be used as placeholder text.
	 */
	@Test
	public void testGetMessageTextLowercaseLabel() {
		final Field<Object> field = this.construct(Object.class);
		field.label("hello").message(new ErrorMessage("{label} world"));
		Assert.assertEquals("hello world", field.getMessageText());
	}

	/**
	 * Ensure that the capitalized label can be used as placeholder text.
	 */
	@Test
	public void testGetMessageTextUppercaseLabel() {
		final Field<Object> field = this.construct(Object.class);
		field.label("hello").message(new ErrorMessage("{Label} world"));
		Assert.assertEquals("Hello world", field.getMessageText());
	}

	/**
	 * Ensure that the value placeholder can be used in the message text.
	 */
	@Test
	public void testGetMessageTextValuePlaceholder() {
		final Field<String> field = this.construct(String.class);
		field.value("hello").message(new ErrorMessage("{value} world"));
		Assert.assertEquals("hello world", field.getMessageText());
	}
}
