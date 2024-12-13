package uni.hcmus.employeemanagement.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uni.hcmus.employeemanagement.entity.*;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDayOffRepository extends JpaRepository<EmployeeDayOff, Long>{
	@Query(value = "SELECT * from employee_day_off e WHERE e.employee = :employeeId AND e.day_off_type = :dayOffType AND e.year = YEAR(CURDATE())", nativeQuery = true)
	EmployeeDayOff findByEmployeeIdAndDayOffType(
			@Param("employeeId") Long employeeId,
			@Param("dayOffType") Long dayOffType);
	
	//Lấy danh sách ngày nghỉ còn lại của nhân viên trong năm hiện tại
	@Query(value = "SELECT * from employee_day_off e WHERE e.employee = :employeeId AND e.year = YEAR(CURDATE())", nativeQuery = true)
	List<EmployeeDayOff> getRemainingDaysByEmployeeId(
			@Param("employeeId") Long employeeId
			);
}
