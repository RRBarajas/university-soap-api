package com.choice.university.service.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String entity, Long id) {
    super(String.format("%s '%d' does not exist", entity, id));
  }
}
