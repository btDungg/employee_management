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
public class LeaveRequest extends MetaData {

    private LocalDate startDate;
    private LocalDate endDate;
    private float requestDays;
    private String reason;
    private String status;
    private String reasonApprove;

    // Liên kết tới bảng EmployeeDayOff
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "employee_id", referencedColumnName = "employee", insertable = true, updatable = true),
        @JoinColumn(name = "day_off_type", referencedColumnName = "day_off_type", insertable = true, updatable = true),
        @JoinColumn(name = "year", referencedColumnName = "year", insertable = true, updatable = true)
    })
    private EmployeeDayOff employeeDayOff;

    // Khóa ngoại tới Manager
    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;
}
