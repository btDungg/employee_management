package uni.hcmus.employeemanagement.service.Interface;

import uni.hcmus.employeemanagement.DTO.EmployeePointDto;
import uni.hcmus.employeemanagement.entity.PointChange;

import java.util.List;

public interface IPointService {
    List<EmployeePointDto> getAllEmployeePoints();
    void autoAddPointsToEmployees();
    List<EmployeePointDto> getEmployeePoints(Long id);
    EmployeePointDto ViewPoint(Long id);
    List<PointChange> ViewChangePoint(Long id);
    String getEmployeeRoleById(Long id);
    void increasePoints(EmployeePointDto employeePointDto);
    int getManagerBonusPointsById(Long id);
    String increasePointsByManager(Long managerId, EmployeePointDto employeePointDto);
}
