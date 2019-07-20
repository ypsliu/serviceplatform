/**
 * 
 */
package com.ruixue.serviceplatform.commons.validation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * the constraint validator for IntegerEnum collection
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class IntegerEnumsConstraintValidator implements ConstraintValidator<IntegerEnum, Collection<Integer>> {

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
	public boolean isValid(final Collection<Integer> value, final ConstraintValidatorContext context) {
		if (value != null && !value.isEmpty()) {
			for (final Integer val1 : value) {
				boolean found = false;
				for (final int val2 : this.values) {
					if (val2 == val1.intValue()) {
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
