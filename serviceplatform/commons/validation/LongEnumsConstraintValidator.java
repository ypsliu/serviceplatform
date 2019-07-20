/**
 * 
 */
package com.ruixue.serviceplatform.commons.validation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * the constraint validator for LongEnum
 * 
 * @author shangchunming@rkylin.com.cn
 *
 */
public class LongEnumsConstraintValidator implements ConstraintValidator<LongEnum, Collection<Long>> {

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
	public boolean isValid(final Collection<Long> value, final ConstraintValidatorContext context) {
		if (value != null && !value.isEmpty()) {
			for (final Long val1 : value) {
				boolean found = false;
				for (final long val2 : this.values) {
					if (val2 == val1.longValue()) {
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
