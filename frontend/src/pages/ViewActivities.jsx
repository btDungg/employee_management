import React, { useState, useEffect } from 'react';
import { getActivities,addActivity } from '../apis/api';
import '../../public/css/ActivityPage.css';

const ViewActivities = ({ onCommit }) => {

    
	const [activities, setActivities] = useState([]);
	const [name, setName] = useState('');
    const [isDenied, setIsDenied] = useState(false);
	const [searchWarning, setSearchWarning] = useState('');
    const [submitWarning, setSubmitWarning] = useState('');
	const [showAddPopup, setShowAddPopup] = useState(false);
	const [newActivity, setNewActivity] = useState({
		activityName: '',
		activityType: 'Thể thao',
		numberOfParticipants: 0,
		startDate: '',
		endDate: '',
		registrationOpenDate: '',
		registrationCloseDate: '',
		status: 'Opened',
		description: '',
		isViewed: true,
	  });


	

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

	const handleAddActivity = async () => {
		try {
		  const activityData = {
			name: newActivity.activityName,
			type: newActivity.activityType,
			startDate: newActivity.startDate,
			endDate: newActivity.endDate,
			numberOfParticipants: parseInt(newActivity.numberOfParticipants, 10),
			registrationOpenDate: newActivity.registrationOpenDate,
			registrationCloseDate: newActivity.registrationCloseDate,
			status:true,
			description: newActivity.description,
			isViewed: newActivity.isViewed,
		  };
		  console.log('activityData: ', activityData);
		  await addActivity(activityData);
		  setShowAddPopup(false);
		  setNewActivity({
			activityName: '',
			activityType: 'Thể thao',
			numberOfParticipants: 0,
			startDate: '',
			endDate: '',
			registrationOpenDate: '',
			registrationCloseDate: '',
			status: '',
			description: '',
			isViewed: true,
		  });
		  const updatedActivities = await getActivities();
		  setActivities(updatedActivities);
		} catch (error) {
		  console.error('Lỗi khi thêm hoạt động mới: ', error);
		}
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

		  {/* { 'HR' === user.type && ( */}
                        <div className="add-activity">
                            <button className="btn_add_activity" type="button" onClick={() => setShowAddPopup(true)}>
								<i class="fa-solid fa-plus"></i>
                            </button>
                        </div>
		  {/* )} */}
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

				{showAddPopup && (
			<div className="popup-overlay">
				<div className="popup-content_add">
				<div className="popup-header_add">
					<h3 className="popup-title_add">Tạo hoạt động</h3>
				</div>
				<div className="popup-body_add">
				<form>
					<div className="form-group_add">
					<label>Tên hoạt động:</label>
					<input
						type="text"
						value={newActivity.activityName}
						onChange={(e) =>
						setNewActivity({ ...newActivity, activityName: e.target.value })
						}
					/>
					</div>

					<div className="form-group_add">
					<label>Loại hoạt động:</label>
					<select
						value={newActivity.activityType}
						onChange={(e) =>
						setNewActivity({ ...newActivity, activityType: e.target.value })
						}
					>
						<option>Thể thao</option>
						<option>Hội thảo</option>
						<option>Khóa học</option>
					</select>
					</div>

					<div className="form-group_add">
					<label>Số người tham gia:</label>
					<input
						type="number"
						value={newActivity.numberOfParticipants}
						onChange={(e) =>
						setNewActivity({
							...newActivity,
							numberOfParticipants: e.target.value,
						})
						}
					/>
					</div>

					<div className="form-group_add">
					<label>Ngày bắt đầu:</label>
					<input
						type="date"
						value={newActivity.startDate}
						onChange={(e) =>
						setNewActivity({ ...newActivity, startDate: e.target.value })
						}
					/>
					</div>

					<div className="form-group_add">
					<label>Ngày kết thúc:</label>
					<input
						type="date"
						value={newActivity.endDate}
						onChange={(e) =>
						setNewActivity({ ...newActivity, endDate: e.target.value })
						}
					/>
					</div>

					<div className="form-group_add">
					<label>Mở đăng ký:</label>
					<input
						type="date"
						value={newActivity.registrationOpenDate}
						onChange={(e) =>
						setNewActivity({
							...newActivity,
							registrationOpenDate: e.target.value,
						})
						}
					/>
					</div>

					<div className="form-group_add">
					<label>Đóng đăng ký:</label>
					<input
						type="date"
						value={newActivity.registrationCloseDate}
						onChange={(e) =>
						setNewActivity({
							...newActivity,
							registrationCloseDate: e.target.value,
						})
						}
					/>
					</div>

					{/* <div className="form-group_add">
					<label>Trạng thái:</label>
					<input
						type="text"
						value={newActivity.status}
						onChange={(e) =>
						setNewActivity({ ...newActivity, status: e.target.value })
						}
					/>
					</div> */}

					<div className="form-group_add">
					<label>Có hiển thị hay không:</label>
					<input
						type="checkbox"
						checked={newActivity.isViewed}
						onChange={(e) =>
						setNewActivity({ ...newActivity, isViewed: e.target.checked })
						}
					/>
					</div>

					<div className="form-group_add">
					<label>Mô tả:</label>
					<textarea
						value={newActivity.description}
						onChange={(e) =>
						setNewActivity({ ...newActivity, description: e.target.value })
						}
					/>
					</div>
				</form>
			</div>
				<div className="popup-footer_add">
					<button className="btn-form" onClick={handleAddActivity}>
					Xác nhận
					</button>
					<button
					className="btn-form cancel"
					onClick={() => setShowAddPopup(false)}
					>
					Hủy
					</button>
				</div>
				</div>
			</div>
			)}


		</div>
	  );
};

export default ViewActivities;


