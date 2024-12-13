import React, { useState, useEffect } from 'react';
import { getMyApproveLeaveRequest } from '../apis/api';
import '../../public/css/ApproveLeaveRequestPage.css';
import { FaCheck, FaTimes } from 'react-icons/fa';

const ViewMyApproveLRPage = ({ onCommit }) => {
	const [leaveRequests, setLeaveRequests] = useState([]);
    const [isDenied, setIsDenied] = useState(false);
    const [submitWarning, setSubmitWarning] = useState('');

	useEffect(() => {
	    const getListLeaveRequest = async () => {
			try	{
				const data = await getMyApproveLeaveRequest();
				setLeaveRequests(data);
	        } catch (error) {
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

	return (
		<div>
			{isDenied ? (
				<div className="submit-warning">
					<h3>{submitWarning}</h3>
				</div>
			) : (
				<div className="leave-request-info">
					<h2>Yêu cầu nghỉ phép</h2>
					<table className="leave-request-table">
						<thead>
							<tr>
								<th>STT</th>
								<th>Tên nhân viên</th>
								<th>Ngày bắt đầu</th>
								<th>Ngày kết thúc</th>
								<th>Số ngày yêu cầu</th>
								<th>Lý do xin nghỉ</th>
								<th>Loại ngày nghỉ</th>
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
									<td>{entry.reason}</td>
									<td>{entry.dayOffType}</td>
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
		</div>
	);
};

export default ViewMyApproveLRPage;


