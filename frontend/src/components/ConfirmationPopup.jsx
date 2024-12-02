import React from 'react';
import '../../public/css/Popup.css';

const ConfirmationPopup = ({ message, onConfirm, onCancel }) => {
    return (
        <div className="popup-overlay">
            <div className="popup-content">
                <p className="popup-message">{message}</p>
                <div className="popup-buttons">
                    <button className="btn-form" onClick={onConfirm}>Xác nhận</button>
                    <button className="btn-form" onClick={onCancel}>Hủy</button>
                </div>
            </div>
        </div>
    );
};

export default ConfirmationPopup;