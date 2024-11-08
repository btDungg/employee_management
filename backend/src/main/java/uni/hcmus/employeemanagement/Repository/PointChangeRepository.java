package uni.hcmus.employeemanagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uni.hcmus.employeemanagement.entity.PointChange;


public interface PointChangeRepository extends JpaRepository<PointChange, Long> {
	List<PointChange> findByEmployeeIdOrderByChangeDateDesc(Long id);
}