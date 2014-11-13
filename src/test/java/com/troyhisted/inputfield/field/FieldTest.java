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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.troyhisted.inputfield.message.ErrorMessage;
import com.troyhisted.inputfield.message.Message;


/**
 * Tests an implementation of the {@link Field} interface to ensure it conforms to the requirements of the
 * interface.
 *
 * @author Troy Histed
 */
public abstract class FieldTest {

	/**
	 * Constructs an instance of a Field.
	 *
	 * @param fieldType the type of field to construct
	 * @return a new Field instance
	 */
	public abstract <T> Field<T> construct(Class<T> fieldType);

	/**
	 * Ensure any subclass property implements the construct method for this test class.
	 */
	@Test
	public void testConstruct() {
		final Object field = this.construct(String.class);
		Assert.assertNotNull(field);
		Assert.assertTrue("Construct method does not create an instanceof Field", field instanceof Field<?>);
	}

	/**
	 * Ensure the value property returns the same object that is set.
	 */
	@Test
	public void testValuePropertySetToObject() {
		final Field<Object> field = this.construct(Object.class);
		final Object testValue = new String[0];
		field.setValue(testValue);
		Assert.assertEquals(testValue, field.getValue());
	}

	/**
	 * Ensure the value property returns null when null is set.
	 */
	@Test
	public void testValuePropertySetToNull() {
		final Field<Object> field = this.construct(Object.class);
		field.setValue(null);
		Assert.assertNull(field.getValue());
	}

	/**
	 * Ensure the label property returns the same string that is set.
	 */
	@Test
	public void testLabelPropertySetToString() {
		final Field<Object> field = this.construct(Object.class);
		final String label = "label";
		field.setLabel(label);
		Assert.assertEquals(label, field.getLabel());
	}

	/**
	 * Ensure the label property returns null when the label is set to null.
	 */
	@Test
	public void testLabelPropertySetToNull() {
		final Field<Object> field = this.construct(Object.class);
		field.setLabel(null);
		Assert.assertNull(field.getLabel());
	}

	/**
	 * Ensure the message property returns the same message that is set.
	 */
	@Test
	public void testMessagePropertySetToMessage() {
		final Field<Object> field = this.construct(Object.class);
		final Message message = new ErrorMessage("test");
		field.setMessage(message);
		Assert.assertEquals(message, field.getMessage());
	}

	/**
	 * Ensure the message property returns null when the message is set to null.
	 */
	@Test
	public void testMessagePropertySetToNull() {
		final Field<Object> field = this.construct(Object.class);
		field.setMessage(null);
		Assert.assertNull(field.getMessage());
	}

	/**
	 * Ensure the options property returns the same options that are set.
	 */
	@Test
	public void testOptionsPropertySetToOptions() {
		final Field<Object> field = this.construct(Object.class);
		final List<String> options = Arrays.asList("one", "two");
		field.setOptions(options);
		Assert.assertEquals(options, field.getOptions());
	}

	/**
	 * Ensure the options property returns null when the options are set to null.
	 */
	@Test
	public void testOptionsPropertySetToNull() {
		final Field<Object> field = this.construct(Object.class);
		field.setOptions(null);
		Assert.assertNull(field.getOptions());
	}

	/**
	 * Ensure the fluent property setters set all the values.
	 */
	@Test
	public void testFluentSetters() {
		final Field<Object> field = this.construct(Object.class);
		final Object value = new String[0];
		final String label = "label";
		final Message message = new ErrorMessage("test");
		final List<String> options = Arrays.asList("one", "two");
		field.value(value).label(label).message(message).options(options);
		Assert.assertEquals(value, field.getValue());
		Assert.assertEquals(label, field.getLabel());
		Assert.assertEquals(message, field.getMessage());
		Assert.assertEquals(options, field.getOptions());
	}

	/**
	 * Test to ensure that just because two fields have the same value, it doesn't mean they're equal.
	 */
	@Test
	public void testEquals() {
		final Field<BigDecimal> field1 = this.construct(BigDecimal.class);
		field1.setValue(new BigDecimal("1"));
		final Field<BigDecimal> field2 = this.construct(BigDecimal.class);
		field2.setValue(new BigDecimal("1"));
		Assert.assertNotEquals(field1, field2);
	}

	/**
	 * Ensure that when the values of two fields are different that isEquals returns false.
	 */
	@Test
	public void testIsEqualTrue() {
		final Field<BigDecimal> field1 = this.construct(BigDecimal.class);
		field1.setValue(new BigDecimal("1"));
		final Field<BigDecimal> field2 = this.construct(BigDecimal.class);
		field2.setValue(new BigDecimal("1"));
		Assert.assertTrue(field1.isEqual(field2));
	}

	/**
	 * Ensure that when the values of two fields are equivalent that isEquals returns true.
	 */
	@Test
	public void testIsEqualFalse() {
		final Field<BigDecimal> field1 = this.construct(BigDecimal.class);
		field1.setValue(new BigDecimal("1"));
		final Field<BigDecimal> field2 = this.construct(BigDecimal.class);
		field2.setValue(new BigDecimal("2"));
		Assert.assertFalse(field1.isEqual(field2));
	}

	/**
	 * Ensure that when the first field has a null value.
	 */
	@Test
	public void testIsEqualFirstFieldNull() {
		final Field<BigDecimal> field1 = this.construct(BigDecimal.class);
		final Field<BigDecimal> field2 = this.construct(BigDecimal.class);
		field2.setValue(new BigDecimal("2"));
		Assert.assertFalse(field1.isEqual(field2));
	}

	/**
	 * Ensure that when the first field has a null value.
	 */
	@Test
	public void testIsEqualSecondFieldNull() {
		final Field<BigDecimal> field1 = this.construct(BigDecimal.class);
		field1.setValue(new BigDecimal("1"));
		final Field<BigDecimal> field2 = this.construct(BigDecimal.class);
		Assert.assertFalse(field1.isEqual(field2));
	}

	/**
	 * Ensure that when the first field has a null value.
	 */
	@Test
	public void testIsEqualBothFieldsNull() {
		final Field<BigDecimal> field1 = this.construct(BigDecimal.class);
		final Field<BigDecimal> field2 = this.construct(BigDecimal.class);
		Assert.assertFalse(field1.isEqual(field2));
	}

	/**
	 * Ensure that when the comparison field is null that false is returned.
	 */
	@Test
	public void testIsEqualCompareToNull() {
		final Field<BigDecimal> field1 = this.construct(BigDecimal.class);
		Assert.assertFalse(field1.isEqual(null));
	}

}
