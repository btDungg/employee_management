package uni.hcmus.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uni.hcmus.employeemanagement.dto.Response.EmployeeDayOffDto;
import uni.hcmus.employeemanagement.dto.Response.NotificationLRDto;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.service.interfaceService.INotificationLRService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	@Autowired
    private INotificationLRService notificationService;
	
	@GetMapping("")
    public List<NotificationLRDto> getNotification(Principal principal) {
        String email = principal.getName();
        List<NotificationLRDto> result = notificationService.getListNotification(email);
        return result;
    }
	
	@PostMapping("/update-read-status")
	public ResponseEntity<NotificationLRDto> updateReadStatus(@RequestBody NotificationLRDto notification, Principal principal)
	{
		String email = principal.getName();
        NotificationLRDto result = notificationService.updateReadStatus(notification, email);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.badRequest().body(result);
        }
	}
}
