package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDayOff extends MetaData {
	@ManyToOne
    @JoinColumn(name = "day_off_type", nullable = false)
    private DayOffType day_off_type;
	
	@ManyToOne
    @JoinColumn(name = "employee", nullable = false)
    private Employee employee;
	
	private long year;
	private float remainingDays;
}
