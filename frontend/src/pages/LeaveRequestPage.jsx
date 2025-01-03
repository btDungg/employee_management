import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import LeaveRequestForm from '../components/LeaveRequestForm';
import NotificationPopup from '../components/NotificationPopup';
import { getMyDayOff, sendLeaveRequest } from '../apis/api';
import '../../public/css/Popup.css';
import '../../public/css/LeaveRequestPage.css'

const LeaveRequestPage = () => {
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [formData, setFormData] = useState({});
    const [notificationMessage, setNotificationMessage] = useState('');
    const [showNotification, setShowNotification] = useState(false);
    const [myDayOff, setMyDayOff] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchMyDayOff = async () => {
            try {
                const types = await getMyDayOff();
                setMyDayOff(types); // Cập nhật danh sách vào state
            } catch (error) {
                console.error('Error fetching list my day-off: ', error);
            }
        };
        fetchMyDayOff();
    }, []);

    const handleCommit = (data) => {
        setFormData(data);
        setShowConfirmation(true);
    };

    const handleConfirm = async () => {
        setShowConfirmation(false);
        try {
            const leaveRequest = {
                startDate: formData.startDate,
                endDate: formData.endDate,
                requestDays: formData.requestDays,
                dayOffType: formData.dayOffType,
                reason: formData.reason,
                session: formData.session
            };
            await sendLeaveRequest(leaveRequest);
            setNotificationMessage("Gửi yêu cầu nghỉ phép thành công");
            setShowNotification(true);
            setTimeout(() => {
                navigate('/leave-request/history'); // Chuyển hướng tới trang lịch sử yêu cầu nghỉ phép
            }, 2000);
        } catch (error) {
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

    const handleCancel = () => {
        setShowConfirmation(false);
    };

    const handleCloseNotification = () => {
        setShowNotification(false);
    };

    return (
        <div className="leave-request-page">
            <div className="main-content">
                <div className="form-container">
                {<LeaveRequestForm onCommit={handleCommit} myDayOff={myDayOff}/>}
                </div>
                <div className="side-dialog">
                    <h2>Số ngày nghỉ trong năm</h2>
                    <table className="day-off-table">
                        <thead>
                            <tr>
                                <th>Loại ngày nghỉ</th>
                                <th>Số ngày còn lại</th>
                            </tr>
                        </thead>
                <tbody>
                    {myDayOff.map((item, index) => (
                        <tr key={index}>
                            <td>{item.typeName}</td>
                            <td style={{ textAlign: 'center' }}>{item.remainingDays}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
                </div>
            </div>
            {showConfirmation && (
                <div className="popup-overlay">
                    <div className="popup-content">
                        <h2>Bạn hãy xác nhận lại thông tin dưới đây ?</h2>
                        <p>Ngày bắt đầu: {formData.startDate}</p>
                        <p>Ngày kết thúc: {formData.endDate}</p>
                        <p>Loại ngày nghỉ: {formData.dayOffType}</p>
                        <p>Ca nghỉ: {formData.session}</p>
                        <p>Lý do: {formData.reason}</p>
                        <div className="popup-buttons">
                            <button className="btn" onClick={handleConfirm}>Xác nhận</button>
                            <button className="btn" onClick={handleCancel}>Hủy</button>
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

export default LeaveRequestPage;