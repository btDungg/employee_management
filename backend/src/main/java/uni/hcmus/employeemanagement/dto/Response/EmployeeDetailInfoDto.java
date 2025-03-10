package uni.hcmus.employeemanagement.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uni.hcmus.employeemanagement.entity.Address;
import uni.hcmus.employeemanagement.entity.Email;
import uni.hcmus.employeemanagement.entity.EmergencyContact;
import uni.hcmus.employeemanagement.entity.Phone;

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

    @JsonProperty("emailCompany")
    private String emailCompany;

    // Personal Information
    @JsonProperty("avatar")
    private String avatar;

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
    private List<Phone> phones;

    @JsonProperty("emails")
    private List<Email> emails;

    @JsonProperty("addresses")
    private List<Address> addresses;

    @JsonProperty("emergencyContacts")
    private List<EmergencyContact> emergencyContacts;

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

    @JsonProperty("organizationName")
    private String organizationName;

    @JsonProperty("managerID")
    private Long managerID;

    @JsonProperty("managerName")
    private String managerName;

    @JsonProperty("salary")
    private int salary;

    public EmployeeDetailInfoDto(Long id, String name, String type, Long id1,
                                 String avatar, Boolean gender,
                                 Date dateOfBirth, int age, String countryOfBirth,
                                 String regionOfBirth, String cityOfBirth, String marital,
                                 String religion, String ethnicty, String citizenshipStatus,
                                 String primaryNationality, List<Phone> phones, List<Email> emails,
                                 List<Address> addresses, List<EmergencyContact> emergencyContacts,
                                 String job, String businessTitle, String jobProfile,
                                 String timeType, String location, Date hireDate, String organizationName, Long managerID, String managerName,
                                 String emailCompany, int salary) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.organizationId = id1;
        this.avatar = avatar;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.countryOfBirth = countryOfBirth;
        this.regionOfBirth = regionOfBirth;
        this.cityOfBirth = cityOfBirth;
        this.marital = marital;
        this.religion = religion;
        this.ethnicity = ethnicty;
        this.citizenshipStatus = citizenshipStatus;
        this.primaryNationality = primaryNationality;
        this.phones = phones;
        this.emails = emails;
        this.addresses = addresses;
        this.emergencyContacts = emergencyContacts;
        this.job = job;
        this.businessTitle = businessTitle;
        this.jobProfile = jobProfile;
        this.timeType = timeType;
        this.location = location;
        this.hireDate = hireDate;
        this.organizationName = organizationName;
        this.managerID = managerID;
        this.managerName = managerName;
        this.emailCompany = emailCompany;
        this.salary = salary;
    }
}