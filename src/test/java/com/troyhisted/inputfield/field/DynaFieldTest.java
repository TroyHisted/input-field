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

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Test;

import com.troyhisted.inputfield.message.ErrorMessage;
import com.troyhisted.inputfield.message.Message;

/**
 * Tests the {@link DynaField} implementation of the {@link Field} interface.
 *
 * @author Troy Histed
 */
public class DynaFieldTest extends InputFieldTest {

	/**
	 * Simple enum for use in this test.
	 *
	 * @author Troy Histed
	 */
	private enum Direction {
		North, East, South, West
	};

	/**
	 * Register a basic converter for the Direction enum.
	 */
	static {
		ConvertUtils.register(new Converter() {
			@SuppressWarnings("unchecked")
			public <T> T convert(Class<T> type, Object value) {
				return value == null ? null : (T) Direction.valueOf(String.valueOf(value));
			}
		}, Direction.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> Field<T> construct(Class<T> fieldType) {
		return DynaField.create(fieldType);
	}

	/**
	 * Constructs and initializes a field.
	 *
	 * @param value
	 *            the value to initialize to (must not be null)
	 * @return the new {@link DynaField}
	 */
	public <T> Field<T> initialize(T value) {
		return DynaField.initialize(value);
	}

	/**
	 * Ensure a String property can be set using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testSetBeanStringProperty() throws IllegalAccessException, InvocationTargetException {
		final Field<String> field = this.construct(String.class);
		BeanUtils.setProperty(field, "value", "sample");
		Assert.assertEquals("sample", field.getValue());
	}

	/**
	 * Ensure a String property can be retrieved using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetBeanStringProperty() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Field<String> field = this.construct(String.class);
		field.value("sample");
		Assert.assertEquals("sample", BeanUtils.getProperty(field, "value"));
	}

	/**
	 * Ensure a BigDecimal property can be set using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testSetBeanBigDecimalProperty() throws IllegalAccessException, InvocationTargetException {
		final Field<BigDecimal> field = this.construct(BigDecimal.class);
		BeanUtils.setProperty(field, "value", "12.34");
		Assert.assertEquals(new BigDecimal("12.34"), field.getValue());
	}

	/**
	 * Ensure a BigDecimal property can be retrieved using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetBeanBigDecimalProperty() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Field<BigDecimal> field = this.construct(BigDecimal.class);
		field.value(new BigDecimal("12.34"));
		Assert.assertEquals("12.34", BeanUtils.getProperty(field, "value"));
		Assert.assertEquals(new BigDecimal("12.34"), PropertyUtils.getProperty(field, "value"));
	}

	/**
	 * Ensure a String property can be set using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testSetBeanEnumPropertyNull() throws IllegalAccessException, InvocationTargetException {
		final Field<Direction> field = this.construct(Direction.class);
		BeanUtils.setProperty(field, "value", null);
		Assert.assertNull(field.getValue());
	}

	/**
	 * Ensure an enum property can be set using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testSetBeanEnumProperty() throws IllegalAccessException, InvocationTargetException {
		final Field<Direction> field = this.construct(Direction.class);
		BeanUtils.setProperty(field, "value", Direction.North);
		Assert.assertEquals(Direction.North, field.getValue());
	}

	/**
	 * Ensure an enum property can be set using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testSetBeanEnumPropertyFromString() throws IllegalAccessException, InvocationTargetException {
		final Field<Direction> field = this.construct(Direction.class);
		BeanUtils.setProperty(field, "value", "North");
		Assert.assertEquals(Direction.North, field.getValue());
	}

	/**
	 * Ensure an enum property can be retrieved using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetBeanEnumProperty() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Field<Direction> field = this.construct(Direction.class);
		field.value(Direction.North);
		Assert.assertEquals(Direction.North.name(), BeanUtils.getProperty(field, "value"));
		Assert.assertEquals(Direction.North, PropertyUtils.getProperty(field, "value"));
	}

	/**
	 * Ensure all properties can be retrieved using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetAllBeanProperties() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Field<String> field = this.construct(String.class);
		final String value = "value";
		final String label = "label";
		final Message message = new ErrorMessage("test");
		final List<String> options = Arrays.asList("one", "two");
		field.value(value).label(label).message(message).options(options);
		Assert.assertEquals(value, BeanUtils.getProperty(field, "value"));
		Assert.assertEquals(label, BeanUtils.getProperty(field, "label"));
		Assert.assertEquals(message, PropertyUtils.getProperty(field, "message"));
		Assert.assertEquals(message.getText(), PropertyUtils.getProperty(field, "messageText"));
		Assert.assertEquals(options, PropertyUtils.getProperty(field, "options"));
	}

	/**
	 * Ensure all properties can be retrieved using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test(expected = NoSuchMethodException.class)
	public void testGetNoSuchMethodBeanProperty() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Field<String> field = this.construct(String.class);
		BeanUtils.getProperty(field, "methodThatDoesntExist");
	}

	/**
	 * Ensure an item from an array can be retrieved using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetIndexedBeanProperty() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Field<String[]> field = this.construct(String[].class);
		field.value(new String[] { "one", "two" });
		Assert.assertEquals("two", BeanUtils.getProperty(field, "value[1]"));
	}

	/**
	 * Ensure an item from an array can be set using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testSetIndexedBeanProperty() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Field<String[]> field = this.construct(String[].class);
		field.value(new String[2]);
		BeanUtils.setProperty(field, "value[1]", "two");
		Assert.assertEquals("two", field.getValue()[1]);
	}

	/**
	 * Ensure an item from a map can be retrieved using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetMappedBeanProperty() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Field<Map<String, String>> field = this
				.construct((Class<Map<String, String>>) (Class<?>) Map.class);
		field.value(new HashMap<String, String>());
		field.getValue().put("one", "sample");
		Assert.assertEquals("sample", BeanUtils.getProperty(field, "value(one)"));
	}

	/**
	 * Ensure an item from a map can be set using {@link BeanUtils}.
	 *
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testSetMappedBeanProperty() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Field<Map<String, String>> field = this
				.initialize((Map<String, String>) new HashMap<String, String>());
		BeanUtils.setProperty(field, "value(one)", "sample");
		System.err.println(field);
		Assert.assertEquals("sample", field.getValue().get("one"));
	}
}
