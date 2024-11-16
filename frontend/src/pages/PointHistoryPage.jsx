import React, { useState, useEffect } from 'react';
import { getPointChanges } from '../apis/api';

const PointHistoryPage = () => {
	const [history, setHistory] = useState([]);
	useEffect(() => {
	        const getHistory = async () => {
				try	{
					const data = await getPointChanges();
					setHistory(data);
	        } catch (error) {
				console.error("Lỗi khi lấy lịch sử điểm:", error);
			}
		};
		getHistory();
	},[]);
		return (
		        <div className="point-info">
				<h2>Lịch sử điểm</h2>
		            <table className="points-table">
		                <thead>
		                <tr>
							<th>STT</th>
		                    <th>Số điểm</th>
		                    <th>Ngày thay đổi</th>
		                    <th>Lý do</th>
		                    <th>Người thay đổi</th>
		                </tr>
		                </thead>
		                <tbody>
		                {history.map((entry, index) => (
		                    <tr key={index}>
								<td>{index + 1}</td>
		                        <td>{entry.amount}</td>
		                        <td>{entry.changedDate}</td>
		                        <td>{entry.reason}</td>
		                        <td>{entry.people_change}</td>
		                    </tr>
		                ))}
		                </tbody>
		            </table>
		        </div>
		    );	
};

export default PointHistoryPage;

