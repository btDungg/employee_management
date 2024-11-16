package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.EmployeePointDto;
import uni.hcmus.employeemanagement.dto.ModifyPointRequest;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.entity.PointChange;

import java.util.List;

public interface IPointService {
    EmployeePointDto ViewMyPoint(String email);
    List<PointChange> ViewMyChangePoint(String email);
    String getEmployeeRoleById(Long id);
    //Tìm kiếm nhân viên
    EmployeeDto getEmployeeById (String myEmail, Long employeeId);
    String modifyPoints(String email, ModifyPointRequest modifyPoint);
    String increasePoints(Employee employee, ModifyPointRequest modifyPoint);
    int getManagerBonusPointsById(Long id);
    String increasePointsByManager(Employee employee, ModifyPointRequest modifyPoint);
    String decreasePoints(Employee employee, ModifyPointRequest modifyPoint);
    //Lấy danh sách điểm của nhân viên theo chức vụ hiêện tại của người xem
    List<EmployeePointDto> getEmployeePointsBasedOnRole(String token);

    EmployeePointDto getEmployeePointDetailBasedOnRole(Long id,String token);
    // lay tat ca diem cua nhan vien
    List<EmployeePointDto> getAllEmployeePoints();

    void autoAddPointsToEmployees();
}
