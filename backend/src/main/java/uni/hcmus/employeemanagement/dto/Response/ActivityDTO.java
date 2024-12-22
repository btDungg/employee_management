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
public class ActivityDTO {

    @JsonProperty("activityId")
    private long id;

    @JsonProperty("activityName")
    private String name;

    @JsonProperty("activityType")
    private String type;

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("numberOfParticipants")
    private int numberOfParticipants;

    @JsonProperty("createdDate")
    private LocalDate createdDate;

    @JsonProperty("registrationOpenDate")
    private LocalDate registrationOpenDate;

    @JsonProperty("registrationCloseDate")
    private LocalDate registrationCloseDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isViewed")
    private Boolean isViewed;

    @JsonProperty("createdBy")
    private Long idCreatedBy;
    @JsonProperty("createdByName")
    private String nameCreatedBy;



}
