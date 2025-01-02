package uni.hcmus.employeemanagement.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailActivityDTO {

    @JsonProperty("detailActivityId")
    private Long id;
    private ActivityDTO activity;
    private EmployeePublicDto_v1 employee;
    @JsonProperty("result")
    private String result;
    @JsonProperty("evidence")
    private String evidence;
    @JsonProperty("ranking")
    private int ranking;
    @JsonProperty("status")
    private String status;
    @JsonProperty("registeredDate")
    private LocalDate registeredDate;

}
