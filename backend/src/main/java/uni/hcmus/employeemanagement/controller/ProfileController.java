package uni.hcmus.employeemanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.hcmus.employeemanagement.dto.Response.EmployeePublicDto_v1;
import uni.hcmus.employeemanagement.service.interfaceService.IEmployeeService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    IEmployeeService employeeService;

    @GetMapping("all")
    public ResponseEntity<List<EmployeePublicDto_v1>> getAllProfile(Principal principal) {
        String email = principal.getName();
        List<EmployeePublicDto_v1> employees = employeeService.getEmployeeByManagerID_v1(email).orElse(null);
        return ResponseEntity.ok(employees);
    }

}
