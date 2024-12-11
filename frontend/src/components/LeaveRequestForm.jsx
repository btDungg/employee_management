import React, { useState, useEffect } from 'react';
import { getListDayOffType } from '../apis/api';
import '../../public/css/LeaveRequestForm.css';

const LeaveRequestForm = ({ onCommit, myDayOff }) => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [dayOffType, setDayOffType] = useState('');
    const [reason, setReason] = useState('');
    const [dayOffTypes, setDayOffTypes] = useState([]);
    const [remainingDays, setRemainingDays] = useState(null);
    const [submitWarning, setSubmitWarning] = useState('');

    // Gọi API để lấy danh sách các loại ngày nghỉ
    useEffect(() => {
        const fetchDayOffTypes = async () => {
            try {
                const types = await getListDayOffType();
                setDayOffTypes(types); // Cập nhật danh sách vào state
            } catch (error) {
                console.error('Error fetching day-off types:', error);
            }
        };

        fetchDayOffTypes();
    }, []);


    const calculateDayOffRequest = (start, end) => {
        const startDate = new Date(start);
        const endDate = new Date(end);

        const days = endDate.getTime() - startDate.getTime();
        return Math.ceil(days / (1000 * 60 * 60 * 24)); // Chuyển đổi mili-giây sang số ngày
    };

    const handleDayOffTypeChange = (e) => {
        const selectedType = e.target.value;
        setDayOffType(selectedType);

        // Tìm loại ngày nghỉ trong danh sách và cập nhật số ngày còn lại
        const selectedTypeData = myDayOff.find(item => item.typeName === selectedType);
        console.log(selectedTypeData);
        if (selectedTypeData) {
            setRemainingDays(selectedTypeData.remainingDays);
        } else {
            setRemainingDays(null); // Không tìm thấy loại ngày nghỉ
        }
    };
    
    const handleSubmit = (e) => {
        e.preventDefault();
        if (startDate > endDate) {
          setSubmitWarning('Ngày bắt đầu phải nhỏ hơn ngày kết thúc!');
          return;
        };
        const requestedDays = calculateDayOffRequest(startDate, endDate);

        if (remainingDays !== null && requestedDays > remainingDays) {
            alert("Số ngày yêu cầu vượt quá số ngày nghỉ còn lại!");
            return;
        }

        setSubmitWarning('');
        onCommit({
            startDate,
            endDate,
            dayOffType,
            reason,
        });
    };

    return (
        <div className="leave-request-form">
            <h2 >Yêu cầu nghỉ phép</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="startDate">Ngày bắt đầu</label>
                    <input
                        type="date"
                        id="startDate"
                        value={startDate}
                        onChange={(e) => setStartDate(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="endDate">Ngày kết thúc</label>
                    <input
                        type="date"
                        id="endDate"
                        value={endDate}
                        onChange={(e) => setEndDate(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="endDate">Số ngày nghỉ</label>
                    <p>{calculateDayOffRequest(startDate, endDate)}</p>
                
                </div>
                <div className="form-group">
                    <label htmlFor="dayOffType">Loại thay đổi</label>
                    <select
                        id="dayOffType"
                        value={dayOffType}
                        onChange={handleDayOffTypeChange}
                        required
                    >
                        <option value="">Chọn loại</option> 
                        {dayOffTypes.map((type) => (
                            <option key={type.type_name} value={type.type_name}>
                                {type.type_name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="endDate">Số ngày nghỉ còn lại</label>
                    <p>{remainingDays - calculateDayOffRequest(startDate, endDate)}</p>
                
                </div>
                <div className="form-group">
                    <label htmlFor="reason">Lý do</label>
                    <textarea
                        id="reason"
                        value={reason}
                        onChange={(e) => setReason(e.target.value)}

                    />
                </div>
                {submitWarning && <div className="submit-warning">{submitWarning}</div>}
                <button className="btn" type="submit">Xác nhận</button>
            </form>
        </div>
    );
};

export default LeaveRequestForm;