package com.nl.abn.bt.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler  {


    /**
     *
     * @param e methodArgumentNotValidException
     * @return bad request
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        AtomicReference<String> errorMessage = new AtomicReference<>("");
        e.getBindingResult().getAllErrors().forEach(error -> errorMessage.set(error.getDefaultMessage()));
        log.error("Bad Request {}", e.getMessage());
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), errorMessage.get(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param e ServiceException
     * @return bad request
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<ExceptionResponse> handleServiceException(ServiceException e) {
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(),HttpStatus.BAD_REQUEST);
        log.error("Bad Request {}", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> UnauthorizedError(ExpiredJwtException e) {
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.FORBIDDEN.value(), e.getMessage(),HttpStatus.FORBIDDEN);
        log.error("Forbidden error {}", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     *
     * @param e RuntimeException
     * @return Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConversionFailedException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(ConversionFailedException e) {
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),"Invalid Player Request",HttpStatus.BAD_REQUEST);
        log.error("Bad Request {}", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param e BadCredentialsException
     * @return Forbidden error
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> UnauthorizedError(BadCredentialsException e) {
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.FORBIDDEN.value(), e.getMessage(),HttpStatus.FORBIDDEN);
        log.error("Forbidden error {}", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     *
     * @param e RuntimeException
     * @return Bad Request
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleInternalError(RuntimeException e) {
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Internal Server Error{}", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }









}