package uni.hcmus.employeemanagement.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uni.hcmus.employeemanagement.entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find list employee by manager id in organization
    @Query(value = "SELECT e.id, e.employee_name, e.point, e.type, o.id " +
            "FROM employee e JOIN organization o ON e.organization_id = o.id " +
            "WHERE o.manager_id = :managerId", nativeQuery = true)
    List<Object[]> findByManagerId(@Param("managerId") Long managerId);


    @Query(value = "SELECT e.id,e.employee_name,e.point,e.type, e.email_company," +
            "e.organization_id, e.date_of_birth, e.age, e.gender, " +
            "e.primary_nationality, e.location, e.hire_date,e.religion, e.marital, e.ethnicty, e.avatar, o.name," +
            "e.country_of_birth, e.region_of_birth, e.city_of_birth,  e.citizenship_status,   " +
            "e.job, e.business_title, e.job_profile, e.time_type " +
            "FROM employee e " +
            "JOIN organization o ON e.organization_id = o.id " +
            "WHERE o.manager_id = :managerId", nativeQuery = true)
    List<Object[]> findByManagerID_v1(@Param("managerId") Long managerId);


    // Find employee by email
    @Query("SELECT e FROM Employee e WHERE e.emailCompany = ?1")
    Optional<Employee> findByEmailCompany(String emailCompany);


    @Query(value = "SELECT e.id,e.employee_name,e.point,e.type, e.email_company," +
            "e.organization_id, e.date_of_birth, e.age, e.gender, " +
            "e.primary_nationality, e.location, e.hire_date,e.religion, e.marital, e.ethnicty, e.avatar, o.name," +
            "e.country_of_birth, e.region_of_birth, e.city_of_birth,  e.citizenship_status,   " +
            "e.job, e.business_title, e.job_profile, e.time_type " +
            "FROM employee e " +
            "JOIN organization o ON e.organization_id = o.id " +
            "WHERE e.organization_id = :id", nativeQuery = true)
    List<Object[]> findTeamMate(@Param("id") Long id);

    @Query(value = "SELECT e.id,e.employee_name,e.point,e.type, e.email_company," +
            "e.organization_id, e.date_of_birth, e.age, e.gender, " +
            "e.primary_nationality, e.location, e.hire_date,e.religion, e.marital, e.ethnicty, e.avatar, o.name," +
            "e.country_of_birth, e.region_of_birth, e.city_of_birth,  e.citizenship_status,   " +
            "e.job, e.business_title, e.job_profile, e.time_type " +
            "FROM employee e " +
            "JOIN organization o ON e.organization_id = o.id ", nativeQuery = true)
    List<Object[]> getAllByHR();


    @Query(value = "SELECT e.id, e.employee_name " +
            "FROM organization o " +
            "JOIN employee e ON e.id = o.manager_id " +
            "WHERE o.id = :id", nativeQuery = true)
    Object[] getManager(@Param("id") Long id);


    Boolean existsByEmailCompany(String emailCompany);
}
