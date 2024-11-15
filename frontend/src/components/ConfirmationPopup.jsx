import React from 'react';
import '../../public/css/Popup.css';

const ConfirmationPopup = ({ message, onConfirm, onCancel }) => {
    return (
        <div className="popup-overlay">
            <div className="popup-content">
                <p className="popup-message">{message}</p>
                <div className="popup-buttons">
                    <button className="btn" onClick={onConfirm}>Confirm</button>
                    <button className="btn" onClick={onCancel}>Cancel</button>
                </div>
            </div>
        </div>
    );
};

export default ConfirmationPopup;