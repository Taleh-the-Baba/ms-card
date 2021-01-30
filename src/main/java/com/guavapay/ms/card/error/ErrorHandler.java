package com.guavapay.ms.card.error;


import com.guavapay.ms.card.error.model.ErrorLevel;
import com.guavapay.ms.card.error.model.RestErrorResponse;
import com.guavapay.ms.card.error.model.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private static final String GLOBAL_VALIDATION_ROOT = "";

    @Resource
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestErrorResponse handleInternalServerErrors(Exception ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("Exception, uuid: {}, message: {}", uuid, ex.getMessage());
        return new RestErrorResponse(
                uuid,
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCardPeriodException.class)
    public RestErrorResponse handleInvalidCardPeriodException(InvalidCardPeriodException ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("InvalidCardPeriodException, uuid: {}, message: {}", uuid, ex.getErrorMessage());
        return new RestErrorResponse(
                uuid,
                HttpStatus.BAD_REQUEST.name(),
                ex.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCardTypeException.class)
    public RestErrorResponse handleInvalidCardTypeException(InvalidCardTypeException ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("InvalidCardTypeException, uuid: {}, message: {}", uuid, ex.getErrorMessage());
        return new RestErrorResponse(
                uuid,
                HttpStatus.BAD_REQUEST.name(),
                ex.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public RestErrorResponse handleInvalidTokenException(InvalidTokenException ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("InvalidTokenException, uuid: {}, message: {}", uuid, ex.getErrorMessage());
        return new RestErrorResponse(
                uuid,
                HttpStatus.UNAUTHORIZED.name(),
                ex.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderNotFoundException.class)
    public RestErrorResponse handleOrderNotFoundException(OrderNotFoundException ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("OrderNotFoundException, uuid: {}, message: {}", uuid, ex.getErrorMessage());
        return new RestErrorResponse(
                uuid,
                HttpStatus.BAD_REQUEST.name(),
                ex.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadySubmittedException.class)
    public RestErrorResponse handleAlreadySubmittedException(AlreadySubmittedException ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("AlreadySubmittedException, uuid: {}, message: {}", uuid, ex.getErrorMessage());
        return new RestErrorResponse(
                uuid,
                HttpStatus.BAD_REQUEST.name(),
                ex.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyHaveCardTypeInPendingException.class)
    public RestErrorResponse handleAlreadyHaveCardTypeInPendingException(AlreadyHaveCardTypeInPendingException ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("AlreadyHaveCardTypeInPendingException, uuid: {}, message: {}", uuid, ex.getErrorMessage());
        return new RestErrorResponse(
                uuid,
                HttpStatus.BAD_REQUEST.name(),
                ex.getErrorMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ValidationError> checks = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        checks.addAll(
                bindingResult
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new ValidationError(
                                ErrorLevel.ERROR,
                                fieldError.getField(),
                                messageSource.getMessage(
                                        Objects.requireNonNull(fieldError.getDefaultMessage()),
                                        fieldError.getArguments(),
                                        fieldError.getDefaultMessage(),
                                        Locale.ENGLISH)

                        ))
                        .collect(Collectors.toList())
        );
        checks.addAll(
                bindingResult
                        .getGlobalErrors()
                        .stream()
                        .map(globalError -> new ValidationError(
                                ErrorLevel.ERROR,
                                GLOBAL_VALIDATION_ROOT,
                                messageSource.getMessage(Objects.requireNonNull(globalError.getDefaultMessage()),
                                        globalError.getArguments(),
                                        Locale.ENGLISH)
                        ))
                        .collect(Collectors.toList())
        );
        String uuid = UUID.randomUUID().toString();
        log.error("MethodArgumentNotValidException, uuid: {}, message: {}",
                uuid, ex.getMessage());
        return new ResponseEntity<>(
                new RestErrorResponse(
                        UUID.randomUUID().toString(),
                        HttpStatus.BAD_REQUEST,
                        checks),
                headers,
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public RestErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<ValidationError> checks = constraintViolations.stream()
                .map(constraintViolation -> new ValidationError(
                        ErrorLevel.ERROR,
                        constraintViolation.getPropertyPath().toString(),
                        messageSource.getMessage(
                                constraintViolation.getMessage(),
                                null,
                                constraintViolation.getMessage(),
                                Locale.ENGLISH
                        )
                )).collect(Collectors.toList());
        String uuid = UUID.randomUUID().toString();
        log.error("ConstraintViolationException, uuid: {}, message: {}",
                uuid, ex.getMessage());
        return new RestErrorResponse(
                UUID.randomUUID().toString(),
                HttpStatus.BAD_REQUEST,
                checks);
    }
}
