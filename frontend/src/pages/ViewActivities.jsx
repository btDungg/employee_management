import React, { useState, useEffect } from 'react';
import { getActivities, addActivity, getEmployeeDetailsByEmail } from '../apis/api';
import { useNavigate } from 'react-router-dom';
import '../../public/css/ActivityPage.css';

const ViewActivities = ({ onCommit }) => {
  const [user, setUser] = useState(null);
  const [activities, setActivities] = useState([]);
  const [name, setName] = useState('');
  const navigate = useNavigate();
  const [searchWarning, setSearchWarning] = useState('');
  const [submitWarning, setSubmitWarning] = useState('');
  const [showAddPopup, setShowAddPopup] = useState(false);
  const [newActivity, setNewActivity] = useState({
    activityName: '',
    activityType: 'Thể thao',
    numberOfParticipants: 1,
    startDate: '',
    endDate: '',
    registrationOpenDate: '',
    registrationCloseDate: '',
    status: 'Opened',
    description: '',
    isViewed: true,
  });

  // Các trạng thái lỗi cho từng trường
  const [errors, setErrors] = useState({
    activityNameError: '',
    numberOfParticipantsError: '',
    startDateError: '',
    endDateError: '',
    registrationOpenDateError: '',
    registrationCloseDateError: '',
  });

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const userDetails = await getEmployeeDetailsByEmail();
        setUser(userDetails);
        console.log('User details:', userDetails);
      } catch (err) {
        console.error('Error fetching user details:', err);
        setSearchWarning('Không thể tải thông tin người dùng.');
      }
    };

    fetchUserDetails();
  }, []);

  const handleSearch = async () => {
    const activity = await getActivities(name);
    setActivities(activity);
    if (activity[0] == null) setSearchWarning('Không tồn tại hoạt động có tên ' + name);
    else setSearchWarning('');
  };

  useEffect(() => {
    const getListActivities = async () => {
      const data = await getActivities();
      setActivities(data);
    };
    getListActivities();
  }, []);

  const handleDetail = (activity) => {
    navigate(`/personal-activity/${activity.activityId}`);
  };

  const handleAddActivity = async () => {
	let isValid = true;
  
	// Kiểm tra các trường và cập nhật trạng thái lỗi
	const newErrors = {
	  activityNameError: '',
	  numberOfParticipantsError: '',
	  startDateError: '',
	  endDateError: '',
	  registrationOpenDateError: '',
	  registrationCloseDateError: '',
	};
  
	if (!newActivity.activityName) {
	  newErrors.activityNameError = 'Tên hoạt động là bắt buộc';
	  isValid = false;
	}
  
	if (!newActivity.numberOfParticipants || newActivity.numberOfParticipants <= 0) {
	  newErrors.numberOfParticipantsError = 'Số người tham gia phải lớn hơn 0';
	  isValid = false;
	}
  
	if (!newActivity.startDate) {
	  newErrors.startDateError = 'Ngày bắt đầu là bắt buộc';
	  isValid = false;
	}
  
	if (!newActivity.endDate) {
	  newErrors.endDateError = 'Ngày kết thúc là bắt buộc';
	  isValid = false;
	}
  
	if (!newActivity.registrationOpenDate) {
	  newErrors.registrationOpenDateError = 'Ngày mở đăng ký là bắt buộc';
	  isValid = false;
	}
  
	if (!newActivity.registrationCloseDate) {
	  newErrors.registrationCloseDateError = 'Ngày đóng đăng ký là bắt buộc';
	  isValid = false;
	}
  
	// Kiểm tra thứ tự ngày tháng
	if (new Date(newActivity.registrationOpenDate) > new Date(newActivity.registrationCloseDate)) {
	  newErrors.registrationCloseDateError = 'Ngày đóng đăng ký phải sau hoặc bằng ngày mở đăng ký';
	  isValid = false;
	}
  
	if (new Date(newActivity.registrationCloseDate) > new Date(newActivity.startDate)) {
	  newErrors.startDateError = 'Ngày bắt đầu phải sau hoặc bằng ngày đóng đăng ký';
	  isValid = false;
	}
  
	if (new Date(newActivity.startDate) > new Date(newActivity.endDate)) {
	  newErrors.endDateError = 'Ngày kết thúc phải sau hoặc bằng ngày bắt đầu';
	  isValid = false;
	}
  
	// Cập nhật trạng thái lỗi
	setErrors(newErrors);

    if (isValid) {
      try {
        const activityData = {
          name: newActivity.activityName,
          type: newActivity.activityType,
          startDate: newActivity.startDate,
          endDate: newActivity.endDate,
          numberOfParticipants: parseInt(newActivity.numberOfParticipants, 10),
          registrationOpenDate: newActivity.registrationOpenDate,
          registrationCloseDate: newActivity.registrationCloseDate,
          status: true,
          description: newActivity.description,
          isViewed: newActivity.isViewed,
        };
        console.log('activityData: ', activityData);
        await addActivity(activityData);
        setShowAddPopup(false);
        setNewActivity({
          activityName: '',
          activityType: 'Thể thao',
          numberOfParticipants: 1,
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
    }
  };

  return (
    <div className="activity-page">
      <h2 className="mb-4">Hoạt động công ty</h2>
      <div className="search-container">
        <input
          type="text"
          className="search-bar"
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Tên hoạt động"
        />
        <button className="btn" type="button" onClick={handleSearch}>
          Tìm kiếm
        </button>
      </div>
      {searchWarning && <div className="search-warning">{searchWarning}</div>}

      {user && user.type === 'HR' && (
        <div className="add-activity">
          <button className="btn_add_activity" type="button" onClick={() => setShowAddPopup(true)}>
            <i className="fa-solid fa-plus"></i>
          </button>
        </div>
      )}

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
				{errors.activityNameError && <div className="error">{errors.activityNameError}</div>}
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
                      setNewActivity({ ...newActivity, numberOfParticipants: e.target.value })
                    }
                  />
                  
                </div>
				{errors.numberOfParticipantsError && <div className="error">{errors.numberOfParticipantsError}</div>}
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
				{errors.startDateError && <div className="error">{errors.startDateError}</div>}
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
				{errors.endDateError && <div className="error">{errors.endDateError}</div>}
                <div className="form-group_add">
                  <label>Mở đăng ký:</label>
                  <input
                    type="date"
                    value={newActivity.registrationOpenDate}
                    onChange={(e) =>
                      setNewActivity({ ...newActivity, registrationOpenDate: e.target.value })
                    }
                  />
                  
                </div>
				{errors.registrationOpenDateError && <div className="error">{errors.registrationOpenDateError}</div>}
                <div className="form-group_add">
                  <label>Đóng đăng ký:</label>
                  <input
                    type="date"
                    value={newActivity.registrationCloseDate}
                    onChange={(e) =>
                      setNewActivity({ ...newActivity, registrationCloseDate: e.target.value })
                    }
                  />
                  
                </div>
				{errors.registrationCloseDateError && <div className="error">{errors.registrationCloseDateError}</div>}
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
              <button className="btn-form cancel" onClick={() => setShowAddPopup(false)}>
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
