package uni.hcmus.employeemanagement.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmergencyContactPublicDtp_v1 {
    private Long id;
    private int priority;
    private String preferredLanguage;
    private String primaryContactInformation;
    private String alternativeContactInformation;
}
