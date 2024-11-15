package uni.hcmus.employeemanagement.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import uni.hcmus.employeemanagement.dto.*;
import uni.hcmus.employeemanagement.entity.PointChange;
import uni.hcmus.employeemanagement.service.interfaceService.IPointService;
import uni.hcmus.employeemanagement.utils.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointController {
    @Autowired
    private IPointService pointService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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

    @GetMapping
    public ResponseEntity<?> getMyPoints(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);
        String email = jwtTokenUtil.extractUserIdentifier(token);
        return ResponseEntity.ok(pointService.ViewMyPoint(email));
    }


    @GetMapping("pointChanges")
    public List<PointChange> getMyPointChanges(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);
        String email = jwtTokenUtil.extractUserIdentifier(token);
        return pointService.ViewMyChangePoint(email);
    }

    @PostMapping("/modify-points")
    public ResponseEntity<String> modifyPoint(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ModifyPointRequest modifyPoint) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);
        String email = jwtTokenUtil.extractUserIdentifier(token);
        String result = pointService.modifyPoints(email, modifyPoint);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    /*@PostMapping("/increase")
    public ResponseEntity<String> increasePoints(@PathVariable Long myId, @RequestBody EmployeePointDto employeePointDto) {
    	String result = pointService.increasePoints(myId, employeePointDto);
    	if ("Points have been increased successfully!".equals(result)) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/increaseByManager")
    public ResponseEntity<String> increasePointsByManager(@PathVariable Long myId, @RequestBody EmployeePointDto employeePointDto) {
        String result = pointService.increasePointsByManager(myId, employeePointDto);
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

    @PostMapping("/decrease")
    public ResponseEntity<String> decreasePoints(@PathVariable Long myId, @RequestBody EmployeePointDto employeePointDto) {
        String result = pointService.decreasePoints(myId, employeePointDto);
        if ("Points have been decreased successfully!".equals(result)) {
            return ResponseEntity.ok(result);
        }
        else if ("Employee does not have enough points to decrease!".equals(result)) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }

*/

}
