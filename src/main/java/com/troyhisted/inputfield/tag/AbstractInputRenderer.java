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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;

import com.troyhisted.inputfield.field.Option;

/**
 * Template {@link InputRenderer} containing many of the common methods used when rendering an input.
 *
 * @author Troy Histed
 */
public abstract class AbstractInputRenderer implements InputRenderer {

	private static final Random random = new Random();

	/**
	 * Takes a map of attributes and writes them out in a name="value" format.
	 *
	 * <p>
	 * If the attribute value is null or evaluates to a blank string, the attribute will not have a value e.g.
	 * "disabled" instead of "disabled=''".
	 *
	 * @param attributes
	 *            the attributes to write out
	 * @param out
	 *            the writer to write to
	 * @throws IOException
	 *             error during write
	 */
	public void writeAttributes(Map<String, Object> attributes, final Writer out) throws IOException {
		for (final Entry<String, Object> attribute : attributes.entrySet()) {
			out.write(" " + attribute.getKey());
			if (attribute.getValue() != null && !"".equals(attribute.getValue().toString())) {
				out.write("=\"" + attribute.getValue() + "\"");
			}
		}
	}

	/**
	 * Builds a list of pre-processed options for the renderer to use while rendering the input.
	 *
	 * <p>
	 * The pre-processing will convert objects to strings and determine if any of the options should be
	 * pre-selected.
	 *
	 * @param input
	 *            the input tag information
	 * @return either null if there are no options or a list of options
	 */
	public List<RendererOption> generateOptions(Input input) {
		if (input.getOptions() instanceof Collection) {
			final List<RendererOption> options = new ArrayList<RendererOption>();
			for (final Object object : (Collection<?>) input.getOptions()) {
				options.add(this.generateOption(object, input));
			}
			return options;
		}
		return null;
	}

	/**
	 * Generates a psuedo random value to be used as an id for an input.
	 *
	 * The id will be prefixed with "input_" and followed by a 5 digit number.
	 *
	 * @return
	 */
	protected String nextRandomId() {
		return "field_" + Math.abs(AbstractInputRenderer.random.nextInt());
	}

	/**
	 * Constructs a {@link RendererOption}.
	 *
	 * @param option
	 *            the non-null option to process
	 * @param input
	 *            the non-null input field information
	 * @return the non-null {@link RendererOption} constructed from the specified information
	 */
	RendererOption generateOption(final Object option, Input input) {

		final RendererOption rendererOption = new RendererOption();

		if (option instanceof Option) {
			rendererOption.setValue(((Option) option).getValue());
			rendererOption.setLabel(((Option) option).getLabel());
			rendererOption.setGroup(((Option) option).getGroup());
			rendererOption.setEnabled(((Option) option).isEnabled());
		} else {
			rendererOption.setValue(this.determineValue(option, input.getValueProperty()));
			rendererOption.setLabel(this.determineLabel(option, input.getLabelProperty()));
			rendererOption.setGroup(this.determineGroup(option, input.getGroupProperty()));
			rendererOption.setEnabled(this.isEnabled(option, input.getEnabledProperty()));
		}

		rendererOption.setSelected(this.isSelected(rendererOption.getValue(), input.getValue()));

		return rendererOption;
	}

