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

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link InputOption} implementation of the {@link Option} interface.
 *
 * @author Troy Histed
 */
public class InputOptionTest {

	/**
	 * Ensure that the default constructor creates a blank option.
	 */
	@Test
	public void testDefaultConstructorCreatesBlankOption() {
		final Option option = new InputOption();
		Assert.assertNull(option.getValue());
		Assert.assertNull(option.getLabel());
		Assert.assertNull(option.getGroup());
		Assert.assertTrue(option.isEnabled());
	}

	/**
	 * Ensure that the single arg constructor creates an option where the key and label match and the
	 * option is enabled.
	 */
	@Test
	public void testSingleArgConstructorCreatesOptionWithMatchingKeyAndLabel() {
		final Option option = new InputOption("North");
		Assert.assertEquals("North", option.getValue());
		Assert.assertEquals("North", option.getLabel());
		Assert.assertNull(option.getGroup());
		Assert.assertTrue(option.isEnabled());
	}

	/**
	 * Ensure that the two arg constructor creates an option where the key and label are both set and the option
	 * is enabled.
	 */
	@Test
	public void testTwoArgConstructorCreatesOptionWithDifferentValueAndLabel() {
		final Option option = new InputOption("n", "North");
		Assert.assertEquals("n", option.getValue());
		Assert.assertEquals("North", option.getLabel());
		Assert.assertNull(option.getGroup());
		Assert.assertTrue(option.isEnabled());
	}

	/**
	 * Ensure that the three arg constructor creates an option where the key, label, and roup are all set and the
	 * option is enabled.
	 */
	@Test
	public void testThreeArgConstructorCreatesKeyLabelGroupOption() {
		final Option option = new InputOption("n", "North", "Direction");
		Assert.assertEquals("n", option.getValue());
		Assert.assertEquals("North", option.getLabel());
		Assert.assertEquals("Direction", option.getGroup());
		Assert.assertTrue(option.isEnabled());
	}

	/**
	 * Ensure that the disabled option wrapper class disables an option.
	 */
	@Test
	public void testDisabledOptionWrapperDisablesOption() {
		final Option option = new InputOption.Disabled();
		final Option option0 = new InputOption.Disabled(new InputOption());
		final Option option1 = new InputOption.Disabled(new InputOption("North"));
		final Option option2 = new InputOption.Disabled(new InputOption("n", "North"));
		final Option option3 = new InputOption.Disabled(new InputOption("n", "North", "Direction"));
		Assert.assertFalse(option.isEnabled());
		Assert.assertFalse(option0.isEnabled());
		Assert.assertFalse(option1.isEnabled());
		Assert.assertFalse(option2.isEnabled());
		Assert.assertFalse(option3.isEnabled());
	}


	/**
	 * Ensure that disabling an option doesn't cause it to lose any values.
	 */
	@Test
	public void testDisabledOptionKeepsValues() {
		final Option option = new InputOption.Disabled(new InputOption("n", "North", "Direction"));
		Assert.assertEquals("n", option.getValue());
		Assert.assertEquals("North", option.getLabel());
		Assert.assertEquals("Direction", option.getGroup());
		Assert.assertFalse(option.isEnabled());
	}
}
