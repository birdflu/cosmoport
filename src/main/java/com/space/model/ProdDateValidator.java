package com.space.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProdDateValidator implements ConstraintValidator<ProdDate, Date> {
   public void initialize(ProdDate constraint) {
   }

   public boolean isValid(Date prodDate, ConstraintValidatorContext context) {
      if (prodDate == null) return false;

      boolean res = false;
      try {
         res = prodDate.after(new SimpleDateFormat("yyyy").parse("2800")) &&
                 prodDate.before(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("3019-12-31 23:59:59"));
      } catch (ParseException e) {
         e.printStackTrace();
      }

      return res;
   }
}
