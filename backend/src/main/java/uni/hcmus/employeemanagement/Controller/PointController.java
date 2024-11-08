package uni.hcmus.employeemanagement.Controller;
import org.springframework.http.ResponseEntity;
import uni.hcmus.employeemanagement.DTO.EmployeePointDto;
import uni.hcmus.employeemanagement.entity.PointChange;
import uni.hcmus.employeemanagement.service.Interface.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointController {
    @Autowired
    private IPointService pointService;

    @GetMapping("/employee/{id}")
    public List<EmployeePointDto> getEmployeePoints(@PathVariable Long id) {
        return pointService.getEmployeePoints(id);
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
    @GetMapping("{id}")
    public ResponseEntity<?> getPoints(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pointService.ViewPoint(id));
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
