package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDetailInfoDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeePublicDto_v1;
import uni.hcmus.employeemanagement.dto.Response.ManagerDto_v1;
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
                employee.getAvatar(),
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

    @Override
    public Optional<List<EmployeePublicDto_v1>> getTeamMate(String email) {
        Employee emp = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Manager not found with email = " + email));
        Long id = emp.getId();

        if ("Manager".equals(emp.getType())) {
            List<Object[]> employees = employeeRepository.findByManagerID_v1(id);
            if (employees.isEmpty()) {
//                throw new DataNotFoundException("Employees not found with managerID = " + id);
                return Optional.empty();
            }
            String managerName = emp.getName();

            return Optional.of(employees.stream().map(employee -> new EmployeePublicDto_v1(
                    (Long) employee[0],
                    (String) employee[1],
                    (int) employee[2],
                    (String) employee[3],
                    (String) employee[4],
                    (String) employee[5],
                    (Long) employee[6],
                    employee[7] != null ? ((java.sql.Timestamp) employee[7]).toLocalDateTime().toLocalDate() : null, // Chuyển Timestamp -> LocalDate
                    (int) employee[8],
                    employee[9] != null && (Boolean) employee[9] ? "Nam" : "Nữ", // Chuyển đổi Boolean thành Nam/Nữ
                    (String) employee[10],
                    (String) employee[11],
                    employee[12] != null ? ((java.sql.Timestamp) employee[7]).toLocalDateTime().toLocalDate() : null,
                    (String) employee[13],
                    (String) employee[14],
                    (String) employee[15],
                    (String) employee[16],
                    (String) employee[17],
                    managerName,
                    id
            )).collect(Collectors.toList()));
        }
        Long orgId = emp.getOrganization().getId();
        Optional[] manager = employeeRepository.getManager(orgId);
        if (manager.length == 0) {
            return Optional.empty();
        }
        ManagerDto_v1 managerDto = new ManagerDto_v1((Long) manager[0].orElse(null), (String) manager[1].orElse(null));


        List<Object[]> employees = employeeRepository.findTeamMate(orgId);
        if (employees.isEmpty()) {
            return Optional.empty();
        }
        if ("HR".equals(emp.getType())) {
            return Optional.of(employees.stream().map(employee -> new EmployeePublicDto_v1(
                    (Long) employee[0],
                    (String) employee[1],
                    (int) employee[2],
                    (String) employee[3],
                    (String) employee[4],
                    (String) employee[5],
                    (Long) employee[6],
                    employee[7] != null ? ((java.sql.Timestamp) employee[7]).toLocalDateTime().toLocalDate() : null, // Chuyển Timestamp -> LocalDate
                    (int) employee[8],
                    employee[9] != null && (Boolean) employee[9] ? "Nam" : "Nữ", // Chuyển đổi Boolean thành Nam/Nữ
                    (String) employee[10],
                    (String) employee[11],
                    employee[12] != null ? ((java.sql.Timestamp) employee[7]).toLocalDateTime().toLocalDate() : null,
                    (String) employee[13],
                    (String) employee[14],
                    (String) employee[15],
                    (String) employee[16],
                    (String) employee[17],
                    managerDto.getName(),
                    managerDto.getId()
            )).collect(Collectors.toList()));

        } else {
            return Optional.of(employees.stream().map(employee -> new EmployeePublicDto_v1(
                    (Long) employee[0],
                    (String) employee[1],
                    (String) employee[3],
                    (String) employee[4],
                    (String) employee[5],
                    (Long) employee[6],
                    employee[9] != null && (Boolean) employee[9] ? "Nam" : "Nữ", // Chuyển đổi Boolean thành Nam/Nữ
                    (String) employee[16],
                    (String) employee[17],
                    managerDto.getName(),
                    managerDto.getId()
            )).collect(Collectors.toList()));
        }

    }

//    @Override
//    public Optional<List<TeamMateDto>> getTeamMate(String email) {
//
//        Employee e = employeeRepository.findByEmailCompany(email)
//                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));
//
//        List<Object[]> employees = employeeRepository.findTeamMate(e.getId());
//        if (employees.isEmpty()) {
//            throw new DataNotFoundException("Employees not found with employeeID = " + e.getId());
//        }
//        return Optional.of(employees.stream().map(employee -> new TeamMateDto(
//                (Long) employee[0],
//                (String) employee[1],
//                (String) employee[2],
//                employee[3] != null && (Boolean) employee[9] ? "Nam" : "Nữ", // Chuyển đổi Boolean thành Nam/Nữ
//                (String) employee[4],
//                (String) employee[5],
//                (String) employee[6],
//                (String) employee[7],
//                (String) employee[8],
//                (String) employee[9],
//                (String) employee[10]
//        )).collect(Collectors.toList()));
//
//    }
}