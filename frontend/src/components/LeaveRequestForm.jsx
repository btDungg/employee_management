import React, { useState, useEffect } from 'react';
import { getListDayOffType } from '../apis/api';
import '../../public/css/LeaveRequestForm.css';

const LeaveRequestForm = ({ onCommit }) => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [dayOffType, setDayOffType] = useState('');
    const [reason, setReason] = useState('');
    const [dayOffTypes, setDayOffTypes] = useState([]);
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


    const handleSubmit = (e) => {
        e.preventDefault();
        if (startDate > endDate) {
          setSubmitWarning('Ngày bắt đầu phải nhỏ hơn ngày kết thúc!');
          return;
        };
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
                    <label htmlFor="endDate">Ngày bắt đầu</label>
                    <input
                        type="date"
                        id="endDate"
                        value={endDate}
                        onChange={(e) => setEndDate(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="dayOffType">Loại thay đổi</label>
                    <select
                        id="dayOffType"
                        value={dayOffType}
                        onChange={(e) => setDayOffType(e.target.value)}
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