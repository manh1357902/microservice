package com.example.apigateway.exception;

import com.example.apigateway.constant.Constant;
import com.example.apigateway.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the REST API.
 * Handles and logs all exceptions thrown by controllers, returning standardized error responses.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles RuntimeExceptions and returns a meaningful response.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse<Object>> handleRuntimeException(RuntimeException ex) {
        log.error(Constant.RUNTIME_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles IOExceptions and returns a meaningful response.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse<Object>> handleIOException(IOException ex) {
        log.error(Constant.IO_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles NotFoundExceptions and returns a meaningful response.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse<Object>> handleNotFoundException(NotFoundException ex) {
        log.error(Constant.NOT_FOUND_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles BadRequestExceptions and returns a meaningful response.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse<Object>> handleBadRequestException(BadRequestException ex) {
        log.error(Constant.BAD_REQUEST_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles MethodArgumentNotValidException for validation errors.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        log.error(Constant.VALIDATION_EXCEPTION_OCCURRED, ex.getMessage(), ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse<Map<String, String>> errorResponse = ErrorResponse.of(Constant.VALIDATION_ERROR, Constant.VALIDATION_FAILED, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles DataAccessException for database-related errors.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse<Object>> handleDatabaseException(DataAccessException ex) {
        log.error(Constant.DATABASE_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handles DataIntegrityViolationException for database constraint violations.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error(Constant.DATA_INTEGRITY_VIOLATION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles MaxUploadSizeExceededException for file size limit errors.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse<Object>> handleFileSizeLimitExceededException(MaxUploadSizeExceededException ex) {
        log.error(Constant.FILE_SIZE_LIMIT_EXCEEDED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles IllegalArgumentException for invalid arguments.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(Constant.ILLEGAL_ARGUMENT_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles ConflictException for resource conflicts.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse<Object>> handleConflictException(ConflictException ex) {
        log.error(Constant.CONFLICT_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Handles exceptions thrown when the request body is not readable,
     * such as malformed JSON or invalid format.
     *
     * @param ex the HttpMessageNotReadableException thrown by Spring when it fails to deserialize the request body
     * @return a ResponseEntity containing the error response with HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(Constant.FAILED_TO_READ_REQUEST_BODY, ex.getMessage(), ex);

        ErrorResponse<Object> errorResponse = ErrorResponse.of(
                Constant.INVALID_REQUEST_BODY,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    /**
     * Handles HttpRequestMethodNotSupportedException for unsupported HTTP methods.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(Constant.HttpRequestMethodNotSupportedException, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.HTTP_METHOD_NOT_ALLOWED, Constant.HTTP_METHOD_NOT_SUPPORT + ex.getMethod());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    /**
     * Handles NoResourceFoundException for url not found.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse<Object>> handlerNoResourceFoundException(NoResourceFoundException ex) {
        log.error(Constant.NO_RESOURCE_FOUND_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles NoResourceFoundException for invalid parameter types.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error(Constant.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        String message = String.format(
                Constant.METHOD_ARGUMENT_TYPE_MISMATCH,
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : Constant.UNKNOWN
        );
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    /**
     * Handles all other exceptions and returns a meaningful response.
     *
     * @param ex the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Object>> handleGenericException(Exception ex) {
        log.error(Constant.UNEXPECTED_EXCEPTION_OCCURRED, ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handles AuthenticationException thrown when authentication fails.
     * This is the base exception for all authentication-related errors.
     *
     * @param ex the AuthenticationException
     * @return a ResponseEntity with HTTP 401 status and error message
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication error: {}", ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);
    }

    /**
     * Handles BadCredentialsException when username or password is incorrect.
     *
     * @param ex the BadCredentialsException
     * @return a ResponseEntity with HTTP 401 status and specific error message
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Bad credentials: {}", ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.UNAUTHORIZED, "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);
    }

    /**
     * Handles DisabledException when the user account is disabled.
     *
     * @param ex the DisabledException
     * @return a ResponseEntity with HTTP 401 status and specific error message
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse<Object>> handleDisabledException(DisabledException ex) {
        log.error("User disabled: {}", ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.UNAUTHORIZED, "User account is disabled");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);
    }

    /**
     * Handles LockedException when the user account is locked.
     *
     * @param ex the LockedException
     * @return a ResponseEntity with HTTP 401 status and specific error message
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorResponse<Object>> handleLockedException(LockedException ex) {
        log.error("User locked: {}", ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.UNAUTHORIZED, "User account is locked");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);
    }

    /**
     * Handles CredentialsExpiredException when user credentials have expired.
     *
     * @param ex the CredentialsExpiredException
     * @return a ResponseEntity with HTTP 401 status and specific error message
     */
    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ErrorResponse<Object>> handleCredentialsExpiredException(CredentialsExpiredException ex) {
        log.error("Credentials expired: {}", ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.UNAUTHORIZED, "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);
    }

    /**
     * Handles UsernameNotFoundException when the username is not found during authentication.
     *
     * @param ex the UsernameNotFoundException
     * @return a ResponseEntity with HTTP 401 status and specific error message
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse<Object>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("Username not found: {}", ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.UNAUTHORIZED, "User not found");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);
    }

    /**
     * Handles AccessDeniedException thrown when an authenticated user
     * tries to access a resource they don't have permission for.
     *
     * @param ex the AccessDeniedException
     * @return a ResponseEntity with HTTP 403 status and error message
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access denied: {}", ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of("FORBIDDEN", "You do not have permission to access this resource");
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(errorResponse);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse<Object>> handleUnAuthorizedException(UnAuthorizedException ex) {
        log.error("Unauthorized access: {}", ex.getMessage(), ex);
        ErrorResponse<Object> errorResponse = ErrorResponse.of(Constant.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
