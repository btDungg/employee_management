package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.EmployeeDto;
import uni.hcmus.employeemanagement.entity.Employee;

public interface IEmployeeService {
    Employee addEmployee(Employee employee);
    EmployeeDto getEmployeeProfileByEmail(String email);
}