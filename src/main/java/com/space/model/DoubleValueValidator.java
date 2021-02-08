package com.space.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleValueValidator implements ConstraintValidator<DoubleBetween, Double> {

  private double min;
  private double max;

  @Override
  public void initialize(DoubleBetween constraintAnnotation) {
    this.min = constraintAnnotation.min();
    this.max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
    if (aDouble == null) return false;
    if (Math.round(aDouble*100) >= Math.round(this.min * 100)  &&
            Math.round(aDouble*100) <= Math.round(this.max * 100)) {
      return true;
    } else return false;
  }
}
