package com.choice.university.service.exception;

public class AlreadyExistsException extends RuntimeException {

  public AlreadyExistsException(String entity, Long id) {
    super(String.format("%s '%d' already exists", entity, id));
  }
}
