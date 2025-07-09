package com.uece.coffeebreak.handler;

import com.uece.coffeebreak.entity.error.StandardError;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<?> database(DatabaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<StandardError> handleExpiredJwt(ExpiredJwtException e, HttpServletRequest request) {
        String error = "Token expired";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> handleAccessDenied(AccessDeniedException e, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGeneric(Exception e, HttpServletRequest request) {
        String error = "Internal server error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
        return ResponseEntity.status(status).body(err);
    }
}
