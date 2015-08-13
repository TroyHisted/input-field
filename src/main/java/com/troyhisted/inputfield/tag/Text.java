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
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.troyhisted.inputfield.field.Option;

/**
 * Renders an HTML Input tag of type Text.
 *
 * @author Troy Histed
 */
public class Text extends AbstractInputRenderer {

	/**
	 * Supports <em>text</em> input.
	 *
	 * {@inheritDoc}
	 */
	public boolean supports(String type) {
		return "text".equals(type);
	}

	/**
	 * Renders an HTML Input tag of type Text.
	 *
	 * <p>
	 * The body of the tag will be rendered after the input tag.
	 *
	 * {@inheritDoc}
	 */
	public void render(Input input, JspWriter out) throws JspException, IOException {
		if (input.getOptions() instanceof Collection) {
			final Object dataListId = input.getDynamicAttributes().get("list");
			final String id = dataListId == null ? this.nextRandomId() : String.valueOf(dataListId);
			this.writeInputTagWithList(input, id, out);
			input.doBody(out);
			this.writeDatalist(input.getOptions(), input, id, out);
		} else {
			this.writeInputTag(input, out);
			input.doBody(out);
		}
	}

	/**
	 * Writes an input tag and adds any of the dynamic attributes from the input.
	 *
	 * @param input
	 *            the input
	 * @param out
	 *            the writer to use
	 * @throws IOException
	 */
	void writeInputTag(Input input, JspWriter out) throws IOException {
		out.println("<input");
		out.println(" value=\"" + input.getValue() + "\"");
		this.writeAttributes(input.getDynamicAttributes(), out);
		out.println(">");
	}

	/**
	 * Writes an input tag and adds any of the dynamic attributes from the input as well as setting a list
	 * attribute for a datalist.
	 *
	 * <p>
	 * If the input has options and no id, an id will be generated for it.
	 *
	 * @param input
	 *            the input
	 * @param dataListId
	 *            the id to use for the list attribute
	 * @param out
	 *            the writer to use
	 * @throws IOException
	 */
	void writeInputTagWithList(Input input, String dataListId, JspWriter out) throws IOException {
		out.println("<input");
		out.println(" value=\"" + input.getValue() + "\"");
		final Map<String, Object> dynamicAttributes = input.getDynamicAttributes();
		dynamicAttributes.put("list", dataListId);
		this.writeAttributes(dynamicAttributes, out);
		out.println(">");
	}

	/**
	 * Writes out the datalist for an <em>input</em> tag.
	 *
	 * <p>
	 * Only Collections are supported.
	 *
	 * @param options
	 *            the options to write out
	 * @param input
	 *            the input field information
	 * @param id
	 *            the id of the input field
	 * @param out
	 *            the writer to use
	 * @throws IOException
	 */
	void writeDatalist(Object options, Input input, String id, JspWriter out) throws IOException {
		out.println("<datalist id=\"" + id + "\">");
		for (final Object object : (Collection<?>) options) {
			this.writeOption(object, input, out);
		}
		out.println("</datalist>");
	}

	/**
	 * Writes out an option.
	 *
	 * <p>
	 * Only the value of the option will be used.
	 *
	 * @param aOption
	 *            the option to write out
	 * @param input
	 *            the input field information
	 * @param out
	 *            the writer to use
	 * @throws IOException
	 */
	void writeOption(final Object aOption, Input input, JspWriter out) throws IOException {

		String optionValue = null;

		if (aOption instanceof Option) {
			final Option option = (Option) aOption;
			optionValue = option.getValue();
		} else {
			if (input.getValueProperty() != null) {
				try {
					optionValue = BeanUtils.getProperty(aOption, input.getValueProperty());
				} catch (final IllegalAccessException e) {
					throw new IllegalArgumentException("Option has no property " + input.getValueProperty(), e);
				} catch (final InvocationTargetException e) {
					throw new IllegalArgumentException("Option has no property " + input.getValueProperty(), e);
				} catch (final NoSuchMethodException e) {
					throw new IllegalArgumentException("Option has no property " + input.getValueProperty(), e);
				}
			} else if (aOption != null) {
				optionValue = String.valueOf(aOption);
			}
		}

		if (StringUtils.isNotBlank(optionValue)) {
			out.print("<option value=\"" + StringUtils.defaultString(optionValue) + "\">");
		}
	}
}
