package uni.hcmus.employeemanagement.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uni.hcmus.employeemanagement.entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>{
	//Kiểm tra có yêu cầu nghỉ phép đã tồn tại nào có dính đến khoảng thời gian xin nghỉ đang yêu cầu 
	@Query(value = "SELECT * FROM leave_request lr WHERE lr.employee_id = :employeeId AND " +
            "((lr.start_date BETWEEN :startDate AND :endDate) OR " +
            "(lr.end_date BETWEEN :startDate AND :endDate) OR " +
            "(lr.start_date <= :startDate AND lr.end_date >= :endDate))", nativeQuery = true)
	List<LeaveRequest> isExistsLeaveRequest(@Param("employeeId") Long employeeId, 
                                @Param("startDate") LocalDate startDate, 
                                @Param("endDate") LocalDate endDate);
	
	//Tìm yêu cầu nghỉ phép theo nhân viên và thời gian yêu cầu
	@Query(value = "SELECT * FROM leave_request lr WHERE lr.employee_id = :employeeId AND " +
            "lr.start_date = :startDate AND " +
            "lr.end_date = :endDate" , nativeQuery = true)
	LeaveRequest findByEmployeeIdAndRequestTime(@Param("employeeId") Long employeeId, 
                                @Param("startDate") LocalDate startDate, 
                                @Param("endDate") LocalDate endDate);
	
	
	//Lấy toàn bộ các yêu cầu nghỉ phép của nhân viên do mình quản lý
	@Query(value = "SELECT * FROM leave_request lr WHERE lr.manager_id = :employeeId ORDER BY lr.start_date DESC", nativeQuery = true)
	List<LeaveRequest> findByManagerIdOrderByStartDateDesc(@Param("employeeId") Long employeeId);
	
	//Lấy toàn bộ các yêu cầu nghỉ phép của mình 
	@Query(value = "SELECT * FROM leave_request lr WHERE lr.employee_id = :employeeId ORDER BY lr.start_date DESC", nativeQuery = true)
	List<LeaveRequest> findByEmloyeeIdOrderByStartDateDesc(@Param("employeeId") Long employeeId);
}
