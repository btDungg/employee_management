package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.EmployeePointDto;
import uni.hcmus.employeemanagement.entity.PointChange;

import java.util.List;

public interface IPointService {
    //Lấy danh sách điểm của nhân viên theo chức vụ hiêện tại của người xem
    List<EmployeePointDto> getEmployeePointsBasedOnRole();

    EmployeePointDto getEmployeePointDetailBasedOnRole(Long id);
    // lay tat ca diem cua nhan vien
    List<EmployeePointDto> getAllEmployeePoints();
    //lấy danh sách điểm của nhân viên dựa trên id của người quản lý
    List<EmployeePointDto> getEmployeePointsByManagerId(Long managerId);
    void autoAddPointsToEmployees();
    List<EmployeePointDto> getEmployeePoints(Long id);

    List<PointChange> ViewChangePoint(Long id);
    String getEmployeeRoleById(Long id);
    void increasePoints(EmployeePointDto employeePointDto);
    int getManagerBonusPointsById(Long id);
    String increasePointsByManager(Long managerId, EmployeePointDto employeePointDto);
}
