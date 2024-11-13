package uni.hcmus.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.hcmus.employeemanagement.dto.EmployeeDto;
import uni.hcmus.employeemanagement.service.interfaceService.IEmployeeService;
import uni.hcmus.employeemanagement.service.interfaceService.IPointService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private IPointService pointService;

    @Autowired
    private IEmployeeService employeeService;


    //Lay thong tin cua nhan vien dang dang nhap
    //Tra ve thong tin cua nhan vien dang dang nhap là
    //int: id, string:name, int point, string type, string managerId
    //URL: http://localhost:8080/api/employees/me/profile
    @GetMapping("/me/profile")
    public ResponseEntity<EmployeeDto> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Địa chỉ email của người dùng

        EmployeeDto employeeDto = employeeService.getEmployeeProfileByEmail(email);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("/{id}/role")
    public ResponseEntity<String> getEmployeeRoleById(@PathVariable Long id) {
        String role = pointService.getEmployeeRoleById(id);
        return (role != null) ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/bonus")
    public ResponseEntity<Integer> getManagerBonusPointsById(@PathVariable Long id) {
        int bonusPoints = pointService.getManagerBonusPointsById(id);
        return ResponseEntity.ok(bonusPoints);
    }
}