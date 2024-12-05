package uni.hcmus.employeemanagement.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApprovedLeaveRequestResponseDto {
	private Long id;
	private String status;
	private String reasonApprove;
}
