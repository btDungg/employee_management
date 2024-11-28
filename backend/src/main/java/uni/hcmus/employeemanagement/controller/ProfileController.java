package uni.hcmus.employeemanagement.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.hcmus.employeemanagement.dto.Response.EmployeePublicDto_v1;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @GetMapping("all")
    public ResponseEntity<EmployeePublicDto_v1> getAllProfile(){
        return ResponseEntity.ok(new EmployeePublicDto_v1());
    }

}
