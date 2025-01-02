package uni.hcmus.employeemanagement.exception_handler.exceptions;

public class RegistrationNotAllowedException extends RuntimeException {
    public RegistrationNotAllowedException(String message) {
        super(message);
    }
}
