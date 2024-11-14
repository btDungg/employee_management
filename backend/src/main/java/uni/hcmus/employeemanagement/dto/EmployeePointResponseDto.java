package uni.hcmus.employeemanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePointResponseDto {
    private long id;
    private String name;
    private int point;
    private int bonusEmployeePoint;
}