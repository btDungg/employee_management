package uni.hcmus.employeemanagement.controller;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.dto.Response.ModifyPointRequest;
import uni.hcmus.employeemanagement.dto.Response.PointChangeDto;
import uni.hcmus.employeemanagement.service.interfaceService.IPointService;
import uni.hcmus.employeemanagement.utils.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uni.hcmus.employeemanagement.dto.Response.EmployeePointDto;
import uni.hcmus.employeemanagement.dto.Request.SearchEmployeeRequest;

import java.security.Principal;
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

//    @GetMapping("/employee/{id}")
//    public List<EmployeePointDto> getEmployeePoints(@PathVariable Long id) {
//        return pointService.getEmployeePoints(id);
//    }
//
//    @GetMapping("/test")
//    public List<EmployeePointDto> getAll() {
//        return pointService.getAllEmployeePoints();
//    }
//
//    @PostMapping("/test/auto-add-points")
//    public ResponseEntity<String> testAutoAddPoints() {
//        pointService.autoAddPointsToEmployees();
//        return ResponseEntity.ok("Points have been added successfully!");
//    }

    @GetMapping
    public ResponseEntity<?> getMyPoints(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);
        String email = jwtTokenUtil.extractUserIdentifier(token);
        return ResponseEntity.ok(pointService.ViewMyPoint(email));
    }



    //Lấy danh sách các nhân viên dựa trên vai trò của người dùng hiện tại.
    //Trả về danh sách các nhân viên và số điểm của họ.
    //Đươờng dẫn: /api/points/employees/point
    @GetMapping("/employees/point")
    public ResponseEntity<List<EmployeePointDto>> getEmployeeWithBaseRole(Principal principal) {
        // Kiểm tra xem principal có null không
        if (principal == null) {
            throw new IllegalArgumentException("Principal is missing. Unable to authenticate user.");
        }

        // Lấy tên người dùng (username) từ Principal
        String useEmail = principal.getName();

        // Truyền tên người dùng vào service để lấy danh sách điểm nhân viên
        List<EmployeePointDto> employeePoints = pointService.getEmployeePointsBasedOnRole(useEmail);
        return ResponseEntity.ok(employeePoints);
    }

    //Lấy thông tin điểm của nhân viên dựa trên id của nhân viên và nguười dùng hiện tại la ai.
    //Trả về thông tin điểm của nhân viên.
    //Đường dẫn: /api/points/employee/{id}
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeePointDto> getDetailEmployeePoints(@PathVariable Long id, Principal principal) {
        // Kiểm tra xem principal có null không
        if (principal == null) {
            throw new IllegalArgumentException("Principal is missing. Unable to authenticate user.");
        }

        // Lấy tên người dùng (username) từ Principal
        String useEmail = principal.getName();
        EmployeePointDto employeePoint = pointService.getEmployeePointDetailBasedOnRole(id, useEmail);
        return ResponseEntity.ok(employeePoint);
    }


    @GetMapping("pointChanges")
    public List<PointChangeDto> getMyPointChanges(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);
        String email = jwtTokenUtil.extractUserIdentifier(token);
        return pointService.ViewMyChangePoint(email);
    }

    @PostMapping("/modify-points")
    public ResponseEntity<String> modifyPoint(Principal principal, @RequestBody ModifyPointRequest modifyPoint) {
        String email = principal.getName();
        String result = pointService.modifyPoints(email, modifyPoint);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }

   
    @PostMapping("/search")
	public ResponseEntity<EmployeeDto> searchEmployeeById(@RequestHeader("Authorization") String authorizationHeader, @RequestBody SearchEmployeeRequest searchRequest)
	{
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid.");
        }
		String token = authorizationHeader.substring(7);
		String email = jwtTokenUtil.extractUserIdentifier(token);
		return ResponseEntity.ok(pointService.getEmployeeById(email, searchRequest));
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
