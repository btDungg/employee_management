package uni.hcmus.employeemanagement.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeePublicDto_v1 {
    //response for list emp public

    private long id;
    private String name;
    private int point;
    private String type;
    private String empType;
    private String emailCompany;
    private Long organization;
    private LocalDate birthDate;
    private int age;
    private String gender;
    private String nationality;
    private String Location;
    private LocalDate hireDate;
    private String religion;
    private String marital;
    private String ethnicity;
    private String avatar;
    private String organizationName;
    private String managerName;
    private Long manager;

    public EmployeePublicDto_v1(long id, String name, String type, String empType, String emailCompany, Long organization,
                                String gender, String avatar, String organizationName, String managerName, Long manager) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.empType = empType;
        this.emailCompany = emailCompany;
        this.organization = organization;
        this.gender = gender;
        this.avatar = avatar;
        this.organizationName = organizationName;
        this.managerName = managerName;
        this.manager = manager;
    }

}
