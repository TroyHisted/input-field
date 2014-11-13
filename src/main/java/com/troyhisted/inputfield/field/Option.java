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

/**
 * Represents a value that could be selected as the desired value for a field.
 *
 * <p>
 * The value property is the value that is submitted when the particular option is selected by the user.
 * Depending on the input control used, this may also be the value that is displayed to the user. Certain
 * controls like a text input with a datalist do not allow the value and label to differ. As such, the label
 * will be ignored and the value will be used both as the displayed text and as the submitted value.
 *
 * <p>
 * The label is the description of the option that is presented to the user. It is from this value that the user
 * must determine if the option is their desired option. Some controls do not allow the label to differ from
 * the value, in those cases, the value will be used instead of the label.
 *
 * <p>
 * The group is a seldom used aspect of an option. Most notably associated with a select control where the group
 * is interpreted as an optGroup value, the group provides a label for distinguishing a group of options. When
 * used, each option in the group must have the same group value. Additionally, multiple options of the same
 * group must be ordered consecutively if they are to be displayed in a single group. Any time a new group is
 * encountered a new group label is displayed.
 *
 * <p>
 * Some controls provide the ability to prevent a particular option from being chosen. When an option is used
 * for one of those controls the enabled property can be used to specify whether a user is allowed or disallowed
 * from selecting the option.
 *
 * @author Troy Histed
 */
public interface Option {

	/**
	 * @return the value
	 */
	String getValue();

	/**
	 * @return the label
	 */
	String getLabel();

	/**
	 * @return the group
	 */
	String getGroup();

	/**
	 * @return <code>true</code> if the option is enabled (not disabled)
	 */
	boolean isEnabled();
}