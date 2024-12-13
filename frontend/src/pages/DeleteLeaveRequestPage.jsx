import React, { useState } from 'react';
import ViewMyLeaveRequestPage from '../pages/ViewMyLeaveRequestPage';
import NotificationPopup from '../components/NotificationPopup';
import { deleteLeaveRequest } from '../apis/api';
import '../../public/css/Popup.css';

const DeleteLeaveRequestPage = () => {
    const [formData, setFormData] = useState({});
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [notificationMessage, setNotificationMessage] = useState('');
    const [showNotification, setShowNotification] = useState(false);

    const handleCommit = (data) => {
        setFormData(data);
        setShowConfirmation(true);
    };

    const handleConfirm = async () => {
        setShowConfirmation(false);
        try {
            const leaveRequest = {
                startDate: formData.startDate,
                endDate: formData.endDate,
                requestDays: formData.requestDays,
                reason: formData.reason,
                dayOffType: formData.dayOffType
            };
            console.log(leaveRequest);
            await deleteLeaveRequest(leaveRequest);
            setNotificationMessage("Xóa yêu cầu nghỉ phép thành công");
            setShowNotification(true);
            setTimeout(() => {
                window.location.reload();
            }, 2000); // Adjust the delay as needed
        } catch (error) {
            setNotificationMessage(`Failure: ${error.message}`);
            setShowNotification(true);
        }
    };


    const handleCancel = () => {
        setShowConfirmation(false);
    };

    const handleCloseNotification = () => {
        setShowNotification(false);
    };

    return (
        <div>
            {<ViewMyLeaveRequestPage onCommit={handleCommit} />}
            {showConfirmation &&
            (
                <div className="popup-overlay">
                    <div className="popup-content">
                        <h2>Bạn có muốn xóa yêu cầu nghỉ phép này không?</h2>
                        <p>Ngày bắt đầu: {formData.startDate}</p>
                        <p>Ngày kết thúc: {formData.endDate}</p>
                        <p>Số ngày nghỉ: {formData.requestDays}</p>
                        <p>Lý do: {formData.endDate}</p>
                        <p>Trạng thái dự kiến: {formData.status}</p>
                        <p>Lý do duyệt: {formData.reasonApprove}</p>
                        <div className="popup-buttons">
                            <button className="btn" onClick={handleConfirm}>Xác nhận</button>
                            <button className="btn" onClick={handleCancel}>Hủy</button>
                        </div>
                    </div>
                </div>
            )
            }
            {showNotification && (
                <NotificationPopup
                    message={notificationMessage}
                    onClose={handleCloseNotification}
                />
            )}
        </div>
    );
};

export default DeleteLeaveRequestPage;
