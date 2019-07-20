/**
 * 
 */
package com.ruixue.serviceplatform.commons.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * the constraint validator for StringEnum
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class StringEnumConstraintValidator implements ConstraintValidator<StringEnum, String> {

	private String[] values;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(final StringEnum constraintAnnotation) {
		this.values = constraintAnnotation.value();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		for (final String val : this.values) {
			if (val.equals(value)) {
				return true;
			}
		}
		return false;
	}

}
