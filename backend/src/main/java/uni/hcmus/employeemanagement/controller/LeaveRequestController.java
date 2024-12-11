package uni.hcmus.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uni.hcmus.employeemanagement.dto.Request.LeaveRequestDto;
import uni.hcmus.employeemanagement.dto.Response.DayOffTypeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDayOffDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.dto.Response.LeaveRequestResponseDto;
import uni.hcmus.employeemanagement.dto.Response.ModifyPointRequest;
import uni.hcmus.employeemanagement.entity.*;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.service.interfaceService.IEmployeeService;
import uni.hcmus.employeemanagement.service.interfaceService.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leave-request")
public class LeaveRequestController {
	@Autowired
    private ILeaveRequestService leaveRequestService;
    
    @Autowired
    private IEmployeeService employeeService;
    
    @PostMapping("/new")
    public ResponseEntity<LeaveRequestResponseDto> sendLeaveRequest(Principal principal, @RequestBody LeaveRequestDto leaveRequest) {
        String email = principal.getName();
        LeaveRequestResponseDto result = leaveRequestService.sendLeaveRequest(email, leaveRequest);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @GetMapping("/get-my-day-off")
    public ResponseEntity<List<EmployeeDayOffDto>> getMyDayOff(Principal principal) {
        String email = principal.getName();
        List<EmployeeDayOffDto> result = leaveRequestService.getMyDayOff(email);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @GetMapping("/get-list-type")
    public ResponseEntity<List<DayOffTypeDto>> getListDayOffType(Principal principal) {
        String email = principal.getName();
        List<DayOffTypeDto> result = leaveRequestService.getListDayOffType();
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
