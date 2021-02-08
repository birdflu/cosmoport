package com.space.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = DoubleValueValidator.class)
@Documented
public @interface DoubleBetween {
  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

  String message() default "{Double.invalid}";

  double min();

  double max();
}
