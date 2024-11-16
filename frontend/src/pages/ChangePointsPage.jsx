import React, { useState, useEffect } from 'react';
import ManagerChangePointsForm from '../components/ManagerChangePointsForm';
import HRChangePointsForm from '../components/HRChangePointsForm';
import ConfirmationPopup from '../components/ConfirmationPopup';
import NotificationPopup from '../components/NotificationPopup';
import { modifyPoints, getEmployeeProfile } from '../apis/api';

const ChangePointsPage = () => {
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [formData, setFormData] = useState({});
    const [notificationMessage, setNotificationMessage] = useState('');
    const [showNotification, setShowNotification] = useState(false);
    const [userRole, setUserRole] = useState('');

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
            await modifyPoints(formData);
            setNotificationMessage("Thay đổi điểm thành công");
        } catch (error) {
            setNotificationMessage(`Failure: ${error.message}`);
        }
        setShowNotification(true);
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
                <ConfirmationPopup
                    message={
                        <>
                            <h2>Bạn hãy xác nhận lại thông tin dưới đây ?</h2>
                            <p>Mã nhân viên nhận: {formData.employeeId}</p>
                            <p>Tên nhân viên nhận: {formData.employeeName}</p>
                            <p>Số điểm hiện có: {formData.currentPoints}</p>
                            <p>Số điểm muốn thay đổi: {formData.changePoints}</p>
                            {formData.changeType === 'increase' && (
                                <p>Số điểm tặng còn lại: {formData.bonusPoints - formData.changePoints}</p>
                            )}
                        </>
                    }
                    onConfirm={handleConfirm}
                    onCancel={handleCancel}
                />
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