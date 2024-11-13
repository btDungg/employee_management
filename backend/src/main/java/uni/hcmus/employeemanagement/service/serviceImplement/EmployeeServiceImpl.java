package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.hcmus.employeemanagement.dto.EmployeeDto;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.repository.EmployeeRepository;
import uni.hcmus.employeemanagement.service.interfaceService.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto getEmployeeProfileByEmail(String email) {
        Employee employee = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));

        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getPoint(),
                employee.getType(),
                employee.getManagerId()
        );
    }
}