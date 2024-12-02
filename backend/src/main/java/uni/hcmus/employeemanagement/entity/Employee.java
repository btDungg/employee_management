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

    /** The manager ID of the employee. */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /** The manager ID of the employee. */
    @Override
    public String getPassword() {
        return password;
    }

    /** The manager ID of the employee. */
    @Override
    public String getUsername() {
        return emailCompany;
    }


    //Khoa ngoai toi bang Organization
    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    // Personal Information
    private String avatar;

    private Boolean gender;

    @Column(name = "date_of_birth")
    private Date DateOfBirth;

    private int age;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "region_of_birth")
    private String regionOfBirth;

    @Column(name = "city_of_birth")
    private String cityOfBirth;

    private String marital;

    private String religion;

    private String ethnicty;

    @Column(name = "citizenship_status")
    private String citizenshipStatus;

    @Column(name = "primary_nationality")
    private String primaryNationality;

    // Job Information
    private String job;

    @Column(name = "business_title")
    private String businessTitle;

    @Column(name = "job_profile")
    private String jobProfile;

    @Column(name = "time_type")
    private String timeType;

    private String location;

    @Column(name = "hire_date")
    private Date hireDate;

}