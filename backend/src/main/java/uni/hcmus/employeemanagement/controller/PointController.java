package uni.hcmus.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.hcmus.employeemanagement.dto.EmployeePointDto;
import uni.hcmus.employeemanagement.entity.PointChange;
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
    public ResponseEntity<List<EmployeePointDto>> getEmployeeWithBaseRole() {
        List<EmployeePointDto> employeePoints = pointService.getEmployeePointsBasedOnRole();
        return ResponseEntity.ok(employeePoints);
    }

    //Lấy thông tin điểm của nhân viên dựa trên id của nhân viên và nguười dùng hiện tại la ai.
    //Trả về thông tin điểm của nhân viên.
    //Đường dẫn: /api/points/employee/{id}
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeePointDto> getDetailEmployeePoints(@PathVariable Long id) {
        EmployeePointDto employeePoint = pointService.getEmployeePointDetailBasedOnRole(id);
        return ResponseEntity.ok(employeePoint);
    }



    @GetMapping("/test")
    public List<EmployeePointDto> getAll() {
        return pointService.getAllEmployeePoints();
    }

    @PostMapping("/test/auto-add-points")
    public ResponseEntity<String> testAutoAddPoints() {
        pointService.autoAddPointsToEmployees();
        return ResponseEntity.ok("Points have been added successfully!");
    }


    @GetMapping("/{id}/pointChanges")
    public List<PointChange> getPointChanges(@PathVariable("id") Long id) {
        return pointService.ViewChangePoint(id);
    }

    @PostMapping("/increase")
    public ResponseEntity<String> increasePoints(@RequestBody EmployeePointDto employeePointDto) {
        pointService.increasePoints(employeePointDto);
        return ResponseEntity.ok("Points have been increased successfully!");
    }

    @PostMapping("/increaseByManager/{managerId}")
    public ResponseEntity<String> increasePointsByManager(@PathVariable Long managerId, @RequestBody EmployeePointDto employeePointDto) {
        String result = pointService.increasePointsByManager(managerId, employeePointDto);
        if ("Points have been increased successfully!".equals(result)) {
            return ResponseEntity.ok(result);
        }
        else if ("Manager does not have enough bonus points".equals(result)) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
