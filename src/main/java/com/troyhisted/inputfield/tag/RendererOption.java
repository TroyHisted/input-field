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

/**
 *
 * @author Troy Histed
 */
public class RendererOption {

	/**
	 * The value that will be submitted.
	 */
	private String value;

	/**
	 * The label that the user will see and choose.
	 */
	private String label;

	/**
	 * The name of a group to which the option belongs.
	 */
	private String group;

	/**
	 * Whether the option is enabled.
	 */
	private boolean enabled;

	/**
	 * Whether the option is selected.
	 */
	private boolean selected;

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return this.group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "RendererOption [value=" + this.value + ", label=" + this.label + ", group=" + this.group
				+ ", enabled=" + this.enabled + ", selected=" + this.selected + "]";
	}

}
