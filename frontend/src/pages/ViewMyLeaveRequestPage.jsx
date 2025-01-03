import React, { useState, useEffect } from 'react';
import { getMyLeaveRequest } from '../apis/api';
import '../../public/css/ApproveLeaveRequestPage.css';
import { FaTrash } from 'react-icons/fa6';

const ViewMyLeaveRequestPage = ({ onCommit }) => {
	const [leaveRequests, setLeaveRequests] = useState([]);
    const [isDenied, setIsDenied] = useState(false);
    const [submitWarning, setSubmitWarning] = useState('');

	useEffect(() => {
	    const getListLeaveRequest = async () => {
			try	{
				const data = await getMyLeaveRequest();
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

    const handleDelete = (entry) => {
        onCommit(entry);
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
								<th>Ngày bắt đầu</th>
								<th>Ngày kết thúc</th>
								<th>Số ngày yêu cầu</th>
								<th>Loại ngày nghỉ</th>
								<th>Ca nghỉ</th>
								<th>Lý do xin nghỉ</th>
								<th>Trạng thái</th>
								<th>Lý do duyệt</th>
								<th>Hành động</th>
							</tr>
						</thead>
						<tbody>
							{leaveRequests.map((entry, index) => (
								<tr key={index}>
									<td>{index + 1}</td>
									<td>{entry.startDate}</td>
									<td>{entry.endDate}</td>
									<td>{entry.requestDays}</td>
									<td>{entry.dayOffType}</td>
									<td>{entry.session}</td>
									<td>{entry.reason}</td>
									<td>{entry.status}</td>
									<td>{entry.reasonApprove}</td>
									<td>
										{entry.status == "Đang chờ duyệt" &&
										<div className="action-buttons">
										{/* Nút Xóa */}
										<button
											className="btn delete-btn"
											type="button"
											title="Xóa"
											onClick={() => handleDelete(entry)}
										>
											<FaTrash/>
										</button>
										</div>
										}
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

export default ViewMyLeaveRequestPage;


