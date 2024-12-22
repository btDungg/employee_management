package uni.hcmus.employeemanagement.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uni.hcmus.employeemanagement.entity.Address;
import uni.hcmus.employeemanagement.entity.Email;
import uni.hcmus.employeemanagement.entity.EmergencyContact;
import uni.hcmus.employeemanagement.entity.Phone;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class EmployeePublicDto_v1 {
    //response for list emp public

    @JsonProperty("id")
    private Long id; //
    @JsonProperty("name")
    private String name;//
    @JsonProperty("point")
    private int point; //
    @JsonProperty("type")
    private String type;//
    @JsonProperty("emailCompany")
    private String emailCompany;
    @JsonProperty("organizationId")
    private Long organization;//
    @JsonProperty("dateOfBirth")
    private LocalDate birthDate;//
    @JsonProperty("age")
    private int age;//
    @JsonProperty("gender")
    private String gender;//
    @JsonProperty("primaryNationality")
    private String nationality;

    @JsonProperty("location")
    private String Location;
    @JsonProperty("hireDate")
    private LocalDate hireDate;//

    @JsonProperty("religion")
    private String religion;//
    @JsonProperty("marital")
    private String marital;//
    @JsonProperty("ethnicity")
    private String ethnicity;//
    @JsonProperty("avatar")
    private String avatar;//
    @JsonProperty("organizationName")
    private String organizationName;


    @JsonProperty("countryOfBirth")
    private String contryOfBirth;//
    @JsonProperty("regionOfBirth")
    private String regionOfBirth;//
    @JsonProperty("cityOfBirth")
    private String cityOfBirth;//
    @JsonProperty("citizenshipStatus")
    private String citizenshipStatus;//
    @JsonProperty("job")
    private String job;
    @JsonProperty("businessTitle")
    private String bussissTitle;
    @JsonProperty("jobProfile")
    private String jobProfile;
    @JsonProperty("timeType")
    private String timeType;


    @JsonProperty("managerName")
    private String managerName;
    @JsonProperty("managerID")
    private Long manager;

    @JsonProperty("phones")
    private List<Phone> phones;


    @JsonProperty("emails")
    private List<Email> emails;


    @JsonProperty("addresses")
    private List<Address> addresses;


    @JsonProperty("emergencyContacts")
    private List<EmergencyContact> emergencyContacts;

    public EmployeePublicDto_v1(Long id, String name, int point, String type, String emailCompany, Long organization,
                                LocalDate birthDate, int age, String gender, String nationality, String location,
                                LocalDate hireDate, String religion, String marital, String ethnicity,
                                String avatar, String organizationName, String contryOfBirth, String regionOfBirth,
                                String cityOfBirth, String citizenshipStatus, String job, String BusinessTitle,
                                String jobProfile, String timeType, String managerName, Long manager,
                                List<Phone> phones, List<Email> emails, List<Address> addresses,
                                List<EmergencyContact> emergencyContacts) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.type = type;
        this.emailCompany = emailCompany;
        this.organization = organization;
        this.birthDate = birthDate;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.Location = location;
        this.hireDate = hireDate;
        this.religion = religion;
        this.marital = marital;
        this.ethnicity = ethnicity;
        this.avatar = avatar;
        this.organizationName = organizationName;
        this.contryOfBirth = contryOfBirth;
        this.regionOfBirth = regionOfBirth;
        this.cityOfBirth = cityOfBirth;
        this.citizenshipStatus = citizenshipStatus;
        this.job = job;
        this.bussissTitle = BusinessTitle;
        this.jobProfile = jobProfile;
        this.timeType = timeType;
        this.managerName = managerName;
        this.manager = manager;
        this.phones = phones;
        this.emails = emails;
        this.addresses = addresses;
        this.emergencyContacts = emergencyContacts;
    }

    public EmployeePublicDto_v1(Long id, String name, String type, String emailCompany, Long organization,
                                String gender, String avatar, String organizationName, String job, String bussissTitle,
                                String jobProfile, String timeType, String managerName, Long manager) {
        this.id = id;
        this.name = name;
        this.type = type;

        this.emailCompany = emailCompany;
        this.organization = organization;
        this.gender = gender;
        this.avatar = avatar;
        this.organizationName = organizationName;
        this.managerName = managerName;
        this.manager = manager;
        this.job = job;
        this.bussissTitle = bussissTitle;
        this.jobProfile = jobProfile;
        this.timeType = timeType;

    }


    public EmployeePublicDto_v1(Long id, String name, String type, String emailCompany, Long organization)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.emailCompany = emailCompany;
        this.organization = organization;
    }

}
