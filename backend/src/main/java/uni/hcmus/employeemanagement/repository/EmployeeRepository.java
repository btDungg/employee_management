package uni.hcmus.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.hcmus.employeemanagement.entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByManagerId(Long managerId);
    Optional<Employee> findByEmailCompany(String emailCompany);
    Boolean existsByEmailCompany(String emailCompany);
}
