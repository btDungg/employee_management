package uni.hcmus.employeemanagement.exception_handler.exceptions;

public class EmployeeNotFoundException  extends  RuntimeException{
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
