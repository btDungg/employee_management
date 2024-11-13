package uni.hcmus.employeemanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    private long id;
    private String name;
    private int point;
    private String type;
    private Long managerId;

    // Constructor
    public EmployeeDto(long id, String name, int point, String type, Long managerId) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.type = type;
        this.managerId = managerId;
    }


}