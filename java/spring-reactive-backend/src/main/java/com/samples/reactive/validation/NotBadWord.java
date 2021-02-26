package com.samples.reactive.validation;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Document
@Constraint(validatedBy = NotBadWordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface NotBadWord {

  String message() default "Bad word in comment";

  String[] badWords();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
