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
 * A basic message with an unimplemented level.
 *
 * @author Troy Histed
 */
public abstract class AbstractMessage implements Message {

	private final String text;

	/**
	 * Constructs a message using the specified text.
	 *
	 * <p>
	 * Any place holders, identified by a number between curly braces (e.g. {0}), will be replaced with the
	 * args variable at the corresponding index.
	 *
	 * <p>
	 * Invoking the constructor as: AbstractMessage("Hello {0}!", "World"); will result in the message
	 * "Hello World!".
	 *
	 * @param message the message text
	 * @param args any message variables
	 */
	public AbstractMessage(String message, String... args) {
		if (message != null && args != null) {
			for (int i = 0; i < args.length; i++) {
				message = message.replace("{" + i + "}", args[i]);
			}
		}
		this.text = message;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract int getLevel();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Message [text=" + this.text + " level=" + this.getLevel() + "]";
	}

}
