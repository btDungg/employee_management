import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import '../../public/css/ActivityPage.css';
import '../../public/css/Popup.css';
import NotificationPopup from '../components/NotificationPopup';
import { getDetailActivity, register, unregister, getEmployeeDetailsByEmail,updateActivity  } from '../apis/api';

const ViewActivityDetailAndRegister = () => {
    const [user, setUser] = useState(null);
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
    const [isEditing, setIsEditing] = useState(false);
    const [isViewed, setIsViewed] = useState(false);

    

    useEffect(() => {
        const fetchUserDetails = async () => {
          try {
            const userDetails = await getEmployeeDetailsByEmail();
            setUser(userDetails);
          } catch (err) {
            console.error('Error fetching user details:', err);
            setSearchWarning('Không thể tải thông tin người dùng.');
          }
        };
    
        fetchUserDetails();
      }, []);

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
                setIsViewed(detailActivity.activity.isViewed === '1');
            } catch (error) {
                console.error('Error fetching activity details:', error);
            }
        };
    
        fetchDetailActivity();
    }, [activityId]);
    
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


    const mapStateToApiPayload = () => {
        return {
            id: parseInt(activityId, 10), // Ensure id is a Long (integer)
            name: activityName, // Map `activityName` to `name`
            type: activityType, // Map `activityType` to `type`
            startDate, // Direct mapping
            endDate, // Direct mapping
            numberOfParticipants: parseInt(numberOfParticipants, 10), // Ensure it's a number
            registrationOpenDate, // Direct mapping
            registrationCloseDate, // Direct mapping
            status, // Direct mapping
            description, // Direct mapping
            isViewed: isViewed === '1', // Convert '1' to `true`, '0' to `false`
        };
    };
    const handleSaveChanges = async () => {
        try {
            const payload = mapStateToApiPayload(); // Convert the state to the API format
            console.log('Mapped Payload:', payload);
            await updateActivity(payload); // Send the payload to the API
            setIsEditing(false);
            setNotificationMessage('Cập nhật hoạt động thành công.');
            setShowNotification(true);
        } catch (error) {
            console.error('Error updating activity:', error);
            setNotificationMessage('Có lỗi xảy ra khi cập nhật hoạt động.');
            setShowNotification(true);
        }
    };

    // Hàm hủy thay đổi
        const handleCancelEdit = () => {
            setIsEditing(false);
        };
    return (
        
            <div>
                <div className="activity-info">
                    <h2>Thông tin hoạt động</h2>
                    {user?.type === 'HR' && !isEditing && (
                        <div className='edit_activity'>
                            <button className="btn_edit_activity" onClick={() => setIsEditing(true)}>
                                <i className="fa-solid fa-edit"></i>
                            </button>
                        </div>
                    )}
    
                    {isEditing ? (
                        <div className="update-form">
                            <div className="update-row">
                                <label className="update-label">Tên hoạt động:</label>
                                <input
                                    className="update-input"
                                    type="text"
                                    value={activityName}
                                    onChange={(e) => setActivityName(e.target.value)}
                                />
                            </div>
                            <div className="update-row">
                                <label className="update-label">Loại hoạt động:</label>
                                <select
                                    className="update-input"
                                    type="text"
                                    value={activityType}
                                    onChange={(e) => setActivityType(e.target.value)}
                                >
                                    <option>{activityType}</option>
                                    <option>Thể thao</option>
                                    <option>Hội thảo</option>
                                    <option>Khóa học</option>
                                    </select>
                            </div>
                            <div className="update-row">
                                <label className="update-label">Số người tham gia:</label>
                                <input
                                    className="update-input"
                                    type="number"
                                    value={numberOfParticipants}
                                    onChange={(e) => setNumberOfParticipants(e.target.value)}
                                />
                            </div>
                            <div className="activity-row">
                                <span className="label">Trạng thái:</span>
                                <input type="radius"
                                        className='update-input' 
                                        value={status}
                                        onChange={(e)=> setStatus(e.target.value)}/>
                            </div>
                            <div className="update-row">
                                <label className="update-label">Có thể xem:</label>
                                <input
                                    type="checkbox"
                                    checked={isViewed === '1'}
                                    onChange={(e) => setIsViewed(e.target.checked ? '1' : '0')}
                                />
                            </div>
                            <div className="update-row">
                                <label className="update-label">Ngày bắt đầu:</label>
                                <input
                                    className="update-input"
                                    type="date"
                                    value={startDate}
                                    onChange={(e) => setStartDate(e.target.value)}
                                />
                            </div>
                            <div className="update-row">
                                <label className="update-label">Ngày kết thúc:</label>
                                <input
                                    className="update-input"
                                    type="date"
                                    value={endDate}
                                    onChange={(e) => setEndDate(e.target.value)}
                                />
                            </div>
                            <div className="update-row">
                                <label className="update-label">Ngày mở đăng ký:</label>
                                <input
                                    className="update-input"
                                    type="date"
                                    value={registrationOpenDate}
                                    onChange={(e) => setRegistrationOpenDate(e.target.value)}
                                />
                            </div>
                            <div className="update-row">
                                <label className="update-label">Ngày đóng đăng ký:</label>
                                <input
                                    className="update-input"
                                    type="date"
                                    value={registrationCloseDate}
                                    onChange={(e) => setRegistrationCloseDate(e.target.value)}
                                />
                            </div>
                            <div className="update-row">
                                <label className="update-label">Mô tả:</label>
                                <textarea
                                    className="update-textarea"
                                    rows="4"
                                    value={description}
                                    onChange={(e) => setDescription(e.target.value)}
                                />
                            </div>
                            <div className="update-buttons">
                                <button className="update-button update-save" onClick={handleSaveChanges}>
                                    Lưu thay đổi
                                </button>
                                <button className="update-button update-cancel" onClick={() => setIsEditing(false)}>
                                    Hủy
                                </button>
                            </div>
                        </div>
                    ) : (
                        <div className="activity-body">
                            <div className="activity-row">
                                <span className="label">Tên hoạt động:</span>
                                <span className="value">{activityName}</span>
                            </div>
                            <div className="activity-row">
                                <span className="label">Loại hoạt động:</span>
                                <span className="value">{activityType}</span>
                            </div>
                            <div className="activity-row">
                                <span className="label">Số người tham gia:</span>
                                <span className="value">{numberOfRegistered}/{numberOfParticipants}</span>
                            </div>
                            <div className="activity-row">
                                <span className="label">Ngày bắt đầu:</span>
                                <span className="value">{startDate}</span>
                            </div>
                            <div className="activity-row">
                                <span className="label">Ngày kết thúc:</span>
                                <span className="value">{endDate}</span>
                            </div>
                            <div className="activity-row">
                                <span className="label">Ngày mở đăng ký:</span>
                                <span className="value">{registrationOpenDate}</span>
                            </div>
                            <div className="activity-row">
                                <span className="label">Ngày đóng đăng ký:</span>
                                <span className="value">{registrationCloseDate}</span>
                            </div>
                            <div className="activity-row">
                                <span className="label">Trạng thái hoạt động:</span>
                                <span className="value">{status}</span>
                            </div>
                         
                            <div className="activity-row description-row">
                                    <span className="label">Mô tả:</span>
                                    <div className="description-container">
                                        <textarea 
                                            className="description-textarea" 
                                            rows="4" 
                                            readOnly 
                                            value={description}
                                        ></textarea>
                                    </div>
                                </div>
                        </div>
                    )}
    
                    <div className="activity-buttons">
                        <button className="btn" type="button" style={{ backgroundColor: 'green' }}>
                            Kết quả hoạt động
                        </button>
                        {isRegistered ? (
                            <button className="btn" type="button" style={{ marginLeft: '20px' }} onClick={handleUnregisterConfirmation}>
                                Hủy đăng ký
                            </button>
                        ) : (
                            <button className="btn" type="button" style={{ backgroundColor: 'red', marginLeft: '20px' }} onClick={handleRegisterConfirmation}>
                                Đăng ký
                            </button>
                        )}
                    </div>
    
                    {showRegisterConfirmation && (
                        <div className="popup-overlay">
                            <div className="popup-content">
                                <h2>Bạn có muốn đăng ký không?</h2>
                                <div className="popup-buttons">
                                    <button className="btn-form" onClick={handleRegister}>Xác nhận</button>
                                    <button className="btn-form" onClick={handleCancel}>Hủy</button>
                                </div>
                            </div>
                        </div>
                    )}
    
                    {showUnregisterConfirmation && (
                        <div className="popup-overlay">
                            <div className="popup-content">
                                <h2>Bạn có muốn hủy đăng ký không?</h2>
                                <div className="popup-buttons">
                                    <button className="btn-form" onClick={handleUnregister}>Xác nhận</button>
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
            </div>
        );
    };


export default ViewActivityDetailAndRegister;