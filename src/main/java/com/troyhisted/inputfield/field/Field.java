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

import com.troyhisted.inputfield.message.Message;

/**
 * Represents a field that a user can interact with to provide information to the application.
 *
 * <p>
 * A field is the smallest validatable piece of input. Typically a Field will simply wrap a String, however there
 * are circumstances where wrapping a more complex object may be appropriate. An example might be a ZipCode
 * object that contains both the zip and additional 4.
 *
 * <p>
 * A field provides both bean properties and fluent setters for the following:
 * <dl>
 * <dt>value
 * <dd>The wrapped value of the field.
 * <dt>label
 * <dd>The label for the input field on the form.
 * <dt>message
 * <dd>A message describing the state of the field, generally used for presenting an error message.
 * <dt>options
 * <dd>A list of values that the user can choose from.
 * </dl>
 *
 * <p>
 * A field may be erred through the {@link Message} it contains. If the message is of an error type, then the
 * field is considered to be erred. Likewise, if the field has no message, it is not erred. A {@link Message} may
 * represent many other states of validation as well.
 *
 * <p>
 * Each property is also exposed with a fluent setter. These setters both set the property value and return the
 * {@link Field}. This allows for convenient chaining of properties when constructing a {@link Field}.
 *
 * @author Troy Histed
 *
 * @param <T>
 *            The class type that the field wraps
 */
public interface Field<T> {

	/**
	 * Compares the values of two fields.
	 *
	 * <p>
	 * The comparison will return <code>false</code> when compareTo is null. Otherwise, returns the result of
	 * calling equals on the field value using the value of the compareTo field as the argument.
	 *
	 * @param compareTo
	 *            the field to compare to
	 * @return <code>true</code> if the value of the compareTo field is equal to the value of this field
	 */
	boolean isEqual(Field<? extends T> compareTo);

	/**
	 * Returns the formatted message text for the field.
	 *
	 * @return formatted message text
	 */
	String getMessageText();

	/**
	 * Set the value of the field.
	 *
	 * @param aValue
	 *            the value to set
	 * @return the field
	 */
	Field<? extends T> value(T aValue);

	/**
	 * Returns the value of the field.
	 *
	 * @return the value
	 */
	T getValue();

	/**
	 * Set the value of the field.
	 *
	 * @param aValue
	 *            the value to set
	 */
	void setValue(T aValue);

	/**
	 * Sets the message for the field.
	 *
	 * @param message
	 *            the message to set
	 * @return the field
	 */
	Field<? extends T> message(Message message);

	/**
	 * @return the message
	 */
	Message getMessage();

	/**
	 * Sets the message for the field.
	 *
	 * @param message
	 *            the message to set
	 */
	void setMessage(Message message);

	/**
	 * Sets the label.
	 *
	 * @param label
	 *            label
	 * @return the field
	 */
	Field<? extends T> label(String label);

	/**
	 * @return the label
	 */
	String getLabel();

	/**
	 * Sets the label.
	 *
	 * @param label
	 *            label
	 */
	void setLabel(String label);

	/**
	 * @param options
	 *            the options to set
	 * @return the field
	 */
	Field<? extends T> options(Iterable<?> options);

	/**
	 * @return the options
	 */
	Iterable<?> getOptions();

	/**
	 * @param options
	 *            the options to set
	 */
	void setOptions(Iterable<?> options);

}
