package uni.hcmus.employeemanagement.exception_handler.exceptions;

public class CanNotSaveException extends RuntimeException {
    public CanNotSaveException(String message) {
        super(message);
    }
}
