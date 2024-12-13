import React, { useState, useEffect } from 'react';
import { getListDayOffType } from '../apis/api';
import '../../public/css/LeaveRequestForm.css';

const LeaveRequestForm = ({ onCommit, myDayOff }) => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [dayOffType, setDayOffType] = useState('');
    const [reason, setReason] = useState('');
    const [dayOffTypes, setDayOffTypes] = useState([]);
    const [remainingDays, setRemainingDays] = useState(0);
    const [submitWarning, setSubmitWarning] = useState('');
    const [isEndDateDisabled, setIsEndDateDisabled] = useState(false);

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

    // Theo dõi thay đổi của startDate và cập nhật endDate nếu yêu cầu nghỉ nửa ngày
    useEffect(() => {
        if (isEndDateDisabled && startDate) {
            setEndDate(startDate);
        }
    }, [startDate, isEndDateDisabled]);

    // Tính số ngày yêu cầu nghỉ
    const calculateDayOffRequest = (start, end) => {
        const startDate = new Date(start);
        const endDate = new Date(end);
        const currentDate = new Date();
        if (startDate.getFullYear() < currentDate.getFullYear())
            return null;
        //Nếu yêu cầu nửa ngày thì cố định số ngày yêu cầu nghỉ là 0.5
        if (isEndDateDisabled)
            return 0.5;
        const days = endDate.getTime() - startDate.getTime();
        return Math.ceil(days / (1000 * 60 * 60 * 24)) + 1; // Chuyển đổi mili-giây sang số ngày
    };

    const handleDayOffTypeChange = (e) => {
        const selectedType = e.target.value;
        setDayOffType(selectedType);

        // Tìm loại ngày nghỉ trong danh sách và cập nhật số ngày còn lại
        const selectedTypeData = myDayOff.find(item => item.typeName === selectedType);
        if (selectedTypeData) {
            setRemainingDays(selectedTypeData.remainingDays);
        } else {
            setRemainingDays(null); // Không tìm thấy loại ngày nghỉ
        }
        
        if (selectedType.toLowerCase().includes('nửa ngày')) {
            setIsEndDateDisabled(true);
            setEndDate(startDate); // Đặt ngày kết thúc bằng ngày bắt đầu
        } else {
            setIsEndDateDisabled(false);
        }
    };
    

    const handleSubmit = (e) => {
        e.preventDefault();

        if (startDate > endDate) {
          setSubmitWarning('Ngày bắt đầu phải nhỏ hơn ngày kết thúc!');
          setTimeout(() => {
            setSubmitWarning('');
        }, 2000);
          return;
        };
        const requestDays = calculateDayOffRequest(startDate, endDate);
        if (requestDays == null)
        {
            setSubmitWarning('Vui lòng chọn ngày nghỉ trong năm hiện tại!');
            setTimeout(() => {
                setSubmitWarning('');
            }, 2000);
            return;
        }
        if (remainingDays === null)
        {
            setSubmitWarning('Không có dữ liệu về số ngày nghỉ của bạn với loại ngày nghỉ này!');
            setTimeout(() => {
                setSubmitWarning('');
            }, 2000);
            return;
        }
        if (requestDays > remainingDays) {
            setSubmitWarning("Số ngày yêu cầu vượt quá số ngày nghỉ còn lại!");
            setTimeout(() => {
                setSubmitWarning('');
            }, 2000);
            return;
        }
        setSubmitWarning('');
        onCommit({
            startDate,
            endDate,
            requestDays,
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
                        disabled={isEndDateDisabled} // Vô hiệu hóa nếu loại "nửa ngày"
                        className={isEndDateDisabled ? 'disabled' : ''} // Áp dụng class nếu cần thêm
                        required
                    />
                </div>
                {calculateDayOffRequest(startDate, endDate) > 0 && <div className="form-group">
                    <label htmlFor="endDate">Số ngày nghỉ</label>
                    <p>{calculateDayOffRequest(startDate, endDate)}</p>
                </div>}
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
                {calculateDayOffRequest(startDate, endDate) > 0 && remainingDays > 0 && <div className="form-group">
                    <label htmlFor="endDate">Số ngày nghỉ còn lại</label>
                    <p>{remainingDays - calculateDayOffRequest(startDate, endDate)}</p>
                
                </div>}
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