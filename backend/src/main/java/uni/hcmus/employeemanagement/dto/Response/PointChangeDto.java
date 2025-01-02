package uni.hcmus.employeemanagement.dto.Response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointChangeDto {
    private int amount;
    private LocalDate changedDate;
    private String reason;
    private String people_change;
    
 // Constructor
    public PointChangeDto(int point, LocalDate changedDate, String reason, String people_change) {
        this.amount = point;
        this.changedDate = changedDate;
        this.reason = reason;
        this.people_change = people_change;
    }
}