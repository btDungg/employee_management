package uni.hcmus.employeemanagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.hcmus.employeemanagement.DTO.EmployeePointDto;
import uni.hcmus.employeemanagement.service.Interface.IPointService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private IPointService pointService;

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