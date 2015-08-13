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
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * Tag for building a Checkbox input or multiple Checkbox inputs.
 *
 * @author Troy Histed
 */
public class Checkbox extends AbstractInputRenderer implements InputRenderer {

	/**
	 * Supports checkbox input.
	 *
	 * {@inheritDoc}
	 */
	public boolean supports(String type) {
		return "checkbox".equals(type);
	}

	/**
	 * Generates the HTML for radio input(s).
	 *
	 * {@inheritDoc}
	 */
	public void render(Input input, JspWriter out) throws JspException, IOException {

		final List<RendererOption> computedOptions = this.generateOptions(input);

		if (computedOptions == null) {
			// It's a single check-box
			out.println("<input type=\"checkbox\"");

			if (input.getSubmitValue() == null || "".equals(input.getSubmitValue())) {
				// just use the value as the value
				out.println(" value=\"" + input.getValue() + "\"");
			} else {
				out.println(" value=\"" + input.getSubmitValue() + "\"");
				if (input.getSubmitValue().toString().equals(String.valueOf(input.getValue()))) {
					out.println(" checked");
				}
			}

			this.writeAttributes(input.getDynamicAttributes(), out);
			out.print(">");
		} else {
			// It's multiple check-boxes
			for (final RendererOption option : computedOptions) {
				out.println("<input type=\"checkbox\"");

				final Map<String, Object> dynamicAttributes = input.getDynamicAttributes();

				final Object id = dynamicAttributes.remove("id");
				if (id != null) {
					out.print(" id=\"" + id + "_" + this.sanitizeForId(option.getValue()) + "\"");
				}

				if (!option.isEnabled()) {
					out.print(" disabled");
				}

				if (option.isSelected()) {
					out.print(" checked");
				}

				this.writeAttributes(input.getDynamicAttributes(), out);
				out.print(">");

				out.print(option.getLabel());
			}
		}
	}

	/**
	 * Replaces spaces with underscores.
	 *
	 * @param string
	 *            the string to sanitize
	 * @return the string
	 */
	private String sanitizeForId(String string) {
		return string.replace(" ", "_");
	}
}
