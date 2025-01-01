package uni.hcmus.employeemanagement.controller;


import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.hcmus.employeemanagement.dto.Request.ActivityRequestDto;
import uni.hcmus.employeemanagement.dto.Request.RegisterRequest;
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
            @PathVariable Long id,
            Principal principal) {
        String email = principal.getName();
        DetailActivityDTO detailActivityDTO = activityService.getDetailActivity(id, email)
                .orElse(null);
        return ResponseEntity.ok(detailActivityDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<DetailActivityDTO> registerActivity(@RequestBody RegisterRequest request,Principal principal) {
    	System.out.println("Received request body: " + request);
        String email = principal.getName();
        Optional<DetailActivityDTO> result = activityService.registerActivity(request.getActivityId(),email );
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("{id}/unregister")
    public ResponseEntity<DetailActivityDTO> unregisterActivity(@PathVariable Long id,Principal principal) {
        String email = principal.getName();
        Optional<DetailActivityDTO> result = activityService.unregisterActivity(id, email);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/create")
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody ActivityRequestDto activityDTO, Principal principal) {
        String email = principal.getName();
        Optional<ActivityDTO> result = activityService.createActivity(activityDTO, email);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }


}
