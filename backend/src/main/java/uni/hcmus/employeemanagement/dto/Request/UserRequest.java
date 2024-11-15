package uni.hcmus.employeemanagement.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    private String username;
    private Long ManagerId;
    private String type;
    private String email;
    private String password;
}
