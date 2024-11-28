package uni.hcmus.employeemanagement.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeePublicDto_v1 {
    List<EmailPublicDto_v1> emails;
    List<AddressPublicDto_v1> addresses;
    List<EmergencyContactPublicDtp_v1> emergencyContacts;
    private long id;
    private String name;
    private int point;
    private String type;
    private String emailCompany;
    private String role;
    private String department;
    private String position;
    private String manager;
    private OrganizationDto organization;
    private Boolean gender;
    private Date dateOfBirth;
    private int age;
    private String country;
    private String marital;
    private String religion;
    private String ethnicty;
    private String job;
    private String businessTitle;
    private String jobProfile;
    private String employeType;
    private String timeType;
    private String Location;
    private String hireDate;


}
