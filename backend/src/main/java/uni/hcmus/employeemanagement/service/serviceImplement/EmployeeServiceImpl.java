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

import java.time.ZoneId;
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
                    (Long) employee[5],
                    employee[6] != null ? ((java.sql.Timestamp) employee[6]).toLocalDateTime().toLocalDate() : null, // Chuyển Timestamp -> LocalDate
                    (int) employee[7],
                    employee[8] != null && (Boolean) employee[8] ? "Nam" : "Nữ", // Chuyển đổi Boolean thành Nam/Nữ
                    (String) employee[9],
                    (String) employee[10],
                    employee[11] != null ? ((java.sql.Timestamp) employee[11]).toLocalDateTime().toLocalDate() : null,
                    (String) employee[12],
                    (String) employee[13],
                    (String) employee[14],
                    (String) employee[15],
                    (String) employee[16],

                    (String) employee[17],
                    (String) employee[18],
                    (String) employee[19],
                    (String) employee[20],
                    (String) employee[21],
                    (String) employee[22],
                    (String) employee[23],
                    (String) employee[24],
                    managerName,
                    id,
                    phoneRepository.findByEmployeeId((Long) employee[0]),
                    emailRepository.findByEmployeeId((Long) employee[0]),
                    addressRepository.findByEmployeeId((Long) employee[0]),
                    emergencyContactRepository.findByEmployeeId((Long) employee[0])

            )).collect(Collectors.toList()));
        }
        Long orgId = emp.getOrganization().getId();
        Object[] manager = employeeRepository.getManager(orgId);
        if (manager.length == 0) {
            return Optional.empty();
        }
        Object[] managerDetails = (Object[]) manager[0]; // manager[0] là Object[], cần cast
        Long managerId = (Long) managerDetails[0];      // Lấy phần tử đầu tiên (kiểu Long)
        String managerName = (String) managerDetails[1];// Lấy phần tử thứ hai (kiểu String)


        ManagerDto_v1 managerDto = new ManagerDto_v1(managerId, managerName);

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
                    (Long) employee[5],
                    employee[6] != null ? ((java.sql.Timestamp) employee[6]).toLocalDateTime().toLocalDate() : null, // Chuyển Timestamp -> LocalDate
                    (int) employee[7],
                    employee[8] != null && (Boolean) employee[8] ? "Nam" : "Nữ", // Chuyển đổi Boolean thành Nam/Nữ
                    (String) employee[9],
                    (String) employee[10],
                    employee[11] != null ? ((java.sql.Timestamp) employee[11]).toLocalDateTime().toLocalDate() : null,
                    (String) employee[12],
                    (String) employee[13],
                    (String) employee[14],
                    (String) employee[15],
                    (String) employee[16],
                    (String) employee[17],
                    (String) employee[18],
                    (String) employee[19],
                    (String) employee[20],
                    (String) employee[21],
                    (String) employee[22],
                    (String) employee[23],
                    (String) employee[24],
                    managerDto.getName(),
                    managerDto.getId(),
                    phoneRepository.findByEmployeeId((Long) employee[0]),
                    emailRepository.findByEmployeeId((Long) employee[0]),
                    addressRepository.findByEmployeeId((Long) employee[0]),
                    emergencyContactRepository.findByEmployeeId((Long) employee[0])

            )).collect(Collectors.toList()));

        } else {
            return Optional.of(employees.stream().map(employee -> new EmployeePublicDto_v1(
                    (Long) employee[0],
                    (String) employee[1],
                    (String) employee[3],
                    (String) employee[4],
                    (Long) employee[5],
                    employee[8] != null && (Boolean) employee[8] ? "Nam" : "Nữ", // Chuyển đổi Boolean thành Nam/Nữ
                    (String) employee[15],
                    (String) employee[16],

                    (String) employee[21],
                    (String) employee[22],
                    (String) employee[23],
                    (String) employee[24],

                    managerDto.getName(),
                    managerDto.getId()
            )).collect(Collectors.toList()));
        }

    }


    @Override
    public Optional<EmployeePublicDto_v1> getMyselft(String email, Long id) {

        Employee emp = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));
        if (emp.getId() != id) {
            return Optional.empty();
        }

        Long orgId = emp.getOrganization().getId();
        Object[] manager = employeeRepository.getManager(orgId);
        if (manager.length == 0) {
            return Optional.empty();
        }
        Object[] managerDetails = (Object[]) manager[0]; // manager[0] là Object[], cần cast
        Long managerId = (Long) managerDetails[0];      // Lấy phần tử đầu tiên (kiểu Long)
        String managerName = (String) managerDetails[1];// Lấy phần tử thứ hai (kiểu String)


        ManagerDto_v1 managerDto = new ManagerDto_v1(managerId, managerName);
        List<Email> emails = emailRepository.findByEmployeeId(emp.getId());
        List<Phone> phones = phoneRepository.findByEmployeeId(emp.getId());
        List<Address> addresses = addressRepository.findByEmployeeId(emp.getId());
        List<EmergencyContact> emergencyContacts = emergencyContactRepository.findByEmployeeId(emp.getId());

        return Optional.of(new EmployeePublicDto_v1(
                emp.getId(),
                emp.getName(),
                emp.getPoint(),
                emp.getType(),
                emp.getEmailCompany(),
                emp.getOrganization().getId(),
                emp.getDateOfBirth() != null ? emp.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null, // Chuyển đổi Date -> LocalDate
                emp.getAge(),
                emp.getGender() != null && emp.getGender() ? "Nam" : "Nữ",
                emp.getPrimaryNationality(),
                emp.getLocation(),
                emp.getHireDate() != null ? emp.getHireDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null,
                emp.getReligion(),
                emp.getMarital(),
                emp.getEthnicty(),
                emp.getAvatar(),
                emp.getOrganization().getName(),
                emp.getCountryOfBirth(),
                emp.getRegionOfBirth(),
                emp.getCityOfBirth(),
                emp.getCitizenshipStatus(),
                emp.getJob(),
                emp.getBusinessTitle(),
                emp.getJobProfile(),
                emp.getTimeType(),
                managerDto.getName(),
                managerDto.getId(),
                phones,
                emails,
                addresses,
                emergencyContacts
        ));


    }

}
