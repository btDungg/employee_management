import React, { useState, useEffect } from 'react';
import { getMyApproveLeaveRequest, approveAllLeaveRequest } from '../apis/api';
import NotificationPopup from '../components/NotificationPopup';
import '../../public/css/ApproveLeaveRequestPage.css';
import '../../public/css/Popup.css';
import { FaCheck, FaTimes } from 'react-icons/fa';

const ViewMyApproveLRPage = ({ onCommit }) => {
	const [leaveRequests, setLeaveRequests] = useState([]);
    const [isDenied, setIsDenied] = useState(false);
    const [submitWarning, setSubmitWarning] = useState('');
	const [showConfirmation, setShowConfirmation] = useState(false);
	const [notificationMessage, setNotificationMessage] = useState('');
	const [showNotification, setShowNotification] = useState(false);

	useEffect(() => {
	    const getListLeaveRequest = async () => {
			try	{
				const data = await getMyApproveLeaveRequest();
				setLeaveRequests(data);
	        } catch (error) {
                if (error.status == '403')
					setIsDenied(true);
                	setSubmitWarning("Bạn không có quyền thực hiện chức năng này!");
				console.error("Lỗi khi lấy danh sách yêu cầu nghỉ phép: ", error);
			}
		};
        setIsDenied(false);
        setSubmitWarning('');
		getListLeaveRequest();
	}, []);

    const handleAccept = (entry) => {
        // Cập nhật trạng thái của yêu cầu thành "Đã duyệt"
        const updatedRequests = leaveRequests.map((item) =>
            item === entry ? { ...item, status: "Đã duyệt" } : item
        );

        // Cập nhật leaveRequest
        setLeaveRequests(updatedRequests);

        // Gửi dữ liệu của hàng đã cập nhật qua prop onCommit
        onCommit({ ...entry, status: "Đã duyệt" });
    };

    const handleReject = (entry) => {
        // Cập nhật trạng thái của yêu cầu thành "Đã duyệt"
        const updatedRequests = leaveRequests.map((item) =>
            item === entry ? { ...item, status: "Bị từ chối" } : item
        );

        // Cập nhật leaveRequest
        setLeaveRequests(updatedRequests);

        // Gửi dữ liệu của hàng đã cập nhật qua prop onCommit
        onCommit({ ...entry, status: "Bị từ chối" });
    };

	const approveAllLR = async () => {
		setShowConfirmation(false);
		try	{
			await approveAllLeaveRequest(leaveRequests);
			setNotificationMessage("Duyệt tất cả yêu cầu nghỉ phép thành công");
            setShowNotification(true);
            setTimeout(() => {
                window.location.reload();
            }, 2000); // Adjust the delay as needed
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
	}

	const handleConfirm = () => {
        setShowConfirmation(true);
    };

	const handleCancel = () => {
        setShowConfirmation(false);
    };

    const handleCloseNotification = () => {
        setShowNotification(false);
    };

	return (
		<div>
			{isDenied ? (
				<div className="submit-warning">
					<h3>{submitWarning}</h3>
				</div>
			) : (
				<div className="leave-request-info">
					<h2>Yêu cầu nghỉ phép</h2>
					<button className="btn" type="button" onClick={handleConfirm}>Duyệt tất cả</button>
					<table className="leave-request-table">
						<thead>
							<tr>
								<th>STT</th>
								<th>Tên nhân viên</th>
								<th>Ngày bắt đầu</th>
								<th>Ngày kết thúc</th>
								<th>Số ngày yêu cầu</th>
								<th>Loại ngày nghỉ</th>
								<th>Ca nghỉ</th>
								<th>Lý do xin nghỉ</th>
								<th>Trạng thái</th>
								<th>Hành động / Lý do duyệt</th>
							</tr>
						</thead>
						<tbody>
							{leaveRequests.map((entry, index) => (
								<tr key={index}>
									<td>{index + 1}</td>
									<td>{entry.employeeName}</td>
									<td>{entry.startDate}</td>
									<td>{entry.endDate}</td>
									<td>{entry.requestDays}</td>
									<td>{entry.dayOffType}</td>
									<td>{entry.session}</td>
									<td>{entry.reason}</td>
									<td>{entry.status}</td>
									<td>
										<div>
											{entry.status === "Đang chờ duyệt" ? (
												<div className="action-buttons">
													{/* Nút Duyệt */}
													<button
														className="btn approve-btn"
														type="button"
														title="Duyệt"
														onClick={() => handleAccept(entry)}
													>
														<FaCheck />
													</button>
													{/* Nút Từ chối */}
													<button
														className="btn deny-btn"
														type="button"
														title="Từ chối"
                                                        onClick={() => handleReject(entry)}
													>
														<FaTimes />
													</button>
												</div>
											)
											: <div>{entry.reasonApprove}</div>}
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
			)}
			{showConfirmation &&
            (
                <div className="popup-overlay">
                    <div className="popup-content">
                        <h2>Bạn có muốn duyệt tất cả yêu cầu nghỉ phép không?</h2>
                        <div className="popup-buttons">
                            <button className="btn" onClick={approveAllLR}>Xác nhận</button>
                            <button className="btn" onClick={handleCancel}>Hủy</button>
                        </div>
                    </div>
                </div>
            )
            }
			{showNotification && (
                <NotificationPopup
                    message={notificationMessage}
                    onClose={handleCloseNotification}
                />
            )}
		</div>
	);
};

export default ViewMyApproveLRPage;


