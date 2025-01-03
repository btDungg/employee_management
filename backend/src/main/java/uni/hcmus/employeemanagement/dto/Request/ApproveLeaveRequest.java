package uni.hcmus.employeemanagement.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApproveLeaveRequest {
	private Long employeeId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;
	private String rejectReason;
}
