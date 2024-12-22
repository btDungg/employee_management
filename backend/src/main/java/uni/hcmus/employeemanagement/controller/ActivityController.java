package uni.hcmus.employeemanagement.controller;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uni.hcmus.employeemanagement.dto.Response.ActivityDTO;
import uni.hcmus.employeemanagement.dto.Response.DetailActivityDTO;
import uni.hcmus.employeemanagement.repository.ActivityRepository;
import uni.hcmus.employeemanagement.repository.DetailActivityRepository;
import uni.hcmus.employeemanagement.service.interfaceService.IActivityService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    @Autowired
    private IActivityService activityService;


    // lấy tất cả các hoạt động. có thể lọc theo tên hoạt động
    @GetMapping("/activities")
    public ResponseEntity<List<ActivityDTO>> getActivity(
            @RequestParam(required = false) String name,
            Principal principal) {

        String email = principal.getName();

        List<ActivityDTO>activities = activityService.getActivities(name, email)
                .orElse(Collections.emptyList());
        return ResponseEntity.ok(activities);
    }

    // lấy chi tiết hoạt động theo id hoạt động và email người dùng
    @GetMapping("{id}")
    public ResponseEntity<DetailActivityDTO> getDetailActivity(
            @RequestParam Long id,
            Principal principal) {
        String email = principal.getName();
        DetailActivityDTO detailActivityDTO = activityService.getDetailActivity(id, email)
                .orElse(null);
        return ResponseEntity.ok(detailActivityDTO);
    }


}
