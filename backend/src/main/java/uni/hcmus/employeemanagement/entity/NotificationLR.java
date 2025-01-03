package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class NotificationLR extends MetaData {
	
	// Liên kết tới LeaveRequest (toàn bộ LeaveRequest)
    @OneToOne
    @JoinColumn(name = "leave_request_id", referencedColumnName = "id")
    private LeaveRequest leaveRequest;
	
	private String readStatus;
	private LocalDate createdDate;
	private String createdTime;
}
