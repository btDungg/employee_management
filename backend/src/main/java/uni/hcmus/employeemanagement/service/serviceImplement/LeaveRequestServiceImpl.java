package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uni.hcmus.employeemanagement.dto.Request.LeaveRequestDto;
import uni.hcmus.employeemanagement.dto.Response.DayOffTypeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeePointDto;
import uni.hcmus.employeemanagement.dto.Response.LeaveRequestResponseDto;
import uni.hcmus.employeemanagement.entity.*;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.repository.*;
import uni.hcmus.employeemanagement.service.interfaceService.ILeaveRequestService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
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
    
    @Override
	public LeaveRequestResponseDto sendLeaveRequest(String MyEmail, LeaveRequestDto leaveRequestDto) {
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
		Long day_off_type_id = dayOffTypeRepository.findByTypeName(leaveRequestDto.getDayOffType());
		// Tìm EmployeeDayOff dựa vào employeeId và dayOffType
		EmployeeDayOff employeeDayOff = employeeDayOffRepository.findByEmployeeIdAndDayOffType(emp.getId(), day_off_type_id);
		
	    if (employeeDayOff == null) {
	        throw new DataNotFoundException("Không có dữ liệu về số ngày nghỉ của nhân viên " + emp.getName() + " với loại ngày nghỉ " + leaveRequestDto.getDayOffType());
	    }

	    LocalDate startDate = leaveRequestDto.getStartDate();
	    LocalDate endDate = leaveRequestDto.getEndDate();
	    long days = startDate.until(endDate, ChronoUnit.DAYS);
	    if (startDate.isAfter(endDate))
	    {
	    	throw new AccessDeniedException("Ngày bắt đầu nhỏ hơn ngày kết thúc!");
	    }
	    List<LeaveRequest> existsLeaveRequest = leaveRequestRepository.isExistsLeaveRequest(emp.getId(), startDate, endDate);
	    if (!existsLeaveRequest.isEmpty())
	    {
	    	throw new AccessDeniedException("Đã tồn tại yêu cầu nghỉ phép trong khoảng thời gian bạn yêu cầu!");
	    }
	    if (days > employeeDayOff.getRemainingDays())
	    {
	    	throw new AccessDeniedException("Số ngày xin nghỉ lớn hơn số ngày nghỉ còn lại của loại ngày nghỉ " + leaveRequestDto.getDayOffType() + "!");
	    }
	    // Tạo LeaveRequest mới
	    LeaveRequest leaveRequest = new LeaveRequest();
	    leaveRequest.setStartDate(startDate);
	    leaveRequest.setEndDate(endDate);
	    leaveRequest.setReason(leaveRequestDto.getReason());
	    leaveRequest.setEmployeeDayOff(employeeDayOff);
	    if (employeeDayOff.getEmployee().getOrganization().getManager_id().getEmployeType().equals("Manager"))
	    {
	    	Manager manager = (Manager) employeeDayOff.getEmployee().getOrganization().getManager_id();
	    	leaveRequest.setManager(manager);
	    }
	    else
	    {
	    	throw new DataNotFoundException("Cấp trên của nhân viên này không phải quản lý!");
	    }
	    leaveRequest.setStatus("Đang chờ duyệt");
	    employeeDayOff.setRemainingDays(employeeDayOff.getRemainingDays() - days);
	    // Lưu LeaveRequest và EmployeeDayOff sau khi giảm số ngày còn lại vào cơ sở dữ liệu
        leaveRequestRepository.save(leaveRequest);
        employeeDayOffRepository.save(employeeDayOff);
        return new LeaveRequestResponseDto(
        		leaveRequest.getId(),
        		emp.getId(),
        		leaveRequest.getStartDate(),
        		leaveRequest.getEndDate(),
        		leaveRequest.getReason(),
        		leaveRequest.getEmployeeDayOff().getDay_off_type().getType(),
        		emp.getOrganization().getManager_id().getId()
        		);
	}
	
	@Override
	public List<DayOffTypeDto> getListDayOffType()
	{
		List<String> listDayOffType = dayOffTypeRepository.findByTypeName();
		return listDayOffType.stream()
                .map(dayOffType -> new DayOffTypeDto(dayOffType))
                .collect(Collectors.toList());
	}
	
}
