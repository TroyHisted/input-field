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
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.troyhisted.inputfield.field.Option;

/**
 * A dynamic form input.
 *
 * <p>
 * The input may take on any of the many input types that exist in HTML, potentially even ones that don't exist
 * yet.
 *
 * <p>
 * {@link Input} delegates rendering of the HTML to a capable {@link InputRenderer} service provider.
 *
 * @author Troy Histed
 */
public final class Input extends SimpleTagSupport implements DynamicAttributes {

	/**
	 * Loader for the {@link InputRenderer} service providers.
	 */
	private static final ServiceLoader<InputRenderer> RENDERER_LOADER = ServiceLoader.load(InputRenderer.class);

	/**
	 * Map providing faster lookup for an {@link InputRenderer} based on the input type.
	 */
	private static final Map<String, InputRenderer> RENDERER_CACHE = new HashMap<String, InputRenderer>();

	/**
	 * Register the default renderers.
	 */
	static {
		RENDERER_CACHE.put("text", new Text());
		RENDERER_CACHE.put("select", new Select());
		RENDERER_CACHE.put("radio", new Radio());
		RENDERER_CACHE.put("checkbox", new Checkbox());
	}

	/**
	 * The type of input (defaults to "text").
	 *
	 * <p>
	 * The type specifies which type of form control to render.
	 */
	private String type;

	/**
	 * The current value of the input field. Depending on the type of input, this can range from the text of a
	 * textarea to the value of a select option.
	 */
	private Object value;

	/**
	 * For radio and check-box inputs, this is the value that will be submitted if the radio or check-box is
	 * selected.
	 */
	private String submitValue;

	/**
	 * The options are an array or list of potential values that the user can choose from. Depending on the type
	 * of input, they are presented as anything from select options to a list of check-boxes. For convenience,
	 * integrated support for {@link Option} objects is included, however any list or collection of objects may
	 * be used.
	 */
	private Object options;

	/**
	 * Specifies which property of an option to use as the value. If unspecified, the toString of the option will
	 * be used.
	 */
	private String valueProperty;

	/**
	 * Specifies which property of an option to use as the label. If unspecified, the toString of the option will
	 * be used.
	 */
	private String labelProperty;

	/**
	 * Specifies which property of an option to use as the value. If unspecified, will default to a blank string.
	 */
	private String groupProperty;

	/**
	 * Specifies which property of an option to use as the indicator for enable. If unspecified, all options will
	 * be enabled.
	 */
	private String enabledProperty;

	/**
	 * The dynamic attributes to give to the input.
	 */
	private final Map<String, Object> dynamicAttributes = new HashMap<String, Object>();

	@Override
	public void doTag() throws JspException, IOException {
		final JspWriter out = this.getJspContext().getOut();

		// Default the type to text if it was not specified
		if (this.type == null || "".equals(this.type)) {
			this.type = "text";
		}

		// Attempt to get the renderer from the cache
		final InputRenderer writer = Input.RENDERER_CACHE.get(this.type);
		if (writer != null) {
			writer.render(this, out);
			return;
		}

		// Find a renderer capable of rendering the specified type of input
		for (final InputRenderer renderer : Input.RENDERER_LOADER) {
			if (renderer.supports(this.type)) {
				Input.RENDERER_CACHE.put(this.type, renderer);
				renderer.render(this, out);
				return;
			}
		}

		throw new IllegalArgumentException("No renderer exists to support an input of type " + this.type);
	}

	/**
	 * Invokes the body of the tag, writing the results to the specified writer.
	 *
	 * <p>
	 * If the tag is self closing, or has no body, nothing will be written.
	 *
	 * @param out
	 *            the writer to write the out to
	 * @throws JspException
	 * @throws IOException
	 */
	public void doBody(Writer out) throws JspException, IOException {
		final JspFragment body = this.getJspBody();
		if (body != null) {
			this.getJspBody().invoke(out);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		this.dynamicAttributes.put(localName, value);
	}

	/**
	 * @return dynamic attributes
	 */
	public Map<String, Object> getDynamicAttributes() {
		return this.dynamicAttributes;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the options
	 */
	public Object getOptions() {
		return this.options;
	}

	/**
	 * @param options
	 *            the options to set
	 */
	public void setOptions(Object options) {
		this.options = options;
	}

	/**
	 * @return the submitValue
	 */
	public String getSubmitValue() {
		return this.submitValue;
	}

	/**
	 * @param submitValue
	 *            the submitValue to set
	 */
	public void setSubmitValue(String submitValue) {
		this.submitValue = submitValue;
	}

	/**
	 * @return the valueProperty
	 */
	public String getValueProperty() {
		return this.valueProperty;
	}

	/**
	 * @param valueProperty
	 *            the valueProperty to set
	 */
	public void setValueProperty(String valueProperty) {
		this.valueProperty = valueProperty;
	}

	/**
	 * @return the labelProperty
	 */
	public String getLabelProperty() {
		return this.labelProperty;
	}

	/**
	 * @param labelProperty
	 *            the labelProperty to set
	 */
	public void setLabelProperty(String labelProperty) {
		this.labelProperty = labelProperty;
	}

	/**
	 * @return the groupProperty
	 */
	public String getGroupProperty() {
		return this.groupProperty;
	}

	/**
	 * @param groupProperty
	 *            the groupProperty to set
	 */
	public void setGroupProperty(String groupProperty) {
		this.groupProperty = groupProperty;
	}

	/**
	 * @return the enabledProperty
	 */
	public String getEnabledProperty() {
		return this.enabledProperty;
	}

	/**
	 * @param enabledProperty
	 *            the enabledProperty to set
	 */
	public void setEnabledProperty(String enabledProperty) {
		this.enabledProperty = enabledProperty;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Input [type=" + this.type + ", value=" + this.value + ", submitValue=" + this.submitValue
				+ ", options=" + this.options + ", valueProperty=" + this.valueProperty + ", labelProperty="
				+ this.labelProperty + ", groupProperty=" + this.groupProperty + ", enabledProperty="
				+ this.enabledProperty + ", dynamicAttributes=" + this.dynamicAttributes + "]";
	}

}
