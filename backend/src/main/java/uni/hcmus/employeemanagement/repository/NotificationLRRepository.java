package uni.hcmus.employeemanagement.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.hcmus.employeemanagement.entity.NotificationLR;
import uni.hcmus.employeemanagement.entity.PointChange;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface NotificationLRRepository extends JpaRepository<NotificationLR, Long>{
	@Query(value = "SELECT noti.id, noti.created_date, noti.created_time, noti.read_status, noti.leave_request_id "
			+ "FROM notificationlr noti JOIN leave_request lr ON noti.leave_request_id = lr.id "
			+ "WHERE employee_id = :employeeId "
			+ "ORDER BY noti.created_date DESC, noti.created_time DESC", nativeQuery = true)
	List<NotificationLR> findByEmployeeIdOrderByCreatedDateAndCreatedTime
	(@Param("employeeId") Long employeeId);
	
	@Query(value = "SELECT noti.id, noti.created_date, noti.created_time, noti.read_status, noti.leave_request_id "
			+ "FROM notificationlr noti JOIN leave_request lr ON noti.leave_request_id = lr.id "
			+ "WHERE employee_id = :employeeId "
			+ "AND lr.start_date = :startDate AND lr.end_date = :endDate", nativeQuery = true)
	NotificationLR findByEmployeeIdAndStartDateAndEndDate
	(@Param("employeeId") Long employeeId,
	@Param("startDate") LocalDate startDate,
	@Param("endDate") LocalDate endDate);
}
