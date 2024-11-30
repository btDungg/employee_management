package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDetailInfoDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeePointDto;
import uni.hcmus.employeemanagement.entity.*;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.repository.*;
import uni.hcmus.employeemanagement.service.interfaceService.IEmployeeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

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
                employee.getOrganization().getId()
        );
    }

    @Override
    public Optional<List<EmployeeDto>> getEmployeesByManagerid(Long id) {
        List<Object[]> employees = employeeRepository.findByManagerId(id);
        if (employees.isEmpty()) {
            throw new DataNotFoundException("Employees not found with managerID = " + id);
        }
        return Optional.of(employees.stream().map(employee -> new EmployeeDto(
                (Long) employee[0],
                (String) employee[1],
                (Integer) employee[2],
                (String) employee[3],
                (Long) employee[4]
        )).collect(Collectors.toList()));
    }

    @Override
    public EmployeeDetailInfoDto getEmployeePersonalInfoByEmail(String email) {
        Employee employee = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));

        List<Phone> phones = phoneRepository.findByEmployeeId(employee.getId());
        List<Email> emails = emailRepository.findByEmployeeId(employee.getId());
        List<Address> addresses = addressRepository.findByEmployeeId(employee.getId());
        List<EmergencyContact> emergencyContacts = emergencyContactRepository.findByEmployeeId(employee.getId());

        return new EmployeeDetailInfoDto(
                employee.getId(),
                employee.getName(),
                employee.getType(),
                employee.getOrganization().getId(),
                employee.getGender(),
                employee.getDateOfBirth(),
                employee.getAge(),
                employee.getCountryOfBirth(),
                employee.getRegionOfBirth(),
                employee.getCityOfBirth(),
                employee.getMarital(),
                employee.getReligion(),
                employee.getEthnicty(),
                employee.getCitizenshipStatus(),
                employee.getPrimaryNationality(),
                phones,
                emails,
                addresses,
                emergencyContacts,
                employee.getJob(),
                employee.getBusinessTitle(),
                employee.getJobProfile(),
                employee.getTimeType(),
                employee.getLocation(),
                employee.getHireDate()
        );
    }
}