package com.nisum.api.exception;

public class UnprocessableEntity extends RuntimeException {

  public UnprocessableEntity(String message) {
    super(message);
  }

}