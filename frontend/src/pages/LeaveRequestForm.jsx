import React, { useState, useEffect } from 'react';
import SendLeaveRequest from '../components/SendLeaveRequest';
import NotificationPopup from '../components/NotificationPopup';
import { sendLeaveRequest } from '../apis/api';
import '../../public/css/Popup.css';

const LeaveRequestForm = () => {
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [formData, setFormData] = useState({});
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
                dayOffType: formData.dayOffType,
                reason: formData.reason
            };
            await sendLeaveRequest(leaveRequest);
            setNotificationMessage("Gửi yêu cầu nghỉ phép thành công");
            setShowNotification(true);
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
            {<SendLeaveRequest onCommit={handleCommit}/>}
            {showConfirmation && (
                <div className="popup-overlay">
                    <div className="popup-content">
                        <h2>Bạn hãy xác nhận lại thông tin dưới đây ?</h2>
                        <p>Ngày bắt đầu: {formData.startDate}</p>
                        <p>Ngày kết thúc: {formData.endDate}</p>
                        <p>Loại ngày nghỉ: {formData.dayOffType}</p>
                        <p>Lý do: {formData.reason}</p>
                        <div className="popup-buttons">
                            <button className="btn" onClick={handleConfirm}>Xác nhận</button>
                            <button className="btn" onClick={handleCancel}>Hủy</button>
                        </div>
                    </div>
                </div>
            )}
            {showNotification && (
                <NotificationPopup
                    message={notificationMessage}
                    onClose={handleCloseNotification}
                />
            )}
        </div>
    );
};

export default LeaveRequestForm;