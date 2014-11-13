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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

import com.troyhisted.inputfield.message.Message;

/**
 * An {@link InputField} that supports using bean properties to set the value using the correct type.
 *
 * <p>
 * Because a {@link Field} has a generic value type, the type is only used during compilation. In order to
 * persist the type past compilation, it must be specified during the Field construction. The type is then
 * exposed to bean methods so that the correct field value type can be set.
 *
 * @author Troy Histed
 *
 * @param <T>
 *            the type of value the field is for
 */
public class DynaField<T> extends InputField<T> implements DynaClass, DynaBean {

	private static final Map<String, DynaProperty> OtherDynaProperties = new HashMap<String, DynaProperty>();
	static {
		OtherDynaProperties.put("label", new DynaProperty("label", String.class));
		OtherDynaProperties.put("message", new DynaProperty("message", Message.class));
		OtherDynaProperties.put("messageText", new DynaProperty("messageText", String.class));
		OtherDynaProperties.put("options", new DynaProperty("options", Iterable.class));
	}

	private final DynaProperty valueDynaProperty;

	private DynaProperty[] dynaProperties;

	/**
	 * Constructs a {@link DynaField} of the specified value type.
	 *
	 * @param valueType
	 *            the class type for the value
	 * @return a new {@link DynaField}
	 */
	public static <T> Field<T> create(Class<T> valueType) {
		return new DynaField<T>(valueType);
	}

	/**
	 * Constructs a {@link DynaField} of the specified value type.
	 *
	 * @param value
	 *            the non-null value to initialize to
	 * @return a new {@link DynaField}
	 */
	public static <T> Field<T> initialize(T value) {
		return new DynaField<T>(value);
	}

	/**
	 * Creates an input field for the specific type of value.
	 *
	 * <p>
	 * The value type is necessary because generic information is lost during compilation, so any auto-mapping
	 * classes need some way to discern the type of the field value. If the value type is not specified, it will
	 * default to Object.
	 *
	 * @param valueType
	 *            the type of the value
	 */
	public DynaField(Class<? extends T> valueType) {
		this.valueDynaProperty = new DynaProperty("value", valueType);
	}

	/**
	 * Creates an input field for the specific type of value.
	 *
	 * <p>
	 * The value type is necessary because generic information is lost during compilation, so any auto-mapping
	 * classes need some way to discern the type of the field value. If the value type is not specified, it will
	 * default to Object.
	 *
	 * @param value
	 *            the non-null value to initialize to
	 */
	public DynaField(T value) {
		this.valueDynaProperty = new DynaProperty("value", value.getClass());
		this.setValue(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(String name, String key) {
		if ("value".equals(name)) {
			return ((Map<String, T>) this.getValue()).containsKey(key);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(String name) {

		if ("value".equals(name)) {
			return this.getValue();
		}
		if ("label".equals(name)) {
			return this.getLabel();
		}
		if ("message".equals(name)) {
			return this.getMessage();
		}
		if ("messageText".equals(name)) {
			return this.getMessageText();
		}
		if ("options".equals(name)) {
			return this.getOptions();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(String name, int index) {
		return "value".equals(name) ? ((Object[]) this.getValue())[index] : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Object get(String name, String key) {
		return "value".equals(name) ? ((Map<String, T>) this.getValue()).get(key) : null;
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
	@SuppressWarnings("unchecked")
	public void remove(String name, String key) {
		if ("value".equals(name)) {
			((Map<String, T>) this.getValue()).remove(key);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void set(String name, Object value) {
		this.value((T) value);
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(String name, int index, Object value) {
		((Object[]) this.getValue())[index] = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void set(String name, String key, Object value) {
		if ("value".equals(name)) {
			((Map<String, Object>) this.getValue()).put(key, value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return DynaField.class.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public DynaProperty getDynaProperty(String name) {
		if ("value".equals(name)) {
			return this.valueDynaProperty;
		}
		return OtherDynaProperties.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public DynaProperty[] getDynaProperties() {
		if (this.dynaProperties == null) {
			this.dynaProperties = new DynaProperty[] { this.valueDynaProperty,
					DynaField.OtherDynaProperties.get("message"), //
					DynaField.OtherDynaProperties.get("messageText"), //
					DynaField.OtherDynaProperties.get("Options"), //
					DynaField.OtherDynaProperties.get("label") }; //
		}
		return this.dynaProperties;
	}

	/**
	 * @throws IllegalStateException
	 *             DynaField class cannot be dynamically instantiated
	 */
	public DynaBean newInstance() throws IllegalAccessException, InstantiationException {
		throw new IllegalStateException("DynaField class cannot be dynamically instantiated.");
	}

}
