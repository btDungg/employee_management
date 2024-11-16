package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uni.hcmus.employeemanagement.dto.EmployeeDto;
import uni.hcmus.employeemanagement.dto.EmployeePointDto;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.entity.Manager;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.repository.EmployeeRepository;
import uni.hcmus.employeemanagement.repository.ManagerRepository;
import uni.hcmus.employeemanagement.repository.PointChangeRepository;

import uni.hcmus.employeemanagement.dto.ManagerPointDto;
import uni.hcmus.employeemanagement.dto.ModifyPointRequest;
import uni.hcmus.employeemanagement.dto.PointChangeDto;
import uni.hcmus.employeemanagement.dto.Request.SearchEmployeeRequest;
import uni.hcmus.employeemanagement.entity.PointChange;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;

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
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public List<EmployeePointDto> getEmployeePointsBasedOnRole(String token) {
        // Giải mã token để lấy email
        String email = jwtTokenUtil.extractUserIdentifier(token);

        // Lấy Employee dựa trên email
        Employee employee = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));

        String role = employee.getType();

        switch (role) {
            case "HR":
                // Trả về toàn bộ danh sách điểm nhân viên cho HR
                return getAllEmployeePoints();

            case "Manager":
                // Trả về danh sách điểm của nhân viên được quản lý bởi Manager
                return getEmployeePointsByManagerId(employee.getId());

            case "Employee":
                return List.of(new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), employee.getManagerId()));

            default:
                throw new IllegalStateException("Unknown role for employee with email = " + email);
        }
    }
    


  @Override
    public EmployeePointDto getEmployeePointDetailBasedOnRole(Long employeeId, String token) {
    // Giải mã token và lấy thông tin người dùng
    String email = jwtTokenUtil.extractUserIdentifier(token);
    Employee currentEmployee = employeeRepository.findByEmailCompany(email)
            .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));

    String currentRole = currentEmployee.getType();
    Long currentId = currentEmployee.getId();
    Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new DataNotFoundException("Employee not found with id = " + employeeId));

    // Kiểm tra vai trò và quyền truy cập
    switch (currentRole) {
        case "HR":
            // HR có quyền xem thông tin điểm của tất cả nhân viên
            return new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), employee.getManagerId());
        case "Manager":
            // Manager chỉ có thể xem thông tin điểm của nhân viên dưới quyền
            if (!currentEmployee.getId().equals(employee.getManagerId())) {
                throw new AccessDeniedException("You do not have permission to view this employee's points.");
            }
            return new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), employee.getManagerId());
        case "Employee":
            // Employee chỉ có thể xem thông tin điểm của chính mình
            if (!currentId.equals(employeeId)) {
                throw new AccessDeniedException("You do not have permission to view this employee's points.");
            }
            return new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), employee.getManagerId());
        default:
            throw new IllegalStateException("Unknown role for employee with email = " + email);
    }
}
    private List<EmployeePointDto> getEmployeePointsByManagerId(Long managerId) {
        // Lấy danh sách điểm của các nhân viên do Manager quản lý
        List<Employee> employees = employeeRepository.findByManagerId(managerId);
        return employees.stream()
                .map(emp -> new EmployeePointDto(emp.getId(), emp.getName(), emp.getPoint(), emp.getType(), emp.getManagerId()))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeePointDto ViewMyPoint(String email)
    {
    	Employee employee = employeeRepository.findByEmailCompany(email).orElse(null);
        if (employee != null) {

            // Kiểm tra nếu đối tượng là Manager
            if ("Manager".equals(employee.getType())) {
                Manager manager = (Manager) employee;
                return new ManagerPointDto(manager.getId(), manager.getName(), manager.getPoint(), manager.getType(), manager.getManagerId(), manager.getBonusEmployeePoint());
            }
            else
            {
            	return new EmployeePointDto(employee.getId(), employee.getName(), employee.getPoint(), employee.getType(), employee.getManagerId());
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
                .map(emp -> new EmployeePointDto(emp.getId(), emp.getName(), emp.getPoint(), emp.getType(), emp.getManagerId()))
                .collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 1 0 25 * ?") // Chạy vào lúc 00:01:00 ngày 25 hàng tháng
    @Transactional //đảm bảo chạy full data
    public void autoAddPointsToEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<Manager> managers = managerRepository.findAll();
        for (Employee employee : employees) {
            System.out.println("Debug: " + employee.getType());
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
    {
        Employee emp = employeeRepository.findById(searchRequest.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("Employee not found with employeeID " + searchRequest.getEmployeeId()));
        Employee myInfo = employeeRepository.findByEmailCompany(myEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + myEmail));
        if ("Manager".equals(myInfo.getType()) && emp.getManagerId() != myInfo.getId())
        {
            throw new AccessDeniedException("You do not have permission to modify this employee's points.");
        }
        else
        {
            return new EmployeeDto(emp.getId(), emp.getName(), emp.getPoint(), emp.getType(), emp.getManagerId());
        }
        
    }

    @Override
    public String modifyPoints(String email, ModifyPointRequest modifyPoint)
    {
    	Employee emp = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));
    	if (!"HR".equals(emp.getType()) || !"Manager".equals(emp.getType()))
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
    		else
    		{
    			return increasePointsByManager(emp, modifyPoint);
    		}
    	case "decrease":
    		return decreasePoints(emp, modifyPoint);
    	default:
    		throw new IllegalArgumentException("Not a modify");
    	}

    }
    
    @Override
    public String increasePoints(Employee employee, ModifyPointRequest modifyPoint) {
        employee.setPoint(employee.getPoint() + modifyPoint.getAmount());
		employeeRepository.save(employee);
		PointChange pointChange = new PointChange(modifyPoint.getAmount(), LocalDate.now(), modifyPoint.getReason(), modifyPoint.getReceive_id(), employee);
		pointChangeRepository.save(pointChange);
		return "Points have been increased successfully!";
    }

    @Override
    public int getManagerBonusPointsById(Long id) {
        Manager manager = managerRepository.findById(id).orElse(null);
        return (manager != null) ? manager.getBonusEmployeePoint() : 0;
    }

    @Override
    public String increasePointsByManager(Employee employee, ModifyPointRequest modifyPoint) {
        Manager manager = managerRepository.findById(employee.getId()).orElseThrow(() -> new IllegalArgumentException("This employee is not manager!"));
        if (manager.getBonusEmployeePoint() >= modifyPoint.getAmount()) {
            employee.setPoint(employee.getPoint() + modifyPoint.getAmount());
            manager.setBonusEmployeePoint(manager.getBonusEmployeePoint() - modifyPoint.getAmount());
            employeeRepository.save(employee);
            managerRepository.save(manager);
            PointChange pointChange = new PointChange(modifyPoint.getAmount(), LocalDate.now(), modifyPoint.getReason() , modifyPoint.getReceive_id(), employee);
            pointChangeRepository.save(pointChange);
            return "Points have been increased successfully!";
        } else {
            return "Manager does not have enough bonus points";
        }
    }
    
    @Override
    public String decreasePoints(Employee employee, ModifyPointRequest modifyPoint) {
        if (employee.getPoint() >= modifyPoint.getAmount()) {
            employee.setPoint(employee.getPoint() - modifyPoint.getAmount());
            employeeRepository.save(employee);
            PointChange pointChange = new PointChange((modifyPoint.getAmount()) * (-1), LocalDate.now(), modifyPoint.getReason() , modifyPoint.getReceive_id(), employee);
            pointChangeRepository.save(pointChange);
            return "Points have been decreased successfully!";
        } else {
            return "Employee does not have enough points to decrease!";
        }
    }
}
