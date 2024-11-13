package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uni.hcmus.employeemanagement.DTO.EmployeePointDto;
import uni.hcmus.employeemanagement.repository.EmployeeRepository;

import uni.hcmus.employeemanagement.repository.ManagerRepository;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.entity.Manager;
import uni.hcmus.employeemanagement.service.interfaceService.IPointService;

import uni.hcmus.employeemanagement.repository.PointChangeRepository;
import uni.hcmus.employeemanagement.DTO.ManagerPointDto;
import uni.hcmus.employeemanagement.entity.PointChange;


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
    public EmployeePointDto ViewPoint(Long Id)
    {
    	Optional<Employee> emp = employeeRepository.findById(Id);
    	Employee employee = emp.get();

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
        	throw new IllegalArgumentException("Invalid ID");
        }
    }
    
    @Override
    public List<PointChange> ViewChangePoint(Long Id)
    {
    	return pointChangeRepository.findByEmployeeIdOrderByChangeDateDesc(Id);
    }

    @Override
    public String getEmployeeRoleById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return (employee != null) ? employee.getType() : null;
    }

    @Override
    public void increasePoints(EmployeePointDto employeePointDto) {
        Employee employee = employeeRepository.findById(employeePointDto.getId()).orElse(null);
        if (employee != null) {
            employee.setPoint(employee.getPoint() + employeePointDto.getPoint());
            employeeRepository.save(employee);
        }
    }

    @Override
    public int getManagerBonusPointsById(Long id) {
        Manager manager = managerRepository.findById(id).orElse(null);
        return (manager != null) ? manager.getBonusEmployeePoint() : 0;
    }

    @Override
    public String increasePointsByManager(Long managerId, EmployeePointDto employeePointDto) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new IllegalArgumentException("Invalid manager ID"));
        Employee employee = employeeRepository.findById(employeePointDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));

        if (manager.getBonusEmployeePoint() >= employeePointDto.getPoint()) {
            employee.setPoint(employee.getPoint() + employeePointDto.getPoint());
            manager.setBonusEmployeePoint(manager.getBonusEmployeePoint() - employeePointDto.getPoint());
            employeeRepository.save(employee);
            managerRepository.save(manager);
            return "Points have been increased successfully!";
        } else {
            return "Manager does not have enough bonus points";
        }
    }
}
