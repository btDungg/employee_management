package uni.hcmus.employeemanagement.exception_handler;

import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uni.hcmus.employeemanagement.exception_handler.exceptions.*;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles DataNotFoundException,... and returns an ErrorDTO.
     *
     * @param request the HTTP request
     * @param ex      the exception
     * @return an ErrorDTO containing error details
     */
    @ExceptionHandler({DataNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleNotFoundException(HttpServletRequest request, Exception ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.addError(ex.getMessage());
        LOGGER.error("Not Found: {}", ex.getMessage(), ex);
        return error;
    }


    /**
     * Handles general exceptions.
     *
     * @param request the HTTP request
     * @param ex      the exception
     * @return an ErrorDTO containing error details
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleGeneralException(HttpServletRequest request, Exception ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.addError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        LOGGER.error("Internal Server Error: {}", ex.getMessage(), ex);
        return error;
    }

    @ExceptionHandler(InvalidKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleInvalidKeyException(HttpServletRequest request, InvalidKeyException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.addError(ex.getMessage());
        LOGGER.error("Invalid Key: {}", ex.getMessage(), ex);
        return error;
    }


    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.addError(ex.getMessage());
        LOGGER.error("Access Denied: {}", ex.getMessage(), ex);
        return error;
    }
    @ExceptionHandler({EmployeeNotFoundException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO handleEmployeeNotFoundException(HttpServletRequest request, EmployeeNotFoundException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.addError(ex.getMessage());
        LOGGER.error("Access Denied: {}", ex.getMessage(), ex);
        return error;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ActivityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleActivityNotFoundException(HttpServletRequest request, ActivityNotFoundException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.addError(ex.getMessage());
        LOGGER.error("Activity Not Found: {}", ex.getMessage(), ex);
        return error;
    }
    @ExceptionHandler({RegistrationNotAllowedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleRegistrationNotAllowedException(HttpServletRequest request, RegistrationNotAllowedException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.addError(ex.getMessage());
        LOGGER.error("Registration Not Allowed: {}", ex.getMessage(), ex);
        return error;
    }
}
