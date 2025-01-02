// src/components/NotificationPopup.jsx
import React from 'react';
import '../../public/css/Popup.css';

const NotificationPopup = ({ message, onClose }) => {
    return (
        <div className="popup-overlay">
            <div className="popup-content">
                <p>{message}</p>
                <button className="btn-form" onClick={onClose}>Đóng</button>
            </div>
        </div>
    );
};

export default NotificationPopup;