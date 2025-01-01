import React, { useState, useEffect } from 'react';
import { getActivities } from '../apis/api';
import '../../public/css/ActivityPage.css';

const ViewActivities = ({ onCommit }) => {
	const [activities, setActivities] = useState([]);
	const [name, setName] = useState('');
    const [isDenied, setIsDenied] = useState(false);
	const [searchWarning, setSearchWarning] = useState('');
    const [submitWarning, setSubmitWarning] = useState('');

	const handleSearch = async () => {
        const activity = await getActivities(name);
        setActivities(activity);
		if (activity[0] == null)
			setSearchWarning('Không tồn tại hoạt động có tên ' + name);
		else
			setSearchWarning('');
    };

	useEffect(() => {
	    const getListActivities = async () => {
			try	{
				const data = await getActivities();
				setActivities(data);
	        } catch (error) {
                setIsDenied(true);
                setSubmitWarning("Bạn không có quyền thực hiện chức năng này!");
				console.error("Lỗi khi lấy danh sách yêu cầu nghỉ phép: ", error);
			}
		};
        setIsDenied(false);
        setSubmitWarning('');
		getListActivities();
	}, []);

	const handleDetail = (activity) => {
        // Gửi dữ liệu của hoạt động đã chọn qua prop onCommit
        onCommit(activity);
    };

	return (
		<div className="activity-page">
		  <h2 className="mb-4">Hoạt động công ty</h2>
		  <div className="search-container">
			<input type="text" 
			className="search-bar" 
			value={name} 
			onChange={(e) => setName(e.target.value)}
			placeholder="Tên hoạt động" />
			<button className="btn" type="button" onClick={handleSearch}>Tìm kiếm</button>
		  </div>
		  {searchWarning && <div className="search-warning">{searchWarning}</div>}
		  <div className="activity-grid">
			{activities.map((activity, index) => (
				<div key={index} className="activity-card" type="button" onClick={() => handleDetail(activity)}>
					<h2 className="activity-title">{activity.activityName}</h2>
					<p>Loại hoạt động: {activity.activityType}</p>
					<p>Số người tham gia: {activity.numberOfRegistered}/{activity.numberOfParticipants}</p>
					<p>Thời gian bắt đầu: {activity.startDate}</p>

          		</div>
        ))}
				</div>
		</div>
	  );
};

export default ViewActivities;


