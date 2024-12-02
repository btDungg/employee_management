import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ManagerChangePointsForm from '../components/ManagerChangePointsForm';
import HRChangePointsForm from '../components/HRChangePointsForm';
import NotificationPopup from '../components/NotificationPopup';
import { modifyPoints, getEmployeeProfile } from '../apis/api';
import '../../public/css/Popup.css';

const ChangePointsPage = () => {
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [formData, setFormData] = useState({});
    const [notificationMessage, setNotificationMessage] = useState('');
    const [showNotification, setShowNotification] = useState(false);
    const [userRole, setUserRole] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                const profile = await getEmployeeProfile();
                setUserRole(profile.type);
            } catch (error) {
                console.error('Error fetching user profile:', error);
            }
        };

        fetchUserProfile();
    }, []);

    const handleCommit = (data) => {
        setFormData(data);
        setShowConfirmation(true);
    };

    const handleConfirm = async () => {
        setShowConfirmation(false);
        try {
            const modifyPoint = {
                receive_id: formData.employeeId,
                amount: formData.changePoints,
                modifyType: formData.changeType,
                reason: formData.reason
            };
            await modifyPoints(modifyPoint);
            setNotificationMessage("Thay đổi điểm thành công");
            setShowNotification(true);
            setTimeout(() => {
                navigate('/point-history'); // Navigate to point history page after showing notification
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
            {userRole === 'Manager' ? <ManagerChangePointsForm onCommit={handleCommit} /> : <HRChangePointsForm onCommit={handleCommit} />}
            {showConfirmation && (
                <div className="popup-overlay">
                    <div className="popup-content">
                        <h2>Bạn hãy xác nhận lại thông tin dưới đây ?</h2>
                        <p>Mã nhân viên nhận: {formData.employeeId}</p>
                        <p>Tên nhân viên nhận: {formData.employeeName}</p>
                        <p>Số điểm hiện có: {formData.currentPoints}</p>
                        <p>Số điểm muốn thay đổi: {formData.changePoints}</p>
                        {userRole === 'Manager' && formData.changeType === 'increase' && (
                            <p>Số điểm tặng còn lại: {formData.bonusPoints - formData.changePoints}</p>
                        )}
                        <div className="popup-buttons">
                            <button className="btn-form" onClick={handleConfirm}>Xác nhận</button>
                            <button className="btn-form" onClick={handleCancel}>Hủy</button>
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

export default ChangePointsPage;