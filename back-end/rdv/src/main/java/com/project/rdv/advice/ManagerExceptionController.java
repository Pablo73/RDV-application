package com.project.rdv.advice;

import com.project.rdv.exception.ErrorRequestException;
import com.project.rdv.exception.ForbiddenException;
import com.project.rdv.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class ManagerExceptionController {


  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(
      NotFoundException exception) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
  }

  @ExceptionHandler(ErrorRequestException.class)
  public ResponseEntity<String> handleErrorRequestException(RuntimeException exception) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
  }


  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<String> handleForbiddenException(
      ForbiddenException exception) {
    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(exception.getMessage());
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception exception) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(exception.getMessage());
  }


  @ExceptionHandler(Throwable.class)
  public ResponseEntity<String> handleThrowable(Throwable exception) {
    return ResponseEntity
        .status(HttpStatus.BAD_GATEWAY)
        .body(exception.getMessage());
  }
}
