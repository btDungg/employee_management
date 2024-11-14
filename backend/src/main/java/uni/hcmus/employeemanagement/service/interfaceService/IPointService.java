package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.EmployeePointDto;
import uni.hcmus.employeemanagement.dto.ModifyPointRequest;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.entity.PointChange;

import java.util.List;

public interface IPointService {
    List<EmployeePointDto> getAllEmployeePoints();
    void autoAddPointsToEmployees();
    List<EmployeePointDto> getEmployeePoints(Long id);
    EmployeePointDto ViewMyPoint(String email);
    List<PointChange> ViewMyChangePoint(String email);
    String getEmployeeRoleById(Long id);
    String modifyPoints(String email, ModifyPointRequest modifyPoint);
    String increasePoints(Employee employee, ModifyPointRequest modifyPoint);
    int getManagerBonusPointsById(Long id);
    String increasePointsByManager(Employee employee, ModifyPointRequest modifyPoint);
    String decreasePoints(Employee employee, ModifyPointRequest modifyPoint);
}
