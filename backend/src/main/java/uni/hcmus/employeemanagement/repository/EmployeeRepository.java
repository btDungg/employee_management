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
    @Query(value = "SELECT e.* FROM employee e JOIN organization o ON e.organization_id = o.id WHERE o.manager_id = ?1", nativeQuery = true)
    List<Employee> findByManagerId(Long managerId);

    // Find employee by email
    @Query("SELECT e FROM Employee e WHERE e.emailCompany = ?1")
    Optional<Employee> findByEmailCompany(String emailCompany);
    Boolean existsByEmailCompany(String emailCompany);
}
