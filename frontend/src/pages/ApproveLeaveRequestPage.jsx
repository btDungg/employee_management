import React, { useState } from 'react';
import ApproveLeaveRequestForm from '../components/ApproveLeaveRequestForm';
import NotificationPopup from '../components/NotificationPopup';
import { approveLeaveRequest } from '../apis/api';
import '../../public/css/Popup.css';

const AcceptLeaveRequestPage = () => {
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
            const approveLR = {
                employeeId: formData.employeeId,
                startDate: formData.startDate,
                endDate: formData.endDate,
                status: formData.status,
                reasonApprove: formData.reasonApprove
            };
            await approveLeaveRequest(approveLR);
            setNotificationMessage("Duyệt yêu cầu nghỉ phép thành công");
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
            {<ApproveLeaveRequestForm onCommit={handleCommit} />}
            {showConfirmation &&
            (
                <div className="popup-overlay">
                    <div className="popup-content">
                        <h2>Bạn hãy xác nhận lại thông tin dưới đây</h2>
                        <p>Mã nhân viên: {formData.employeeId}</p>
                        <p>Tên nhân viên: {formData.employeeName}</p>
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

export default AcceptLeaveRequestPage;
