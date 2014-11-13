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

/**
 * Represents a message that can be presented to a user.
 *
 * <p>
 * A message has a level which indicates the significance of the message. There are several default levels which
 * range from debug to error. The implementor may use or expand upon these default levels, or may choose to
 * use their own scheme.
 *
 * @author Troy Histed
 */
public interface Message {

	/**
	 * Debug message has a level of {@value}.
	 */
	public static final int DEBUG_LEVEL = 10;

	/**
	 * Info message has a level of {@value}.
	 */
	public static final int INFO_LEVEL = 20;

	/**
	 * Success message has a level of {@value}.
	 */
	public static final int SUCCESS_LEVEL = 25;

	/**
	 * Warning message has a level of {@value}.
	 */
	public static final int WARNING_LEVEL = 30;

	/**
	 * Error message has a level of {@value}.
	 */
	public static final int ERROR_LEVEL = 40;

	/**
	 * @return message text
	 */
	String getText();

	/**
	 * @return the level
	 */
	int getLevel();

}