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
package com.troyhisted.inputfield.field;

/**
 * An immutable {@link Option} bean.
 *
 * <p>
 * Designed for use with an {@link InputField}.
 *
 * <p>
 * Because an InputOption is immutable, it cannot be modified after creation. The InputOption may be constructed
 * from one of the several parameterized constructors. If a "blank" option is desired, the default constructor
 * can be used. An InputOption is always an enabled option(the user may select it). If an option should be
 * disabled, the InputOption may be wrapped in a {@link Disabled}. Alternatively, the
 * {@link OptionBuilder} may be used to construct the option. The builder may prove more convenient if the
 * attributes of the option are not all known at the time of construction.
 *
 * @author Troy Histed
 */
public class InputOption implements Option {

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
	 * Default constructor.
	 *
	 * <p>
	 * Creates a blank option that has no value, label, or group.
	 */
	public InputOption() {
		super();
	}

	/**
	 * Constructs an input option with the given value.
	 *
	 * <p>
	 * The value will also be used as the label.
	 *
	 * @param value
	 *            the value is what will be submitted by the form
	 */
	public InputOption(String value) {
		this.value = value;
		this.label = value;
	}

	/**
	 * Constructs an input option with the given value and label.
	 *
	 * @param value
	 *            the value is what will be submitted by the form
	 * @param label
	 *            the label is what the user will see
	 */
	public InputOption(String value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * Constructs an input option with the given value, label, and group.
	 *
	 * @param value
	 *            the value is what will be submitted by the form
	 * @param label
	 *            the label is what the user will see
	 * @param group
	 *            the name of a group to which the value belongs and which is used to create an optgroup
	 */
	public InputOption(String value, String label, String group) {
		this.value = value;
		this.label = label;
		this.group = group;
	}

	public String getValue() {
		return this.value;
	}

	public String getLabel() {
		return this.label;
	}

	public String getGroup() {
		return this.group;
	}

	public boolean isEnabled() {
		return true;
	}

	/**
	 * An InputOption that is disabled.
	 *
	 * <p>
	 * Can only be constructed from an existing option.
	 *
	 * @author Troy Histed
	 */
	public static class Disabled extends InputOption {

		/**
		 * Constructs a blank disabled option.
		 */
		public Disabled() {
			super();
		}

		/**
		 * Constructs a disabled option from an enabled option.
		 *
		 * @param option
		 *            the Option to disable
		 */
		public Disabled(Option option) {
			super(option.getValue(), option.getLabel(), option.getGroup());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isEnabled() {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "Disabled" + super.toString();
		}
	}

	/**
	 * Builder for an {@link InputOption}.
	 *
	 * @author Troy Histed
	 */
	public static class OptionBuilder {

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
		 * Indicates whether the option can be chosen.
		 *
		 * <p>
		 * Default value is <code>true</code>.
		 */
		private boolean enabled = true;

		/**
		 * Constructs the input option from the builder.
		 *
		 * @return a finalized input option
		 */
		public InputOption build() {
			if (this.enabled) {
				return new InputOption(this.value, this.label, this.group);
			}
			final InputOption option = new Disabled();
			option.label = this.label;
			option.value = this.value;
			option.group = this.group;
			return option;
		}

		/**
		 * Sets the label of the option.
		 *
		 * @param label
		 *            the label text
		 * @return the builder
		 */
		public OptionBuilder label(String label) {
			this.label = label;
			return this;
		}

		/**
		 * Sets the value of the option.
		 *
		 * @param value
		 *            the value
		 * @return the builder
		 */
		public OptionBuilder value(String value) {
			this.value = value;
			return this;
		}

		/**
		 * Sets the group of the option.
		 *
		 * @param group
		 *            the group text
		 * @return the builder
		 */
		public OptionBuilder group(String group) {
			this.group = group;
			return this;
		}

		/**
		 * Sets the enabled state of the option.
		 *
		 * @param enabled
		 *            whether the option is enabled (setting to false will disable the option)
		 * @return the builder
		 */
		public OptionBuilder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		@Override
		public String toString() {
			return "OptionBuilder [value=" + this.value + ", label=" + this.label + ", group=" + this.group
					+ "]";
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "InputOption [value=" + this.value + ", label=" + this.label + ", group=" + this.group + "]";
	}

}
