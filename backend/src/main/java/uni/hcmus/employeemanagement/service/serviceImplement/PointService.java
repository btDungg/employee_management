package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import uni.hcmus.employeemanagement.dto.Request.ModifyPointRequest;
import uni.hcmus.employeemanagement.dto.Request.SearchEmployeeRequest;
import uni.hcmus.employeemanagement.dto.Response.*;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.entity.Manager;
import uni.hcmus.employeemanagement.entity.Organization;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.repository.EmployeeRepository;
import uni.hcmus.employeemanagement.repository.ManagerRepository;
import uni.hcmus.employeemanagement.repository.OrganizationRepository;
import uni.hcmus.employeemanagement.repository.PointChangeRepository;

import uni.hcmus.employeemanagement.entity.PointChange;

import java.time.LocalDate;
import uni.hcmus.employeemanagement.service.interfaceService.IPointService;
import uni.hcmus.employeemanagement.utils.JwtTokenUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointService implements IPointService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private PointChangeRepository pointChangeRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public List<EmployeePointDto> getEmployeePointsBasedOnRole(String userEmail) {


        // Lấy Employee dựa trên email
        Employee employee = employeeRepository.findByEmailCompany(userEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + userEmail));

        String role = employee.getType();

        switch (role) {
            case "HR":
                // Trả về toàn bộ danh sách điểm nhân viên cho HR
                return getAllEmployeePoints();

            case "Manager":
                // Trả về danh sách điểm của nhân viên được quản lý bởi Manager
                return getEmployeePointsByManagerId(employee.getId());

            case "Employee":
                return List.of(new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), employee.getOrganization().getId()));

            default:
                throw new IllegalStateException("Unknown role for employee with email = " + userEmail);
        }
    }



    @Override
    public EmployeePointDto getEmployeePointDetailBasedOnRole(Long employeeId, String userEmail) {


        Employee currentEmployee = employeeRepository.findByEmailCompany(userEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + userEmail));

        String currentRole = currentEmployee.getType();
        Long currentId = currentEmployee.getId();

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with id = " + employeeId));

        // Kiểm tra vai trò và quyền truy cập
        switch (currentRole) {
            case "HR":
                // HR có quyền xem thông tin điểm của tất cả nhân viên
                return new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), employee.getOrganization().getId());
            case "Manager":
                // Manager chỉ có thể xem thông tin điểm của nhân viên dưới quyền
                if (!currentEmployee.getOrganization().getId().equals(employee.getOrganization().getId())) {
                    throw new AccessDeniedException("You do not have permission to view this employee's points.");
                }
                return new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), employee.getOrganization().getId());
            case "Employee":
                // Employee chỉ có thể xem thông tin điểm của chính mình
                if (!currentId.equals(employeeId)) {
                    throw new AccessDeniedException("You do not have permission to view this employee's points.");
                }
                return new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), employee.getOrganization().getId());
            default:
                throw new IllegalStateException("Unknown role for employee with email = " + userEmail);
        }
    }
    private List<EmployeePointDto> getEmployeePointsByManagerId(Long managerId) {
        // Lấy danh sách điểm của các nhân viên do Manager quản lý
        List<Object[]> employees = employeeRepository.findByManagerId(managerId);
        if(employees.isEmpty()) {
            throw new DataNotFoundException("Employees not found with managerID = " + managerId);
        }
        return employees.stream()
                .map(emp -> new EmployeePointDto(
                        (Long) emp[0],
                        (String) emp[1],
                        (int) emp[2],
                        (String) emp[3],
                        (Long) emp[4]
                ))
                .collect(Collectors.toList());

    }

    @Override
    public EmployeePointDto ViewMyPoint(String email)
    {
        Employee employee = employeeRepository.findByEmailCompany(email).orElse(null);
        if (employee != null) {
            Organization organization = organizationRepository
                    .findById(employee.getOrganization().getId())
                    .orElse(null);

            Long managerID= organization.getManager_id().getId();
            // Kiểm tra nếu đối tượng là Manager
            if ("Manager".equals(employee.getType())) {
                Manager manager = managerRepository.findById(employee.getId()).orElse(null);
                return new ManagerPointDto(manager.getId(), manager.getName(), manager.getPoint(), manager.getType(), managerID, manager.getBonusEmployeePoint());
            } else {
                return new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), managerID);
            }
        }
        else
        {
            throw new DataNotFoundException("Employee not found with email = " + email);
        }
    }

    public List<EmployeePointDto> getAllEmployeePoints() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(emp -> new EmployeePointDto(emp.getId(), emp.getName(), emp.getPoint(), emp.getType(), emp.getOrganization().getId()))
                .collect(Collectors.toList());
    }

    @Override
