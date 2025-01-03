package uni.hcmus.employeemanagement.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class NotificationLRDto {
	private String employeeName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String approveStatus;
	private String rejectReason;
	private LocalDate createdDate;
	private String createdTime;
	private String readStatus;
}
