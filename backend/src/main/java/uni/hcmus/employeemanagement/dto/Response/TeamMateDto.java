package uni.hcmus.employeemanagement.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamMateDto {
    private Long id;
    private String name;

    private String emailCompany;
    private String gender;

    private String job;

    private String businessTitle;

    private String location;

    private String avatar;

    private String organizationName;

//    public TeamMateDto(Long id, String name, String emailCompany, String gender, String job, String businessTitle, String location, String avatar, String organizationName, String phones, String addresses) {
//        this.id = id;
//        this.name = name;
//        this.emailCompany = emailCompany;
//        this.gender = gender;
//        this.job = job;
//        this.businessTitle = businessTitle;
//        this.location = location;
//        this.avatar = avatar;
//        this.organizationName = organizationName;
//        this.phones = phones;
//        this.addresses = addresses;
//    }

}