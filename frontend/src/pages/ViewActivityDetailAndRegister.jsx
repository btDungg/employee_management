import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import '../../public/css/ActivityPage.css';
import '../../public/css/Popup.css';
import NotificationPopup from '../components/NotificationPopup';
import { getDetailActivity, register, unregister } from '../apis/api';

const ViewActivityDetailAndRegister = () => {
    const { activityId } = useParams();
    const [activityName, setActivityName] = useState('');
    const [activityType, setActivityType] = useState('');
    const [numberOfRegistered, setNumberOfRegistered] = useState('');
    const [numberOfParticipants, setNumberOfParticipants] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [registrationOpenDate, setRegistrationOpenDate] = useState('');
    const [registrationCloseDate, setRegistrationCloseDate] = useState('');
    const [status, setStatus] = useState('');
    const [description, setDescription] = useState('');
    const [isRegistered, setIsRegistered] = useState(false);
    const [showRegisterConfirmation, setShowRegisterConfirmation] = useState(false);
    const [showUnregisterConfirmation, setShowUnregisterConfirmation] = useState(false);
    const [showNotification, setShowNotification] = useState(false);
    const [notificationMessage, setNotificationMessage] = useState('');

    

    useEffect(() => {
        const fetchDetailActivity = async () => {
          try {
            const detailActivity = await getDetailActivity(activityId); // Fetch activity details
            setActivityName(detailActivity.activity.activityName);
            setActivityType(detailActivity.activity.activityType);
            setNumberOfParticipants(detailActivity.activity.numberOfParticipants);
            setNumberOfRegistered(detailActivity.activity.numberOfRegistered);
            setStartDate(detailActivity.activity.startDate);
            setEndDate(detailActivity.activity.endDate);
            setRegistrationOpenDate(detailActivity.activity.registrationOpenDate);
            setRegistrationCloseDate(detailActivity.activity.registrationCloseDate);
            setStatus(detailActivity.activity.status);
            setDescription(detailActivity.activity.description);
            setIsRegistered(detailActivity.status !== 'Not Registered');
            //console.log(detailActivity.activity.activityName);
          } catch (error) {
            console.error('Error fetching activity details:', error);
          }
        };
    
        fetchDetailActivity();
      }, {});
    
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
                activityId: activityId
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
                activityId: activityId
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
            <div className="activity-info">
                <h2>Thông tin hoạt động</h2>
                <p><strong>Tên hoạt động: {activityName}</strong></p>
                <p><strong>Loại hoạt động: </strong>{activityType}</p>
                <p><strong>Số người tham gia: </strong>{numberOfRegistered}/{numberOfParticipants}</p>
				<p><strong>Ngày bắt đầu: </strong>{startDate}</p>
                <p><strong>Ngày kết thúc: </strong>{endDate}</p>
                <p><strong>Ngày mở đăng ký: </strong>{registrationOpenDate}</p>
                <p><strong>Ngày đóng đăng ký: </strong>{registrationCloseDate}</p>
                <p><strong>Trạng thái đăng ký: </strong>{status}</p>
                <p><strong>Mô tả:</strong></p>
                <textarea rows="3" cols="100"
                        readOnly
                        value={description}
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
            
        </div>
    );
}

export default ViewActivityDetailAndRegister;