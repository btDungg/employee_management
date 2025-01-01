import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getActivities } from '../apis/api';
import '../../public/css/ActivityPage.css';

const ViewActivities = ({ onCommit }) => {
	const [activities, setActivities] = useState([]);
	const [name, setName] = useState('');
	const [searchWarning, setSearchWarning] = useState('');
	const navigate = useNavigate();

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
			const data = await getActivities();
			setActivities(data);
		};
		getListActivities();
	}, []);

	const handleDetail = (activity) => {
        // Gửi dữ liệu của hoạt động đã chọn qua prop onCommit
        //onCommit(activity);
		navigate(`/personal-activity/${activity.activityId}`);
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


