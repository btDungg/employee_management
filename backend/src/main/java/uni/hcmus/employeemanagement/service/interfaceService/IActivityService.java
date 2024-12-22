package uni.hcmus.employeemanagement.service.interfaceService;

import uni.hcmus.employeemanagement.dto.Response.ActivityDTO;
import uni.hcmus.employeemanagement.dto.Response.DetailActivityDTO;

import java.util.List;
import java.util.Optional;

public interface IActivityService {
    Optional<List<ActivityDTO>> getActivities(String name,String email);
    Optional<DetailActivityDTO> getDetailActivity(Long id,String email);
}
