package com.samples.reactive.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class NotBadWordValidator implements ConstraintValidator<NotBadWord, String> {

  private String[] badWords;

  @Override
  public void initialize(NotBadWord constraintAnnotation) {
    this.badWords = constraintAnnotation.badWords();
  }

  /**
   * A short test of a custom validator checking for bad words in spring
   *
   * @param comment to validate
   * @param constraintValidatorContext
   * @return true if comment is valid, false otherwise
   */
  @Override
  public boolean isValid(String comment, ConstraintValidatorContext constraintValidatorContext) {
    return Stream.of(badWords).noneMatch(comment::contains);
  }
}
