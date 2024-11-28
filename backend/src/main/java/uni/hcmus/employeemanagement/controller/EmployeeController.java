package uni.hcmus.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.service.interfaceService.IEmployeeService;
import uni.hcmus.employeemanagement.service.interfaceService.IPointService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private IPointService pointService;
    
    @Autowired
    private IEmployeeService employeeService;
    
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

    @GetMapping("/me/bonus")
    public ResponseEntity<Integer> getMyBonusPointsIfManager(Principal principal) {
        // Lấy email của người dùng từ Principal
        String email = principal.getName();

        // Lấy thông tin nhân viên từ service bằng email
        EmployeeDto employeeDto = employeeService.getEmployeeByEmail(email);

        // Kiểm tra nếu nhân viên là manager
        if ("manager".equalsIgnoreCase(employeeDto.getType())) {
            int bonusPoints = pointService.getManagerBonusPointsById(employeeDto.getId());
            return ResponseEntity.ok(bonusPoints);
        } else {
            return ResponseEntity.status(403).body(null);
        }
    }

    //Lay thong tin cua nhan vien dang dang nhap
    //Tra ve thong tin cua nhan vien dang dang nhap là
    //int: id, string:name, int point, string type, string managerId
    //URL: http://localhost:8080/api/employees/me/profile
    @GetMapping("/me/profile")
    public ResponseEntity<EmployeeDto> getMyProfile(Principal principal) {
        // Lấy email của người dùng từ Principal (Spring Security sẽ tự động cung cấp thông tin này)
        String email = principal.getName();

        // Lấy thông tin nhân viên từ service bằng email
        EmployeeDto employeeDto = employeeService.getEmployeeByEmail(email);

        // Trả về thông tin nhân viên dưới dạng ResponseEntity
        return ResponseEntity.ok(employeeDto);
    }


    //Lay danh sach tat ca nhan vien duoc quan li boi nguoi dung hien tai
    //Tra ve danh sach nhan vien va thong tin lien lac va thong tin lien quan
    //URL: http://localhost:8080/api/employees
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getMyEmployees(Principal principal) {
        // Lấy email của người dùng từ Principal (Spring Security sẽ tự động cung cấp thông tin này)
        String email = principal.getName();
        EmployeeDto empdto = employeeService.getEmployeeByEmail(email);

        // Kiểm tra nếu vai trò không phải là MANAGER
        if (!"Manager".equals(empdto.getType())) {
            throw new AccessDeniedException("Access denied: You do not have permission to view this resource.");
        }

        // Lấy danh sách nhân viên từ service dựa trên email của người dùng hiện tại
        Optional<List<EmployeeDto>> employeesOptional = employeeService.getEmployeesByManagerid(empdto.getId());

        // Kiểm tra nếu danh sách nhân viên không tồn tại hoặc rỗng
        if (employeesOptional.isEmpty() || employeesOptional.get().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Trả về danh sách nhân viên dưới dạng ResponseEntity
        return ResponseEntity.ok(employeesOptional.get());
    }


}