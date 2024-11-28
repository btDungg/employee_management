package uni.hcmus.employeemanagement.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailInfoDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("organizationId")
    private Long organizationId;

    // Personal Information
    @JsonProperty("gender")
    private Boolean gender;

    @JsonProperty("dateOfBirth")
    private Date dateOfBirth;

    @JsonProperty("age")
    private int age;

    @JsonProperty("countryOfBirth")
    private String countryOfBirth;

    @JsonProperty("regionOfBirth")
    private String regionOfBirth;

    @JsonProperty("cityOfBirth")
    private String cityOfBirth;

    @JsonProperty("marital")
    private String marital;

    @JsonProperty("religion")
    private String religion;

    @JsonProperty("ethnicity")
    private String ethnicity;

    @JsonProperty("citizenshipStatus")
    private String citizenshipStatus;

    @JsonProperty("primaryNationality")
    private String primaryNationality;

    @JsonProperty("phones")
    private List<String> phones;

    @JsonProperty("emails")
    private List<String> emails;

    @JsonProperty("addresses")
    private List<String> addresses;

    @JsonProperty("emergencyContacts")
    private List<String> emergencyContacts;

    // Job Information
    @JsonProperty("job")
    private String job;

    @JsonProperty("businessTitle")
    private String businessTitle;

    @JsonProperty("jobProfile")
    private String jobProfile;

    @JsonProperty("employeeType")
    private String employeeType;

    @JsonProperty("timeType")
    private String timeType;

    @JsonProperty("location")
    private String location;

    @JsonProperty("hireDate")
    private Date hireDate;
}