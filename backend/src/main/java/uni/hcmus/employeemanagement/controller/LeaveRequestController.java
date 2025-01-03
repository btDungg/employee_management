package uni.hcmus.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uni.hcmus.employeemanagement.dto.Request.ApproveLeaveRequest;
import uni.hcmus.employeemanagement.dto.Request.LeaveRequestDto;
import uni.hcmus.employeemanagement.dto.Response.ApprovedLeaveRequestResponseDto;
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
    
    @PostMapping("/approve")
    public ResponseEntity<ApprovedLeaveRequestResponseDto> approveLeaveRequest(Principal principal, @RequestBody ApproveLeaveRequest leaveRequest) {
        String email = principal.getName();
        ApprovedLeaveRequestResponseDto result = leaveRequestService.approveLeaveRequest(email, leaveRequest);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @PostMapping("/approve-all")
    public ResponseEntity<List<ApprovedLeaveRequestResponseDto>> approveAllLeaveRequest(Principal principal, @RequestBody List<ApproveLeaveRequest> listLeaveRequest) {
        String email = principal.getName();
        List<ApprovedLeaveRequestResponseDto> result = leaveRequestService.approveAllLeaveRequests(email, listLeaveRequest);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @GetMapping("/get-my-approve-lr")
    public ResponseEntity<List<LeaveRequestResponseDto>> getMyListApproveLeaveRequest(Principal principal) {
        String email = principal.getName();
        List<LeaveRequestResponseDto> result = leaveRequestService.getMyApproveLeaveRequest(email);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @GetMapping("/get-my-leave-request")
    public ResponseEntity<List<LeaveRequestResponseDto>> getMyListLeaveRequest(Principal principal) {
        String email = principal.getName();
        List<LeaveRequestResponseDto> result = leaveRequestService.getMyLeaveRequest(email);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLeaveRequest(Principal principal, @RequestBody LeaveRequestDto leaveRequest) {
        String email = principal.getName();
        try {
        	leaveRequestService.deleteLeaveRequest(email, leaveRequest);
            return ResponseEntity.ok("Yêu cầu nghỉ phép đã được xóa thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
