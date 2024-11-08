package uni.hcmus.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.hcmus.employeemanagement.entity.Manager;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    //Tìm nhân viên theo Id quản lý
    List<Manager> findByManagerId(Long managerId);
}
