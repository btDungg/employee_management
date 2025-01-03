import React, { useState, useEffect } from 'react';
import { updateReadStatus } from '../apis/api';
import '../../public/css/NotificationPage.css';

const NotificationPage = ({ notifications, updateUnreadCount }) => {
  const [selectedNotification, setSelectedNotification] = useState(null);

  useEffect(() => {
    const savedNotification = localStorage.getItem('selectedNotification');
    if (savedNotification) {
      setSelectedNotification(JSON.parse(savedNotification));
    }
  }, []);

  const handleSelectNotification = async (notification) => {
    // Đánh dấu thông báo là đã đọc
    const updatedNotifications = notifications.map((n) =>
      n.employeeName === notification.employeeName &&
      n.startDate === notification.startDate &&
      n.endDate === notification.endDate
        ? { ...n, isRead: true }
        : n
    );

     // Tạo bản sao của notification và xóa thuộc tính isRead
     const notificationToSend = { ...notification };
     delete notificationToSend.isRead;
     try {
         // Gửi thông báo đã chỉnh sửa tới API
         await updateReadStatus(notificationToSend);
         console.log('Cập nhật trạng thái thành công!');
       } catch (error) {
         console.error('Lỗi khi cập nhật trạng thái thông báo: ', error);
       }
    
    // Cập nhật trạng thái lên App
    updateUnreadCount(updatedNotifications);

    // Lưu thông báo đã chọn vào localStorage
    localStorage.setItem('selectedNotification', JSON.stringify(notification));
    setSelectedNotification(notification);

  };

  return (
    <div className="notification-page">
      <div className="notification-list">
        {notifications.map((notification, index) => (
          <div
            key={index}
            className={`notification-item ${
              selectedNotification?.employeeName === notification.employeeName &&
              selectedNotification?.startDate === notification.startDate &&
              selectedNotification?.endDate === notification.endDate
                ? 'active'
                : ''
            } ${notification.isRead ? '' : 'unread'}`}
            onClick={() => handleSelectNotification(notification)}
          >
            {notification.endDate !== notification.startDate ? (
              <p>
                Kết quả phê duyệt yêu cầu nghỉ phép từ ngày {notification.startDate} - {notification.endDate}
              </p>
            ) : (
              <p>Kết quả phê duyệt yêu cầu nghỉ phép vào ngày {notification.startDate}</p>
            )}
          </div>
        ))}
      </div>
      <div className="notification-detail">
        {selectedNotification ? (
          <>
            {selectedNotification.endDate !== selectedNotification.startDate ? (
              <>
                <h1>
                  Kết quả phê duyệt yêu cầu nghỉ phép từ ngày {selectedNotification.startDate} - {selectedNotification.endDate}
                </h1>
                <p>
                  Yêu cầu nghỉ phép từ ngày {selectedNotification.startDate} đến ngày {selectedNotification.endDate} của nhân viên {selectedNotification.employeeName}{' '}
                  {selectedNotification.approveStatus.toLowerCase()}
                </p>
              </>
            ) : (
              <>
                <h1>Kết quả phê duyệt yêu cầu nghỉ phép vào ngày {selectedNotification.startDate}</h1>
                <p>
                  Yêu cầu nghỉ phép vào ngày {selectedNotification.startDate} của nhân viên {selectedNotification.employeeName}{' '}
                  {selectedNotification.approveStatus.toLowerCase()}
                </p>
              </>
            )}
            {selectedNotification.approveStatus === 'Bị từ chối' && <p>Lý do: {selectedNotification.rejectReason}</p>}
          </>
        ) : (
          <h2>Vui lòng chọn một thông báo từ danh sách</h2>
        )}
      </div>
    </div>
  );
};

export default NotificationPage;
