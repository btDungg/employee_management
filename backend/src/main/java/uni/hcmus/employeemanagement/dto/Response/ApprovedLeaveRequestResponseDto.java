package uni.hcmus.employeemanagement.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApprovedLeaveRequestResponseDto {
	private LocalDate startDate;
	private LocalDate endDate;
	private float requestDays;
	private String dayOffType;
	private String session;
	private String reason;
	private String status;
	private String reasonApprove;
}
