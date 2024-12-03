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
    @JsonProperty("email_company")
    private String emailCompany;
    @JsonProperty("organization")
    private Long organization;//
    @JsonProperty("birth_date")
    private LocalDate birthDate;//
    @JsonProperty("age")
    private int age;//
    @JsonProperty("gender")
    private String gender;//
    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("location")
    private String Location;
    @JsonProperty("hire_date")
    private LocalDate hireDate;//

    @JsonProperty("religion")
    private String religion;//
    @JsonProperty("marital")
    private String marital;//
    @JsonProperty("ethnicity")
    private String ethnicity;//
    @JsonProperty("avatar")
    private String avatar;//
    @JsonProperty("organization_name")
    private String organizationName;


    @JsonProperty("country_of_birth")
    private String contryOfBirth;//
    @JsonProperty("region_of_birth")
    private String regionOfBirth;//
    @JsonProperty("city_of_birth")
    private String cityOfBirth;//
    @JsonProperty("citizenship_status")
    private String citizenshipStatus;//
    @JsonProperty("job")
    private String job;
    @JsonProperty("business_title")
    private String bussissTitle;
    @JsonProperty("job_profile")
    private String jobProfile;
    @JsonProperty("time_type")
    private String timeType;


    @JsonProperty("manager_name")
    private String managerName;
    @JsonProperty("manager")
    private Long manager;

    @JsonProperty("phones")
    private List<Phone> phones;


    @JsonProperty("emails")
    private List<Email> emails;


    @JsonProperty("addresses")
    private List<Address> addresses;


    @JsonProperty("emergency_contacts")
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

}
