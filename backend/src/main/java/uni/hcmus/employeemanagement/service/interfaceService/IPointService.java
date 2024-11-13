package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.EmployeePointDto;

import java.util.List;

public interface IPointService {
    //Lấy danh sách điểm của nhân viên theo chức vụ hiêện tại của người xem
    List<EmployeePointDto> getEmployeePointsBasedOnRole(String token);

    EmployeePointDto getEmployeePointDetailBasedOnRole(Long id,String token);
    // lay tat ca diem cua nhan vien
    List<EmployeePointDto> getAllEmployeePoints();

    void autoAddPointsToEmployees();

}
