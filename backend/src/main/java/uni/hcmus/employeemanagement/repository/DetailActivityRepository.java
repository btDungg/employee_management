package uni.hcmus.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.hcmus.employeemanagement.entity.ActivityDetail;

import java.util.Optional;

@Repository
public interface DetailActivityRepository extends JpaRepository<ActivityDetail,Long> {
    Optional<ActivityDetail> findByEmployeeIdAndActivityId(Long employeeId, Long activityId);
}
