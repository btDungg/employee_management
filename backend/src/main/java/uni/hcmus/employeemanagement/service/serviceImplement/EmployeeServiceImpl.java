package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.repository.EmployeeRepository;
import uni.hcmus.employeemanagement.service.interfaceService.IEmployeeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    private OtpServiceImpl otpServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void sendOTP(String email) throws Exception {
        Optional<Employee> existedEmployee = employeeRepository.findByEmailCompany(email);
        if (existedEmployee.isEmpty()) {
            throw new DataNotFoundException("Email does not exist in the system!");
        }
        String otp = emailServiceImpl.generateOtp();
        otpServiceImpl.storeOtp(email, otp);
        emailServiceImpl.sendOtpEmail(email, otp);
    }

    @Override
    public boolean verifyOTP(String otp, String email) throws Exception {
        Optional<Employee> existedEmployee = employeeRepository.findByEmailCompany(email);
        if (existedEmployee.isEmpty()) {
            throw new DataNotFoundException("Email does not exist in the system!");
        }
        return otpServiceImpl.verifyOtp(otp, email);
    }

    @Override
    public void resetPassword(String email, String password) throws Exception {
        Optional<Employee> existedEmployee = employeeRepository.findByEmailCompany(email);
        if (existedEmployee.isEmpty()) {
            throw new DataNotFoundException("Email does not exist in the system!");
        }
        existedEmployee.get().setPassword(passwordEncoder.encode(password));
        employeeRepository.save(existedEmployee.get());
    }
    
    public EmployeeDto getEmployeeByEmail(String email) {
        // Tìm Employee dựa trên email công ty
        Employee employee = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));

        // Chuyển đổi đối tượng Employee thành EmployeeDto
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getPoint(),
                employee.getType(),
                employee.getManagerId()
        );
    }

    @Override
    public Optional<List<EmployeeDto>> getEmployeesByManagerid(Long id) {
        List<Employee> employees = employeeRepository.findByManagerId(id);
        if (employees.isEmpty()) {
            return Optional.empty();
        }
        List<EmployeeDto> employeeDtos = employees.stream()
                .map(employee -> new EmployeeDto(
                        employee.getId(),
                        employee.getName(),
                        employee.getPoint(),
                        employee.getType(),
                        employee.getManagerId()
                ))
                .toList();
        return Optional.of(employeeDtos);
    }
}