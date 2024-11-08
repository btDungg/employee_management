package uni.hcmus.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.hcmus.employeemanagement.entity.Employee;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //Tìm nhân viên theo Id quản lý
    List<Employee> findByManagerId(Long managerId);
}
