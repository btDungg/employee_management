import React, { useState } from 'react';
import '../../public/css/ChangePointsForm.css';

const SendLeaveRequest = ({ onCommit }) => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [dayOffType, setDayOffType] = useState('');
    const [reason, setReason] = useState('');


    const handleSubmit = (e) => {
        e.preventDefault();
        if (startDate > endDate) {
          setSubmitWarning('Ngày bắt đầu phải nhỏ hơn ngày kết thúc!');
          return;
      }
        onCommit({
            startDate,
            endDate,
            dayOffType,
            reason,
        });
    };

    return (
        <div className="change-points-form">
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
                        <option value='Co luong'>Có lương</option>
                        <option value='Khong luong'>Không lương</option>
                        <option value='Nghi thai san'>Nghỉ thai sản</option>
                        <option value='Nghi benh'>Nghỉ bệnh</option>
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
                <button className="btn" type="submit">Xác nhận</button>
            </form>
        </div>
    );
};

export default SendLeaveRequest;