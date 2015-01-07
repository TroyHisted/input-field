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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

/**
 * Specialized list designed for use with BeanUtils.
 *
 * <p>
 * This class alters the behavior of a list in a couple important ways. First, calling <em>get</em> with an index
 * greater than the size of the list will result in <em>null</em> being returned instead of an
 * {@link IndexOutOfBoundsException}. Secondly, calling <em>set</em> with an index greater than the size of the
 * list will cause the list to grow to the index size by inserting null values into the list.
 *
 * <p>
 * The list is constructed with the specific class type object that it is designed to hold. This is used when
 * BeanUtils are used to add items to the list. When BeanUtils attempts to add a new item to the list, the
 * correct type is known, so BeanUtils can properly convert the value to the correct type. It is necessary to
 * pass in the type because generics are lost after compilation due to type erasure.
 *
 * @author Troy Histed
 *
 * @param <T>
 *            list type
 */
public class DynaList<T> extends ArrayList<T> implements DynaClass, DynaBean {

	/**
	 * Indication of whether the previous action was successful.
	 */
	private boolean successful = true;

	private final DynaProperty listDynaProperty;

	/**
	 * Creates a DynaList.
	 *
	 * @param listType
	 *            the type of object the list will hold
	 * @return new DynaList
	 */
	public static <T> DynaList<T> construct(Class<? extends T> listType) {
		return new DynaList<T>(listType);
	}

	/**
	 * Constructor.
	 *
	 * @param listType
	 *            the type of object the list will hold
	 */
	public DynaList(Class<? extends T> listType) {
		this.listDynaProperty = new DynaProperty(null, listType);
	}

	/**
	 * Adds the specified item to the list and returns it.
	 *
	 * <p>
	 * Provides a shorthand for times when an item is to be added to a list and assigned to a local variable.
	 *
	 * <p>
	 * An indication on whether the add was successful can be retrieved via {@link #wasSuccessful()}.
	 *
	 * @param aItem
	 *            the item to add
	 * @return the item that was added to the list
	 */
	public T addItem(T aItem) {
		this.successful = super.add(aItem);
		return aItem;
	}

	/**
	 * Adds the specified items to the list and returns them.
	 *
	 * <p>
	 * Provides a shorthand for times when a items are to be added to a list and assigned to a local variable.
	 *
	 * <p>
	 * An indication on whether the add was successful can be retrieved via {@link #wasSuccessful()}.
	 *
	 * @param aItems
	 *            the items to add
	 * @return the items that were added to the list
	 */
	public Collection<? extends T> addAllItems(Collection<? extends T> aItems) {
		this.successful = this.addAll(aItems);
		return aItems;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(T e) {
		return this.successful = super.add(e);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(Collection<? extends T> c) {
		return this.successful = super.addAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return this.successful = super.addAll(index, c);
	}

	/**
	 * Sets the value into the array at the specified index.
	 *
	 * Calling <em>set</em> with an index greater than the size of the list will cause the list to grow to the
	 * index size by inserting null values into the list.
	 *
	 * @param index
	 *            the index where the element is to be inserted
	 * @param element
	 *            the object to be inserted
	 * @return the element that was previously at the specified index
	 */
	@Override
	public T set(int index, T element) {
		while (index >= super.size()) {
			this.add(null);
		}
		return super.set(index, element);
	}

	/**
	 * Returns the item in the list at the specified index.
	 *
	 * @param index
	 *            the location in the array
	 * @return the element at the specified index or <em>null</em> if the index is greater than the array size
	 */
	@Override
	public T get(int index) {
		if (index >= super.size()) {
			return null;
		}
		return super.get(index);
	}

	/**
	 * Indicates whether the previous action was successful.
	 *
	 * @return <code>true</code> if the previous action defined by {@link DynaList} was successful
	 */
	public boolean wasSuccessful() {
		return this.successful;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(String name, String key) {
		throw new UnsupportedOperationException("Map operations are not supported on a list.");
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(String name) {
		throw new UnsupportedOperationException("A List has no properties.");
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(String name, int index) {
		throw new UnsupportedOperationException("A List has no properties.");
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(String name, String key) {
		throw new UnsupportedOperationException("Map operations are not supported on a list.");
	}

	/**
	 * {@inheritDoc}
	 */
	public DynaClass getDynaClass() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(String name, String key) {
		throw new UnsupportedOperationException("Map operations are not supported on a list.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(String name, Object value) {
		throw new UnsupportedOperationException("Map operations are not supported on a list.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(String name, int index, Object value) {
		throw new UnsupportedOperationException("A List has no properties.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(String name, String key, Object value) {
		throw new UnsupportedOperationException("Map operations are not supported on a list.");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public DynaProperty getDynaProperty(String name) {
		return this.listDynaProperty;
	}

	/**
	 * {@inheritDoc}
	 */
	public DynaProperty[] getDynaProperties() {
		return null;
	}

	/**
	 * @throws IllegalStateException
	 *             DynaList class cannot be dynamically instantiated
	 */
	public DynaBean newInstance() throws IllegalAccessException, InstantiationException {
		throw new IllegalStateException("DynaList class cannot be dynamically instantiated.");
	}

}
