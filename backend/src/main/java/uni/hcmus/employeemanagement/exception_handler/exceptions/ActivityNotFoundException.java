package uni.hcmus.employeemanagement.exception_handler.exceptions;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException(String message) {
        super(message);
    }
}
