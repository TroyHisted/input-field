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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;

/**
 * Tag for building a Select input.
 *
 * @author Troy Histed
 */
public class Select extends AbstractInputRenderer implements InputRenderer {

	/**
	 * Supports select input.
	 *
	 * {@inheritDoc}
	 */
	public boolean supports(String type) {
		return "select".equals(type);
	}

	/**
	 * Renders an HTML select tag.
	 *
	 * Any body elements will be nested within the select tag before any options are written.
	 *
	 * {@inheritDoc}
	 */
	public void render(Input input, JspWriter out) throws JspException, IOException {
		this.writeOpeningSelectTag(input, out);
		input.doBody(out);
		this.writeOptions(this.generateOptions(input), out);
		this.writeClosingSelectTag(out);
	}

	/**
	 * Writes a select tag and adds any of the dynamic attributes from the input.
	 *
	 * @param input
	 *            the input
	 * @param out
	 *            the writer to use
	 * @throws IOException
	 */
	void writeOpeningSelectTag(Input input, JspWriter out) throws IOException {
		out.print("<select");
		this.writeAttributes(input.getDynamicAttributes(), out);
		out.println(">");
	}

	/**
	 * writes a closing select tag.
	 *
	 * @param out
	 *            the writer to use
	 * @throws IOException
	 */
	void writeClosingSelectTag(JspWriter out) throws IOException {
		out.println("</select>");
	}

	/**
	 * Writes out the options for a <em>select</em> tag. If any of the options match the value of the input the
	 * option will be selected.
	 *
	 * <p>
	 * Only Collections are supported.
	 *
	 * @param options
	 *            the options to write out
	 * @param out
	 *            the writer to use
	 * @throws IOException
	 */
	void writeOptions(List<RendererOption> options, JspWriter out) throws IOException {
		String previousOptGroup = null;
		if (options != null) {
			for (final RendererOption option : options) {
				this.writeOptGroup(option.getGroup(), previousOptGroup, out);
				previousOptGroup = option.getGroup();
				this.writeOption(option, out);
			}
			// make sure to close any open optgroup tag
			this.writeOptGroup(null, previousOptGroup, out);
		}
	}

	/**
	 * Writes out an option, pre-selecting it if it matches the value.
	 *
	 * @param option
	 *            the option to write out
	 * @param out
	 *            the writer to use
	 * @throws IOException
	 */
	void writeOption(final RendererOption option, JspWriter out) throws IOException {
		out.print("<option");
		if (option.getValue() != null) {
			out.print(" value=\"" + option.getValue() + "\"");
		}
		if (option.isSelected()) {
			out.print(" selected");
		}
		if (!option.isEnabled()) {
			out.print(" disabled");
		}
		out.print(">");
		out.print(StringUtils.defaultString(option.getLabel()));
		out.println("</option>");
	}

	/**
	 * Handles writing out an option group if applicable.
	 *
	 * <p>
	 * An optgroup will be written out so long as the option has an optgroup specified and it differs from the
	 * previous opt group.
	 *
	 * @param optionGroup
	 *            the optionGroup text for the option
	 * @param previousOptGroup
	 *            the previous options opt group text
	 * @param out
	 *            the writer to use
	 * @throws IOException
	 *             exception writing option
	 */
	void writeOptGroup(String optionGroup, String previousOptGroup, final JspWriter out) throws IOException {
		// If the previous optgroup is different than the current one, close out the optgroup tag.
		if (previousOptGroup != null && !previousOptGroup.equals(optionGroup)) {
			out.println("</optgroup>");
		}
		// If there's and optgroup and it's different than the previous one, create a new optgroup tag.
		if (optionGroup != null && !optionGroup.equals(previousOptGroup)) {
			out.println("<optgroup label=\"" + optionGroup + "\">");
		}
	}

}
