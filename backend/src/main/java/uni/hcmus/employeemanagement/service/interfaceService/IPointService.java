package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeePointDto;
import uni.hcmus.employeemanagement.dto.Response.ModifyPointRequest;
import uni.hcmus.employeemanagement.dto.Response.PointChangeDto;
import uni.hcmus.employeemanagement.dto.Request.SearchEmployeeRequest;
import uni.hcmus.employeemanagement.entity.Employee;

import java.util.List;

public interface IPointService {
    EmployeePointDto ViewMyPoint(String email);
    List<PointChangeDto> ViewMyChangePoint(String email);
    String getEmployeeRoleById(Long id);
    //Tìm kiếm nhân viên
    EmployeeDto getEmployeeById (String myEmail, SearchEmployeeRequest searchRequest);
    String modifyPoints(String email, ModifyPointRequest modifyPoint);
    String increasePoints(Employee employee, ModifyPointRequest modifyPoint);
    int getManagerBonusPointsById(Long id);
    String increasePointsByManager(Employee employee, ModifyPointRequest modifyPoint);
    String decreasePoints(Employee employee, ModifyPointRequest modifyPoint);
    //Lấy danh sách điểm của nhân viên theo chức vụ hiêện tại của người xem
    List<EmployeePointDto> getEmployeePointsBasedOnRole(String userEmail);

    EmployeePointDto getEmployeePointDetailBasedOnRole(Long id,String userEmail);
    // lay tat ca diem cua nhan vien
    List<EmployeePointDto> getAllEmployeePoints();

//    void autoAddPointsToEmployees();
}
