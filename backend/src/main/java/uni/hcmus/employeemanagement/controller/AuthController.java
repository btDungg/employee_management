package uni.hcmus.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import uni.hcmus.employeemanagement.dto.*;
=======
import uni.hcmus.employeemanagement.dto.LoginRequest;
import uni.hcmus.employeemanagement.dto.LoginResponse;
import uni.hcmus.employeemanagement.dto.Request.UserRequest;
>>>>>>> origin/feature/viewPoint
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.exception_handler.exceptions.EmailAlreadyTakenException;
import uni.hcmus.employeemanagement.repository.EmployeeRepository;
import uni.hcmus.employeemanagement.service.serviceImplement.EmployeeServiceImpl;
import uni.hcmus.employeemanagement.utils.JwtTokenUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Employee employee = employeeRepository.findByEmailCompany(loginRequest.getEmail())
                .orElseThrow(() -> new DataNotFoundException("Cannot find employee with email company = " + loginRequest.getEmail()));
        String token = jwtTokenUtil.generateToken(employee);
        System.out.println("Generated Token: " + token);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO request) {
        try {
            employeeService.sendOTP(request.getEmail());
            return ResponseEntity.ok("Send OTP successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO request) {
        try {
            employeeService.resetPassword(request.getEmail(), request.getPassword());
            return ResponseEntity.ok("Change password successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpDTO request) {
        try {
            boolean isValidOtp = employeeService.verifyOTP(request.getOtp(), request.getEmail());
            if (!isValidOtp) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP is wrong");
            }
            return ResponseEntity.ok("OTP verified successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successfully");
    }


    //Tạo tài khoản mới cho nhân viên
    //Truyền vào thông tin nhân viên cần tạo. UserRequest gồm có: username, email, password, managerId, type
    @PostMapping("/register")
    public ResponseEntity<Employee> register(@RequestBody UserRequest userRequest) {
        if(employeeRepository.existsByEmailCompany(userRequest.getEmail())) {
            throw new EmailAlreadyTakenException("Email is already taken! " + userRequest.getEmail());
        }

        Employee employee = new Employee();
        employee.setName(userRequest.getUsername());
        employee.setManagerId(userRequest.getManagerId());
        employee.setType(userRequest.getType());
        employee.setEmailCompany(userRequest.getEmail());
        employee.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        employeeRepository.save(employee);
        return ResponseEntity.ok(employee);
    }
}

