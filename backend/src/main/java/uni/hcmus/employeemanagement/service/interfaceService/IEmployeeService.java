package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.entity.Employee;

public interface IEmployeeService {
    Employee addEmployee(Employee employee);
    void sendOTP(String email) throws Exception;
    boolean verifyOTP(String otp, String email) throws Exception;
    void resetPassword(String email, String password) throws Exception;
}