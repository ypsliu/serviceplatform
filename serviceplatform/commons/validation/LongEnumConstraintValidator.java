/**
 * 
 */
package com.ruixue.serviceplatform.commons.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * the constraint validator for LongEnum
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class LongEnumConstraintValidator implements ConstraintValidator<LongEnum, Long> {

	private long[] values;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(final LongEnum constraintAnnotation) {
		this.values = constraintAnnotation.value();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(final Long value, final ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		for (final long val : this.values) {
			if (val == value) {
				return true;
			}
		}
		return false;
	}

}
