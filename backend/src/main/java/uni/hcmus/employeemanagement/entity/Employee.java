package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Represents an employee entity with basic attributes such as name, point, and manager ID.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "clazz_", discriminatorType = DiscriminatorType.STRING)
public class Employee extends MetaData implements UserDetails {

    /** The email address of the employee as same as username to login. */
    @Column(name = "email_company")
    private String emailCompany;

    /** The password of the employee to login. */
    private String password;

    /** The name of the employee. */
    @Column(name = "employee_name")
    private String name;

    /** The point value associated with the employee. */
    private int point;


    /** The type of employee. */
    private String type;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emailCompany;
    }


    //Khoa ngoai toi bang Organization
    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    private Boolean gender;

    private Date DateOfBirth;

    private int age;
    private String countryOfBirth;
    private String regionOfBirth;
    private String cityOfBirth;
    private String marital;
    private String religion;
    private String ethnicty;
    private String citizenshipStatus;
    private String primaryNationality;
    private String job; //
    private String businessTitle;
    private String jobProfile;
    private String employeType; //
    private String timeType;
    private String Location;
    private String hireDate;

}