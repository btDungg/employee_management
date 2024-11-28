package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.Response.EmployeeDetailInfoDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeePublicDto_v1;
import uni.hcmus.employeemanagement.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    Employee addEmployee(Employee employee);

    void sendOTP(String email) throws Exception;

    boolean verifyOTP(String otp, String email) throws Exception;

    void resetPassword(String email, String password) throws Exception;

    EmployeeDto getEmployeeByEmail(String email);

    EmployeeDetailInfoDto getEmployeePersonalInfoByEmail(String email);

    Optional<List<EmployeeDto>> getEmployeesByManagerid(Long id); //lay danh sach nhan vien theo id cua manager

    Optional<List<EmployeePublicDto_v1>> getEmployeeByManagerID_v1(String email); //lay danh sach nhan vien theo id cua manager
}