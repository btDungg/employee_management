package uni.hcmus.employeemanagement.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto_v1 {
    private long id;
    private String name;
    private String email;
    private String role;
    private String department;
    private String position;
    private String manager;
    private int point;
    private String type;
    private Long managerId;
}
