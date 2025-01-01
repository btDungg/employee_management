import React, { useState, useEffect } from 'react';
import '../../public/css/ActivityPage.css';
import '../../public/css/Popup.css';
import ViewActivities from './ViewActivities';
import NotificationPopup from '../components/NotificationPopup';
import { getDetailActivity, register, unregister } from '../apis/api';

const ViewActivityDetailAndRegister = ({ onCommit }) => {
    const [formData, setFormData] = useState({});
    const [showApprove, setShowApprove] = useState(false);
    const [isRegistered, setIsRegistered] = useState(false);
    const [showRegisterConfirmation, setShowRegisterConfirmation] = useState(false);
    const [showUnregisterConfirmation, setShowUnregisterConfirmation] = useState(false);
    const [showNotification, setShowNotification] = useState(false);
    const [notificationMessage, setNotificationMessage] = useState('');

    const handleCommit = (data) => {
        setFormData(data);
        setShowApprove(true);
    };

    const fetchDetailActivity = async (activityId) => {
        const detailActivity = await getDetailActivity(activityId);
        if (detailActivity.status != "Not Registered")
            setIsRegistered(true);
    };
    
    useEffect(() => {
        if (showApprove) {
            fetchDetailActivity(formData.activityId);
        }
    }, [showApprove]); 
    
    const handleRegisterConfirmation = () => {
        setShowRegisterConfirmation(true);
    };

    const handleUnregisterConfirmation = () => {
        setShowUnregisterConfirmation(true);
    };

    const handleCancel = () => {
        if (showRegisterConfirmation)
            setShowRegisterConfirmation(false);
        if (setShowUnregisterConfirmation)
            setShowUnregisterConfirmation(false);
    };

    const handleCloseNotification = () => {
        setShowNotification(false);
    };

    const handleRegister = async () => {
        setShowRegisterConfirmation(false);
        try {
            const registerRequest = {
                activityId: formData.activityId
            };
            await register(registerRequest);
            setNotificationMessage("Bạn đã đăng ký thành công");
            setShowNotification(true);
            setTimeout(() => {
                window.location.reload();
            }, 2000); // Adjust the delay as needed
        } catch (error) {
            console.log(error);
            if (error.response && error.response.data) {
                const { errors } = error.response.data; // Truy cập trường "errors"
                if (errors && errors.length > 0) {
                    // Hiển thị tất cả các lỗi dưới dạng chuỗi
                    setNotificationMessage(`Lỗi: ${errors.join(', ')}`);
                } else {
                    setNotificationMessage('Đã xảy ra lỗi không xác định.');
                }
            } else {
                // Trường hợp không có phản hồi từ server (lỗi mạng hoặc lỗi khác)
                setNotificationMessage(`Lỗi: ${error.message}`);
            }
            setShowNotification(true);
        }
    };
    
    const handleUnregister = async () => {
        setShowRegisterConfirmation(false);
        try {
            const registerRequest = {
                activityId: formData.activityId
            };
            await unregister(registerRequest);
            setNotificationMessage("Bạn đã hủy đăng ký thành công");
            setShowNotification(true);
            setTimeout(() => {
                window.location.reload();
            }, 2000); // Adjust the delay as needed
        } catch (error) {
            console.log(error);
            if (error.response && error.response.data) {
                const { errors } = error.response.data; // Truy cập trường "errors"
                if (errors && errors.length > 0) {
                    // Hiển thị tất cả các lỗi dưới dạng chuỗi
                    setNotificationMessage(`Lỗi: ${errors.join(', ')}`);
                } else {
                    setNotificationMessage('Đã xảy ra lỗi không xác định.');
                }
            } else {
                // Trường hợp không có phản hồi từ server (lỗi mạng hoặc lỗi khác)
                setNotificationMessage(`Lỗi: ${error.message}`);
            }
            setShowNotification(true);
        }
    };

    return (
        <div>
            {!showApprove && <ViewActivities onCommit={handleCommit} />}
            {showApprove && 
            <div className="activity-info">
                <h2>Thông tin hoạt động</h2>
                <p><strong>Tên hoạt động: {formData.activityName}</strong></p>
                <p><strong>Loại hoạt động: </strong>{formData.activityType}</p>
                <p><strong>Số người tham gia: </strong>{formData.numberOfRegistered}/{formData.numberOfParticipants}</p>
				<p><strong>Ngày bắt đầu: </strong>{formData.startDate}</p>
                <p><strong>Ngày kết thúc: </strong>{formData.endDate}</p>
                <p><strong>Ngày mở đăng ký: </strong>{formData.registrationOpenDate}</p>
                <p><strong>Ngày đóng đăng ký: </strong>{formData.registrationCloseDate}</p>
                <p><strong>Trạng thái đăng ký: </strong>{formData.status}</p>
                <p><strong>Mô tả:</strong></p>
                <textarea rows="3" cols="100"
                        readOnly
                        value={formData.description}
                />
                <button className="btn" type="button" style={{ backgroundColor: 'green'}}>Kết quả hoạt động</button>
                {isRegistered ? <button className="btn" type="button" style={{ marginLeft: '20px'}} onClick={handleUnregisterConfirmation}>Hủy đăng ký</button>
                : <button className="btn" type="button" style={{ backgroundColor: 'red', marginLeft: '20px'}} onClick={handleRegisterConfirmation}>Đăng ký</button>}
                {showRegisterConfirmation && 
                <div className="popup-overlay">
                    <div className="popup-content">
                        <h2>Bạn có muốn đăng ký không?</h2>
                        <div className="popup-buttons">
                            <button className="btn-form" onClick={handleRegister}>Xác nhận</button>
                            <button className="btn-form" onClick={handleCancel}>Hủy</button>
                        </div>
                    </div>
                </div>
                }
                {showUnregisterConfirmation &&
                <div className="popup-overlay">
                    <div className="popup-content">
                        <h2>Bạn có muốn hủy đăng ký không?</h2>
                        <div className="popup-buttons">
                            <button className="btn-form" onClick={handleUnregister}>Xác nhận</button>
                            <button className="btn-form" onClick={handleCancel}>Hủy</button>
                        </div>
                    </div>
                </div>
                }
                {showNotification && (
                <NotificationPopup
                    message={notificationMessage}
                    onClose={handleCloseNotification}
                />
                )}
            </div>
            }
            
        </div>
    );
}

export default ViewActivityDetailAndRegister;