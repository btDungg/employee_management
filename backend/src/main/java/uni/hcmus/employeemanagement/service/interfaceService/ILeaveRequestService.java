package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.Response.*;

import java.util.List;

import uni.hcmus.employeemanagement.dto.Request.*;
import uni.hcmus.employeemanagement.entity.*;

public interface ILeaveRequestService {
	List<DayOffTypeDto> getListDayOffType();
	List<EmployeeDayOffDto> getMyDayOff(String MyEmail);
	LeaveRequestResponseDto sendLeaveRequest(String MyEmail, LeaveRequestDto leaveRequest);
	//ApprovedLeaveRequestResponseDto approveLeaveRequest(String MyEmail, ApproveLeaveRequestDto approveRequest);
}
