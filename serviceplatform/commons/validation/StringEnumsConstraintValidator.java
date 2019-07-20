/**
 * 
 */
package com.ruixue.serviceplatform.commons.validation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * the constraint validator for StringEnum collection
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class StringEnumsConstraintValidator implements ConstraintValidator<StringEnum, Collection<String>> {

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
	public boolean isValid(final Collection<String> value, final ConstraintValidatorContext context) {
		if (value != null && !value.isEmpty()) {
			for (final String val1 : value) {
				boolean found = false;
				for (final String val2 : this.values) {
					if (val2.equals(val1)) {
						found = true;
						break;
					}
				}
				if (!found) {
					return false;
				}
			}
			return true;
		}
		return true;
	}

}
