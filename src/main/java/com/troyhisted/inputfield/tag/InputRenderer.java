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
package com.troyhisted.inputfield.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * Handles writing out the HTML for an input tag.
 *
 * <p>
 * Additional Renderer implementations may be registered by implementing this interface and adding the fully
 * qualified class name to the service providers file for {@link InputRenderer}.
 *
 * <p>
 * A single renderer instance may be used multiple times, therefore implementations must be thread safe.
 *
 * @author Troy Histed
 */
public interface InputRenderer {

	/**
	 * Writes the HTML for the specified input.
	 *
	 * @param input the input tag information
	 * @param out the writer to write to
	 * @throws JspException
	 * @throws IOException
	 */
	void render(Input input, JspWriter out) throws JspException, IOException;

	/**
	 * Declares that this renderer is or is not capable of rendering the specified input type.
	 *
	 * @param type type of HTML input in all lower-case ("text", "select", etc...)
	 * @return <code>true</code> if this renderer supports rendering the specified type
	 */
	boolean supports(String type);

}
