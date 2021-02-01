package com.space.controller;

import com.space.model.ShipType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

//public class ShipOrderValidator implements ConstraintValidator<ShipOrderSubSet, ShipOrder> {
public class ShipTypeValidator implements ConstraintValidator<ShipTypeSubSet, String> {
  private ShipType[] subset;

  @Override
  public void initialize(ShipTypeSubSet constraint) {
    this.subset = constraint.anyOf();
  }

//  @Override
//  public boolean isValid(ShipOrder value, ConstraintValidatorContext context) {
//    return value == null || Arrays.asList(subset).contains(value);
//  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    System.out.println("Arrays.asList(subset) = " + Arrays.asList(subset).toString());
    System.out.println("value = " + value);
    System.out.println("ShipOrder.valueOf(value) = " + ShipType.valueOf(value));

    return value == null || Arrays.asList(subset).contains(ShipType.valueOf(value));
  }
}
