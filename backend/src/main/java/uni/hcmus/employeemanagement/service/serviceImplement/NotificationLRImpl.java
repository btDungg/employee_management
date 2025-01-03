package uni.hcmus.employeemanagement.service.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.nio.sctp.Notification;

import uni.hcmus.employeemanagement.dto.Request.ApproveLeaveRequest;
import uni.hcmus.employeemanagement.dto.Request.LeaveRequestDto;
import uni.hcmus.employeemanagement.dto.Response.NotificationLRDto;
import uni.hcmus.employeemanagement.entity.*;
import uni.hcmus.employeemanagement.exception_handler.exceptions.DataNotFoundException;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.repository.*;
import uni.hcmus.employeemanagement.service.interfaceService.INotificationLRService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class NotificationLRImpl implements INotificationLRService{
	@Autowired
	private NotificationLRRepository notificationRepository;
	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Override
	public List<NotificationLRDto> getListNotification(String email) {
		Employee emp = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));
        List<NotificationLR> notifications = notificationRepository.findByEmployeeIdOrderByCreatedDateAndCreatedTime(emp.getId());
        return notifications.stream()
                .map(notification -> new NotificationLRDto
                		(emp.getName(),
                		notification.getLeaveRequest().getStartDate(),
                		notification.getLeaveRequest().getEndDate(),
                		notification.getLeaveRequest().getStatus(),
                		notification.getLeaveRequest().getRejectReason(),
                		notification.getCreatedDate(),
                		notification.getCreatedTime(),
                		notification.getReadStatus()
                		))
                .collect(Collectors.toList());
    }
	
	@Override
	public NotificationLRDto updateReadStatus(NotificationLRDto notification, String email)
	{
		Employee emp = employeeRepository.findByEmailCompany(email)
                .orElseThrow(() -> new DataNotFoundException("Employee not found with email = " + email));
		if ("Đã đọc".equals(notification.getReadStatus()))
			throw new AccessDeniedException("Thông báo này đã được đọc trước đó rồi!");
		LocalDate startDate = notification.getStartDate();
		LocalDate endDate = notification.getEndDate();
		NotificationLR updatedNotification = notificationRepository.findByEmployeeIdAndStartDateAndEndDate(emp.getId(), startDate, endDate);
		updatedNotification.setReadStatus("Đã đọc");
		notificationRepository.save(updatedNotification);
		return new NotificationLRDto
        		(emp.getName(),
        		updatedNotification.getLeaveRequest().getStartDate(),
        		updatedNotification.getLeaveRequest().getEndDate(),
        		updatedNotification.getLeaveRequest().getStatus(),
        		updatedNotification.getLeaveRequest().getRejectReason(),
        		updatedNotification.getCreatedDate(),
        		updatedNotification.getCreatedTime(),
        		updatedNotification.getReadStatus()
        		);
	}
}
