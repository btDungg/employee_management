package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uni.hcmus.employeemanagement.dto.Request.ApproveLeaveRequest;
import uni.hcmus.employeemanagement.dto.Request.LeaveRequestDto;
import uni.hcmus.employeemanagement.dto.Response.ApprovedLeaveRequestResponseDto;
import uni.hcmus.employeemanagement.dto.Response.DayOffTypeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDayOffDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeeDto;
import uni.hcmus.employeemanagement.dto.Response.EmployeePointDto;
import uni.hcmus.employeemanagement.dto.Response.LeaveRequestResponseDto;
import uni.hcmus.employeemanagement.entity.*;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.repository.*;
import uni.hcmus.employeemanagement.service.interfaceService.ILeaveRequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    
    @Autowired
    private NotificationLRRepository notificationLRRepository;
    
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
	    if (startDate.isAfter(endDate))
	    {
	    	throw new AccessDeniedException("Ngày bắt đầu lớn hơn ngày kết thúc!");
	    }
	    List<LeaveRequest> existsLeaveRequest = leaveRequestRepository.isExistsLeaveRequest(emp.getId(), startDate, endDate);
	    if (!existsLeaveRequest.isEmpty())
	    {
	    	throw new AccessDeniedException("Đã tồn tại yêu cầu nghỉ phép trong khoảng thời gian bạn yêu cầu!");
	    }
	    if (leaveRequestDto.getRequestDays() > employeeDayOff.getRemainingDays())
	    {
	    	throw new AccessDeniedException("Số ngày xin nghỉ lớn hơn số ngày nghỉ còn lại của loại ngày nghỉ " + leaveRequestDto.getDayOffType() + "!");
	    }
	    // Tạo LeaveRequest mới
	    LeaveRequest leaveRequest = new LeaveRequest();
	    leaveRequest.setStartDate(startDate);
	    leaveRequest.setEndDate(endDate);
	    leaveRequest.setRequestDays(leaveRequestDto.getRequestDays());
	    leaveRequest.setReason(leaveRequestDto.getReason());
	    leaveRequest.setSession(leaveRequestDto.getSession());
	    leaveRequest.setEmployeeDayOff(employeeDayOff);
	    if (employeeDayOff.getEmployee().getOrganization().getManager_id() == null)
	    {
	    	throw new DataNotFoundException("Bộ phận của nhân viên này chưa có quản lý!");
	    }
	    if (employeeDayOff.getEmployee().getOrganization().getManager_id().getTimeType().equals("Manager"))
	    {
	    	Manager manager = (Manager) employeeDayOff.getEmployee().getOrganization().getManager_id();
	    	leaveRequest.setManager(manager);
	    }
	    else
	    {
	    	throw new DataNotFoundException("Cấp trên của nhân viên này không phải quản lý!");
	    }
	    leaveRequest.setStatus("Đang chờ duyệt");
	    employeeDayOff.setRemainingDays(employeeDayOff.getRemainingDays() - leaveRequestDto.getRequestDays());
	    // Lưu LeaveRequest và EmployeeDayOff sau khi giảm số ngày còn lại vào cơ sở dữ liệu
        leaveRequestRepository.save(leaveRequest);
        employeeDayOffRepository.save(employeeDayOff);
        return new LeaveRequestResponseDto(
        		leaveRequest.getId(),
        		emp.getName(),
        		leaveRequest.getStartDate(),
        		leaveRequest.getEndDate(),
        		leaveRequest.getRequestDays(),
        		leaveRequest.getReason(),
        		leaveRequest.getStatus(),
        		null,
        		leaveRequest.getEmployeeDayOff().getDay_off_type().getType(),
        		leaveRequest.getRejectReason(),
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
	
	@Override
	public List<EmployeeDayOffDto> getMyDayOff(String MyEmail)
	{
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
		List<EmployeeDayOff> listMyDayOff = employeeDayOffRepository.getRemainingDaysByEmployeeId(emp.getId());
		return listMyDayOff.stream()
                .map(myDayOff -> new EmployeeDayOffDto(myDayOff.getDay_off_type().getType(), myDayOff.getRemainingDays()))
                .collect(Collectors.toList());
	}
	
	@Override
	public ApprovedLeaveRequestResponseDto approveLeaveRequest(String MyEmail, ApproveLeaveRequest leaveRequest)
	{
		System.out.println(leaveRequest.getRejectReason());
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
		LeaveRequest approveLeaveRequest = leaveRequestRepository.findByEmployeeIdAndRequestTime(leaveRequest.getEmployeeId(), leaveRequest.getStartDate(), leaveRequest.getEndDate());
		if (approveLeaveRequest == null)
			throw new DataNotFoundException("Không có yêu cầu nghỉ phép từ ngày " + leaveRequest.getStartDate() + " đến ngày " + leaveRequest.getEndDate() + "!");
		approveLeaveRequest.setStatus(leaveRequest.getStatus());
		if (leaveRequest.getRejectReason() == null)
			approveLeaveRequest.setRejectReason(null);
		else
			approveLeaveRequest.setRejectReason(leaveRequest.getRejectReason());
		System.out.println(approveLeaveRequest.getRejectReason());
		leaveRequestRepository.save(approveLeaveRequest);
		LocalDate createdDate = LocalDate.now();
        LocalTime createdTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String createdTimeFormatted = createdTime.format(formatter);
        NotificationLR notification = new NotificationLR(approveLeaveRequest, "Chưa đọc", createdDate, createdTimeFormatted);
        notificationLRRepository.save(notification);
		return new ApprovedLeaveRequestResponseDto(
        		approveLeaveRequest.getStartDate(),
        		approveLeaveRequest.getEndDate(),
        		approveLeaveRequest.getRequestDays(),
        		approveLeaveRequest.getEmployeeDayOff().getDay_off_type().getType(),
        		approveLeaveRequest.getSession(),
        		approveLeaveRequest.getReason(),
        		approveLeaveRequest.getStatus(),
        		approveLeaveRequest.getRejectReason()
        		);
	}
	
	@Override
	public List<LeaveRequestResponseDto> getMyApproveLeaveRequest (String MyEmail)
	{
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
	
        if (!"Manager".equals(emp.getType()))
        {
        	throw new AccessDeniedException("You do not have permission to view employee's leave request!");
        }
        List<LeaveRequest> myListApproveLeaveRequest = leaveRequestRepository.findByManagerIdOrderByStartDateDesc(emp.getId());
        if (myListApproveLeaveRequest.isEmpty())
        {
        	throw new DataNotFoundException("Don't find leave request in data!");
        }
        return myListApproveLeaveRequest.stream()
                .map(myApproveLeaveRequest -> new LeaveRequestResponseDto(myApproveLeaveRequest.getEmployeeDayOff().getEmployee().getId(),
                															myApproveLeaveRequest.getEmployeeDayOff().getEmployee().getName(),
                															myApproveLeaveRequest.getStartDate(),
                															myApproveLeaveRequest.getEndDate(),
                															myApproveLeaveRequest.getRequestDays(),
                															myApproveLeaveRequest.getReason(),
                															myApproveLeaveRequest.getStatus(),
                															myApproveLeaveRequest.getRejectReason(),
                															myApproveLeaveRequest.getEmployeeDayOff().getDay_off_type().getType(),
                															myApproveLeaveRequest.getSession(),
                															emp.getId()
                															))
                .collect(Collectors.toList()); 
	}
	
	@Override
	public List<LeaveRequestResponseDto> getMyLeaveRequest (String MyEmail)
	{
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
        List<LeaveRequest> myListApproveLeaveRequest = leaveRequestRepository.findByEmloyeeIdOrderByStartDateDesc(emp.getId());
        return myListApproveLeaveRequest.stream()
                .map(myApproveLeaveRequest -> new LeaveRequestResponseDto(myApproveLeaveRequest.getEmployeeDayOff().getEmployee().getId(),
                															myApproveLeaveRequest.getEmployeeDayOff().getEmployee().getName(),
                															myApproveLeaveRequest.getStartDate(),
                															myApproveLeaveRequest.getEndDate(),
                															myApproveLeaveRequest.getRequestDays(),
                															myApproveLeaveRequest.getReason(),
                															myApproveLeaveRequest.getStatus(),
                															myApproveLeaveRequest.getRejectReason(),
                															myApproveLeaveRequest.getEmployeeDayOff().getDay_off_type().getType(),
                															myApproveLeaveRequest.getSession(),
                															emp.getId()
                															))
                .collect(Collectors.toList()); 
	}
	
	@Override
	public void deleteLeaveRequest (String MyEmail, LeaveRequestDto leaveRequest)
	{
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
		LeaveRequest deleteLeaveRequest = leaveRequestRepository.findByEmployeeIdAndRequestTime(emp.getId(), leaveRequest.getStartDate(), leaveRequest.getEndDate());
		System.out.println(deleteLeaveRequest.getRequestDays());
		if (deleteLeaveRequest == null)
			throw new DataNotFoundException("Không có yêu cầu nghỉ phép từ ngày " + leaveRequest.getStartDate() + " đến ngày " + leaveRequest.getEndDate() + "!");
		leaveRequestRepository.deleteById(deleteLeaveRequest.getId());
	}
	
	@Override
	public List<ApprovedLeaveRequestResponseDto> approveAllLeaveRequests(String MyEmail, List<ApproveLeaveRequest> listApproveLeaveRequests)
	{
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
	
        if (!"Manager".equals(emp.getType()))
        {
        	throw new AccessDeniedException("You do not have permission to view employee's leave request!");
        }
        if (listApproveLeaveRequests.isEmpty())
        {
        	throw new DataNotFoundException("Don't find leave request in data!");
        }
        List<ApprovedLeaveRequestResponseDto> approvedList = new ArrayList<>(); 
        listApproveLeaveRequests.forEach(approveLeaveRequest -> {
        	if (!"Đang chờ duyệt".equals(approveLeaveRequest.getStatus()))
        	{
        		return;
        	}
        	LeaveRequest leaveRequest = leaveRequestRepository.findByEmployeeIdAndRequestTime(approveLeaveRequest.getEmployeeId(), approveLeaveRequest.getStartDate(), approveLeaveRequest.getEndDate());
            // Thực hiện xử lý với từng LeaveRequest
            leaveRequest.setStatus("Đã duyệt");
            leaveRequestRepository.save(leaveRequest);
            LocalDate createdDate = LocalDate.now();
            LocalTime createdTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String createdTimeFormatted = createdTime.format(formatter);
            NotificationLR notification = new NotificationLR(leaveRequest, "Chưa đọc", createdDate, createdTimeFormatted);
            notificationLRRepository.save(notification);
            
            ApprovedLeaveRequestResponseDto approvedLRDto = new ApprovedLeaveRequestResponseDto(
            		leaveRequest.getStartDate(),
            		leaveRequest.getEndDate(),
            		leaveRequest.getRequestDays(),
            		leaveRequest.getEmployeeDayOff().getDay_off_type().getType(),
            		leaveRequest.getSession(),
            		leaveRequest.getReason(),
            		leaveRequest.getStatus(),
            		leaveRequest.getRejectReason()
            );
            
            approvedList.add(approvedLRDto);
        });
        return approvedList;
	}
}
