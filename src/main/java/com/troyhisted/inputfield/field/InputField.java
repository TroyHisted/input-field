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

import org.apache.commons.lang3.StringUtils;

import com.troyhisted.inputfield.message.Message;

/**
 * Represents a field that a user can interact with to provide information to the application.
 *
 * <p>
 * A field is the smallest validatable piece of input. Typically a Field will simply wrap a String, however there
 * are circumstances where wrapping a more complex object may be appropriate. An example might be a ZipCode
 * object that contains both the zip and additional 4.
 *
 * @author Troy Histed
 * @param <T>
 *            The type of field
 */
public class InputField<T> implements Field<T> {

	private T value;
	private String label;
	private Message message;
	private Iterable<?> options;

	/**
	 * Convenience constructor for a field.
	 *
	 * @return a new field
	 */
	public static <T> InputField<T> create() {
		return new InputField<T>();
	}

	/**
	 * {@inheritDoc}
	 */

	public boolean isEqual(Field<? extends T> compareTo) {
		if (compareTo != null && this.value != null) {
			return this.value.equals(compareTo.getValue());
		}
		return false;
	}

	/**
	 * Performs the standard equals comparison.
	 *
	 * <p>
	 * Deprecated because it is unlikely that comparing two field objects is the desired behavior.
	 *
	 * <p>
	 * {@link Field#isEqual(Field)}
	 */
	@Override
	@Deprecated
	public boolean equals(Object compareTo) {
		return super.equals(compareTo);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Provides additional placeholder support for the field's label using either {label} or {Label} and the
	 * field's value using {value}.
	 *
	 */
	public String getMessageText() {
		if (this.message != null) {
			return this.message.getText().replace("{label}", String.valueOf(this.label))
					.replace("{Label}", String.valueOf(StringUtils.capitalize(this.label)))
					.replace("{value}", String.valueOf(this.value));
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public InputField<? extends T> value(T aValue) {
		this.value = aValue;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public T getValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setValue(T aValue) {
		this.value = aValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public InputField<? extends T> message(Message message) {
		this.message = message;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public Message getMessage() {
		return this.message;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	public InputField<? extends T> label(String label) {
		this.label = label;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * {@inheritDoc}
	 */
	public InputField<? extends T> options(Iterable<?> options) {
		this.options = options;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<?> getOptions() {
		return this.options;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setOptions(Iterable<?> options) {
		this.options = options;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Field [value=" + this.value + ", label=" + this.label + ", message=" + this.message + "]";
	}

}
