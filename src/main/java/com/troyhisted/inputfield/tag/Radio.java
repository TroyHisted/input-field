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
 * Tag for building a Radio input or multiple radio inputs.
 *
 * @author Troy Histed
 */
public class Radio extends AbstractInputRenderer implements InputRenderer {

	/**
	 * Supports radio input.
	 *
	 * {@inheritDoc}
	 */
	public boolean supports(String type) {
		return "radio".equals(type);
	}

	/**
	 * Generates the HTML for radio input(s).
	 *
	 * {@inheritDoc}
	 */
	public void render(Input input, JspWriter out) throws JspException, IOException {

		final List<RendererOption> computedOptions = this.generateOptions(input);

		if (computedOptions != null) {
			for (final RendererOption option : computedOptions) {
				out.println("<input type=\"radio\"");

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

	private String sanitizeForId(String string) {
		return string.replace(" ", "_");
	}
}
