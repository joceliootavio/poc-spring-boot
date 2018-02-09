package com.ia.dell.springbootsample.exception;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ia.dell.springbootsample.controller.LoginController;

@ControllerAdvice(basePackageClasses=LoginController.class)
@RequestMapping(produces = "application/json")
public class RestResponseEntityExceptionHandler {
 
    @ExceptionHandler(value = { ConstraintViolationException.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<?> handleValidation(ConstraintViolationException ex) {
    	String errors = ex.getConstraintViolations().stream()
    			.map(c -> ((PathImpl)c.getPropertyPath()).getLeafNode().getName() + ": " + c.getMessage())
    			.collect(Collectors.joining(", "));
    	return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}