//    @Scheduled(cron = "0 23 15 * * ?") // Chạy vào 15:05:00 mỗi ngày
    @Scheduled(cron = "0 1 0 25 * ?") // Chạy vào lúc 00:01:00 ngày 25 hàng tháng
    @Transactional // đảm bảo chạy full data
    public void autoAddPointsToEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<Manager> managers = managerRepository.findAll();
        for (Employee employee : employees) {
            int point = switch (employee.getType().toUpperCase()) {
                case "MANAGER" -> 20;
                case "EMPLOYEE", "HR" -> 10;
                default -> 1;
            };
            employee.setPoint(employee.getPoint() + point);
        }
        for (Manager manager : managers) {
            manager.setBonusEmployeePoint(10);
        }
        employeeRepository.saveAll(employees);
        managerRepository.saveAll(managers);
    }

    @Override
    public List<PointChangeDto> ViewMyChangePoint(String email)
    {
        Employee emp = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));
        Long myId = emp.getId();
        List<PointChange> listPointChanges = pointChangeRepository.findByReceivedIdOrderByChangeDateDesc(myId);
        return listPointChanges.stream()
                .map(pointChange -> {
                    String changerName = employeeRepository.findById(pointChange.getEmployee().getId())
                            .map(Employee::getName)
                            .orElse("Unknown"); // Tên người thay đổi hoặc "Unknown" nếu không tìm thấy
                    return new PointChangeDto(
                            pointChange.getAmount(),
                            pointChange.getChangeDate(), // Chuyển đổi Date sang LocalDate
                            pointChange.getReason(),
                            changerName
                    );
                })
                .collect(Collectors.toList());

    }

    @Override
    public String getEmployeeRoleById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return (employee != null) ? employee.getType() : null;
    }

    @Override
    public EmployeeDto getEmployeeById(String myEmail, SearchEmployeeRequest searchRequest)
    {// Lấy thông tin người gọi API
        Employee myInfo = employeeRepository.findByEmailCompany(myEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + myEmail));

        // Lấy thông tin employee cần tìm
        Employee emp = employeeRepository.findById(searchRequest.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("Employee not found with employeeID " + searchRequest.getEmployeeId()));

        // Trường hợp HR: Được phép lấy tất cả thông tin
        if ("HR".equals(myInfo.getType())) {
            return new EmployeeDto(emp.getId(), emp.getName(), emp.getPoint(), emp.getType(), emp.getOrganization().getId());
        }

        // Trường hợp Manager: Kiểm tra emp thuộc organization do manager quản lý
        if ("Manager".equals(myInfo.getType())) {
            // Kiểm tra Manager có quản lý tổ chức của Employee hay không
            Organization organization = organizationRepository.findById(emp.getOrganization().getId())
                    .orElseThrow(() -> new DataNotFoundException("Organization not found with ID = " + emp.getOrganization().getId()));

            if (organization.getManager_id() == null || !organization.getManager_id().getId().equals(myInfo.getId())) {
                throw new AccessDeniedException("You do not have permission to access this employee's information.");
            }
            return new EmployeeDto(emp.getId(), emp.getName(), emp.getPoint(), emp.getType(), emp.getOrganization().getId());
        }

        // Trường hợp Employee: Chỉ được phép lấy thông tin của chính họ
        if ("Employee".equals(myInfo.getType())) {
            if (!myInfo.getId().equals(emp.getId())) {
                throw new AccessDeniedException("You can only access your own information.");
            }
            return new EmployeeDto(myInfo.getId(), myInfo.getName(), myInfo.getPoint(), myInfo.getType(), myInfo.getOrganization().getId());
        }

        // Nếu không thuộc loại nào, trả về ngoại lệ
        throw new AccessDeniedException("Your role is not authorized to access this information.");

    }

    @Override
    public String modifyPoints(String email, ModifyPointRequest modifyPoint)
    {
        Employee emp = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));
        if (!"HR".equals(emp.getType()) && !"Manager".equals(emp.getType()))
        {
            throw new AccessDeniedException("You do not have permission to modify employee's points.");
        }

        String type = modifyPoint.getModifyType();
        switch (type)
        {
            case "increase":
                if ("HR".equals(emp.getType()))
                {
                    return increasePoints(emp, modifyPoint);
                }
                else {
                    return increasePointsByManager(emp, modifyPoint);
                }
            case "decrease":
                return decreasePoints(emp, modifyPoint);
            default:
                throw new IllegalArgumentException("Not a modify");
        }

    }

    @Override
    public String increasePoints(Employee currentEmployee, ModifyPointRequest modifyPoint) {
        Employee receiveEmployee = employeeRepository.findById(modifyPoint.getReceive_id())
                .orElseThrow(() -> new DataNotFoundException("Employee not found with employeeID " + modifyPoint.getReceive_id()));
        receiveEmployee.setPoint(receiveEmployee.getPoint() + modifyPoint.getAmount());
        employeeRepository.save(receiveEmployee);

        PointChange pointChange = new PointChange(modifyPoint.getAmount(), LocalDate.now(), modifyPoint.getReason(), receiveEmployee, currentEmployee);
        pointChangeRepository.save(pointChange);
        return "Points have been increased successfully!";
    }

    @Override
    public int getManagerBonusPointsById(Long id) {
        Manager manager = managerRepository.findById(id).orElse(null);
        return (manager != null) ? manager.getBonusEmployeePoint() : 0;
    }

    @Override
    public String increasePointsByManager(Employee currentEmployee, ModifyPointRequest modifyPoint) {
        Manager manager = managerRepository.findById(currentEmployee.getId())
                .orElseThrow(() -> new IllegalArgumentException("This employee is not manager!"));
        if (manager.getBonusEmployeePoint() < modifyPoint.getAmount()) {
            return "Manager does not have enough bonus points";
        }

        Employee receiveEmployee = employeeRepository.findById(modifyPoint.getReceive_id())
                .orElseThrow(() -> new DataNotFoundException("Employee not found with employeeID " + modifyPoint.getReceive_id()));

        receiveEmployee.setPoint(receiveEmployee.getPoint() + modifyPoint.getAmount());
        manager.setBonusEmployeePoint(manager.getBonusEmployeePoint() - modifyPoint.getAmount());

        employeeRepository.save(receiveEmployee);
        managerRepository.save(manager);

        PointChange pointChange = new PointChange(modifyPoint.getAmount(), LocalDate.now(), modifyPoint.getReason(), receiveEmployee, currentEmployee);
        pointChangeRepository.save(pointChange);

        return "Points have been increased successfully!";
    }

    @Override
    public String decreasePoints(Employee currentEmployee, ModifyPointRequest modifyPoint) {
        Employee receiveEmployee = employeeRepository.findById(modifyPoint.getReceive_id())
                .orElseThrow(() -> new DataNotFoundException("Employee not found with employeeID " + modifyPoint.getReceive_id()));

        if (receiveEmployee.getPoint() < modifyPoint.getAmount()) {
            return "Employee does not have enough points to decrease!";
        }

        receiveEmployee.setPoint(receiveEmployee.getPoint() - modifyPoint.getAmount());
        employeeRepository.save(receiveEmployee);

        PointChange pointChange = new PointChange(-modifyPoint.getAmount(), LocalDate.now(), modifyPoint.getReason(), receiveEmployee, currentEmployee);
        pointChangeRepository.save(pointChange);

        return "Points have been decreased successfully!";
    }
}
