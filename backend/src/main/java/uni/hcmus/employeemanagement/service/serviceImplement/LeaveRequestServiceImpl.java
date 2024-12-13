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
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
		LeaveRequest approveLeaveRequest = leaveRequestRepository.findByEmployeeIdAndRequestTime(leaveRequest.getEmployeeId(), leaveRequest.getStartDate(), leaveRequest.getEndDate());
		if (approveLeaveRequest == null)
			throw new DataNotFoundException("Không có yêu cầu nghỉ phép từ ngày " + leaveRequest.getStartDate() + " đến ngày " + leaveRequest.getEndDate() + "!");
		approveLeaveRequest.setStatus(leaveRequest.getStatus());
		approveLeaveRequest.setReasonApprove(leaveRequest.getReasonApprove());
		leaveRequestRepository.save(approveLeaveRequest);
		return new ApprovedLeaveRequestResponseDto(
        		approveLeaveRequest.getStartDate(),
        		approveLeaveRequest.getEndDate(),
        		approveLeaveRequest.getRequestDays(),
        		approveLeaveRequest.getReason(),
        		approveLeaveRequest.getEmployeeDayOff().getDay_off_type().getType(),
        		approveLeaveRequest.getStatus(),
        		approveLeaveRequest.getReasonApprove()
        		);
	}
	
	@Override
	public List<LeaveRequestResponseDto> getMyApproveLeaveRequest (String MyEmail)
	{
		Employee emp = employeeRepository.findByEmailCompany(MyEmail)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + MyEmail));
        if (!"Manager".equals(emp.getEmployeType()))
        {
        	throw new AccessDeniedException("You do not have permission to view employee's leave request!");
        }
        List<LeaveRequest> myListApproveLeaveRequest = leaveRequestRepository.findByManagerId(emp.getId());
        return myListApproveLeaveRequest.stream()
                .map(myApproveLeaveRequest -> new LeaveRequestResponseDto(myApproveLeaveRequest.getEmployeeDayOff().getEmployee().getId(),
                															myApproveLeaveRequest.getEmployeeDayOff().getEmployee().getName(),
                															myApproveLeaveRequest.getStartDate(),
                															myApproveLeaveRequest.getEndDate(),
                															myApproveLeaveRequest.getRequestDays(),
                															myApproveLeaveRequest.getReason(),
                															myApproveLeaveRequest.getStatus(),
                															myApproveLeaveRequest.getReasonApprove(),
                															myApproveLeaveRequest.getEmployeeDayOff().getDay_off_type().getType(),
                															emp.getId()
                															))
                .collect(Collectors.toList()); 
	}
}
