package com.project.rdv.exception;

public class ErrorRequestException extends RuntimeException {

  public ErrorRequestException(String message) {
    super(message);
  }

  public ErrorRequestException() {
    super();
  }

}
