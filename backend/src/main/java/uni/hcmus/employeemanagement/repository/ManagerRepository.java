package uni.hcmus.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.hcmus.employeemanagement.entity.Manager;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
//    List<Manager> findByManagerId(Long managerId);
}
