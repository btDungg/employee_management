package uni.hcmus.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.hcmus.employeemanagement.dto.EmployeePointDto;
import uni.hcmus.employeemanagement.service.interfaceService.IPointService;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointController {
    @Autowired
    private IPointService pointService;



    //Lấy danh sách các nhân viên dựa trên vai trò của người dùng hiện tại.
    //Trả về danh sách các nhân viên và số điểm của họ.
    //Đươờng dẫn: /api/points/employees/point
    @GetMapping("/employees/point")
    public ResponseEntity<List<EmployeePointDto>> getEmployeeWithBaseRole(@RequestHeader("Authorization") String authorizationHeader) {
        // Kiểm tra token hợp lệ
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid.");
        }

        // Lấy token từ header Authorization (loại bỏ "Bearer ")
        String token = authorizationHeader.substring(7);

        // Truyền token xuống service để lấy danh sách điểm nhân viên
        List<EmployeePointDto> employeePoints = pointService.getEmployeePointsBasedOnRole(token);
        return ResponseEntity.ok(employeePoints);
    }



    //Lấy thông tin điểm của nhân viên dựa trên id của nhân viên và nguười dùng hiện tại la ai.
    //Trả về thông tin điểm của nhân viên.
    //Đường dẫn: /api/points/employee/{id}
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeePointDto> getDetailEmployeePoints(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);
        EmployeePointDto employeePoint = pointService.getEmployeePointDetailBasedOnRole(id, token);
        return ResponseEntity.ok(employeePoint);
    }







}
