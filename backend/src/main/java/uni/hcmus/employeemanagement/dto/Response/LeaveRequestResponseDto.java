package uni.hcmus.employeemanagement.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LeaveRequestResponseDto {
	private Long id;
	private Long employeeId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String reason;
	private String dayOffType;
	private Long managerId;
}
