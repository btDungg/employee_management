// src/pages/ChangePointsPage.jsx
import React, { useState } from 'react';
import ManagerChangePointsForm from '../components/ManagerChangePointsForm';
import HRChangePointsForm from '../components/HRChangePointsForm';
import ConfirmationPopup from '../components/ConfirmationPopup';
import NotificationPopup from '../components/NotificationPopup';


const ChangePointsPage = ({ userRole }) => {
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [formData, setFormData] = useState({});
    const [notificationMessage, setNotificationMessage] = useState('');
    const [showNotification, setShowNotification] = useState(false);

    const handleCommit = (data) => {
        setFormData(data);
        setShowConfirmation(true);
    };

    const handleConfirm = () => {
        setShowConfirmation(false);
        // Simulate success or failure
        const isSuccess = Math.random() > 0.5;
        setNotificationMessage(isSuccess ? 'Success!' : 'Failure!');
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
            {userRole === 'manager' ? <ManagerChangePointsForm onCommit={handleCommit} /> : <HRChangePointsForm onCommit={handleCommit} />}
            {showConfirmation && (
                <ConfirmationPopup
                    message={`Are you sure you want to commit the following changes?\n
                    Employee ID: ${formData.employeeId}\n
                    Employee Name: ${formData.employeeName}\n
                    Current Points: ${formData.currentPoints}\n
                    Change Points: ${formData.changePoints}\n
                    Change Type: ${formData.changeType}\n
                    Reason: ${formData.reason}`}
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