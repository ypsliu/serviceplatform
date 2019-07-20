/**
 * 
 */
package com.ruixue.serviceplatform.commons.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * the constraint validator for IntegerEnum
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class IntegerEnumConstraintValidator implements ConstraintValidator<IntegerEnum, Integer> {

	private int[] values;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(final IntegerEnum constraintAnnotation) {
		this.values = constraintAnnotation.value();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		for (final int val : this.values) {
			if (val == value) {
				return true;
			}
		}
		return false;
	}

}
