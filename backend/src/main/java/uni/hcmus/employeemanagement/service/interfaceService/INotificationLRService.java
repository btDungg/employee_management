package uni.hcmus.employeemanagement.service.interfaceService;

import java.util.List;

import uni.hcmus.employeemanagement.dto.Response.NotificationLRDto;

public interface INotificationLRService {
	public List<NotificationLRDto> getListNotification(String email);
	NotificationLRDto updateReadStatus(NotificationLRDto notification, String email);
}
