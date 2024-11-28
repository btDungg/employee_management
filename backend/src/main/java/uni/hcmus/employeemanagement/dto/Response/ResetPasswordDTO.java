package uni.hcmus.employeemanagement.dto.Response;

    import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {
    String email;
    String password;
}
