package uni.hcmus.employeemanagement.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LeaveRequestDto {
	private LocalDate startDate;
	private LocalDate endDate;
	private float requestDays;
	private String reason;
	private String dayOffType;
	private String session;
}
