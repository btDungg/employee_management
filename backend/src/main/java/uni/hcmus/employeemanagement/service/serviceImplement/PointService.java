package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uni.hcmus.employeemanagement.dto.EmployeePointDto;
import uni.hcmus.employeemanagement.repository.EmployeeRepository;

import uni.hcmus.employeemanagement.repository.ManagerRepository;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.entity.Manager;
import uni.hcmus.employeemanagement.service.interfaceService.IPointService;

import uni.hcmus.employeemanagement.repository.PointChangeRepository;
import uni.hcmus.employeemanagement.dto.ManagerPointDto;
import uni.hcmus.employeemanagement.dto.ModifyPointRequest;
import uni.hcmus.employeemanagement.entity.PointChange;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointService implements IPointService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private PointChangeRepository pointChangeRepository;

    @Override
    public List<EmployeePointDto> getAllEmployeePoints() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(emp -> new EmployeePointDto(emp.getId(), emp.getName(), emp.getPoint(), emp.getType(), emp.getManagerId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeePointDto> getEmployeePoints(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));

        List<Employee> employees;
        if ("HR".equalsIgnoreCase(employee.getType())) {
            // Nếu là HR, lấy tất cả nhân viên
            employees = employeeRepository.findAll();
        } else if ("Manager".equalsIgnoreCase(employee.getType())) {
            // Nếu là Manager, chỉ lấy các nhân viên do họ quản lý
            employees = employeeRepository.findByManagerId(employee.getId());
        } else {
            throw new IllegalArgumentException("Employee does not have access");
        }
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
    
    @Override
    public List<PointChange> ViewMyChangePoint(String email)
    {
    	Employee emp = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));
    	Long myId = emp.getId();
    	return pointChangeRepository.findByEmployeeIdOrderByChangeDateDesc(myId);
    }

    @Override
    public String getEmployeeRoleById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return (employee != null) ? employee.getType() : null;
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
