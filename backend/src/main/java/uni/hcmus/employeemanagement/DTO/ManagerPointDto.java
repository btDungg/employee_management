package uni.hcmus.employeemanagement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerPointDto extends EmployeePointDto {
    private int bonusEmployeePoint;

    // Constructor
    public ManagerPointDto(long id, String name, int point, String type, Long managerId, int bonusEmployeePoint) {
        super(id, name, point, type, managerId);  // Gọi constructor của EmployeePointDto
        this.bonusEmployeePoint = bonusEmployeePoint;
    }
}

