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
package com.troyhisted.inputfield.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Test;

import com.troyhisted.inputfield.field.DynaField;
import com.troyhisted.inputfield.field.Field;

/**
 * Tests the {@link DynaField} class.
 *
 * @author Troy Histed
 */
public class DynaListTest {

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
	 * Verify a {@link DynaField} works properly with a {@link DynaList}.
	 *
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testDynaListInDynaField() throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		final Field<? extends List<Direction>> field = DynaField.initialize(DynaList.construct(Direction.class));
		BeanUtils.setProperty(field, "value.[0]", "South");
		Assert.assertEquals(Direction.South, field.getValue().get(0));
		Assert.assertEquals(Direction.South, PropertyUtils.getProperty(field, "value.[0]"));
	}

	/**
	 * Verify that an indexed property can be set and that the list will grow to the specified size.
	 *
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testSetIndexedProperty() throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		final List<String> list = new DynaList<String>(String.class);
		BeanUtils.setProperty(list, "[2]", "value");
		Assert.assertEquals("value", list.get(2));
		Assert.assertNull(list.get(0));
		Assert.assertNull(list.get(1));
		Assert.assertEquals(3, list.size());
	}

	/**
	 * Verify get returns the value of the array at the correct index.
	 *
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetIndexedProperty() throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		final List<String> list = new DynaList<String>(String.class);
		list.add("value");
		Assert.assertEquals("value", BeanUtils.getProperty(list, "[0]"));
	}

	/**
	 * Verify calling get with an index greater than the size of the list returns null.
	 *
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetIndexedPropertyGreaterThanArraySize() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		final List<String> list = new DynaList<String>(String.class);
		Assert.assertNull(BeanUtils.getProperty(list, "[0]"));
	}

	/**
	 * Verify that an enum can be set and that the list using the registered converter.
	 *
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testSetIndexedEnum() throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		final List<Direction> list = DynaList.construct(Direction.class);
		BeanUtils.setProperty(list, "[2]", "North");
		Assert.assertEquals(Direction.North, list.get(2));
	}

	/**
	 * Verify an enum can be returned from the list.
	 *
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testGetIndexedEnum() throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		final List<Direction> list = DynaList.construct(Direction.class);
		list.add(Direction.North);
		Assert.assertEquals(Direction.North.name(), BeanUtils.getProperty(list, "[0]"));
		Assert.assertEquals(Direction.North, PropertyUtils.getProperty(list, "[0]"));
	}

	/**
	 * Verify add item will add the item to the list and then return it.
	 *
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testAddItem() throws IllegalAccessException, InvocationTargetException,
	NoSuchMethodException {
		final DynaList<Direction> list = DynaList.construct(Direction.class);
		Assert.assertEquals(Direction.North, list.addItem(Direction.North));
		Assert.assertEquals(Direction.North, list.get(0));
		Assert.assertTrue(list.wasSuccessful());
	}

	/**
	 * Verify add items will add the items to the list and then return them.
	 *
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testAddItems() throws IllegalAccessException, InvocationTargetException,
	NoSuchMethodException {
		final DynaList<Direction> list = DynaList.construct(Direction.class);
		final List<Direction> directions = Arrays.asList(Direction.North, Direction.East);
		Assert.assertEquals(directions, list.addAllItems(directions));
		Assert.assertEquals(Direction.North, list.get(0));
		Assert.assertEquals(Direction.East, list.get(1));
		Assert.assertTrue(list.wasSuccessful());
	}

	/**
	 * Verify add items will add the items to the list at the specified index.
	 *
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testAddItemsAtIndex() throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		final DynaList<Direction> list = DynaList.construct(Direction.class);
		final List<Direction> directions = Arrays.asList(Direction.North, Direction.East);
		final List<Direction> directions2 = Arrays.asList(Direction.South, Direction.West);
		list.addAllItems(directions);
		Assert.assertTrue(list.addAll(0, directions2));
		Assert.assertEquals(Direction.South, list.get(0));
		Assert.assertEquals(Direction.West, list.get(1));
		Assert.assertTrue(list.wasSuccessful());
	}

}
