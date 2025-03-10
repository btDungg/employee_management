package uni.hcmus.employeemanagement.service.serviceImplement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PersistenceContext
    private EntityManager entityManager;

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

        Long orgId = employee.getOrganization().getId();
        String orgName = employee.getOrganization().getName();
        Employee manager = employee.getOrganization().getManager_id();
        Long managerId = manager != null ? manager.getId() : null;
        String managerName = manager != null ? manager.getName() : null;

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
                employee.getHireDate(),
                orgName,
                managerId,
                managerName,
                employee.getEmailCompany(),
                employee.getSalary()
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
                    emergencyContactRepository.findByEmployeeId((Long) employee[0]),
                    (int) employee[25]
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
                    emergencyContactRepository.findByEmployeeId((Long) employee[0]),
                    (int) employee[25]
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
    public Optional<EmployeePublicDto_v1> getDetailEmployeesByHR(String email, Long id) {
        Optional<Employee> emp = employeeRepository.findByEmailCompany(email);
        if (emp.isEmpty() || !"HR".equals(emp.get().getType())) {
            return Optional.empty();
        }

        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            return Optional.empty();
        }
        EmployeePublicDto_v1 detail = new EmployeePublicDto_v1(
                employee.get().getId(),
                employee.get().getName(),
                employee.get().getPoint(),
                employee.get().getType(),
                employee.get().getEmailCompany(),
                employee.get().getOrganization().getId(),
                employee.get().getDateOfBirth() != null ? employee.get().getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null,
                employee.get().getAge(),
                employee.get().getGender() != null && employee.get().getGender() ? "Nam" : "Nữ",
                employee.get().getPrimaryNationality(),
                employee.get().getLocation(),
                employee.get().getHireDate() != null ? employee.get().getHireDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null,
                employee.get().getReligion(),
                employee.get().getMarital(),
                employee.get().getEthnicty(),
                employee.get().getAvatar(),
                employee.get().getOrganization().getName(),
                employee.get().getCountryOfBirth(),
                employee.get().getRegionOfBirth(),
                employee.get().getCityOfBirth(),
                employee.get().getCitizenshipStatus(),
                employee.get().getJob(),
                employee.get().getBusinessTitle(),
                employee.get().getJobProfile(),
                employee.get().getTimeType(),
                employee.get().getOrganization().getManager_id().getName(),
                employee.get().getOrganization().getManager_id().getId(),
                phoneRepository.findByEmployeeId(employee.get().getId()),
                emailRepository.findByEmployeeId(employee.get().getId()),
                addressRepository.findByEmployeeId(employee.get().getId()),
                emergencyContactRepository.findByEmployeeId(employee.get().getId()),
                employee.get().getSalary()
                );
    return Optional.of(detail);
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
        Object[] managerDetails = (Object[]) manager[0];
        Long managerId = (Long) managerDetails[0];
        String managerName = (String) managerDetails[1];


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
                emergencyContacts,
                emp.getSalary()
        ));


    }

    @Override
    public Optional<List<EmployeePublicDto_v1>> searchEmployees(String id, String name,
                                                                String email,
                                                                String nameOrganization, String hrEmail) {

        // Cơ sở truy vấn SQL ban đầu
        String baseQuery = "SELECT e FROM Employee e join e.organization o WHERE 1=1";
        Map<String, Object> parameters = new HashMap<>();

        // Thêm điều kiện động
        if (id != null) {
            if (!id.matches("\\d+")) {  // Nếu id không phải là số nguyên (chứa chữ hoặc ký tự đặc biệt)
                return Optional.empty();  // Trả về Optional.empty() hoặc null tùy yêu cầu
            }

            baseQuery += " AND e.id = :id";
            parameters.put("id", id);
        }
        if (name != null) {
            baseQuery += " AND e.name LIKE :name";
            parameters.put("name", "%" + name + "%");
        }
        if (email != null) {
            baseQuery += " AND e.emailCompany LIKE :email";
            parameters.put("email", "%" + email + "%");
        }
        if (nameOrganization != null) {
            baseQuery += " AND o.name LIKE :nameOrganization";
            parameters.put("nameOrganization", "%" + nameOrganization + "%");
        }

        // Thực thi truy vấn
        TypedQuery<Employee> query = entityManager.createQuery(baseQuery, Employee.class);
        parameters.forEach(query::setParameter);

        List<Employee> employees = query.getResultList();

        if (employees.isEmpty()) {
            return Optional.empty();
        }

        // Chuyển đổi kết quả sang DTO
        List<EmployeePublicDto_v1> result = employees.stream()
                .map(employee -> new EmployeePublicDto_v1(
                        employee.getId(),
                        employee.getName(),
                        employee.getPoint(),
                        employee.getType(),
                        employee.getEmailCompany(),
                        employee.getOrganization().getId(),
                        employee.getDateOfBirth() != null ? employee.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null, // Chuyển đổi Date -> LocalDate
                        employee.getAge(),
                        employee.getGender() != null && employee.getGender() ? "Nam" : "Nữ",
                        employee.getPrimaryNationality(),
                        employee.getLocation(),
                        employee.getHireDate() != null ? employee.getHireDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null,
                        employee.getReligion(),
                        employee.getMarital(),
                        employee.getEthnicty(),
                        employee.getAvatar(),
                        employee.getOrganization().getName(),
                        employee.getCountryOfBirth(),
                        employee.getRegionOfBirth(),
                        employee.getCityOfBirth(),

                        employee.getCitizenshipStatus(),
                        employee.getJob(),
                        employee.getBusinessTitle(),
                        employee.getJobProfile(),
                        employee.getTimeType(),
                        employee.getOrganization().getManager_id().getName(),
                        employee.getOrganization().getManager_id().getId(),
                        phoneRepository.findByEmployeeId(employee.getId()),
                        emailRepository.findByEmployeeId(employee.getId()),
                        addressRepository.findByEmployeeId(employee.getId()),
                        emergencyContactRepository.findByEmployeeId(employee.getId()),
                        employee.getSalary()
                ))
                .collect(Collectors.toList());

        return Optional.of(result);
    }

}
