package uni.hcmus.employeemanagement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePointDto {
    private long id;
    private String name;
    private int point;
    private String type;
    private Long managerId;

    // Constructor
    public EmployeePointDto(long id, String name, int point, String type, Long managerId) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.type = type;
        this.managerId = managerId;
    }
}