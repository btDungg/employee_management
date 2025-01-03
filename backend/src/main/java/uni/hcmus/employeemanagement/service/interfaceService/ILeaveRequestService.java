package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.Response.*;

import java.util.List;

import uni.hcmus.employeemanagement.dto.Request.*;
import uni.hcmus.employeemanagement.entity.*;

public interface ILeaveRequestService {
	List<DayOffTypeDto> getListDayOffType();
	List<EmployeeDayOffDto> getMyDayOff(String MyEmail);
	LeaveRequestResponseDto sendLeaveRequest(String MyEmail, LeaveRequestDto leaveRequest);
	List<LeaveRequestResponseDto> getMyApproveLeaveRequest (String MyEmail);
	List<LeaveRequestResponseDto> getMyLeaveRequest (String MyEmail);
	void deleteLeaveRequest (String MyEmail, LeaveRequestDto leaveRequest);
	ApprovedLeaveRequestResponseDto approveLeaveRequest(String MyEmail, ApproveLeaveRequest approveRequest);
	List<ApprovedLeaveRequestResponseDto> approveAllLeaveRequests(String MyEmail, List<ApproveLeaveRequest> listApproveLeaveRequests);
}