	/**
	 * Determines what the value of the option is.
	 *
	 * <p>
	 * If the valueProperty was set, the specified property of the option will be treated as the value. If the
	 * valueProperty is null, the toString of the option will be used.
	 *
	 * @param option
	 *            the option to get the value from
	 * @param valueProperty
	 *            the property of the option to get the value from
	 * @return the value of the option
	 */
	String determineValue(Object option, String valueProperty) {
		if (valueProperty == null) {
			return option == null ? null : String.valueOf(option);
		}
		try {
			return BeanUtils.getProperty(option, valueProperty);
		} catch (final IllegalAccessException e) {
			throw new IllegalArgumentException("Option has no property " + valueProperty, e);
		} catch (final InvocationTargetException e) {
			throw new IllegalArgumentException("Option has no property " + valueProperty, e);
		} catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException("Option has no property " + valueProperty, e);
		}

	}

	/**
	 * Determines what the label of the option is.
	 *
	 * <p>
	 * If the labelProperty was set, the specified property of the option will be treated as the label. If the
	 * labelProperty is null, the toString of the option will be used.
	 *
	 * @param option
	 *            the option to get the label from
	 * @param labelProperty
	 *            the property of the option to get the label from
	 * @return the label of the option
	 */
	String determineLabel(Object option, String labelProperty) {
		if (labelProperty == null) {
			return option == null ? null : String.valueOf(option);
		}
		try {
			return BeanUtils.getProperty(option, labelProperty);
		} catch (final IllegalAccessException e) {
			throw new IllegalArgumentException("Option has no property " + labelProperty, e);
		} catch (final InvocationTargetException e) {
			throw new IllegalArgumentException("Option has no property " + labelProperty, e);
		} catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException("Option has no property " + labelProperty, e);
		}
	}

	/**
	 * Determines what the group of the option is.
	 *
	 * <p>
	 * If the groupProperty was set, the specified property of the option will be treated as the group. If the
	 * groupProperty is null, the group will be considered null.
	 *
	 * @param option
	 *            the option to get the group from
	 * @param groupProperty
	 *            the property of the option to get the group from
	 * @return the group of the option
	 */
	String determineGroup(Object option, String groupProperty) {
		if (groupProperty == null) {
			return null;
		}
		try {
			return BeanUtils.getProperty(option, groupProperty);
		} catch (final IllegalAccessException e) {
			throw new IllegalArgumentException("Option has no property " + groupProperty, e);
		} catch (final InvocationTargetException e) {
			throw new IllegalArgumentException("Option has no property " + groupProperty, e);
		} catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException("Option has no property " + groupProperty, e);
		}
	}

	/**
	 * Determines if an option is enabled.
	 *
	 * <p>
	 * Defaults to true unless the enabledProperty is non-null. If the enabledProperty is non-null this will
	 * check the specified property on the option and return the boolean value of the property.
	 *
	 * @param option
	 *            the option to check
	 * @param enabledProperty
	 *            the name of the property of the option object to determine enabled from
	 * @return <code>true</code> if the option should be enabled
	 */
	boolean isEnabled(final Object option, String enabledProperty) {
		if (enabledProperty == null) {
			return true;
		}
		try {
			return "true".equals(BeanUtils.getProperty(option, enabledProperty));
		} catch (final IllegalAccessException e) {
			throw new IllegalArgumentException("Option has no property " + enabledProperty, e);
		} catch (final InvocationTargetException e) {
			throw new IllegalArgumentException("Option has no property " + enabledProperty, e);
		} catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException("Option has no property " + enabledProperty, e);
		}
	}

	/**
	 * Determines if the specified option should be <em>selected</em>.
	 *
	 * <p>
	 * The option will be selected if it "matches" the value or one of the values (if the value is an array or
	 * list). All options and values are converted to strings via their toString and then compared to determine
	 * equivalence.
	 *
	 * @param aOption
	 *            the option to test
	 * @param aValue
	 *            the value of the input which is used to determine which item(s) to pre-select.
	 * @return <code>true</code> if the option should be considered <em>selected</em>
	 */
	boolean isSelected(String aOption, Object aValue) {
		if (aValue == null || aOption == null) {
			return false;
		} else if (aValue instanceof Object[]) {
			for (final Object val : (Object[]) aValue) {
				if (aOption.equals(val.toString())) {
					return true;
				}
			}
			return false;
		} else if (aValue instanceof Collection<?>) {
			for (final Object val : (Collection<?>) aValue) {
				// if (aOption.equals(val.toString())) {
				if (String.valueOf(aOption).equals(String.valueOf(val))) {
					return true;
				}
			}
			return false;
		} else if (aValue instanceof Map<?, ?>) {
			throw new UnsupportedOperationException("Maps not supported");
		} else {
			// return aOption.equals(aValue.toString());
			return aOption.toString().equals(String.valueOf(aValue));
		}
	}
}
