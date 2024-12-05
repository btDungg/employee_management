package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import uni.hcmus.employeemanagement.dto.Request.LeaveRequestDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeePointDto;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.entity.*;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.repository.*;
import uni.hcmus.employeemanagement.service.interfaceService.IEmployeeService;
import uni.hcmus.employeemanagement.service.interfaceService.ILeaveRequestService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;

@Service
public class LeaveRequestServiceImpl implements ILeaveRequestService {
	@Autowired
    private LeaveRequestRepository leaveRequestRepository;

	@Autowired
    private EmployeeRepository employeeRepository;
	
    @Autowired
    private EmployeeDayOffRepository employeeDayOffRepository;

    @Autowired
    private DayOffTypeRepository dayOffTypeRepository;

	public LeaveRequest sendLeaveRequest(String MyEmail, LeaveRequestDto dto) {
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
		Long type_id = dayOffTypeRepository.findByTypeName(dto.getDayOffType());
		// Tìm EmployeeDayOff dựa vào employeeId và dayOffType
		EmployeeDayOff employeeDayOff = employeeDayOffRepository.findByEmployeeIdAndDayOffType(emp.getId(), type_id);
		
	    if (employeeDayOff == null) {
	        throw new IllegalArgumentException("No matching EmployeeDayOff found for employeeId: " + emp.getId() + " and dayOffType: " + dto.getDayOffType());
	    }


    // Tạo LeaveRequest mới
	    LeaveRequest leaveRequest = new LeaveRequest();
	    leaveRequest.setStartDate(dto.getStartDate());
	    leaveRequest.setEndDate(dto.getEndDate());
	    leaveRequest.setReason(dto.getReason());
	    leaveRequest.setEmployeeDayOff(employeeDayOff);
	    if (employeeDayOff.getEmployee().getOrganization().getManager_id().getEmployeType().equals("Manager"))
	    {
	    	Manager manager = (Manager) employeeDayOff.getEmployee().getOrganization().getManager_id();
	    	leaveRequest.setManager(manager);
	    }
	    else
	    {
	    	throw new IllegalArgumentException("Error");
	    }
	    leaveRequest.setStatus("Pending");
	    long days = dto.getStartDate().until(dto.getEndDate(), ChronoUnit.DAYS);
	    employeeDayOff.setRemainingDays(employeeDayOff.getRemainingDays() - days);

    // Lưu LeaveRequest vào cơ sở dữ liệu
        return leaveRequestRepository.save(leaveRequest);
	}
	}
